package com.miaomi.fenbei.imkit.ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.miaomi.fenbei.base.bean.event.C2CMsgBean;
import com.miaomi.fenbei.imkit.R;
import com.miaomi.fenbei.imkit.listener.OnMsgOprateListener;

public class MsgOpratePopWindow extends PopupWindow {
    private LayoutInflater inflater;
    private View parentView;
    private int popupHeight;
    private int popupWidth;
    private OnMsgOprateListener onMsgOprateListener;
    private C2CMsgBean c2CMsgBean;
    private boolean canCopy;



    public MsgOpratePopWindow(Context context,boolean canCopy,C2CMsgBean c2CMsgBean,OnMsgOprateListener onMsgOprateListener) {
        super(context);
        inflater = LayoutInflater.from(context);
        this.c2CMsgBean = c2CMsgBean;
        this.onMsgOprateListener = onMsgOprateListener;
        this.canCopy = canCopy;
        init(context);
    }

    private void init(Context context) {
        parentView = inflater.inflate(R.layout.chatting_msg_oprate_pop_window, null);
        if (canCopy){
            parentView.findViewById(R.id.tv_copy).setVisibility(View.VISIBLE);
        }else{
            parentView.findViewById(R.id.tv_copy).setVisibility(View.GONE);
        }
        parentView.findViewById(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMsgOprateListener != null){
                    onMsgOprateListener.onDeleteMessageClick(c2CMsgBean);
                }
                dismiss();
            }
        });
        parentView.findViewById(R.id.tv_copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMsgOprateListener != null){
                    onMsgOprateListener.onCopyClick(c2CMsgBean);
                }
                dismiss();
            }
        });
        setContentView(parentView);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);
        parentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupHeight = parentView.getMeasuredHeight();
        popupWidth = parentView.getMeasuredWidth();
    }



    public void show(View v){
        //获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        //在控件上方显示
        showAtLocation(v, Gravity.NO_GRAVITY, (location[0] + v.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight);
    }


}
