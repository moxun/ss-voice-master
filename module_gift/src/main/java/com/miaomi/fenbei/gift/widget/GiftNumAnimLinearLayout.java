package com.miaomi.fenbei.gift.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.miaomi.fenbei.base.bean.LuckGiftNumbBean;

public class GiftNumAnimLinearLayout extends LinearLayout implements Animator.AnimatorListener {
    private AnimatorSet initNumScaleAnimatorSet; // 初始化礼物数字缩放动画
    private AnimatorSet numScaleAnimatorSet; // 实际礼物数字缩放动画
    Context context;

    public GiftNumAnimLinearLayout(Context context) {
        super(context);
        init(context);
    }

    public GiftNumAnimLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GiftNumAnimLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
//        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        initNumScaleAnimatorSet = new AnimatorSet();
        final ObjectAnimator scaleX = new ObjectAnimator();
        scaleX.setPropertyName("scaleX");
        scaleX.setFloatValues(1.8f, 0.8f, 1f);
//        scaleX.setInterpolator(bounceInterpolator);

        final ObjectAnimator scaleY = new ObjectAnimator();
        scaleY.setPropertyName("scaleY");
        scaleY.setFloatValues(1.8f, 0.8f, 1f);
//        scaleY.setInterpolator(bounceInterpolator);

        initNumScaleAnimatorSet.playTogether(scaleX, scaleY);
        initNumScaleAnimatorSet.setDuration(200);
        initNumScaleAnimatorSet.setTarget(this);

        // 预先往里面塞几个数字
        for (int i = 0; i <= 8; i++) {
            GiftNumAnimView numberView = new GiftNumAnimView(context);
            addView(numberView);
            numberView.setVisibility(GONE);
        }
    }

    private volatile int lastNumber;

    // 入口类
    public void addNumber(int count) {
        int type = LuckGiftNumbBean.SCALE_NUMB;
        if (count - lastNumber >= 9) {
            type = LuckGiftNumbBean.SCROOL_NUMB;
        }
        LuckGiftNumbBean giftNumbBean = new LuckGiftNumbBean(count, type);
        startAnimation(giftNumbBean);
        lastNumber = count;
    }

    public void reset() {
        for (int i = 0; i < getChildCount(); i++) {
            GiftNumAnimView giftNumberView = (GiftNumAnimView) getChildAt(i);
            giftNumberView.reset();
        }
        lastNumber = 0;
    }

    public void destroy() {
        for (int i = 0; i < getChildCount(); i++) {
            GiftNumAnimView giftNumberView = (GiftNumAnimView) getChildAt(i);
            giftNumberView.reset();
            giftNumberView.destroy();

        }
        lastNumber = 0;
    }

    private void loadNumber(int count) {
        String[] arrStr = String.valueOf(count).split("");
        int numberLength = arrStr.length - 1;

        // 显示要展示的前几位数字
        for (int i = 0; i < numberLength; i++) {
            if (i < getChildCount()) {
                GiftNumAnimView giftNumberView = (GiftNumAnimView) getChildAt(i);
                giftNumberView.setVisibility(View.VISIBLE);
            }
        }

        // 对不需要显示的礼物数字位数要隐藏
        for (int i = numberLength; i < getChildCount(); i++) {
            getChildAt(i).setVisibility(View.GONE);
        }

    }

    private void startAnimation(LuckGiftNumbBean giftNumbBean) {
//        if (!giftNumbBeanList.isEmpty() && !isRunning) {
        loadNumber(giftNumbBean.getCount());
        if (giftNumbBean.getType() == LuckGiftNumbBean.SCROOL_NUMB) {
            startScrollAni(giftNumbBean);
        } else {
            startScaleAni(giftNumbBean);
        }
//        }

    }

    Handler handler = new Handler();

    private void startScrollAni(LuckGiftNumbBean giftNumbBean) {
        String[] arrStr = String.valueOf(giftNumbBean.getCount()).split("");

        // 把数字分离，第一个其实是空字符，所以不处理
        for (int i = 1; i < arrStr.length; i++) {
            String anArrStr = arrStr[i];
            if (!anArrStr.isEmpty()) {
                GiftNumAnimView numberView = (GiftNumAnimView) getChildAt(i - 1);
                numberView.smoothToPosition(Integer.valueOf(anArrStr));
            }
        }
    }

    private void startScaleAni(LuckGiftNumbBean giftNumbBean) {

        String[] arrStr = String.valueOf(giftNumbBean.getCount()).split("");

        // 把数字分离，第一个其实是空字符，所以不处理
        for (int i = 1; i < arrStr.length; i++) {
            String anArrStr = arrStr[i];
            if (!anArrStr.isEmpty()) {
                GiftNumAnimView numberView = (GiftNumAnimView) getChildAt(i - 1);
                numberView.scrollToPosition(Integer.valueOf(anArrStr));
            }
        }
        if (numScaleAnimatorSet != null) {
            numScaleAnimatorSet.cancel();
        }
        numScaleAnimatorSet = initNumScaleAnimatorSet.clone();
        numScaleAnimatorSet.addListener(this);
        numScaleAnimatorSet.start();
    }


    // 缩放数字动画监听器
    @Override
    public void onAnimationStart(Animator animation) {
    }

    @Override
    public void onAnimationEnd(Animator animation) {
//        startAnimation();
    }

    @Override
    public void onAnimationCancel(Animator animation) {
    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

}
