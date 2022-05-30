package com.miaomi.fenbei.gift.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.miaomi.fenbei.gift.R;

public class ChestGiftSendButton extends FrameLayout implements View.OnClickListener {

    //    CircleProgressBarView circleProgressBar;
    TextView timeTv;
    private int position = 0;

    private Handler mHandler;
    private final static int TIME_COUNT = 3000;

    public ChestGiftSendButton(Context context) {
        super(context);
        init(context);
    }

    public ChestGiftSendButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChestGiftSendButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mHandler = new Handler(context.getMainLooper());
        LayoutInflater.from(context).inflate(R.layout.gift_button_chest_gift_send, this);
        setVisibility(GONE);
        timeTv = findViewById(R.id.tv_time);
//        circleProgressBar = findViewById(R.id.circleProgressBar);
//        circleProgressBar.setMax(TIME_COUNT);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
//        if (id == R.id.tv_time) {
//            start();
//            if (doubleClickListener != null) {
//                doubleClickListener.onDoubleClick(this);
//            }
//        }
    }

    public interface DoubleClickListener {
        void onDoubleClick(FrameLayout view);

        void onDoubleClickTimerFinish(FrameLayout view);


    }

    private void start() {
        position ++;
        timeTv.setText("x "+position);
        setVisibility(VISIBLE);
        giftTimeCount.cancel();
        giftTimeCount.start();
    }

    public void start(ChestGiftSendButton.DoubleClickListener doubleClickListener) {
        this.doubleClickListener = doubleClickListener;
        start();
    }

    public void stop(){
        if (mHandler!=null)
            mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }
    private GiftTimeCount giftTimeCount = new GiftTimeCount(TIME_COUNT, 100);

    private class GiftTimeCount extends CountDownTimer {
        private GiftTimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
//            circleProgressBar.setProgress((int) millisUntilFinished);
        }

        @Override
        public void onFinish() {
            position = 0;
//            circleProgressBar.setProgress(0);
            setVisibility(GONE);
            if (doubleClickListener != null) {
                doubleClickListener.onDoubleClickTimerFinish(ChestGiftSendButton.this);
            }
        }
    }

//    public void add(){
//        position ++;
//        timeTv.setText("X "+position);
//    }

    ChestGiftSendButton.DoubleClickListener doubleClickListener;

//    @Override
//    public void onClick(View v) {
//        if (v == ivClickSend) {
//            start();
//            if (doubleClickListener != null) {
//                doubleClickListener.onDoubleClick(this);
//            }
//        }
//    }


}