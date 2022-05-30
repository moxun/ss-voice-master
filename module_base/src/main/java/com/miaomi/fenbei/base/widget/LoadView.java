package com.miaomi.fenbei.base.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.miaomi.fenbei.base.R;

public class LoadView extends androidx.appcompat.widget.AppCompatImageView {
    private Context context;
    private Animation animation;
    public LoadView(Context context) {
        super(context);
        init(context);
    }

    public LoadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context){
        this.context  = context;
//        startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_loading));
        if (animation == null){
            animation = AnimationUtils.loadAnimation(context, R.anim.anim_loading);
        }
    }
    public void startAnimation(){
        if (animation == null){
            return;
        }
        startAnimation(animation);
    }


//    public  void clearAnimation(){
//        clearAnimation();
//    }
}
