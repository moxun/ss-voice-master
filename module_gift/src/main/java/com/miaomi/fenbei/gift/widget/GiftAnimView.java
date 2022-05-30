package com.miaomi.fenbei.gift.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.miaomi.fenbei.base.AudioPlayer;
import com.miaomi.fenbei.base.bean.GiftMsgBean;
import com.miaomi.fenbei.base.bean.GiftPoint;
import com.miaomi.fenbei.base.bean.NobleOnlineBean;
import com.miaomi.fenbei.base.bean.SVGUrlBean;
import com.miaomi.fenbei.base.bean.TopNotifyBean;
import com.miaomi.fenbei.base.bean.UserInfo;
import com.miaomi.fenbei.base.bean.XqResultBean;
import com.miaomi.fenbei.base.config.BaseConfig;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.DensityUtil;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.ScreenUtil;
import com.miaomi.fenbei.gift.GiftManager;
import com.miaomi.fenbei.gift.OnTopNotifyClick;
import com.miaomi.fenbei.gift.R;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGADynamicEntity;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GiftAnimView extends ConstraintLayout {
    private Context context;

    private SVGAImageView svgaImageView;
    private SVGAImageView svgaXQImageView;
    private SVGAParser parser;
    private boolean isSVGAPlaying = false;
    private SVGAImageView nobleOnlieSvgaIv;
    private SVGAParser nobleOnlieParser;
    private boolean isNobleOnlieSVGAPlaying = false;
//    private CommonSendGiftLayout mSendGiftLayout;
//    private LuckGiftAnimView mLuckGiftAnimView1;
//    private LuckGiftAnimView mLuckGiftAnimView2;
//    private LuckGiftAnimView mLuckGiftAnimView3;
    private GiftTopNotifyAnim mGiftTopNotifyAnim;
    private ExpressGiftTopNotifyAnim expressGiftTopNotifyAnim;
    private EnterRoomNotifyView enterRoomView;
//    private FrameLayout rewardFl;
    private MyHandler handler;
//    private List<LuckGiftAnimView> luckGiftAnimViews = new ArrayList<>();
//    private TextView rewardTv;
    private final static int SVGA_ANIM_FLAG = 0x1111;
    private final static int TOP_NOTIFY_ANIM_EXPRESS_FLAG = 0x2222;
    private final static int NOBLE_ONLINE_SVGA_ANIM_FLAG = 0x4444;
    private final static int TOP_NOTIFY_ANIM_FLAG = 0x3333;
    private final static int ENTER_ROOM_ANIM_FLAG = 0x5555;
    private final static int XIANGQING_SUCCESS =19;
    private final static int XIANGQING_FAIL =18;
//    BlockingQueue<String> mRewardQueue = new LinkedBlockingQueue<>();
    BlockingQueue<SVGUrlBean> mSvgQueue = new LinkedBlockingQueue<>();
    BlockingQueue<NobleOnlineBean> mNobleOnlineSvgQueue = new LinkedBlockingQueue<>();
    BlockingQueue<TopNotifyBean> mExpressTopNotifyQueue = new LinkedBlockingQueue<>();
    BlockingQueue<TopNotifyBean> mTopNotifyQueue = new LinkedBlockingQueue<>();
    BlockingQueue<UserInfo> mEnterRoomQueue = new LinkedBlockingQueue<>();
    XqResultBean xqResultBean=new XqResultBean();


    private float width;
    private float height;

    public GiftAnimView(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }

    public GiftAnimView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public GiftAnimView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public void setOnTopNotifyClick(OnTopNotifyClick onTopNotifyClick) {
        mGiftTopNotifyAnim.setOnTopNotifyClick(onTopNotifyClick);
        expressGiftTopNotifyAnim.setOnTopNotifyClick(onTopNotifyClick);
    }

    private void init(){
        width = ScreenUtil.getScreenWidth();
        height = ScreenUtil.getRealHeight();
        View rootView = inflate(context, R.layout.gift_send_layout, this);
        enterRoomView = rootView.findViewById(R.id.view_enter_room);
        nobleOnlieSvgaIv = rootView.findViewById(R.id.iv_svg_noble_online);
//        mSendGiftLayout = rootView.findViewById(R.id.ly_send_gift);
//        mLuckGiftAnimView1 = rootView.findViewById(R.id.anim_view_luck1);
//        mLuckGiftAnimView2 = rootView.findViewById(R.id.anim_view_luck2);
//        mLuckGiftAnimView3 = rootView.findViewById(R.id.anim_view_luck3);
        expressGiftTopNotifyAnim = rootView.findViewById(R.id.top_danmu_express);
        mGiftTopNotifyAnim = rootView.findViewById(R.id.top_danmu_one);
        svgaImageView = rootView.findViewById(R.id.iv_svg);
//        rewardTv = rootView.findViewById(R.id.tv_reward);
//        rewardFl = rootView.findViewById(R.id.fl_reward);
        svgaXQImageView=rootView.findViewById(R.id.iv_xq_svg);
//        addView(rootView);
        handler = new MyHandler();
//        luckGiftAnimViews.clear();
//        luckGiftAnimViews.add(mLuckGiftAnimView1);
//        luckGiftAnimViews.add(mLuckGiftAnimView2);
//        luckGiftAnimViews.add(mLuckGiftAnimView3);
    }


    public void clear(){
//        mLuckQueue.clear();
        mTopNotifyQueue.clear();
        mEnterRoomQueue.clear();
        mSvgQueue.clear();
        mNobleOnlineSvgQueue.clear();
        mExpressTopNotifyQueue.clear();
//        mRewardQueue.clear();
//        for (LuckGiftAnimView view :luckGiftAnimViews){
//            view.clear();
//        }
        isSVGAPlaying = false;
        handler.removeCallbacksAndMessages(null);
        expressGiftTopNotifyAnim.reset();
        expressGiftTopNotifyAnim.stop();
        mGiftTopNotifyAnim.reset();
        mGiftTopNotifyAnim.stop();
        enterRoomView.reset();
        enterRoomView.stop();
    }

//    public void destroy(){
//        handler.removeCallbacksAndMessages(null);
//        mGiftTopNotifyAnim.reset();
//        mGiftTopNotifyAnim.stop();
//        for (LuckGiftAnimView view :luckGiftAnimViews){
//            view.clear();
//        }
//    }

//    public void addRewardAnim(String str){
//        mRewardQueue.add(str);
//        if (mRewardQueue.size() == 1&&!isRewardRunning){
//            sendAnimMsg(LUCK_REWARD_FLAG);
//        }
//    }
//    public void addLuckGiftAnim(GiftMsgBean bean){
//        mLuckQueue.add(bean);
//        if (mLuckQueue.size() == 1){
//            sendAnimMsg(LUCK_ANIM_FLAG);
//        }
//    }

    public void addNobleOnline(NobleOnlineBean bean){
        mNobleOnlineSvgQueue.add(bean);
        if (mNobleOnlineSvgQueue.size() == 1 && !isNobleOnlieSVGAPlaying){
            sendAnimMsg(NOBLE_ONLINE_SVGA_ANIM_FLAG);
        }
    }

    public void addTopNotifyGiftAnim(TopNotifyBean bean){
        mTopNotifyQueue.add(bean);
        if (mTopNotifyQueue.size() == 1 && !mGiftTopNotifyAnim.isRunning){
            sendAnimMsg(TOP_NOTIFY_ANIM_FLAG);
        }
    }

    public void addExpressTopNotifyAnim(TopNotifyBean bean){
        mExpressTopNotifyQueue.add(bean);
        if (mExpressTopNotifyQueue.size() == 1 && !expressGiftTopNotifyAnim.isRunning){
            sendAnimMsg(TOP_NOTIFY_ANIM_EXPRESS_FLAG);
        }
    }

    public void addEnterRoom(UserInfo bean){
        mEnterRoomQueue.add(bean);
        if (mEnterRoomQueue.size() == 1 && !enterRoomView.isRunning){
            sendAnimMsg(ENTER_ROOM_ANIM_FLAG);
        }
    }

    public void addSVGAAnim(SVGUrlBean bean){
        mSvgQueue.add(bean);
        if (mSvgQueue.size() == 1 && !isSVGAPlaying){
            sendAnimMsg(SVGA_ANIM_FLAG);
        }
    }



//    private boolean showLuckGiftAnim(GiftMsgBean bean){
//        boolean isDeal = false;
//        for (LuckGiftAnimView view :luckGiftAnimViews){
//            if (view.isBusy()) {
//                if (view.isShowAnima(bean)) {
//                    view.onNewAnimaArrive(bean);
//                    isDeal = true;
//                    break;
//                }
//            }
//        }
//        if (!isDeal){
//            for (LuckGiftAnimView view : luckGiftAnimViews){
//                if (view.isBusy()) {
//                    if (view.isShowAnima(bean)) {
//                        view.onNewAnimaArrive(bean);
//                        isDeal = true;
//                        break;
//                    }
//                } else{
//                    view.onNewAnimaArrive(bean);
//                    isDeal = true;
//                    break;
//                }
//            }
//        }
//        return isDeal;
//    }

    public void showCommonGiftAnim(int num, String url, String icon , GiftPoint startView, GiftPoint endView){
        if (url == null || icon == null || startView == null || endView == null){
            return;
        }
        GiftMsgBean bean = new GiftMsgBean();
        bean.setGiftUrl(url);
        bean.setGiftIcon(icon);
        bean.setStartPoint(startView);
        bean.setEndPoint(endView);
        bean.setGiftNum(num);
        startAnimator(bean);
    }

    public void showChestGiftAnim(int num, String giftIcon , GiftPoint startView, GiftPoint midView, GiftPoint endView){
        if (startView == null || endView == null){
            return;
        }
        GiftMsgBean bean = new GiftMsgBean();
        bean.setChestIcon(giftIcon);
//        bean.setGiftUrl(giftBean.getGiftUrl());
        bean.setGiftIcon(giftIcon);
        bean.setMidPoint(midView);
        bean.setStartPoint(startView);
        bean.setEndPoint(endView);
        bean.setGiftNum(num);
        startChestAnimator(bean);
    }


    public void startChestAnimator(final GiftMsgBean mBean){
        int startX = (int) (width/2) - 100;
        int startY = (int) (height/2) - 100;
        final int midX = startX - 50;
        final int minY = startY - 250;
        final int endX = mBean.getEndPoint().getX();
        final int endY = mBean.getEndPoint().getY();
        final ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(60,60));
        imageView.setEnabled(true);
        addView(imageView);
        ImgUtil.INSTANCE.loadFaceIcon(context,mBean.getGiftIcon(),imageView);
        ObjectAnimator objectAnimator01 = ObjectAnimator.ofFloat(imageView,"scaleX",2f,5f,3f);
        objectAnimator01.setDuration(1000);
        ObjectAnimator objectAnimator05 = ObjectAnimator.ofFloat(imageView,"scaleY",2f,5f,3f);
        objectAnimator05.setDuration(1000);
//        ObjectAnimator objectAnimator03 = ObjectAnimator.ofFloat(imageView,"alpha",0,1);
//        objectAnimator03.setDuration(1000);
//        final int mWidthPixels = Math.abs(startX - midX);
//        final int mHeightPixels = Math.abs(startY - minY);
//        ValueAnimator mValueAnimator = ValueAnimator.ofObject(new BezierEvaluator(mWidthPixels,mHeightPixels)
//                , new PointF(startX, startY), new PointF(midX, minY));
//        mValueAnimator.setDuration(1000);
//        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                PointF pointF = (PointF) animation.getAnimatedValue();
//                imageView.setX(pointF.x);
//                imageView.setY(pointF.y);
//            }
//        });
        PropertyValuesHolder yProperty1 = PropertyValuesHolder
                .ofFloat("y", startY, minY);//Y坐标轴：第二个参数是起始点，第三个是结束点坐标，下行X轴同理
        PropertyValuesHolder xProperty1 = PropertyValuesHolder
                .ofFloat("x", startX, midX);
        ObjectAnimator startAnim = ObjectAnimator.ofPropertyValuesHolder(imageView,
                xProperty1,yProperty1);//创建动画对象，把所有属性拼起来
        startAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
            }
        });
        startAnim.setDuration(1000);
        AnimatorSet animatorSet = new AnimatorSet();
