package com.miaomi.fenbei.base.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class HeartView extends ImageView {
    public HeartView(Context context) {
        super(context);
        init();
    }

    public HeartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
//        setBackgroundResource(R.drawable.base_shape_icon_detail_heart);
    }

//    @Override
//    public boolean performClick() {
//        if (!isSelected()){
//            return super.performClick();
//        } else{
//            return false;
//        }
//        return super.performClick();
//    }

    private void showAnima() {
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 0.8f, 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 0.8f, 1f);
        AnimatorSet setAnima =new AnimatorSet();
        setAnima.play(scaleX).with(scaleY);
        setAnima.setDuration(500);
        setAnima.start();
    }

    public void success(){
        if (!isSelected()){
            showAnima();
            setSelected(true);
        }
    }
    public void faile(){
        if (isSelected()){
            showAnima();
            setSelected(false);
        }
    }
}
