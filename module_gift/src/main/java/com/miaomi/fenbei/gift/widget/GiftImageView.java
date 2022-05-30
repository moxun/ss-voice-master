package com.miaomi.fenbei.gift.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

public class GiftImageView extends androidx.appcompat.widget.AppCompatImageView {
//    private ValueAnimator animator;
    private boolean isRunning;
    private AnimatorSet animatorSet;
    public GiftImageView(Context context) {
        super(context);
    }

    public GiftImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GiftImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
//    private void init(){
//        if (animator ==null){
//            animator = ValueAnimator.ofFloat(1,0.5f,1);
//        }
//
//        animator.setDuration(2000);//播放时长
//        animator.setStartDelay(200);//延迟播放
//        animator.setRepeatCount(10);//重放次数
//        animator.setRepeatMode(ValueAnimator.RESTART);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float currentValue = (float) animation.getAnimatedValue();
//                setScaleX(currentValue);
//                setScaleY(currentValue);
//            }
//        });
//
//    }



//    @Override
//    public boolean performClick() {
//        if (!isSelected()){
//            ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 0.8f, 1.2f);
//            ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 0.8f, 1.2f);
//            AnimatorSet setAnima =new AnimatorSet();
//            setAnima.play(scaleX).with(scaleY);
//            setAnima.setDuration(500);
//            setAnima.start();
//        }
//        return super.performClick();
//    }

//    @Override
//    public void setSelected(boolean selected) {
//        super.setSelected(selected);
//        if (selected){
//            ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 0.8f, 1.2f);
//            ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 0.8f, 1.2f);
//            AnimatorSet setAnima =new AnimatorSet();
//            setAnima.play(scaleX).with(scaleY);
//            setAnima.setDuration(500);
//            setAnima.start();
//        }
//    }

    public void start(){
//        setScaleX(1);
//        setScaleY(1);
//        animator.start();

        if (isRunning){
            return;
        }
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 0.8f, 1.2f,1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 0.8f, 1.2f,1f);
        animatorSet = new AnimatorSet();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isRunning = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isRunning = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.play(scaleX).with(scaleY);
        animatorSet.setDuration(500);
        animatorSet.start();
    }

    public void cancel(){
        isRunning = false;
        if (animatorSet != null){
            animatorSet.cancel();
        }
//        setScaleX(1);
//        setScaleY(1);
//        if (animator.isStarted()){
//            animator.cancel();
//        }
    }

//    @Override
//    protected void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//        animator.cancel();
//        animator = null;
//    }
}
