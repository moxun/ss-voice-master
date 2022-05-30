package com.miaomi.fenbei.base.widget;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;


public class ViewDragMode implements View.OnTouchListener {
    private View targetView;

    public ViewDragMode(final View targetView) {
        this.targetView = targetView;
        this.targetView.setOnTouchListener(this);
    }

    private int parentHeight;
    private int parentWidth;
    private int lastX;
    private int lastY;

    private int downX;
    private int downY;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                targetView.setPressed(true);
                ViewParent viewParent = targetView.getParent();
                viewParent.requestDisallowInterceptTouchEvent(true);
                lastX = rawX;
                lastY = rawY;
                downX= rawX;
                downY = rawY;
                ViewGroup parent;
                parent = (ViewGroup) viewParent;
                parentHeight = parent.getHeight();
                parentWidth = parent.getWidth();
                break;
            case MotionEvent.ACTION_MOVE:
//                if (parentHeight <= 0 || parentWidth == 0) {
//                    isDrag = false;
//                    break;
//                } else {
//                    isDrag = true;
//                }
                int dx = rawX - lastX;
                int dy = rawY - lastY;
                float x = targetView.getX() + dx;
                float y = targetView.getY() + dy;
                //检测是否到达边缘 左上右下
                x = x < 0 ? 0 : x > parentWidth - targetView.getWidth() ? parentWidth - targetView.getWidth() : x;
                y = targetView.getY() < 0 ? 0 : targetView.getY() + targetView.getHeight() > parentHeight ? parentHeight - targetView.getHeight() : y;
                targetView.setX(x);
                targetView.setY(y);
                lastX = rawX;
                lastY = rawY;

                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if(Math.abs(rawX-downX)<20&&Math.abs(rawY-downY)<20){
                    targetView.performClick();
                }

                //恢复按压效果
                targetView.setPressed(false);

                if (rawX >= parentWidth / 2) {
                    //靠右吸附
                    targetView.animate().setInterpolator(new DecelerateInterpolator())
                            .setDuration(500)
                            .x(parentWidth - targetView.getWidth())
                            .start();
                } else {
                    //靠左吸附
                    targetView.animate().setInterpolator(new DecelerateInterpolator())
                            .setDuration(500)
                            .x(0)
                            .start();
                }
                break;
        }
        //如果是拖拽则消s耗事件，否则正常传递即可。
        return true;
    }

}