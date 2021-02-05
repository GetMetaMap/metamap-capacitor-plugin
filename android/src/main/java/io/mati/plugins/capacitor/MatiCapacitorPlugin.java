package io.mati.plugins.capacitor;

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

@NativePlugin
public class MatiCapacitorPlugin extends Plugin implements MatiCallback {

    private MatiCallbackManager mCallbackManager = MatiCallbackManager.createNew();

    @PluginMethod
    public void initialization(PluginCall call) {

        Mati.init(getContext(), call.getString("clientId"));

        MatiLoginManager.getInstance().registerCallback(mCallbackManager, this);
        MatiLoginManager.getInstance().registerCallback(mCallbackManager, Mati);

        call.success();
    }

    @PluginMethod
    public void setParams(PluginCall call) {

        Mati.init(getContext(), call.getString("clientId"));

        MatiLoginManager.getInstance().registerCallback(mCallbackManager, this);
        MatiLoginManager.getInstance().registerCallback(mCallbackManager, Mati);

        call.success();
    }

    @PluginMethod
    public void showFlow(PluginCall call) {

        Mati.init(getContext(), call.getString("clientId"));

        MatiLoginManager.getInstance().registerCallback(mCallbackManager, this);
        MatiLoginManager.getInstance().registerCallback(mCallbackManager, Mati);

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



