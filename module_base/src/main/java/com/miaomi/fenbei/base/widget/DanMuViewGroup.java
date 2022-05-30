package com.miaomi.fenbei.base.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.JustifyContent;
import com.miaomi.fenbei.base.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DanMuViewGroup extends FlexboxLayout {
    private Handler mHandler;
    private Context mContext;
    private int width;
    private boolean isRunning = false;
    private ConcurrentLinkedQueue<View> mQueue = new ConcurrentLinkedQueue<>();
    private List<ObjectAnimator> objectAnimators = new ArrayList<>();

    public DanMuViewGroup(@NonNull Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public DanMuViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public DanMuViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init(){
        mHandler = new Handler(Looper.getMainLooper());
        width = ScreenUtil.getScreenWidth();
        setFlexWrap(FlexWrap.WRAP);
        setJustifyContent(JustifyContent.FLEX_START);
        setFlexDirection(FlexDirection.COLUMN);
    }


    public void addView(final String face, final String content){
        if (mQueue.size() < 12){
            int index = mQueue.size();
            final DanMuView view = new DanMuView(mContext);
            view.setContent(content);
            view.loadFace(face);
            view.setColor(index);
            mQueue.offer(view);
            addView(view);
            view.setVisibility(INVISIBLE);
        }

//        view.setAnimation(animation);
//        animation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                removeView(view);
//                addView(face,content);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        PropertyValuesHolder xProperty = PropertyValuesHolder.ofFloat("x", width, -width*2/3);
//        final ObjectAnimator animEnd = ObjectAnimator.ofPropertyValuesHolder(view, xProperty);
//        animEnd.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                super.onAnimationStart(animation);
//                view.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
////                removeView(view);
//                animEnd.setStartDelay(0);
//                animEnd.start();
////                addView(face,content);
//            }
//        });
//        animEnd.setInterpolator(new LinearInterpolator());
//        animEnd.setStartDelay(index*200);
//        animEnd.setDuration(5000);
////        animatorSet.play(startAnim).before(animEnd);
////        animatorSet.setStartDelay(startDelay);
////        animatorSet.start();
//        animEnd.start();
    }

    public void startAnim(){
        if (isRunning){
            return;
        }
        isRunning = true;
        mHandler.post(mFlutterTask);
    }

    private Runnable mFlutterTask = new Runnable() {
        @Override
        public void run() {
            addAnimView();
            mHandler.postDelayed(this, 450);

        }
    };

    private void addAnimView(){
        if (mQueue.isEmpty())
            return;
        final View view = mQueue.poll();
        mQueue.offer(view);
        PropertyValuesHolder xProperty = PropertyValuesHolder.ofFloat("x", width, -width*2);
        final ObjectAnimator animEnd = ObjectAnimator.ofPropertyValuesHolder(view, xProperty);
        animEnd.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }
        });
        animEnd.setInterpolator(new LinearInterpolator());
        animEnd.setDuration(12000);
        animEnd.start();
        objectAnimators.add(animEnd);
    }

    public void pauseAnim(){
        for (ObjectAnimator objectAnimator:objectAnimators){
            objectAnimator.pause();
        }
    }
    public void resumeAnim(){
        for (ObjectAnimator objectAnimator:objectAnimators){
            objectAnimator.resume();
        }
    }



    public void clear(){
        isRunning = false;
        mQueue.clear();
        mHandler.removeCallbacksAndMessages(null);
        removeAllViews();
        for (ObjectAnimator objectAnimator:objectAnimators){
            objectAnimator.cancel();
        }
    }

}
