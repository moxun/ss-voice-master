package com.miaomi.fenbei.base.core.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.miaomi.fenbei.base.R;

public class LoadingDialog extends Dialog {
    private Context context;
    private ImageView loadingIv;
    private AnimationDrawable mVolumeAnim;

    public LoadingDialog(@NonNull Context context) {
        super(context,R.style.common_dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_loading_dialog);
        setCancelable(false);
        loadingIv  = findViewById(R.id.iv_loading);
//        loadingIv.setImageResource(R.drawable.anim_loading);
        mVolumeAnim = (AnimationDrawable) loadingIv.getDrawable();
        mVolumeAnim.start();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mVolumeAnim != null && mVolumeAnim.isRunning()){
            mVolumeAnim.stop();
        }
    }

    public void loginDismiss(){
        if(context instanceof Activity) {
            if(!((Activity)context).isFinishing() && !((Activity)context).isDestroyed())
                dismiss();
        } else
            dismiss();

    }
}
