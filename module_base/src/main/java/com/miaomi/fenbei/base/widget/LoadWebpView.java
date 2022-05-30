package com.miaomi.fenbei.base.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.miaomi.fenbei.base.R;

public class LoadWebpView extends androidx.appcompat.widget.AppCompatImageView {
    private Animation animation;
    public LoadWebpView(Context context) {
        super(context);
        init(context);
    }

    public LoadWebpView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadWebpView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        if (animation == null){
            animation = AnimationUtils.loadAnimation(context, R.anim.anim_loading);
        }
        this.startAnimation(animation);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clearAnimation();
    }
}