//        mValueAnimator.addListener(new AnimatorListenerAdapter() {
//
//            @Override
//            public void onAnimationStart(Animator animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                ImgUtil.INSTANCE.loadFaceIcon(context,mBean.getGiftIcon(),imageView);
////                imageView.setImageDrawable(null);
////                removeView(imageView);
////                final DCBTextView mNumberImg = new DCBTextView(context);
////                mNumberImg.setText("+ "+mBean.getGiftNum());
////                mNumberImg.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
////                mNumberImg.setTextColor(Color.parseColor("#FFB700"));
////                mNumberImg.setTextSize(20f);
////                addView(mNumberImg);
//                //数字加1的动画效果组合有：位移动画从指定坐标点移动到指定目标坐标点，并带有透明度变化的属性动画
//            }
//        });
//        PropertyValuesHolder alphaProperty = PropertyValuesHolder.ofFloat("rotation", 0f, 360f);//设置透明度的动画属性，过渡到0.1f透明度
//        ObjectAnimator changeAmin = ObjectAnimator.ofPropertyValuesHolder(imageView, alphaProperty);//创建动画对象，把所有属性拼起来
//        changeAmin.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                PropertyValuesHolder yProperty = PropertyValuesHolder
//                        .ofFloat("y", minY, endY);//Y坐标轴：第二个参数是起始点，第三个是结束点坐标，下行X轴同理
//                PropertyValuesHolder xProperty = PropertyValuesHolder
//                        .ofFloat("x", midX, endX);
//                ObjectAnimator animEnd = ObjectAnimator.ofPropertyValuesHolder(imageView,
//                        xProperty, yProperty);//创建动画对象，把所有属性拼起来
//                animEnd.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        imageView.setImageDrawable(null);
//                        removeView(imageView);
//                    }
//                });
//                animEnd.setDuration(1000).start();
//            }
//        });
        PropertyValuesHolder yProperty;
        PropertyValuesHolder xProperty;
        if (endX == 0 && endY == 0){
            yProperty = PropertyValuesHolder.ofFloat("y", minY, minY);//Y坐标轴：第二个参数是起始点，第三个是结束点坐标，下行X轴同理
            xProperty = PropertyValuesHolder.ofFloat("x", midX, midX);
        }else{
            yProperty = PropertyValuesHolder.ofFloat("y", minY, endY);//Y坐标轴：第二个参数是起始点，第三个是结束点坐标，下行X轴同理
            xProperty = PropertyValuesHolder.ofFloat("x", midX, endX);
        }
        ObjectAnimator animEnd = ObjectAnimator.ofPropertyValuesHolder(imageView,
                xProperty, yProperty);//创建动画对象，把所有属性拼起来
        animEnd.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                imageView.setImageDrawable(null);
                removeView(imageView);
