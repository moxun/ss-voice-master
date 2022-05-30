package com.miaomi.fenbei.room.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;

import androidx.annotation.AnimRes;

import android.text.Html;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.miaomi.fenbei.room.R;
import com.miaomi.fenbei.base.bean.MsgBean;
import com.miaomi.fenbei.base.core.ViewSizeChangeAnimation;
import com.miaomi.fenbei.base.util.DensityUtil;
import com.miaomi.fenbei.base.util.ImgUtil;

import java.util.List;

/**
 *
 * @描述 文字自动轮播（跑马灯）
 * @author luoweichao
 * @email superluo666@gmail.com
 * @date 2018/3/28/028 21:21
 *
 */
public class TextBannerView extends RelativeLayout {
    private ViewFlipper mViewFlipper;
    private int mInterval = 3000;/**文字切换时间间隔,默认3s*/
    private boolean isSingleLine = false;/**文字是否为单行,默认false*/
    private int mTextColor = 0xff000000;/**设置文字颜色,默认黑色*/
    private Float mTextSize = 16f; /**设置文字尺寸,默认16px*/
    private int mGravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;/**文字显示位置,默认左边居中*/
    private static final int GRAVITY_LEFT = 0;
    private static final int GRAVITY_CENTER = 1;
    private static final int GRAVITY_RIGHT = 2;

    private boolean hasSetDirection = false;
    private int direction = DIRECTION_TOP_TO_BOTTOM;
    private static final int DIRECTION_BOTTOM_TO_TOP = 0;
    private static final int DIRECTION_TOP_TO_BOTTOM = 1;
    private static final int DIRECTION_RIGHT_TO_LEFT = 2;
    private static final int DIRECTION_LEFT_TO_RIGHT = 3;
    @AnimRes
    private int inAnimResId = R.anim.anim_right_in;
    @AnimRes
    private int outAnimResId = R.anim.anim_left_out;
    private boolean hasSetAnimDuration = false;
    private int animDuration = 1500;/**默认1.5s*/
    private int mFlags = -1;/**文字划线*/
    private static final int STRIKE = 0;
    private static final int UNDER_LINE = 1;
    private int mTypeface = Typeface.NORMAL;/**设置字体类型：加粗、斜体、斜体加粗*/
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_BOLD = 1;
    private static final int TYPE_ITALIC = 2;
    private static final int TYPE_ITALIC_BOLD = 3;

    private List<MsgBean> mDatas;
    private List<String> mStrDatas;
    private ITextBannerItemClickListener mListener;
    private boolean isAutoWidth = false;
    private boolean isStarted;
    private boolean isDetachedFromWindow;


    public TextBannerView(Context context) {
        this(context,null);
    }

