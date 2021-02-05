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

        Mati.init(getContext(), call.getString("clientId"));

        MatiLoginManager.getInstance().registerCallback(mCallbackManager, this);

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
        Intent intent = new Intent(getActivity(), MatiCapacitorPlugin.MatiLoginButtonLauncher.class);
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


    public static class MatiLoginButtonLauncher extends AppCompatActivity
    {
        public static WeakReference<MatiCallbackManager> weakReferenceCallbackManager;
        Handler mMainHandler ;
        MatiLoginButton mMatiLoginButton;
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(getView());
            mMainHandler = new Handler(this.getMainLooper());
            mMainHandler.post (new Runnable() {
                @Override
                public void run() {
                    mMatiLoginButton.performClick();
                }
            });

        }

        public View getView(){
            FrameLayout layout = new FrameLayout(this);
            layout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT));
            mMatiLoginButton = new MatiLoginButton(this);
            if (SPEC_FLOW_ID != null) {
                mMatiLoginButton.setMFlowId(SPEC_FLOW_ID);
            }
            mMatiLoginButton.setVisibility(View.INVISIBLE);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(100, 100);
            params.setMargins(-100, 0, 0, 0);
            mMatiLoginButton.setLayoutParams(params);
            layout.addView(mMatiLoginButton);

            return layout;
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(weakReferenceCallbackManager != null && weakReferenceCallbackManager.get() != null){
                if(weakReferenceCallbackManager.get().onActivityResult(requestCode, resultCode, data)){
                    this.finish();
                }
            }

        }
    }
}



