package com.miaomi.fenbei.base.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.miaomi.fenbei.base.R;

import androidx.annotation.Nullable;

public class WordListSelectedView extends LinearLayout {
    public final static int WSV_SELECTED_MSG = 2;
    public final static int WSV_SELECTED_ALL = 1;
    public final static int WSV_SELECTED_GIFT = 3;
    //广场
    public final static int WSV_SELECTED_GC = 4;
    private ImageView allTv;
    private ImageView msgTv;
    private ImageView giftTv;
    private ImageView guangchangTv;
    private int crruentStatus = WSV_SELECTED_ALL;
    private boolean isShow;
    private boolean isAnim;

    public WordListSelectedView(Context context) {
        super(context);
        init(context);
    }

    public WordListSelectedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WordListSelectedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(HORIZONTAL);
        View view = LayoutInflater.from(context).inflate(R.layout.base_layout_word_list_select, this, true);

        allTv = view.findViewById(R.id.tv_all);
        allTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (crruentStatus != WSV_SELECTED_ALL) {
                    changeStatus(WSV_SELECTED_ALL);
                }
            }
        });
        msgTv = view.findViewById(R.id.tv_msg);
        msgTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (crruentStatus != WSV_SELECTED_MSG) {
                    changeStatus(WSV_SELECTED_MSG);
                }
            }
        });
        giftTv = view.findViewById(R.id.tv_gift);
        giftTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (crruentStatus != WSV_SELECTED_GIFT) {
                    changeStatus(WSV_SELECTED_GIFT);
                }
            }
        });
        guangchangTv = view.findViewById(R.id.tv_guangchang);
        guangchangTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (crruentStatus != WSV_SELECTED_GC) {
                    changeStatus(WSV_SELECTED_GC);
                }
            }
        });
        changeStatus(WSV_SELECTED_ALL);
    }

    private void changeStatus(int status) {
        crruentStatus = status;
        allTv.setSelected(false);
        msgTv.setSelected(false);
        giftTv.setSelected(false);
        guangchangTv.setSelected(false);
        if (status == WSV_SELECTED_ALL) {
            allTv.setSelected(true);
        }
        if (status == WSV_SELECTED_MSG) {
            msgTv.setSelected(true);
        }
        if (status == WSV_SELECTED_GIFT) {
            giftTv.setSelected(true);
        }
        if (status == WSV_SELECTED_GC) {
            guangchangTv.setSelected(true);
        }
        if (onSeletedListener != null) {
            onSeletedListener.onSeleted(status);
        }
    }

    OnSeletedListener onSeletedListener;

    public OnSeletedListener getOnSeletedListener() {
        return onSeletedListener;
    }

    public void setOnSeletedListener(OnSeletedListener onSeletedListener) {
        this.onSeletedListener = onSeletedListener;
    }

    public interface OnSeletedListener {
        void onSeleted(int type);
    }

    public void show() {
        if (!isShow) {
            setVisibility(View.VISIBLE);
        }
//        if (crruentStatus == WSV_SELECTED_ALL){
//            if (!isShow && !isAnim){
//                ObjectAnimator outTranslation = ObjectAnimator.ofFloat(allTv,"translationX",150, 0);
//                outTranslation.setDuration(150);
//
//                ObjectAnimator outTranslation1 = ObjectAnimator.ofFloat(msgTv,"translationX",150, 0);
//                outTranslation1.setDuration(150);
//                outTranslation1.setStartDelay(50);
//
//                ObjectAnimator outTranslation2 = ObjectAnimator.ofFloat(giftTv,"translationX",150, 0);
//                outTranslation2.setDuration(150);
//                outTranslation2.setStartDelay(100);
//
//                ObjectAnimator scaleX = ObjectAnimator.ofFloat(allTv,"scaleX",1, 0.8f,1);
//                scaleX.setInterpolator(new AccelerateDecelerateInterpolator());
//                scaleX.setDuration(100);
//                scaleX.setStartDelay(150);
//                ObjectAnimator scaleY = ObjectAnimator.ofFloat(allTv,"scaleY",1, 0.8f,1);
//                scaleY.setInterpolator(new AccelerateDecelerateInterpolator());
//                scaleY.setDuration(100);
//                scaleY.setStartDelay(150);
//
//                ObjectAnimator scaleX1 = ObjectAnimator.ofFloat(msgTv,"scaleX",1, 0.8f,1);
//                scaleX1.setInterpolator(new AccelerateDecelerateInterpolator());
//                scaleX1.setDuration(100);
//                scaleX1.setStartDelay(200);
//                ObjectAnimator scaleY1 = ObjectAnimator.ofFloat(msgTv,"scaleY",1, 0.8f,1);
//                scaleY1.setInterpolator(new AccelerateDecelerateInterpolator());
//                scaleY1.setDuration(100);
//                scaleY1.setStartDelay(200);
//
//                ObjectAnimator scaleX2 = ObjectAnimator.ofFloat(giftTv,"scaleX",1, 0.8f,1);
//                scaleX2.setInterpolator(new AccelerateDecelerateInterpolator());
//                scaleX2.setDuration(100);
//                scaleX2.setStartDelay(250);
//                ObjectAnimator scaleY2 = ObjectAnimator.ofFloat(giftTv,"scaleY",1, 0.8f,1);
//                scaleY2.setInterpolator(new AccelerateDecelerateInterpolator());
//                scaleY2.setDuration(100);
//                scaleY2.setStartDelay(250);
//
//                AnimatorSet animatorSet = new AnimatorSet();
//                animatorSet.play(outTranslation).with(outTranslation1).with(outTranslation2)
//                        .with(scaleX).with(scaleY)
//                        .with(scaleX1).with(scaleY1)
//                        .with(scaleX2).with(scaleY2);
//                animatorSet.addListener(new Animator.AnimatorListener() {
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//                        isAnim = true;
//                        setVisibility(View.VISIBLE);
//                        allTv.setTranslationX(150f);
//                        msgTv.setTranslationX(150f);
//                        giftTv.setTranslationX(150f);
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        isShow = true;
//                        isAnim =false;
//                        setVisibility(View.VISIBLE);
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animator animation) {
//
//                    }
//                });
//                animatorSet.start();
//            }
//        }
    }

    public void hide() {
//        if (crruentStatus == WSV_SELECTED_ALL){
//            if (isShow && !isAnim){
//                isShow = false;
//                ObjectAnimator outTranslation = ObjectAnimator.ofFloat(allTv,"translationX",0, 150);
//                outTranslation.setDuration(150);
//
//                ObjectAnimator outTranslation1 = ObjectAnimator.ofFloat(msgTv,"translationX",0, 150);
//                outTranslation1.setDuration(150);
//                outTranslation1.setStartDelay(50);
//
//
//                ObjectAnimator outTranslation2 = ObjectAnimator.ofFloat(giftTv,"translationX",0, 150);
//                outTranslation2.setDuration(150);
//                outTranslation2.setStartDelay(100);
//
//                AnimatorSet animatorSet = new AnimatorSet();
//                animatorSet.play(outTranslation).with(outTranslation1).with(outTranslation2);
//                animatorSet.addListener(new Animator.AnimatorListener() {
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//                        isAnim = true;
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        isShow = false;
//                        isAnim = false;
//                        if (crruentStatus == WSV_SELECTED_ALL){
//                            setVisibility(View.GONE);
//                        }
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animator animation) {
//
//                    }
//                });
//                animatorSet.start();
//            }
//        }
    }
}
