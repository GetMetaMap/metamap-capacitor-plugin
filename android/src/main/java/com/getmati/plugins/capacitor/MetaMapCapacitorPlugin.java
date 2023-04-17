package com.getmati.plugins.capacitor;

import android.app.Activity;
import android.content.Intent;
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
import org.json.JSONException;


import java.util.Iterator;

import android.graphics.Color;

@CapacitorPlugin(name = "MetaMapCapacitor")
public class MetaMapCapacitorPlugin extends Plugin {

    @SuppressWarnings("unused")
    @PluginMethod
    public void showMetaMapFlow(PluginCall call) {
        Log.e("MetaMapCapacitorPlugin", "showMetaMapFlow");
        bridge.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                final String clientId = call.getString("clientId");
                final String flowId = call.getString("flowId");
                final JSONObject metadata = call.getObject("metadata", new JSObject());
                try {
                    if (clientId == null) {
                        Log.e("MetaMapCapacitorPlugin", "\"Client Id should be not null\"");
                    } else {

                        Iterator<String> keys = metadata.keys();

                        Metadata.Builder metadataBuilder = new Metadata.Builder();

                        while (keys.hasNext()) {
                            String key = keys.next();
                            try {
                                if (key.toLowerCase().contains("color")) {
                                    String hexColor = (String) metadata.get(key);
                                    int color = Color.parseColor(hexColor);
                                    if (hexColor.length() == 9) {
                                        color = Color.argb(Color.blue(color), Color.alpha(color), Color.red(color), Color.green(color));
                                    }
                                    metadataBuilder.with(key, color);
                                } else {
                                    metadataBuilder.with(key, metadata.get(key));
                                }
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                        metadataBuilder.with("sdkType", "capacitor");
                        Metadata data = metadataBuilder.build();




                        Intent flowIntent = MetamapSdk.INSTANCE.createFlowIntent(bridge.getActivity(), clientId, flowId, data);
                        startActivityForResult(call, flowIntent, "callback");
                    }
                } catch(Exception excepion) {
                    call.reject("Verification failed");
                }
            }
        });
    }

    @Override
    protected void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        super.handleOnActivityResult(requestCode, resultCode, data);
        Log.e("MetaMapCapacitorPlugin", "WILL NOT BE CALLED");
    }

    @SuppressWarnings("unused")
    @ActivityCallback
    public void callback(PluginCall call, ActivityResult activityResult) {
        if(activityResult.getResultCode() == Activity.RESULT_OK && activityResult.getData() != null) {
            JSObject result = new JSObject();
            String identityId = activityResult.getData().getStringExtra("ARG_IDENTITY_ID");
            String verificationID = activityResult.getData().getStringExtra("ARG_VERIFICATION_ID");
            result.put("identityId", identityId);
            result.put("verificationID", verificationID);
            call.resolve(result);
            Log.e("MetaMapCapacitorPlugin", "Activity.RESULT_OK");
        } else {
            call.reject("verificationCancelled");
            Log.e("MetaMapCapacitorPlugin", "Activity.RESULT_CANCELLED");
        }
    }
}
