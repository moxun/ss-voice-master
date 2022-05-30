package com.miaomi.fenbei.gift.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.miaomi.fenbei.gift.R;

public class GiftNumAnimView extends LinearLayout {
    private static final String TAG = "Scroller";

    private Scroller mScroller;

    // 每个数字的高
    int item_height;
    // 0-9 一轮数字的高度
    int CIRCLE_HEIGHT;
    Context context;

    public final static int DURATION = 300;
    MyHandler handler = new MyHandler();

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == -1) {
                setFinal(0, 0);
            } else if (!mScroller.isFinished() && isShown()) {
                this.removeCallbacksAndMessages(null);
                Log.d("handleMessage-->", msg.what + "");
                this.sendEmptyMessageDelayed(msg.what, 100);
            } else {
                int number = msg.what;
                if (number == -1) {
                    setFinal(0, 0);
                } else {

                    int currY = mScroller.getCurrY();
                    // 首先判断当前数字是否在第二轮，是的话把数字定位到第一轮的同一数字，因为数字只能往下滚，要留足滚动空间
                    if (currY > CIRCLE_HEIGHT) {
                        setFinal(0, mScroller.getCurrY() - CIRCLE_HEIGHT);
                    }
                    int lastY = mScroller.getFinalY();
                    int y = number * item_height;
                    Log.d("number ", number + " lastY " + lastY + " y " + y);
                    // 判断新的position是否比原来的小，是的话要跳滚动到下一轮数字中，保证数字永远都是向下滚动的
                    if (lastY > y) {
                        smoothScrollTo(0, CIRCLE_HEIGHT + y, DURATION);
                    } else {
                        smoothScrollTo(0, y, DURATION);
                    }
                }
            }
        }
    }

    private int[] numbers = {R.drawable.gift_icon_anim_luck_0, R.drawable.gift_icon_anim_luck_1, R.drawable.gift_icon_anim_luck_2, R.drawable.gift_icon_anim_luck_3, R.drawable.gift_icon_anim_luck_4
            , R.drawable.gift_icon_anim_luck_5, R.drawable.gift_icon_anim_luck_6, R.drawable.gift_icon_anim_luck_7, R.drawable.gift_icon_anim_luck_8, R.drawable.gift_icon_anim_luck_9
            , R.drawable.gift_icon_anim_luck_0, R.drawable.gift_icon_anim_luck_1, R.drawable.gift_icon_anim_luck_2, R.drawable.gift_icon_anim_luck_3, R.drawable.gift_icon_anim_luck_4
            , R.drawable.gift_icon_anim_luck_5, R.drawable.gift_icon_anim_luck_6, R.drawable.gift_icon_anim_luck_7, R.drawable.gift_icon_anim_luck_8, R.drawable.gift_icon_anim_luck_9};

    public GiftNumAnimView(Context context) {
        super(context);
        init(context);
    }

    public GiftNumAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        mScroller = new Scroller(context, new AccelerateDecelerateInterpolator());
        item_height = dip2px(context, 28);
        CIRCLE_HEIGHT = item_height * 10;
        setOrientation(VERTICAL);
        for (int i = 0; i < numbers.length; i++) {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(dip2px(context, 16), dip2px(context, 28));
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(numbers[i]);
            imageView.setLayoutParams(layoutParams);
            addView(imageView);
        }
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(dip2px(context, 16), dip2px(context, 28));
        setLayoutParams(layoutParams);
    }

    public void forceFinished() {
        // 相当于暂停了正在进行的滑动动画
        mScroller.forceFinished(true);
    }

    public void reset() {
        handler.sendEmptyMessage(-1);
    }

    public void destroy() {
        handler.removeCallbacksAndMessages(null);
//        handler = null;
    }

    //设置mScroller最终停留的\位置，没有动画效果，直接跳到目标位置
    private void setFinal(int newX, int newY) {
//        mScroller.setFinalX(newX);
//        mScroller.setFinalY(newY);
//        invalidate();
        smoothScrollTo(newX,newY,250);
    }

    // 平滑滚动到某个数字
    public void smoothToPosition(int number) {
        handler.sendEmptyMessage(number);
    }

    // 立刻跳到某个位置
    public void scrollToPosition(int number) {
        setVisibility(VISIBLE);
        setFinal(0, number * item_height);
    }

    //调用此方法滚动到目标位置
    private void smoothScrollTo(int fx, int fy, int duration) {

        setVisibility(VISIBLE);
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx, dy, duration);
    }

    //调用此方法设置滚动的相对偏移
    private void smoothScrollBy(int dx, int dy, int duration) {

        //设置mScroller的滚动偏移量
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy, duration);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    @Override
    public void computeScroll() {

        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {

            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        super.computeScroll();
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        try {
            if (context != null) {
                if (context.getResources() != null) {
                    final float scale = context.getResources().getDisplayMetrics().density;
                    return (int) (dipValue * scale + 0.5f);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


}