//                final DCBTextView mNumberImg = new DCBTextView(context);
//                mNumberImg.setText("魅力+ "+mBean.getGiftNum());
//                mNumberImg.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
//                mNumberImg.setTextColor(Color.parseColor("#FFB700"));
//                mNumberImg.setTextSize(20f);
//                addView(mNumberImg);
//                //数字加1的动画效果组合有：位移动画从指定坐标点移动到指定目标坐标点，并带有透明度变化的属性动画
//                PropertyValuesHolder yProperty = PropertyValuesHolder
//                        .ofFloat("y", endY, endY - distanceY);//Y坐标轴：第二个参数是起始点，第三个是结束点坐标，下行X轴同理
//                PropertyValuesHolder xProperty = PropertyValuesHolder
//                        .ofFloat("x", endX, endX);
//                PropertyValuesHolder alphaProperty = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);//设置透明度的动画属性，过渡到0.1f透明度
//                //动画效果：目标View逐步变大，X轴和Y轴两个方向
//                ObjectAnimator animEnd = ObjectAnimator.ofPropertyValuesHolder(mNumberImg,
//                        xProperty, yProperty, alphaProperty);//创建动画对象，把所有属性拼起来
//                animEnd.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        mNumberImg.setText("");
//                        removeView(mNumberImg);
//                    }
//                });
//                animEnd.setDuration(900).start();
            }
        });
        animEnd.setStartDelay(500);
        animEnd.setDuration(1000);
        animatorSet.play(objectAnimator01).with(startAnim).with(objectAnimator05).before(animEnd);