    public TextBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    /**初始化控件*/
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextBannerViewStyle, defStyleAttr, 0);
        mInterval = typedArray.getInteger(R.styleable.TextBannerViewStyle_setInterval, mInterval);//文字切换时间间隔
        isSingleLine = typedArray.getBoolean(R.styleable.TextBannerViewStyle_setSingleLine, false);//文字是否为单行
        mTextColor = typedArray.getColor(R.styleable.TextBannerViewStyle_setTextColor, mTextColor);//设置文字颜色
        if (typedArray.hasValue(R.styleable.TextBannerViewStyle_setTextSize)) {//设置文字尺寸
            mTextSize = typedArray.getDimension(R.styleable.TextBannerViewStyle_setTextSize, mTextSize);
            mTextSize = DensityUtil.INSTANCE.px2sp(context, mTextSize);
        }
        int gravityType = typedArray.getInt(R.styleable.TextBannerViewStyle_setGravity, GRAVITY_LEFT);//显示位置
        switch (gravityType) {
            case GRAVITY_LEFT:
                mGravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
                break;
            case GRAVITY_CENTER:
                mGravity = Gravity.CENTER;
                break;
            case GRAVITY_RIGHT:
                mGravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
                break;
        }
        hasSetAnimDuration = typedArray.hasValue(R.styleable.TextBannerViewStyle_setAnimDuration);
        animDuration = typedArray.getInt(R.styleable.TextBannerViewStyle_setAnimDuration, animDuration);//动画时间
        hasSetDirection = typedArray.hasValue(R.styleable.TextBannerViewStyle_setDirection);
        direction = typedArray.getInt(R.styleable.TextBannerViewStyle_setDirection, direction);//方向
        if (hasSetDirection) {
            switch (direction) {
                case DIRECTION_BOTTOM_TO_TOP:
                    inAnimResId = R.anim.anim_bottom_in;
                    outAnimResId = R.anim.anim_top_out;
                    break;
                case DIRECTION_TOP_TO_BOTTOM:
                    inAnimResId = R.anim.anim_top_in;
                    outAnimResId = R.anim.anim_bottom_out;
                    break;
                case DIRECTION_RIGHT_TO_LEFT:
                    inAnimResId = R.anim.anim_right_in;
                    outAnimResId = R.anim.anim_left_out;
                    break;
                case DIRECTION_LEFT_TO_RIGHT:
                    inAnimResId = R.anim.anim_left_in;
                    outAnimResId = R.anim.anim_right_out;
                    break;
            }
        } else {
            inAnimResId = R.anim.anim_right_in;
            outAnimResId = R.anim.anim_left_out;
        }
        mFlags = typedArray.getInt(R.styleable.TextBannerViewStyle_setFlags, mFlags);//字体划线
        switch (mFlags) {
            case STRIKE:
                mFlags = Paint.STRIKE_THRU_TEXT_FLAG| Paint.ANTI_ALIAS_FLAG;
                break;
            case UNDER_LINE:
                mFlags = Paint.UNDERLINE_TEXT_FLAG| Paint.ANTI_ALIAS_FLAG;
                break;
            default:
                mFlags = 0| Paint.ANTI_ALIAS_FLAG;
                break;
        }
        mTypeface = typedArray.getInt(R.styleable.TextBannerViewStyle_setTypeface, mTypeface);//字体样式
        switch (mTypeface) {
            case TYPE_BOLD:
                mTypeface = Typeface.BOLD;
                break;
            case TYPE_ITALIC:
                mTypeface = Typeface.ITALIC;
                break;
            case TYPE_ITALIC_BOLD:
                mTypeface = Typeface.ITALIC| Typeface.BOLD;
                break;
            default:
                break;
        }


        mViewFlipper = new ViewFlipper(getContext());//new 一个ViewAnimator
        mViewFlipper.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mViewFlipper);
        startViewAnimator();
        //设置点击事件
