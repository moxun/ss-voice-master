package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChatLineRecyclerView extends RecyclerView {
    private int lastVisibleItemPosition;
    private int firstVisibleItemPosition;
    private float mLastY = 0;// 记录上次Y位置
    private float mStartY = 0;
    private float mEndY = 0;
    private boolean isTopToBottom = false;
    private boolean isBottomToTop = false;
    private OnTransListener onTransListener;

    public void setOnTransListener(OnTransListener onTransListener){
        this.onTransListener = onTransListener;
    }

    public ChatLineRecyclerView(Context context) {
        this(context, null);
    }

    public ChatLineRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatLineRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = event.getY();
                mStartY = event.getY();
                //不允许父View拦截事件
//                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float nowY = event.getY();
                isIntercept(nowY);
                if (isBottomToTop||isTopToBottom){
//                    getParent().requestDisallowInterceptTouchEvent(true);
                    return true;
                }else{
//                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                mLastY = nowY;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mEndY = event.getY();
                if (onTransListener != null){
                    if ((mEndY - mStartY)>0){
                        onTransListener.toBottom();
                    }else{
                        onTransListener.toTop();
                    }
                }
//                getParent().requestDisallowInterceptTouchEvent(true);
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    private void isIntercept(float nowY){

        isTopToBottom = false;
        isBottomToTop = false;
        LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
         firstVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
         lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
        //得到当前界面可见数据的大小
        int visibleItemCount = layoutManager.getChildCount();
        //得到RecyclerView对应所有数据的大小
        int totalItemCount = layoutManager.getItemCount();
        if (visibleItemCount>0) {
            if (lastVisibleItemPosition == totalItemCount - 1) {
                //最后视图对应的position等于总数-1时，说明上一次滑动结束时，触底了
                if ( nowY <= mLastY) {
                    // 不能向上滑动
                    isBottomToTop = true;
                } else {
                    if(firstVisibleItemPosition == 0){
                        isTopToBottom = true;
                    }
                }
            } else if (firstVisibleItemPosition == 0) {
                //第一个视图的position等于0，说明上一次滑动结束时，触顶了
                if ( nowY >= mLastY) {
                    // 不能向下滑动
                    isTopToBottom = true;
                } else {

                }
            }
        }
    }
    public interface OnTransListener{
        void toTop();
        void toBottom();
    }
}

