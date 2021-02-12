package io.mati.plugins.capacitor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.matilock.mati_kyc_sdk.LoginError;
import com.matilock.mati_kyc_sdk.LoginResult;
import com.matilock.mati_kyc_sdk.Mati;
import com.matilock.mati_kyc_sdk.MatiCallback;
import com.matilock.mati_kyc_sdk.MatiCallbackManager;
import com.matilock.mati_kyc_sdk.MatiLoginButton;
import com.matilock.mati_kyc_sdk.MatiLoginManager;
import com.matilock.mati_kyc_sdk.Metadata;

import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;

@NativePlugin
public class MatiCapacitorPlugin extends Plugin implements MatiCallback {

    private MatiCallbackManager mCallbackManager = MatiCallbackManager.createNew();
    public static String SPEC_FLOW_ID = "";

    @PluginMethod
    public void initialization(PluginCall call) {
        final String clientId = call.getString("clientId");
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Mati.init(bridge.getActivity().getBaseContext(), clientId);
                MatiLoginManager.getInstance().registerCallback(mCallbackManager, MatiCapacitorPlugin.this);
            }
        });
        call.success();
    }

    @PluginMethod
    public void setParams(PluginCall call) {
        SPEC_FLOW_ID = call.getString("flowId");
        call.success();
    }

    @PluginMethod
    public void showFlow(PluginCall call) {
        MatiLoginButtonLauncher.weakReferenceCallbackManager = new WeakReference<>(mCallbackManager);

        Intent intent = new Intent(getContext(), MatiLoginButtonLauncher.class);
        getActivity().startActivity(intent);

        call.success();
    }

    @Override
    public void onCancel() {
        bridge.triggerWindowJSEvent("mfKYCLoginCancelled");
    }

    @Override
    public void onError(@Nullable LoginError loginError) {
        bridge.triggerWindowJSEvent("mfKYCLoginError", String.format("{ 'error': %s }", loginError.getMessage()));
    }

    @Override
    public void onSuccess(@Nullable LoginResult loginResult) {
        bridge.triggerWindowJSEvent("mfKYCLoginSuccess", String.format("{ 'login success': %s }", loginResult.getIdentityId()));
    }
}