//        mViewFlipper.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = mViewFlipper.getDisplayedChild();//当前显示的子视图的索引位置
//                if (mListener!=null){
//                    mListener.onItemClick(mDatas.get(position),position);
//                }
//            }
//        });

    }

    /**暂停动画*/
    public void stopViewAnimator(){
        if (isStarted){
            removeCallbacks(mRunnable);
            isStarted = false;
        }
    }

    /**开始动画*/
    public void startViewAnimator(){
        if (!isStarted){
            if (!isDetachedFromWindow){
                isStarted = true;
                postDelayed(mRunnable,mInterval);
            }
        }
    }

    /**
     * 设置延时间隔
     */
    private AnimRunnable mRunnable = new AnimRunnable();
    private class AnimRunnable implements Runnable {

        @Override
        public void run() {
            if (isStarted){
                setInAndOutAnimation(inAnimResId, outAnimResId);
                mViewFlipper.showNext();//手动显示下一个子view。
                if (mViewFlipper.getChildCount() > 0 && isAutoWidth) {
                    mViewFlipper.getCurrentView().measure(View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//                    mViewFlipper.setLayoutParams(new LayoutParams(mViewFlipper.getCurrentView().getMeasuredWidth(), ViewGroup.LayoutParams.MATCH_PARENT));
                    Animation animation = new ViewSizeChangeAnimation(mViewFlipper,ViewGroup.LayoutParams.MATCH_PARENT,  mViewFlipper.getCurrentView().getMeasuredWidth());
                    animation.setDuration(300);
                    AnimationSet animationSet = new AnimationSet(true);
                    animationSet.addAnimation(animation);
                    animationSet.setStartOffset(1000);
                    startAnimation(animationSet);
                }
                postDelayed(this,mInterval + animDuration);
            }else {
                stopViewAnimator();
            }

        }
    }


    /**
     * 设置进入动画和离开动画
     *
     * @param inAnimResId  进入动画的resID
     * @param outAnimResID 离开动画的resID
     */
    private void setInAndOutAnimation(@AnimRes int inAnimResId, @AnimRes int outAnimResID) {
        Animation inAnim = AnimationUtils.loadAnimation(getContext(), inAnimResId);
        inAnim.setDuration(animDuration);
        mViewFlipper.setInAnimation(inAnim);

        Animation outAnim = AnimationUtils.loadAnimation(getContext(), outAnimResID);
        outAnim.setDuration(animDuration);
        mViewFlipper.setOutAnimation(outAnim);
    }


    /**设置数据集合*/
    public void setDatas(List<String> datas, boolean isAutoWidth){
        this.isAutoWidth = isAutoWidth;
        if (mStrDatas != null && mStrDatas.size() == datas.size()) {
            updateDatas(datas);
            return;
        }
        mStrDatas = datas;
        if (!mStrDatas.isEmpty()){
            mViewFlipper.removeAllViews();
            for (int i = 0; i < mStrDatas.size(); i++) {
                TextView textView = new TextView(getContext());
//                TextView textView1 = new TextView(getContext());
                setTextView(textView, i);
//
//                mViewFlipper.addView(textView,i);//添加子view,并标识子view位置

                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);//水平方向
                linearLayout.setGravity(mGravity);//子view显示位置跟随TextView
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT , LinearLayout.LayoutParams.MATCH_PARENT);
//                LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(QMUIDisplayHelper.dp2px(getContext(), 80), LinearLayout.LayoutParams.MATCH_PARENT);
                linearLayout.addView(textView, param);
//                linearLayout.addView(textView1, param1);


                mViewFlipper.addView(linearLayout,i);//添加子view,并标识子view位置
            }
            mViewFlipper.getCurrentView().measure(View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            mViewFlipper.setLayoutParams(new RelativeLayout.LayoutParams(mViewFlipper.getCurrentView().getMeasuredWidth(), RelativeLayout.LayoutParams.MATCH_PARENT));
        }
        if (mStrDatas.size() == 1) {
            stopViewAnimator();
        } else {
            startViewAnimator();
        }
    }



    public void updateDatas(List<String> datas) {
        this.mStrDatas = datas;
        for (int i = 0; i < mViewFlipper.getChildCount(); i++) {
            ((TextView)((LinearLayout)mViewFlipper.getChildAt(i)).getChildAt(0)).setText(Html.fromHtml(mStrDatas.get(i)));
        }
        if (mStrDatas.size() == 1) {
            stopViewAnimator();
        } else {
            startViewAnimator();
        }
    }

