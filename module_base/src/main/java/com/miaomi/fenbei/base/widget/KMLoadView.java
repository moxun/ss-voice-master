package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.miaomi.fenbei.base.R;

import androidx.annotation.Nullable;

public class KMLoadView extends ImageView {
//    private CoffeeDrawable coffeeDrawable;
    private AnimationDrawable mVolumeAnim;
    public KMLoadView(Context context) {
        super(context);
        init();
    }

    public KMLoadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KMLoadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        setImageResource(R.drawable.anim_loading);
        mVolumeAnim = (AnimationDrawable) getDrawable();
        mVolumeAnim.start();
//        coffeeDrawable = CoffeeDrawable.create(this,50);
//        setBackground(coffeeDrawable);
//        coffeeDrawable.setProgress(1F);
//        coffeeDrawable.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mVolumeAnim != null && mVolumeAnim.isRunning()){
            mVolumeAnim.stop();
        }
    }
}
