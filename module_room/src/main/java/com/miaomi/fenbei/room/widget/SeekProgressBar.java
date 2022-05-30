package com.miaomi.fenbei.room.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ProgressBar;

public class SeekProgressBar extends ProgressBar {

    public interface ProgressListen{
        void onProgress(int progress);
    }


    float startY;
    ProgressListen mProgressListen = null;

    boolean moveEnable = true;

    public SeekProgressBar(Context context) {
        super(context);
    }

    public SeekProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SeekProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addOnProgressListener(ProgressListen progressListen){
        mProgressListen = progressListen;
    }

    public boolean isMoveEnable() {
        return moveEnable;
    }

    public void setMoveEnable(boolean moveEnable) {
        this.moveEnable = moveEnable;
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
        if (mProgressListen !=null){
            mProgressListen.onProgress(getProgress());
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!isMoveEnable()){
            return super.onTouchEvent(event);
        }


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY= 1-(event.getY()  - startY);
                float progress = moveY/ ((float) getHeight())*getMax();
                setProgress(getProgress()+(int) progress);
                startY = event.getY();

                if (mProgressListen !=null){
                    mProgressListen.onProgress(getProgress());
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }

        return true;
    }


}