//    /**
//     * 设置数据集合伴随drawable-icon
//     * @param datas 数据
//     * @param drawable 图标
//     * @param direction 图标位于文字方位
//     */
//    public void setDatasWithDrawableIcon(List<MsgBean> datas, Drawable drawable, int direction){
//        this.mDatas = datas;
//        if (mDatas.isEmpty()){
//            return;
//        }
//        mViewFlipper.removeAllViews();
//
//        for (int i = 0; i < mDatas.size(); i = i + 2) {
//            View view = View.inflate(getContext(), R.layout.chatting_home_top_msg, null);
//            TextView contentOne = view.findViewById(R.id.content_one_tv);
//            TextView contentTwo = view.findViewById(R.id.content_two_tv);
//            ImageView headerOne = view.findViewById(R.id.header_one_iv);
//            ImageView headerTwo = view.findViewById(R.id.header_two_iv);
//            ImgUtil.INSTANCE.loadCircleImg(getContext(), mDatas.get(i).getFromUserInfo().getFace(), headerOne, R.drawable.common_avter_placeholder);
//            SpannableString spOne = new SpannableString(mDatas.get(i).getFromUserInfo().getNickname() + "：" + mDatas.get(i).getContent());
//            spOne.setSpan(new ForegroundColorSpan(Color.parseColor("#555555")), 0, mDatas.get(i).getFromUserInfo().getNickname().length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            contentOne.setText(spOne);
//            if (mDatas.size() % 2 != 0 && i == mDatas.size() - 1) {
//                contentTwo.setVisibility(View.GONE);
//                headerTwo.setVisibility(View.GONE);
//            } else {
//                contentTwo.setVisibility(View.VISIBLE);
//                headerTwo.setVisibility(View.VISIBLE);
//                ImgUtil.INSTANCE.loadCircleImg(getContext(), mDatas.get(i + 1).getFromUserInfo().getFace(), headerTwo, R.drawable.common_avter_placeholder);
//                SpannableString spTwo = new SpannableString(mDatas.get(i + 1).getFromUserInfo().getNickname() + "：" + mDatas.get(i + 1).getContent());
//                spTwo.setSpan(new ForegroundColorSpan(Color.parseColor("#555555")), 0, mDatas.get(i + 1).getFromUserInfo().getNickname().length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                contentTwo.setText(spTwo);
//            }
//            mViewFlipper.addView(view);
//            mViewFlipper.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        }
//    }
    /**设置TextView*/
    private void setTextView(TextView textView, int position){
        textView.setText(Html.fromHtml(mStrDatas.get(position)));
        //任意设置你的文字样式，在这里
        textView.setSingleLine(isSingleLine);
//        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setTextColor(mTextColor);
        textView.setTextSize(mTextSize);
        textView.setGravity(mGravity);
        textView.getPaint().setFlags(mFlags);//字体划线
        textView.setTypeface(null, mTypeface);//字体样式
        textView.setCompoundDrawablePadding(DensityUtil.INSTANCE.dp2px(getContext(), 2));
//        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.chatting_right_point, 0);
    }

    private void setImageView(ImageView imageView, int position) {
        LayoutParams params = new LayoutParams(DensityUtil.INSTANCE.dp2px(getContext(), 20f), DensityUtil.INSTANCE.dp2px(getContext(), 20f));
        params.leftMargin = DensityUtil.INSTANCE.dp2px(getContext(), 10f);
        imageView.setLayoutParams(params);
        ImgUtil.INSTANCE.loadCircleImg(getContext(), mDatas.get(position).getFromUserInfo().getFace(), imageView, R.drawable.common_avter_placeholder);

    }

//    /**设置TextView*/
//    private void setTextView1(TextView textView, int position){
//        textView.setText(mDatas.get(position).getContent());
//        //任意设置你的文字样式，在这里
//        textView.setSingleLine(true);
//        textView.setEllipsize(TextUtils.TruncateAt.END);
//        textView.setTextColor(Color.parseColor("#E84F41"));
//        textView.setTextSize(15);
//        textView.setGravity(Gravity.CENTER);
//        textView.getPaint().setFlags(mFlags);//字体划线
//        textView.setTypeface(null, Typeface.BOLD);//字体样式
//    }


    /**设置点击监听事件回调*/
    public void setItemOnClickListener(ITextBannerItemClickListener listener){
        this.mListener = listener;
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isDetachedFromWindow=true;
        stopViewAnimator();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isDetachedFromWindow=false;
        startViewAnimator();

    }

    public interface ITextBannerItemClickListener {
        void onItemClick(MsgBean data, int position);
    }

}
