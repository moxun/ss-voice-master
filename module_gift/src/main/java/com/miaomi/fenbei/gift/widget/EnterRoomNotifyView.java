package com.miaomi.fenbei.gift.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.UserInfo;
import com.miaomi.fenbei.base.config.BaseConfig;
import com.miaomi.fenbei.base.util.DensityUtil;
import com.miaomi.fenbei.base.util.URLDrawable;
import com.miaomi.fenbei.gift.R;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;

public class EnterRoomNotifyView extends FrameLayout {
    private View view;
    private AnimatorSet scaleAnimatorSet;
    private AnimatorSet cloneAnimatorSet;
    private ObjectAnimator translateInAnimation;
    private ObjectAnimator translateOutAnimation;
    private ObjectAnimator alphaAnimator;
    public boolean isRunning = false;
    //    private OnTopNotifyListener danAction;
    private int ENTER_THE_ARENA_TIME = 500;//进场动画时间
    private int OUT_THE_ARENA_TIME = 300;//出场动画时间
    private int DELAY_ARENA_TIME = 5000;//进场时延迟执行出场动画时间
    private int REMAIN_ARENA_TIME = DELAY_ARENA_TIME - ENTER_THE_ARENA_TIME;//持续停留时间
    Context context;
    private URLDrawable urlDrawable = null;
    TextView tvMsg;
    private SVGAImageView svgaView;

//    public void setDanAction(OnTopNotifyListener danAction) {
//        this.danAction = danAction;
//    }

    public EnterRoomNotifyView(Context context) {
        super(context);
        init(context);
    }

    public EnterRoomNotifyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EnterRoomNotifyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.setClipToOutline(false);
        }
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

//    int rankId = 1;

    /**
     * 启动动画
     */
    public void mStartAnimation(UserInfo msg, final EnterRoomNotifyView.onAnimationListener onAnimationListener) {
        isRunning = true;
        removeAllViews();
        view = LayoutInflater.from(context).inflate(R.layout.view_anim_enter_room, this,true);
        svgaView = view.findViewById(R.id.iv_svg);
        tvMsg = view.findViewById(R.id.tv_content);
        int rankId = getRankBgRes(msg.getEffects());
        if (rankId > 0){
            tvMsg.setText(msg.getNickname()+"  来了");
        }else{
            tvMsg.setText("");
        }
        tvMsg.setBackgroundResource(rankId);
        tvMsg.setSelected(true);
        try {
            showSvgaGiftAnim(msg.getSeat());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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
//                    tvMsg.startScroll();
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
                            EnterRoomNotifyView.this.removeView(view);
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
//        if (tvMsg!=null){
//            tvMsg.stopScroll();
//        }
        if (!((Activity) getContext()).isDestroyed()) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    if (view != null) {
                        view.clearAnimation();
                        EnterRoomNotifyView.this.removeView(view);
                    }
//                        if (danAction != null) {
//                            danAction.pollTopNotify();
//                        }
                }
            });
        }
    }

    private int getRankBgRes(int rank){
        if (rank == BaseConfig.NOBLE_JC_LEVEL_ZJ){
            return R.drawable.icon_jc_juewei_1;
        }
        if (rank == BaseConfig.NOBLE_JC_LEVEL_BJ){
            return R.drawable.icon_jc_juewei_2;
        }
        if (rank == BaseConfig.NOBLE_JC_LEVEL_HJ){
            return R.drawable.icon_jc_juewei_3;
        }
        if (rank == BaseConfig.NOBLE_JC_LEVEL_GJ){
            return R.drawable.icon_jc_juewei_4;
        }
        if (rank == BaseConfig.NOBLE_JC_LEVEL_GW){
            return R.drawable.icon_jc_juewei_5;
        }
        if (rank == BaseConfig.NOBLE_JC_LEVEL_DH){
            return R.drawable.icon_jc_juewei_6;
        }
        if (rank == BaseConfig.NOBLE_JC_LEVEL_HS){
            return R.drawable.icon_jc_juewei_7;
        }
        return 0;
    }



    private String getRankBgAnim(int rank){
        if (rank == BaseConfig.NOBLE_LEVEL_ZJ){
            return "anim_jc_zijue.svga";
        }
        if (rank == BaseConfig.NOBLE_LEVEL_BJ){
            return "anim_jc_bojue.svga";
        }
        if (rank == BaseConfig.NOBLE_LEVEL_HJ){
            return "anim_jc_houjue.svga";
        }
        if (rank == BaseConfig.NOBLE_LEVEL_GJ){
            return "anim_jc_gongjue.svga";
        }
        if (rank == BaseConfig.NOBLE_LEVEL_GW){
            return "anim_jc_guowang.svga";
        }
        if (rank == BaseConfig.NOBLE_LEVEL_DH){
            return "anim_jc_dihuang.svga";
        }
        if (rank == BaseConfig.NOBLE_LEVEL_HS){
            return "anim_jc_huanshen.svga";
        }
        return "anim_jc_huanshen.svga";
    }


    private void showSvgaGiftAnim(String name) throws MalformedURLException {
        if (TextUtils.isEmpty(name)){
            return;
        }
        SVGAParser parser = new SVGAParser(context);
        svgaView.setCallback(new SVGACallback() {
            @Override
            public void onPause() {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onRepeat() {
                svgaView.stopAnimation();
            }

            @Override
            public void onStep(int i, double v) {

            }
        });
        parser.decodeFromURL(new URL(name), new SVGAParser.ParseCompletion() {
            @Override
            public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                SVGADrawable drawable = new SVGADrawable(videoItem);
                svgaView.setImageDrawable(drawable);
                svgaView.startAnimation();
            }
            @Override
            public void onError() {

            }
        });
    }

    public void stop() {
        if (cloneAnimatorSet != null) {
            cloneAnimatorSet.cancel();
            cloneAnimatorSet = null;
        }
        if (view != null) {
            view.clearAnimation();
        }
//        if (tvMsg != null){
//            tvMsg.stopScroll();
//        }
        if (svgaView != null){
            svgaView.stopAnimation();
            svgaView.clearAnimation();
        }
        this.clearAnimation();
        this.removeAllViews();
    }

}
