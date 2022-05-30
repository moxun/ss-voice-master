package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import androidx.customview.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.miaomi.fenbei.base.R;


/**
 * Created by lzq on 2017/12/18.
 */

public class SwipeMenuLayout extends FrameLayout {

    private static SwipeMenuLayout mCacheView;
    private View contentView;
    private View menuView;
    private ViewDragHelper mDragHelper;
    private Point originPos = new Point();
    private boolean isOpen, ios, clickToClose;
    private float offset;
    private float needOffset = 0.2f;
    private SwipeListener mListener;
    private int TouchSlop ;

    private boolean isCloseTranslation = false;

    public SwipeMenuLayout(Context context) {
        this(context, null);
        TouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public SwipeMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        TouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public SwipeMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SwipeDragLayout);
        needOffset = array.getFloat(R.styleable.SwipeDragLayout_need_offset, 0.2f);
        //是否有回弹效果
        ios = array.getBoolean(R.styleable.SwipeDragLayout_ios, true);
        clickToClose = array.getBoolean(R.styleable.SwipeDragLayout_click_to_close, false);
        init();
        array.recycle();
    }

    public static SwipeMenuLayout getmCacheView() {
        return mCacheView;
    }

    //初始化dragHelper，对拖动的view进行操作
    private void init() {
        mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {


            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == contentView;
            }


            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if (releasedChild == contentView) {
                    if (isOpen()) {
                        if (offset != 1 && offset > (1 - needOffset)) {
                            open();
                        } else if (offset == 1) {
                            if (clickToClose) {
                                close();
                            }
                        } else {
                            close();

                        }
                    } else {
                        if (offset != 0 && offset < needOffset) {
                            close();
                        } else if (offset == 0) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        } else {
                            open();
                            Log.d("Released and isOpen", "" + isOpen);
                            if (mListener != null) {
                                mListener.onOpened(SwipeMenuLayout.this);
                            }
                        }
                    }
                    invalidate();
                }
            }


            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                //滑动距离,如果启动效果，则可滑动3/2倍菜单宽度的距离
                final int leftBound = getPaddingLeft() - (ios ? menuView.getWidth() * 3 / 2 : menuView.getWidth());
                final int rightBound = getWidth() - child.getWidth();
                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
                return newLeft;
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return contentView == child ? menuView.getWidth() : 0;
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                final int childWidth = menuView.getWidth();
                offset = -(float) (left - getPaddingLeft()) / childWidth;
                //offset can callback here
                if (mListener!=null){
                    mListener.onUpdate(SwipeMenuLayout.this,offset);
                }
            }


        });

    }

    public void setClickToClose(boolean clickToClose) {
        this.clickToClose = clickToClose;
    }

    public void setIos(boolean ios) {
        this.ios = ios;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void open() {
        mCacheView = SwipeMenuLayout.this;
        mDragHelper.settleCapturedViewAt(originPos.x - menuView.getWidth(), originPos.y);
        isOpen = true;
    }

    public void smoothOpen(boolean smooth) {
        mCacheView = SwipeMenuLayout.this;
        if (smooth) {
            mDragHelper.smoothSlideViewTo(contentView, originPos.x - menuView.getWidth(), originPos.y);
        } else {
            contentView.layout(originPos.x - menuView.getWidth(), originPos.y, menuView.getLeft(), menuView.getBottom());
        }
    }

    private void smoothClose(boolean smooth) {
        if (smooth) {
            mDragHelper.smoothSlideViewTo(contentView, getPaddingLeft(), getPaddingTop());
            postInvalidate();
        } else {
            contentView.layout(originPos.x, originPos.y, menuView.getRight(), menuView.getBottom());
        }
        isOpen = false;
        mCacheView = null;

    }



    public void close() {
        mDragHelper.settleCapturedViewAt(originPos.x, originPos.y);
        isOpen = false;
        mCacheView = null;
        if (mListener != null) {
            mListener.onClosed(SwipeMenuLayout.this);
        }
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        int startX = 0;
//        int startY = 0;
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                startX= (int) ev.getX();
//                startY= (int) ev.getY();
//            case MotionEvent.ACTION_HOVER_MOVE:
//                int dX= (int) (ev.getX()-startX);
//                int dY= (int) (ev.getY()-startY);
//                if(Math.abs(dX)>Math.abs(dY)){//左右滑动
//                    Log.i("lzq","左右滑动");
//                    getParent().requestDisallowInterceptTouchEvent(true);
//                }else {//上下滑动
//                    Log.i("lzq","上下滑动");
//                    return mDragHelper.shouldInterceptTouchEvent(ev);
//                }
//
//        }
//        return super.dispatchTouchEvent(ev);
//    }

    float startX = 0;
    float startY = 0;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                if (mCacheView != null) {
//                    if (mCacheView != this) {
//                        mCacheView.smoothClose(true);
//                    }
//                }
//                getParent().requestDisallowInterceptTouchEvent(true);
//                break;
//
//        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX= ev.getX();
                startY=  ev.getY();
                if (mCacheView != null) {
                    if (mCacheView != this) {
                        mCacheView.smoothClose(true);
                    }
                }
            case MotionEvent.ACTION_MOVE:
                float dX= ev.getX()-startX;
                float dY= ev.getY()-startY;
                if(Math.abs(dX)>Math.abs(dY)){
                    //左右滑动
                    getParent().requestDisallowInterceptTouchEvent(true);
                }else {//上下滑动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

        }
        return mDragHelper.shouldInterceptTouchEvent(ev);


    }


    public boolean isCloseTranslation() {
        return isCloseTranslation;
    }

    public void setCloseTranslation(boolean closeTranslation) {
        isCloseTranslation = closeTranslation;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isCloseTranslation){
            return true;
        }
        mDragHelper.processTouchEvent(event);
//        float downX;
//        float upX;
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                downX = event.getRawX();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                break;
//            case MotionEvent.ACTION_UP:
//                upX = event.getRawX();
//                if
//                break;
//
//        }

        return true;
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        originPos.x = contentView.getLeft();
        originPos.y = contentView.getTop();
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(1);
        menuView = getChildAt(0);
        FrameLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT;
        menuView.setLayoutParams(params);
        //重写OnClickListener会导致关闭失效
        if (contentView!=null){
            contentView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickToClose&&isOpen()){
                        smoothClose(true);
                        return;
                    }
                    if (mListener!=null){
                        mListener.onClick(SwipeMenuLayout.this);
                    }

                }
            });
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mCacheView == this) {
            mCacheView.smoothClose(false);
            mCacheView = null;
        }
        super.onDetachedFromWindow();

    }


    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);
    }

    public void addListener(SwipeListener listener) {
        mListener = listener;
    }

    //滑动监听
    public interface SwipeListener {

        /**
         * 拖动中，可根据offset 进行其他动画
         * @param layout
         * @param offset 偏移量
         */
        void onUpdate(SwipeMenuLayout layout, float offset);

        /**
         * 展开完成
         * @param layout
         */
        void onOpened(SwipeMenuLayout layout);

        /**
         * 关闭完成
         * @param layout
         */
        void onClosed(SwipeMenuLayout layout);

        /**
         * 点击内容layout {@link #onFinishInflate()}
         * @param layout
         */
        void onClick(SwipeMenuLayout layout);
    }

}
