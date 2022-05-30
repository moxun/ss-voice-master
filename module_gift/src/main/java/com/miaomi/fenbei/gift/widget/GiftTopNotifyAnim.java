package com.miaomi.fenbei.gift.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.miaomi.fenbei.base.bean.TopNotifyBean;
import com.miaomi.fenbei.base.util.DensityUtil;
import com.miaomi.fenbei.base.util.HHtml;
import com.miaomi.fenbei.base.util.URLDrawable;
import com.miaomi.fenbei.base.util.URLImageParser;
import com.miaomi.fenbei.gift.OnTopNotifyClick;
import com.miaomi.fenbei.gift.R;
import com.xiaweizi.marquee.MarqueeTextView;

public class GiftTopNotifyAnim extends LinearLayout {
    private View view;
    private AnimatorSet scaleAnimatorSet;
    private AnimatorSet cloneAnimatorSet;
    private ObjectAnimator translateInAnimation;
    private ObjectAnimator translateOutAnimation;
    private ObjectAnimator alphaAnimator;
    public boolean isRunning = false;
//    private OnTopNotifyListener danAction;
    private int ENTER_THE_ARENA_TIME = 1000;//进场动画时间
    private int OUT_THE_ARENA_TIME = 300;//出场动画时间
    private int DELAY_ARENA_TIME = 7000;//进场时延迟执行出场动画时间
    private int REMAIN_ARENA_TIME = DELAY_ARENA_TIME - ENTER_THE_ARENA_TIME;//持续停留时间
    Context context;
    private URLDrawable urlDrawable = null;
    MarqueeTextView tvMsg;
    private String currentRoomId = "";

    private OnTopNotifyClick onTopNotifyClick;

//    public void setDanAction(OnTopNotifyListener danAction) {
//        this.danAction = danAction;
//    }


    public void setOnTopNotifyClick(OnTopNotifyClick onTopNotifyClick) {
        this.onTopNotifyClick = onTopNotifyClick;
    }

//    public void setDanAction(OnTopNotifyListener danAction) {
//        this.danAction = danAction;
//    }

    public GiftTopNotifyAnim(Context context) {
        super(context);
        init(context);
    }

    public GiftTopNotifyAnim(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GiftTopNotifyAnim(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.setClipToOutline(false);
        }
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        setPadding(0,0,0,0);
        translateInAnimation = new ObjectAnimator();
        translateInAnimation.setDuration(ENTER_THE_ARENA_TIME);
        translateInAnimation.setInterpolator(new OvershootInterpolator());
        translateInAnimation.setPropertyName("translationX");
        translateInAnimation.setFloatValues(1300f, 0);

        translateOutAnimation = new ObjectAnimator();
        translateOutAnimation.setPropertyName("translationX");
        translateOutAnimation.setFloatValues(0, -3000f);
        translateOutAnimation.setDuration(OUT_THE_ARENA_TIME);

        alphaAnimator = new ObjectAnimator();
        alphaAnimator.setPropertyName("alpha");
        alphaAnimator.setFloatValues(1, 0);
        alphaAnimator.setDuration(OUT_THE_ARENA_TIME);

        scaleAnimatorSet = new AnimatorSet();
        scaleAnimatorSet.play(translateOutAnimation).with(alphaAnimator).after(translateInAnimation);
        translateOutAnimation.setStartDelay(DELAY_ARENA_TIME);
        alphaAnimator.setStartDelay(DELAY_ARENA_TIME);
    }

    public interface onAnimationListener{
        void onEnd();
    }

    /**
     * 启动动画
     */
    public void mStartAnimation(TopNotifyBean msg, final onAnimationListener onAnimationListener) {
        currentRoomId = msg.getRoomId();
        isRunning = true;
        removeAllViews();
        view = LayoutInflater.from(context).inflate(R.layout.gift_item_translate_danmu, this,true);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTopNotifyClick != null && !TextUtils.isEmpty(currentRoomId)){
                    onTopNotifyClick.onChange(currentRoomId);
                }
            }
        });
        final ImageView bgIv = view.findViewById(R.id.iv_bg);
        bgIv.setImageLevel(msg.getBg());
        tvMsg = view.findViewById(R.id.tv_content);
        tvMsg.setText(HHtml.fromHtml(msg.getContent(),new Html.ImageGetter(){
            @Override
            public Drawable getDrawable(String source) {
                return new URLImageParser(tvMsg, context, DensityUtil.INSTANCE.dp2px(context,20f)).getDrawable(source);
            }
        }, null));
        tvMsg.setSelected(true);
        //文字的长度
        final float textLen = tvMsg.getPaint().measureText(tvMsg.getText().toString());
        final float viewLen = DensityUtil.INSTANCE.dp2px(context, 250);
        cloneAnimatorSet = scaleAnimatorSet.clone();
        cloneAnimatorSet.setTarget(view);
        cloneAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
                view.setAlpha(1);
                view.setTranslationX(0);
                //文字长度大于textview长度，文字滚动
                if (textLen > viewLen) {
                    tvMsg.startScroll();
//                    startTextAnim(tvMsg, textLen);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
                //防止内存溢出
                if (!((Activity) getContext()).isDestroyed()) {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            view.clearAnimation();
                            GiftTopNotifyAnim.this.removeView(view);
                        }
                    });
                }
                onAnimationListener.onEnd();
                isRunning = false;
            }
        });
        cloneAnimatorSet.start();
    }

//    private void startTextAnim(TextView tvMsg, float textLen) {
//        Animation translateAnimation = new TranslateAnimation(0, -textLen, 0, 0);
//        translateAnimation.setDuration(REMAIN_ARENA_TIME);
//        translateAnimation.setRepeatCount(0);
//        translateAnimation.setStartOffset(1000);
//        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                animation.cancel();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        tvMsg.startAnimation(translateAnimation);
//    }



    public void reset() {
        //防止内存溢出
        if (tvMsg!=null){
            tvMsg.stopScroll();
        }
        if (!((Activity) getContext()).isDestroyed()) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    if (view != null) {
                        view.clearAnimation();
                        GiftTopNotifyAnim.this.removeView(view);
                    }
//                        if (danAction != null) {
//                            danAction.pollTopNotify();
//                        }
                }
            });
        }
    }

    public void stop() {
        if (cloneAnimatorSet != null) {
            cloneAnimatorSet.cancel();
            cloneAnimatorSet = null;
        }
        if (view != null) {
            view.clearAnimation();
        }
        if (tvMsg != null){
            tvMsg.stopScroll();
        }
        this.clearAnimation();
        this.removeAllViews();
    }

}