//        changeAmin.setDuration(1000);
        animatorSet.setStartDelay(1500);
        animatorSet.start();
    }

    public void startAnimator(final GiftMsgBean mBean){
        final int startX = (int) (width/2);
        final int startY = (int) (height/2);
        final int endX = mBean.getEndPoint().getX();
        final int endY = mBean.getEndPoint().getY();
        final ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new FrameLayout.LayoutParams(60,60));
        imageView.setEnabled(true);
        imageView.setX(width);
        imageView.setY(0);
        addView(imageView);
        ImgUtil.INSTANCE.loadFaceIcon(context,mBean.getGiftIcon(),imageView);
        ObjectAnimator objectAnimator01 = ObjectAnimator.ofFloat(imageView,"scaleX",1f,4f,2f);
        objectAnimator01.setDuration(2000);
        ObjectAnimator objectAnimator05 = ObjectAnimator.ofFloat(imageView,"scaleY",1f,4f,2f);
        objectAnimator05.setDuration(2000);
        ObjectAnimator objectAnimator03 = ObjectAnimator.ofFloat(imageView,"alpha",1,0);
        objectAnimator03.setDuration(2000);
        final int mWidthPixels = Math.abs(startX - endX);
        final int mHeightPixels = Math.abs(startY - endY);
        ValueAnimator mValueAnimator = ValueAnimator.ofObject(new BezierEvaluator(mWidthPixels,mHeightPixels)
                , new PointF(startX, startY), new PointF(endX, endY));
        mValueAnimator.setDuration(2000);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                imageView.setX(pointF.x);
                imageView.setY(pointF.y);
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        objectAnimator03.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imageView.setImageDrawable(null);
                removeView(imageView);
