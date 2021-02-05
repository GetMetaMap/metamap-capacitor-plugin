package io.mati.plugins.capacitor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.matilock.mati_kyc_sdk.MatiCallbackManager;
import com.matilock.mati_kyc_sdk.MatiLoginButton;

import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;

public class MatiLoginButtonLauncher extends AppCompatActivity {
    private static final String SPEC_FLOW_ID = "";
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