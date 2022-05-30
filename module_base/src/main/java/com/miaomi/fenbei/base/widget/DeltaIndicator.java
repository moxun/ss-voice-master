package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.miaomi.fenbei.base.R;
import com.miaomi.fenbei.base.util.DensityUtil;


public class DeltaIndicator extends LinearLayout implements ViewPager.OnPageChangeListener {
    private Paint mPaint; // 画指示符的paint

    private int mTop; // 指示符的top
    private int mLeft; // 指示符的left
    private int mWidth; // 指示符的width
    private int mHeight = 5; // 指示符的高度，固定了
    private int mChildCount; // 子item的个数，用于计算指示符的宽度

    /**
     * 初始时，三角形指示器的偏移量
     */
    private int mInitTranslationX;

    /**
     * path构成一个三角形
     */
    private Path mPath;
    /**
     * 三角形的宽度
     */
    private int mTriangleWidth;
    /**
     * 三角形的高度
     */
    private int mTriangleHeight = 20;

    /**
     * 三角形的宽度为单个Tab的1/6
     */
    private static final float RADIO_TRIANGEL = 1.0f / 6;
    /**
     * 三角形的最大宽度
     */
    private final int DIMENSION_TRIANGEL_WIDTH = 30;
    private ViewPager mViewPager;
    /**
     * 手指滑动时的偏移量
     */
    private float mTranslationX;
    private float childwidth;

    private int type;
    private int paintColor;
    private boolean isScale;
    private Context context;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DeltaIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setBackgroundColor(Color.TRANSPARENT);  // 必须设置背景，否则onDraw不执行
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DeltaIndicator);
        type = ta.getInt(R.styleable.DeltaIndicator_type,0);
        paintColor = ta.getColor(R.styleable.DeltaIndicator_indictorColor,Color.parseColor("#FFFFFF"));
        isScale = ta.getBoolean(R.styleable.DeltaIndicator_isScale,false);
        childwidth = ta.getFloat(R.styleable.DeltaIndicator_indictorWidth,80);
        ta.recycle();  //注意回收

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(paintColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(3));

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        mTriangleWidth = (int) (w / getChildCount() * RADIO_TRIANGEL);// 1/6 of
        // width
        mTriangleWidth = Math.min(DIMENSION_TRIANGEL_WIDTH, mTriangleWidth);

        // 初始化三角形
        initTriangle();
//        initRectangle();

        // 初始时的偏移量
        if (type == 1){
            mInitTranslationX = DensityUtil.INSTANCE.dp2px(context,26f);
//            canvas.translate( DensityUtil.INSTANCE.dp2px(context,30f)+mTranslationX, getHeight() + 1);
//            canvas.drawRect(new RectF(-10,-8,40,0),mPaint);
        }else if(type == 2){
            mInitTranslationX = DensityUtil.INSTANCE.dp2px(context,10f);
//            canvas.translate(DensityUtil.INSTANCE.dp2px(context,10f)+mTranslationX, getHeight() + 1);
//            canvas.drawRoundRect(new RectF(0,-18,DensityUtil.INSTANCE.dp2px(context,childwidth),0), 20, 15, mPaint);
//            canvas.drawOval(new RectF(0,-18,55,0),mPaint);
        }else if(type == 3){
            mInitTranslationX = getWidth() / getChildCount() / 2 - mTriangleWidth
                    / 2;
//            canvas.drawOval(new RectF(0,-18,55,0),mPaint);
        }else{
            mInitTranslationX = getWidth() / getChildCount() / 2 - mTriangleWidth
                    / 2;
        }
    }


    /**
     * 初始化三角形指示器
     */
    private void initTriangle()
    {
        mPath = new Path();

//        mTriangleHeight = (int) (mTriangleWidth / 2 / Math.sqrt(2));
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();
    }



    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mChildCount = getChildCount();  // 获取子item的个数
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mTop = getMeasuredHeight(); // 测量的高度即指示符的顶部位置
        int width = getMeasuredWidth(); // 获取测量的总宽度
        int height = mTop + mHeight; // 重新定义一下测量的高度
        mWidth = width / mChildCount; // 指示符的宽度为总宽度/item的个数

        setMeasuredDimension(width, height);
    }


    /**
     * 设置点击事件
     */
    public void setItemClickEvent()
    {
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++)
        {
            final int j = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mViewPager.setCurrentItem(j);
                }
            });
        }
    }

    private void scaleView(int position){
        for (int i = 0; i < getChildCount(); i++)
        {
            View view = getChildAt(i);
            if (i == position){
                if (isScale){
                    view.setScaleX(1.14f);
                    view.setScaleY(1.14f);
                }
//                view.setAlpha(1f);
                view.setSelected(true);
            }else{
//                view.setAlpha(0.5f);
                view.setScaleX(1);
                view.setScaleY(1);
                view.setSelected(false);
            }
        }

    }


    /**
     * 指示符滚动
     * @param position 现在的位置
     * @param offset  偏移量 0 ~ 1
     */
    public void scroll(int position, float offset) {
        mLeft = (int) ((position + offset) * mWidth);
        mTranslationX = getWidth() / getChildCount() * (position + offset);

        invalidate();
    }


    public void setViewPager(ViewPager viewPager){
        viewPager.setOnPageChangeListener(this);
        this.mViewPager = viewPager;
        setItemClickEvent();
        scaleView(0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        // 画笔平移到正确的位置
        if (type == 1){
            canvas.translate(mInitTranslationX + mTranslationX, getHeight() + 1);
//            canvas.translate( DensityUtil.INSTANCE.dp2px(context,30f)+mTranslationX, getHeight() + 1);
//            canvas.drawRect(new RectF(0,-8,40,0),mPaint);
            canvas.drawRoundRect(new RectF(0,-8,DensityUtil.INSTANCE.dp2px(context,childwidth),0), 20, 20, mPaint);
        }else if(type == 2){
            canvas.translate(mInitTranslationX + mTranslationX, getHeight() + 1);
//            canvas.translate(DensityUtil.INSTANCE.dp2px(context,10f)+mTranslationX, getHeight() + 1);
            canvas.drawRoundRect(new RectF(0,-18,DensityUtil.INSTANCE.dp2px(context,childwidth),0), 20, 15, mPaint);
//            canvas.drawOval(new RectF(0,-18,55,0),mPaint);
        }else if(type == 3){
            canvas.translate(mInitTranslationX + mTranslationX, getHeight() + 1);
//            canvas.translate(mInitTranslationX + mTranslationX, getHeight() + 1);
            canvas.drawRoundRect(new RectF(-10,-8,40,0), 20, 20, mPaint);
//            canvas.drawOval(new RectF(0,-18,55,0),mPaint);
        }else{
            canvas.translate(mInitTranslationX + mTranslationX, getHeight() + 1);
            canvas.drawPath(mPath, mPaint);
        }
        canvas.restore();

        super.dispatchDraw(canvas);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        scroll(position,positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
        scaleView(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