//                final DCBTextView mNumberImg = new DCBTextView(context);
//                mNumberImg.setText("魅力+ "+mBean.getGiftNum());
//                mNumberImg.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
//                mNumberImg.setTextColor(Color.parseColor("#FFB700"));
//                mNumberImg.setTextSize(20f);
//                addView(mNumberImg);
//                //数字加1的动画效果组合有：位移动画从指定坐标点移动到指定目标坐标点，并带有透明度变化的属性动画
//                PropertyValuesHolder yProperty = PropertyValuesHolder
//                        .ofFloat("y", endY, endY - distanceY);//Y坐标轴：第二个参数是起始点，第三个是结束点坐标，下行X轴同理
//                PropertyValuesHolder xProperty = PropertyValuesHolder
//                        .ofFloat("x", endX, endX);
//                PropertyValuesHolder alphaProperty = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);//设置透明度的动画属性，过渡到0.1f透明度
//                //动画效果：目标View逐步变大，X轴和Y轴两个方向
//                ObjectAnimator animEnd = ObjectAnimator.ofPropertyValuesHolder(mNumberImg,
//                        xProperty, yProperty, alphaProperty);//创建动画对象，把所有属性拼起来
//                animEnd.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        mNumberImg.setText("");
//                        removeView(mNumberImg);
//                    }
//                });
//                animEnd.setDuration(900).start();
            }
        });
        animatorSet.play(objectAnimator01).with(mValueAnimator).before(objectAnimator03).with(objectAnimator05);
        animatorSet.start();
    }

    private boolean isRewardRunning;
    private void showRewardGiftAnim(String msg){
//        rewardFl.setVisibility(VISIBLE);
//        rewardTv.setText(msg);
//        ObjectAnimator objectAnimator03 = ObjectAnimator.ofFloat(rewardFl,"alpha",1,0);
//        objectAnimator03.setDuration(3000);
//        objectAnimator03.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                isRewardRunning = true;
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                isRewardRunning = false;
//                rewardFl.setVisibility(GONE);
//                sendAnimMsg(LUCK_REWARD_FLAG);
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
//        objectAnimator03.start();
    }
    public void XqResult(int Xqresult, XqResultBean xqResultBean){
        this.xqResultBean=xqResultBean;
       sendAnimMsg(Xqresult);
    }
    private void showSvgaXqtAnim(final String url){
        if (parser == null){
            parser = new SVGAParser(context);
        }
        svgaXQImageView.setCallback(new SVGACallback() {
            @Override
            public void onPause() {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onRepeat() {
                svgaXQImageView.stopAnimation();
            }

            @Override
            public void onStep(int i, double v) {

            }
        });
        parser.decodeFromAssets(url, new SVGAParser.ParseCompletion() {
            @Override
            public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                if(url.equals("xiangqin_success.svga")){
                    SVGADynamicEntity dynamicEntity = new SVGADynamicEntity();
                    dynamicEntity.setDynamicImage(xqResultBean.getXiangqing_head().getZhu(), "head01"); //nvt是替换的图片的名称
                    dynamicEntity.setDynamicImage(xqResultBean.getXiangqing_head().getCi(), "head02");
                    SVGADrawable drawable = new SVGADrawable(videoItem, dynamicEntity);
                    svgaXQImageView.setImageDrawable(drawable);
                    svgaXQImageView.stepToFrame(0, true);
                }else{
                    SVGADrawable drawable = new SVGADrawable(videoItem);
                    svgaXQImageView.setImageDrawable(drawable);
                    svgaXQImageView.startAnimation();
                }




            }
            @Override
            public void onError() {
            }
        });
    }

    private void showSvgaGiftAnim(final SVGUrlBean bean){
        if (isSVGAPlaying){
            return;
        }
        isSVGAPlaying = true;
        if (parser == null){
            parser = new SVGAParser(context);
        }
        svgaImageView.setCallback(new SVGACallback() {
            @Override
            public void onPause() {

            }

            @Override
            public void onFinished() {
                isSVGAPlaying = false;
                sendAnimMsg(SVGA_ANIM_FLAG);
            }

            @Override
            public void onRepeat() {
                svgaImageView.stopAnimation();
            }

            @Override
            public void onStep(int i, double v) {

            }
        });
        try {
            String url = bean.getGiftUrl();
            parser.decodeFromURL(new URL(url), new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                    if (bean.getType() == GiftManager.GIFT_TYPE_CHEST){
                        showChestGiftAnim(bean.getNum(),bean.getGifIcon(),bean.getStartPosition(),bean.getMidPosition(),bean.getEndPosition());
                    }
                    SVGADynamicEntity dynamicItem = new SVGADynamicEntity();
                    SVGADrawable drawable = new SVGADrawable(videoItem, dynamicItem);
                    TextPaint textPaint = new TextPaint();
                    textPaint.setTextSize(20);
                    textPaint.setARGB(0xff, 0xf7, 0xff, 0x05);
                    dynamicItem.setDynamicText(bean.getContent(), textPaint, "banner");
                    svgaImageView.setImageDrawable(drawable);
                    svgaImageView.startAnimation();
                }
                @Override
                public void onError() {
                    isSVGAPlaying = false;
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
            isSVGAPlaying = false;
        }
    }

    private void sendAnimMsg(int type){
        handler.sendEmptyMessage(type);
    }

    @SuppressLint("HandlerLeak")
    class MyHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == SVGA_ANIM_FLAG){
                if (mSvgQueue.peek()== null){
                    return;
                }
                showSvgaGiftAnim(mSvgQueue.poll());
            }
            if (msg.what == XIANGQING_FAIL){
                AudioPlayer.getInstance().openAssetMusics(context,"hand_in_hand_fail.mp3");
                showSvgaXqtAnim("xiangqin_fail.svga");
            }
            if (msg.what == XIANGQING_SUCCESS){
                AudioPlayer.getInstance().openAssetMusics(context,"hand_in_hand_success.mp3");
                showSvgaXqtAnim("xiangqin_success.svga");
            }
            if (msg.what == SVGA_ANIM_FLAG){
                if (mSvgQueue.peek()== null){
                    return;
                }
                showSvgaGiftAnim(mSvgQueue.poll());
            }
            if (msg.what == SVGA_ANIM_FLAG){
                if (mSvgQueue.peek()== null){
                    return;
                }
                showSvgaGiftAnim(mSvgQueue.poll());
            }
            if (msg.what == NOBLE_ONLINE_SVGA_ANIM_FLAG){
                if (mNobleOnlineSvgQueue.peek()== null){
                    return;
                }
                showNobleOnlineSvgaGiftAnim(mNobleOnlineSvgQueue.poll());
            }
