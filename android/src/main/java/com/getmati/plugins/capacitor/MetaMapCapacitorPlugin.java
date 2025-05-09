package com.getmati.plugins.capacitor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import androidx.activity.result.ActivityResult;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.metamap.metamap_sdk.MetamapSdk;
import com.metamap.metamap_sdk.Metadata;

import org.json.JSONObject;

import java.util.Iterator;

@CapacitorPlugin(name = "MetaMapCapacitor")
public class MetaMapCapacitorPlugin extends Plugin {

    private static final String TAG = "📲 MetaMapCapacitor";

    @PluginMethod
    public void showMetaMapFlow(PluginCall call) {
        Log.d(TAG, "🔔 showMetaMapFlow invoked");

        bridge.getActivity().runOnUiThread(() -> {
            String clientId = call.getString("clientId");
            String flowId = call.getString("flowId");
            JSONObject metadata = call.getObject("metadata", new JSObject());

            if (clientId == null || clientId.isEmpty()) {
                Log.e(TAG, "❌ Missing clientId");
                call.reject("Client Id should not be null or empty");
                return;
            }

            try {
                Metadata.Builder metadataBuilder = new Metadata.Builder();

                // Process metadata
                Iterator<String> keys = metadata.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    try {
                        Object value = metadata.get(key);
                        if (key.toLowerCase().contains("color") && value instanceof String) {
                            String hexColor = (String) value;
                            int color = Color.parseColor(hexColor);
                            if (hexColor.length() == 9) {
                                color = Color.argb(Color.blue(color), Color.alpha(color), Color.red(color), Color.green(color));
                            }
                            metadataBuilder.with(key, color);
                        } else {
                            metadataBuilder.with(key, value);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "⚠️ Error parsing metadata key: " + key, e);
                    }
                }

                metadataBuilder.with("sdkType", "capacitor");
                Metadata builtMetadata = metadataBuilder.build();

                // Create flow intent with verificationStarted callback
                Intent flowIntent = MetamapSdk.INSTANCE.createFlowIntent(
                        bridge.getActivity(),
                        clientId,
                        flowId,
                        builtMetadata,
                        null,
                        null,
                        (identityId, verificationId) -> {
                            Log.d(TAG, "🟡 verificationStarted: identityId=" + identityId + ", verificationId=" + verificationId);
                            JSObject startedResult = new JSObject();
                            startedResult.put("identityId", identityId != null ? identityId : "");
                            startedResult.put("verificationId", verificationId != null ? verificationId : "");
                            startedResult.put("status", "started");
                            notifyListeners("verificationCreated", startedResult);
                        }
                );

                Log.d(TAG, "🚀 Starting MetaMap flow...");
                startActivityForResult(call, flowIntent, "callback");

            } catch (Exception e) {
                Log.e(TAG, "🔥 Failed to start MetaMap flow", e);
                call.reject("Failed to start MetaMap flow: " + e.getMessage());
            }
        });
    }

    @ActivityCallback
    private void callback(PluginCall call, ActivityResult activityResult) {
        Intent data = activityResult.getData();
        if (data == null) {
            Log.e(TAG, "❗ MetaMap returned null data");
            call.reject("MetaMap result data is null");
            return;
        }

        String identityId = data.getStringExtra("ARG_IDENTITY_ID");
        String verificationId = data.getStringExtra("ARG_VERIFICATION_ID");

        JSObject result = new JSObject();
        result.put("identityId", identityId != null ? identityId : "");
        result.put("verificationId", verificationId != null ? verificationId : "");

        if (activityResult.getResultCode() == Activity.RESULT_OK) {
            Log.d(TAG, "✅ verificationSuccess: identityId=" + identityId + ", verificationId=" + verificationId);
            call.resolve(result);
        } else {
            result.put("status", "cancelled");
            Log.d(TAG, "❌ verificationCancelled: identityId=" + identityId + ", verificationId=" + verificationId);
            call.reject(
                    "Verification was cancelled by the user",
                    "verificationCancelled",
                    null,
                    result
            );
        }
    }
}