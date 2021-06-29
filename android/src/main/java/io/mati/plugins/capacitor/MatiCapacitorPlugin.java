package io.mati.plugins.capacitor;

import android.content.Intent;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getmati.mati_sdk.Metadata;
import com.getmati.mati_sdk.MatiSdk;
import static android.app.Activity.RESULT_OK;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

@NativePlugin
public class MatiCapacitorPlugin extends Plugin {

    @PluginMethod
    public void showMatiFlow(PluginCall call) {
        final String clientId = call.getString("clientId");
        final String flowId = call.getString("flowId");
        final JSONObject metadata = call.getObject("metadata", null);

        bridge.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MatiSdk.INSTANCE.startFlow(getActivity(),
                        clientId,
                        flowId,
                        convertToMetadata(metadata));
            }
        });

        call.success();
    }

    public Metadata convertToMetadata(final JSONObject metadata)
    {
        if (metadata == null)
            return null;

        Metadata.Builder metadataBuilder = new Metadata.Builder();
        Iterator<String> keys = metadata.keys();
        String key;
        while(keys.hasNext()) {
            key = keys.next();
            try {
                metadataBuilder.with(key, metadata.get (key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return metadataBuilder.build();
    }

    @Override
    protected void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == MatiSdk.REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                bridge.triggerWindowJSEvent("Verification success", String.format("{ 'login success': %s }", data.getStringExtra(MatiSdk.ARG_VERIFICATION_ID)));
            } else {
                bridge.triggerWindowJSEvent("Verification cancelled");
            }
        }
    }
}