//            if (msg.what == LUCK_ANIM_FLAG){
//                if (mLuckQueue.size() == 0){
//                    return;
//                }
//                if (mLuckQueue.peek()== null){
//                    return;
//                }
//                GiftMsgBean bean = mLuckQueue.poll();
//                if (!showLuckGiftAnim(bean)){
//                    mLuckQueue.add(bean);
//                }
//                sendAnimMsg(LUCK_ANIM_FLAG);
//            }
            if (msg.what == TOP_NOTIFY_ANIM_FLAG){
                if (mTopNotifyQueue.peek()== null){
                    return;
                }
                if (!mGiftTopNotifyAnim.isRunning){
                    mGiftTopNotifyAnim.mStartAnimation(mTopNotifyQueue.poll(), new GiftTopNotifyAnim.onAnimationListener() {
                        @Override
                        public void onEnd() {
                            sendAnimMsg(TOP_NOTIFY_ANIM_FLAG);
                        }
                    });
                }
            }

            if (msg.what == TOP_NOTIFY_ANIM_EXPRESS_FLAG){
                if (mExpressTopNotifyQueue.peek()== null){
                    return;
                }
                if (!expressGiftTopNotifyAnim.isRunning){
                    expressGiftTopNotifyAnim.mStartAnimation(mExpressTopNotifyQueue.poll(), new ExpressGiftTopNotifyAnim.onAnimationListener() {
                        @Override
                        public void onEnd() {
                            sendAnimMsg(TOP_NOTIFY_ANIM_EXPRESS_FLAG);
                        }
                    });
                }
            }

            if (msg.what == ENTER_ROOM_ANIM_FLAG){
                if (mEnterRoomQueue.peek()== null){
                    return;
                }
                if (!enterRoomView.isRunning){
                    enterRoomView.mStartAnimation(mEnterRoomQueue.poll(), new EnterRoomNotifyView.onAnimationListener() {
                        @Override
                        public void onEnd() {
                            sendAnimMsg(ENTER_ROOM_ANIM_FLAG);
                        }
                    });
                }
            }
