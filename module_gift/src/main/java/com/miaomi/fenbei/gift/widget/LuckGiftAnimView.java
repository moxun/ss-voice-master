//package com.miaomi.fenbei.gift.view;
//
//import android.animation.Animator;
//import android.animation.AnimatorListenerAdapter;
//import android.animation.AnimatorSet;
//import android.animation.ObjectAnimator;
//import android.content.Context;
//import android.graphics.PointF;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import android.text.Html;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.view.animation.BounceInterpolator;
//import android.view.animation.OvershootInterpolator;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.miaomi.fenbei.common.bean.GiftMsgBean;
//import com.miaomi.fenbei.common.util.DataHelper;
//import com.miaomi.fenbei.common.util.DensityUtil;
//import com.miaomi.fenbei.common.util.ImgUtil;
//import com.miaomi.fenbei.gift.R;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.util.ArrayDeque;
//import java.util.Queue;
//
//
//public class LuckGiftAnimView extends FrameLayout {
//    private static final long TIME_OUT = 3500;                  //持续时间
//    private ConstraintLayout rlGiftRoot;
//    private ImageView ivGiftSenderAvatar;
//    private TextView tvGiftSenderName;
//    private TextView mtvGiftName;
//    private ImageView ivGiftImage;
//    private ImageView ivGiftMulti;
//    private LuckGiftNumAnimLinearLayout gllGiftNumRoot;
//    private RelativeLayout gllRelativeLayout;
//    private TextView tvGiftMultipleReward;
//    private FrameLayout rlCongratulationRoot;
//    private ImageView ivGiftRewardBack;
//    private LinearLayout llGiftBigRewardMultiple;
//    /**
//     * 动画第一部 show
//     */
//    private static final int STEP_SHOW = 1;
//    private AnimatorSet initInAnimatorSet; // 初始化礼物进场动画
//    private ObjectAnimator initGiftTranslation; // 初始化礼物图片进入动画
//    private AnimatorSet initOutAnimatorSet; // 初始化礼物退场动画
//
//    AnimatorSet prizeAnimatorSet;
//    // 中奖动画后面那个转圈圈黄色放射性背景图
//    private Animation prizeBackAnimation;
//
//
//    public LuckGiftAnimView(Context context) {
//        super(context);
//        initView(context);
//    }
//
//    public LuckGiftAnimView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        initView(context);
//    }
//
//    public LuckGiftAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        initView(context);
//    }
//
//    private void initView(Context context) {
//        LayoutInflater.from(context).inflate(R.layout.gift_view_anim_luck, this, true);
//        rlGiftRoot =  findViewById(R.id.constraintLayout);
//        ivGiftSenderAvatar = findViewById(R.id.ivGiftSenderAvatar);
//        tvGiftSenderName = findViewById(R.id.tvGiftSenderName);
//        mtvGiftName = findViewById(R.id.mtvGiftName);
//        ivGiftImage =  findViewById(R.id.ivGiftImage);
//        ivGiftMulti =  findViewById(R.id.ivGiftMulti);
//        gllGiftNumRoot =  findViewById(R.id.gllGiftNumRoot);
//        gllRelativeLayout = findViewById(R.id.rlGiftInfoRoot);
//        tvGiftMultipleReward =  findViewById(R.id.tvGiftMultipleReward);
//        rlCongratulationRoot = findViewById(R.id.rl_congratulation_root);
//        ivGiftRewardBack =  findViewById(R.id.ivGiftRewardBack);
//        llGiftBigRewardMultiple =  findViewById(R.id.llGiftBigRewardMultiple);
//        initAnima();
//    }
//
//    private ObjectAnimator prizealpha2;
//    private void initAnima() {
//        final ObjectAnimator inTranslation = new ObjectAnimator();
//        inTranslation.setPropertyName("translationX");
//        inTranslation.setFloatValues(-200, 0);
//        inTranslation.setInterpolator(new BounceInterpolator());
//        final ObjectAnimator inAlpha = new ObjectAnimator();
//        inAlpha.setPropertyName("alpha");
//        inAlpha.setFloatValues(0, 1);
//
//        initInAnimatorSet = new AnimatorSet();
//        initInAnimatorSet.playTogether(inTranslation, inAlpha);
//        initInAnimatorSet.setDuration(50);
//
//        initInAnimatorSet.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
//                rlGiftRoot.setAlpha(1);
//                rlGiftRoot.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//
//            }
//        });
//
//
//        initGiftTranslation = new ObjectAnimator();
//        initGiftTranslation.setPropertyName("translationX");
//        initGiftTranslation.setFloatValues(-500, 0);
//        initGiftTranslation.setDuration(300);
//        initGiftTranslation.setStartDelay(30);
//        initGiftTranslation.setInterpolator(new OvershootInterpolator());
//
//        final ObjectAnimator outAlpha = new ObjectAnimator();
//        outAlpha.setPropertyName("alpha");
//        outAlpha.setFloatValues(1, 0);
//        final ObjectAnimator outTranslation = new ObjectAnimator();
//        outTranslation.setPropertyName("translationX");
//        outTranslation.setFloatValues(0, 350);
//        initOutAnimatorSet = new AnimatorSet();
//        initOutAnimatorSet.playTogether(outAlpha, outTranslation);
//        initOutAnimatorSet.setDuration(50);
//
//        initOutAnimatorSet.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                setClearTake();
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                ivGiftMulti.setVisibility(View.INVISIBLE);
//                gllRelativeLayout.setVisibility(INVISIBLE);
//                gllGiftNumRoot.setVisibility(View.INVISIBLE);
//                gllGiftNumRoot.reset();
//                rlGiftRoot.setVisibility(View.INVISIBLE);
//                isShowAnimaing = false;
//                ivGiftImage.setImageResource(R.drawable.common_avter_placeholder);
//                currentAnimaStep = 0;
//                currentModel = null;
//                showCount = 0;
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
//
//
//        prizeAnimatorSet = new AnimatorSet();
//        final ObjectAnimator scaleX = new ObjectAnimator();
//        scaleX.setPropertyName("scaleX");
//        scaleX.setFloatValues(2f, 0.5f, 1f);
//        scaleX.setInterpolator(new OvershootInterpolator());
//        scaleX.setDuration(500);
//
//        final ObjectAnimator scaleY = new ObjectAnimator();
//        scaleY.setPropertyName("scaleY");
//        scaleY.setFloatValues(2f, 0.5f, 1f);
//        scaleY.setInterpolator(new OvershootInterpolator());
//        scaleY.setDuration(500);
//
//        final ObjectAnimator alpha = new ObjectAnimator();
//        alpha.setPropertyName("alpha");
//        alpha.setFloatValues(0.6f, 1f);
//        alpha.setDuration(150);
//
//        prizealpha2 = new ObjectAnimator();
//        prizealpha2.setPropertyName("alpha");
//        prizealpha2.setFloatValues(1f, 1f);
//        prizealpha2.setDuration(2500);
//        prizealpha2.setStartDelay(150);
//        prizeAnimatorSet.playTogether(scaleX, scaleY, alpha, prizealpha2);
//        prizeAnimatorSet.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
//                gift500Queue.poll();
//                if(!gift500Queue.isEmpty()){
//                    loadCongratulationAnim(gift500Queue.peek());
//                }else {
//                    rlCongratulationRoot.setVisibility(View.GONE);
//                    checkFinish();
//                }
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//                rlCongratulationRoot.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//
//            }
//        });
//    }
//
//
//    private void checkFinish(){
//
//        if(gift500Queue.isEmpty() && isTimeOut){
//
//            initOutAnimatorSet.setTarget(rlGiftRoot);
//            initOutAnimatorSet.start();
//        }
//    }
//
//    private void animGiftImage(final ImageView giftImage, final String url) {
//        giftImage.setVisibility(View.INVISIBLE);
//        initGiftTranslation.setTarget(giftImage);
//        initGiftTranslation.removeAllListeners();
//        initGiftTranslation.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                giftImage.setVisibility(View.VISIBLE);
////                ImageLoader.with(getContext())
////                        .url(url)
////                        .error(R.drawable.default_square_middle).override(120, 120)
////                        .diskCacheStrategy(com.pince.imageloader.config.DiskCacheStrategy.ALL)
////                        .into(giftImage);
//                ImgUtil.INSTANCE.loadCircleImg(getContext(),url,giftImage,R.drawable.common_avter_placeholder);
//            }
//        });
//        initGiftTranslation.start();
//    }
//
//
//    public   volatile boolean isShowAnimaing = false;
//    private volatile GiftMsgBean currentModel;
//    private int currentAnimaStep = 0;
//    private Queue<GiftMsgBean> gift500Queue = new ArrayDeque<GiftMsgBean>();
//
//    public boolean isShowAnima(@NotNull GiftMsgBean giftMsgBean) {
//        if (currentModel == null) {
//            return false;
//        }
//        if (isShowAnimaing && currentModel.getGiftId() == giftMsgBean.getGiftId() &&
//                currentModel.getNickname().equals(giftMsgBean.getNickname()  )
//                &&currentModel.getUserId()== giftMsgBean.getUserId()
//                &&currentModel.getToUserId()== giftMsgBean.getToUserId()) {
//            return true;
//        }
//        return false;
//    }
//
//    public boolean isBusy() {
//        return isShowAnimaing;
//    }
//
//    /**
//     * 恭喜中奖动画两秒后消失
//     * 清除500倍中奖倍数
//     */
//    private void setClearTake() {
//        rlCongratulationRoot.setVisibility(View.GONE);
//        llGiftBigRewardMultiple.removeAllViews();
//        tvGiftMultipleReward.setVisibility(View.GONE);
//        if (prizeBackAnimation != null) {
//            prizeBackAnimation.cancel();
//            ivGiftRewardBack.clearAnimation();
//        }
//    }
//
//
//    private boolean isTimeOut = false;
//    /**
//     * 隐藏 结束送礼任务
//     */
//    private Runnable alphaRunnable = new Runnable() {
//        @Override
//        public void run() {
//            isTimeOut = true;
//            checkFinish();
//        }
//    };
//
//    public void onNewAnimaArrive(@NotNull final GiftMsgBean giftaModel) {
//        isTimeOut = false;
//        isShowAnimaing = true;
//        currentModel = giftaModel;
//
//        post(new Runnable() {
//            @Override
//            public void run() {
//
//                removeCallbacks(alphaRunnable);
//                postDelayed(alphaRunnable, TIME_OUT);
//
//                // 显示ui
//                tvGiftSenderName.setText(giftaModel.getNickname());
//                if (giftaModel.getToUserName() != null){
//                    mtvGiftName.setText(Html.fromHtml("送给 <font color='#ff9600'>" + giftaModel.getToUserName() + "</font>"));
//                }
//                rlGiftRoot.setVisibility(View.VISIBLE);
//
//                //还没有滑动
//                if (currentAnimaStep == 0) {
////                    ImageLoader.with(getContext()).url(header)
////                            .placeHolder(R.drawable.default_circle_small)
////                            .override(80, 80).error(R.drawable.default_circle_small)
////                            .transform(new CircleCrop())
////                            .thumbnail(0.1f).into(ivGiftSenderAvatar);
//                    ImgUtil.INSTANCE.loadCircleImg(getContext(),giftaModel.getAvter(),ivGiftSenderAvatar,R.drawable.common_avter_placeholder);
//                    animGiftImage(ivGiftImage, giftaModel.getGiftIcon());
//                    initInAnimatorSet.setTarget(rlGiftRoot);
//                    initInAnimatorSet.start();
//                    currentAnimaStep = STEP_SHOW;
//                }
//
//                int multiples = getLuckRewardType(giftaModel);
//                //中奖动画
//                if (multiples == 2 || multiples == 1) {
//                    //本人不显示消息动画
//                    if (DataHelper.INSTANCE.getUserInfo() == null){
//                        return;
//                    }
//                    if (DataHelper.INSTANCE.getUserInfo().getUser_id() == giftaModel.getUserId()){
//                        return;
//                    }
//                    checkPlayCongratulationAnim(giftaModel);
//                }
//                numberAndGiftBackAnim(giftaModel);
//            }
//        });
//    }
//
//    int showCount;
//    /**
//     * 礼物数字跳动动画和礼物弹幕背景变换
//     */
//    private void numberAndGiftBackAnim(GiftMsgBean bean) {
//        //数字背景以及数字动画
//        int count = bean.getGiftNum();
//        ivGiftMulti.setVisibility(View.VISIBLE);
//        gllRelativeLayout.setVisibility(VISIBLE);
//        gllGiftNumRoot.setVisibility(View.VISIBLE);
//        showCount = showCount + bean.getGiftNum();
//        loadNumber(showCount);
//    }
//
//    // 新的数字跳动代码
//    private synchronized void loadNumber(int count) {
//        gllGiftNumRoot.addNumber(count);
//    }
//
//    private void performSmallAnimation(int multiples) {
//        tvGiftMultipleReward.setVisibility(View.VISIBLE);
////        CharSequence str = getCharSequence(multiples);
//        tvGiftMultipleReward.setText("+"+multiples);
//        removeCallbacks(performSmallAnimationRun);
//        postDelayed(performSmallAnimationRun, 2000);
//    }
//
////    private CharSequence getCharSequence(int getMutils) {
////        CharSequence str;
////        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
////            str = Html.fromHtml("获得<font color='#ff9600'>" + getMutils + "</font>" + "幸运币奖励", Html.FROM_HTML_MODE_LEGACY);
////        } else {
////            str = Html.fromHtml("获得<font color='#ff9600'>" + getMutils + "</font>" + "幸运币奖励");
////        }
////        return str;
////    }
//
//    private Runnable performSmallAnimationRun = new Runnable() {
//        @Override
//        public void run() {
//            tvGiftMultipleReward.setVisibility(View.GONE);
//        }
//    };
//
//    private PointF targetPointF;
//
//
//    private void checkPlayCongratulationAnim(GiftMsgBean giftaModel){
//        if(!prizeAnimatorSet.isRunning() && gift500Queue.isEmpty()){
//            gift500Queue.add(giftaModel);
//            prizealpha2.setDuration(1600);
//            loadCongratulationAnim(giftaModel);
//        }else {
//            gift500Queue.add(giftaModel);
//            prizealpha2.setDuration(800);
//        }
//    }
//    /**
//     * 中大奖动画
//     */
//    private void loadCongratulationAnim(final GiftMsgBean giftaModel) {
//        rlCongratulationRoot.setVisibility(View.VISIBLE);
//        prizeBackAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.win_500_small_bg_anim1);
//        ivGiftRewardBack.startAnimation(prizeBackAnimation);
//        prizeAnimatorSet.setTarget(rlCongratulationRoot);
//
//
//        // 加载恭喜赢得xx倍奖励图案
//        loadWinMultiple(getLuckReward(giftaModel));
//        llGiftBigRewardMultiple.setVisibility(View.VISIBLE);
////
//        if(targetPointF == null){
//            rlCongratulationRoot.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    targetPointF = new PointF();
//                    int[] location = new int[2];
//                    rlCongratulationRoot.getLocationInWindow(location); //获取在当前窗口内的绝对坐标
//                    targetPointF.x = location[0] + DensityUtil.INSTANCE.dp2px(getContext(),110);
//                    targetPointF.y = location[1] + DensityUtil.INSTANCE.dp2px(getContext(),60);
//                    if (on500AnimaListener != null) {
//                        on500AnimaListener.onStartPlay(targetPointF,giftaModel);
//                    }
//                }
//            },400);
//        }else {
//            if (on500AnimaListener != null) {
//                on500AnimaListener.onStartPlay(targetPointF,giftaModel);
//            }
//        }
//
//        prizeAnimatorSet.start();
//    }
//
//
//    private int[] numbers = {R.drawable.gift_shuzi0, R.drawable.gift_shuzi1, R.drawable.gift_shuzi2, R.drawable.gift_shuzi3, R.drawable.gift_shuzi4
//            , R.drawable.gift_shuzi5, R.drawable.gift_shuzi6, R.drawable.gift_shuzi7, R.drawable.gift_shuzi8, R.drawable.gift_shuzi9};
//
//
//
//    /**
//     * 加载中奖倍数
//     */
//    private void loadWinMultiple(int reward) {
//        llGiftBigRewardMultiple.removeAllViews();
//        ImageView ivWin = new ImageView(getContext());
//        ivWin.setImageResource(R.drawable.win);
//        ImageView ivTimesCoins = new ImageView(getContext());
//        ivTimesCoins.setImageResource(R.drawable.times_coins);
//        llGiftBigRewardMultiple.addView(ivWin);
//
//
//        String[] arrStr = String.valueOf(reward).split("");
//        for (String anArrStr : arrStr) {
//            if (!anArrStr.isEmpty()) {
//                int index = Integer.valueOf(anArrStr);
//                ImageView ivNumber = new ImageView(getContext());
//                ivNumber.setImageResource(numbers[index]);
//                llGiftBigRewardMultiple.addView(ivNumber);
//            }
//        }
//        llGiftBigRewardMultiple.addView(ivTimesCoins);
//    }
//
//    /**
//     * 获取此次送礼中奖的倍数
//     *
//     * @param bean
//     * @return
//     */
//    private int getLuckRewardType(GiftMsgBean bean) {
////        if (bean.data != null && bean.data.getMutil() != null && !bean.data.getMutil().isEmpty()) {
////            multiples = Integer.parseInt(bean.data.getMutil());
//        return bean.getLuck_reward_type();
//    }
//    private int getLuckReward(GiftMsgBean bean) {
//        return bean.getLuck_reward_count();
//    }
//
//    @Override
//    protected void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//        clear();
//    }
//
//
//    public void clear() {
//
//        if (initInAnimatorSet.isRunning()) {
//            initInAnimatorSet.cancel();
//        }
//        if (initGiftTranslation.isRunning()) {
//            initGiftTranslation.cancel();
//        }
//        if (initOutAnimatorSet.isRunning()) {
//            initOutAnimatorSet.cancel();
//        }
//        if (prizeAnimatorSet.isRunning()) {
//            prizeAnimatorSet.cancel();
//        }
//        showCount = 0;
//        setClearTake();
//        ivGiftMulti.setVisibility(View.INVISIBLE);
//        gllRelativeLayout.setVisibility(INVISIBLE);
//        gllGiftNumRoot.setVisibility(View.INVISIBLE);
//        gllGiftNumRoot.reset();
//        rlGiftRoot.setVisibility(View.INVISIBLE);
//        isShowAnimaing = false;
//        currentAnimaStep = 0;
//        gift500Queue.clear();
//        ivGiftImage.setImageResource(R.drawable.common_avter_placeholder);
//        currentModel = null;
//        removeCallbacks(alphaRunnable);
//        removeCallbacks(performSmallAnimationRun);
//
//    }
//
//
//
//
//    public interface On500AnimaListener {
//        void onStartPlay(PointF targetPointF, GiftMsgBean bean);
//    }
//
//    /**
//     * 500bei播放回调
//     */
//    public On500AnimaListener on500AnimaListener;
//}
