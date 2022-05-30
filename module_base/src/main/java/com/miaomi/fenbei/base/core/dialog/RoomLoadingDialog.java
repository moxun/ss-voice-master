package com.miaomi.fenbei.base.core.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.miaomi.fenbei.base.R;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;

public class RoomLoadingDialog extends Dialog {
    private Context context;
    private SVGAImageView loadingIv;


    public RoomLoadingDialog(@NonNull Context context) {
        super(context, R.style.common_dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_loading_dialog_room);
        setCancelable(false);
        loadingIv  = findViewById(R.id.iv_svga);
        playSvgaAnim(loadingIv,"km_dialog_loading_anim.svga");
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        loadingIv.stopAnimation();
        loadingIv.clearAnimation();
    }

    public void loginDismiss(){
        if(context instanceof Activity) {
            if(!((Activity)context).isFinishing() && !((Activity)context).isDestroyed())
                dismiss();
        } else
            dismiss();

    }

    private void playSvgaAnim(final SVGAImageView view, String name) {
        SVGAParser parser =new SVGAParser(context);
        parser.decodeFromAssets(name, new SVGAParser.ParseCompletion() {
            @Override
            public void onComplete(@NotNull SVGAVideoEntity svgaVideoEntity) {
                SVGADrawable drawable =new SVGADrawable(svgaVideoEntity);
                view.setImageDrawable(drawable);
                view.startAnimation();
            }

            @Override
            public void onError() {

            }
        });

    }
}