//            if (msg.what == LUCK_REWARD_FLAG){
//                if (mRewardQueue.peek()== null){
//                    return;
//                }
//                showRewardGiftAnim(mRewardQueue.poll());
//            }
        }
    }


    private void showNobleOnlineSvgaGiftAnim(final NobleOnlineBean bean){
        final int rankId = bean.getRankId();
        if (isNobleOnlieSVGAPlaying){
            return;
        }
        isNobleOnlieSVGAPlaying = true;
        if (nobleOnlieParser == null){
            nobleOnlieParser = new SVGAParser(context);
        }
        nobleOnlieSvgaIv.setCallback(new SVGACallback() {
            @Override
            public void onPause() {

            }

            @Override
            public void onFinished() {
                isNobleOnlieSVGAPlaying = false;
                sendAnimMsg(NOBLE_ONLINE_SVGA_ANIM_FLAG);
            }

            @Override
            public void onRepeat() {
                nobleOnlieSvgaIv.stopAnimation();
            }

            @Override
            public void onStep(int i, double v) {

            }
        });
        String name = "noble_online_anim_dh.svga";
        if (rankId == BaseConfig.NOBLE_LEVEL_DH){
            name = "noble_online_anim_dh.svga";
        }
        if (rankId == BaseConfig.NOBLE_LEVEL_HS){
            name = "noble_online_anim_hs.svga";
        }
        if (rankId == BaseConfig.NOBLE_LEVEL_GW){
            name = "noble_online_anim_gw.svga";
        }
        nobleOnlieParser.decodeFromAssets(name, new SVGAParser.ParseCompletion() {
            @Override
            public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                if (rankId == BaseConfig.NOBLE_LEVEL_HS){
                    final SVGADynamicEntity dynamicEntity = new SVGADynamicEntity();

                    ImgUtil.INSTANCE.getCircleBitmap(getContext(), bean.getFromUserAvtar(), new ImgUtil.OnGetBitmap() {
                        @Override
                        public void onGetBitmap(@NotNull Bitmap resource) {
                            dynamicEntity.setDynamicImage(resource, "70x70");
                        }
                    });

                    TextPaint textPaint = new TextPaint();
                    textPaint.setTextSize(DensityUtil.INSTANCE.dp2px(context,5f));
                    textPaint.setARGB(0xFF, 0xFF, 0xFF, 0xFF);
//                    dynamicEntity.setDynamicText(toUserInfo.getNickname(), textPaint, "receiver_nickname");
                    dynamicEntity.setDynamicText(bean.getFromUserNickname()+"上线了！", textPaint, "120x14");
                    SVGADrawable drawable = new SVGADrawable(videoItem, dynamicEntity);
                    nobleOnlieSvgaIv.setImageDrawable(drawable);
                    nobleOnlieSvgaIv.startAnimation();
                }

                if (rankId == BaseConfig.NOBLE_LEVEL_GW){
                    final SVGADynamicEntity dynamicEntity = new SVGADynamicEntity();

                    TextPaint textPaint = new TextPaint();
                    textPaint.setTextSize(DensityUtil.INSTANCE.dp2px(context,14f));
                    textPaint.setARGB(0xFF, 0xFF, 0xFF, 0xFF);
                    dynamicEntity.setDynamicText(bean.getFromUserNickname()+"上线了！", textPaint, "112_00018");
                    SVGADrawable drawable = new SVGADrawable(videoItem, dynamicEntity);
                    nobleOnlieSvgaIv.setImageDrawable(drawable);
                    nobleOnlieSvgaIv.startAnimation();
                }

                if (rankId == BaseConfig.NOBLE_LEVEL_DH){
                    final SVGADynamicEntity dynamicEntity = new SVGADynamicEntity();

                    TextPaint textPaint = new TextPaint();
                    textPaint.setTextSize(DensityUtil.INSTANCE.dp2px(context,25f));
                    textPaint.setARGB(0xFF, 0xFF, 0xFF, 0xFF);
                    dynamicEntity.setDynamicText(bean.getFromUserNickname()+"上线了！", textPaint, "img_3841908034");
                    SVGADrawable drawable = new SVGADrawable(videoItem, dynamicEntity);
                    nobleOnlieSvgaIv.setImageDrawable(drawable);
                    nobleOnlieSvgaIv.startAnimation();
                }
            }
            @Override
            public void onError() {
                isNobleOnlieSVGAPlaying = false;
            }
        });
    }

}
