package com.miaomi.fenbei.base.core.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.miaomi.fenbei.base.R;

import androidx.annotation.NonNull;

public class EnterRoomTipDialog extends Dialog {
    private String leftStr;
    private String rightStr;
    private String contentStr;
    private String titleStr;
    private boolean isCenter = true;
    private View.OnClickListener leftOnClickListener;
    private View.OnClickListener rightOnClickListener;
    public EnterRoomTipDialog(@NonNull Context context) {
        super(context, R.style.common_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_dialog_enter_room_tip);
        TextView leftTv = findViewById(R.id.tv_left);
        TextView rightTv = findViewById(R.id.tv_right);
        TextView contentTv = findViewById(R.id.tv_content);
        TextView titleTv = findViewById(R.id.tv_title);
        contentTv.setMovementMethod(ScrollingMovementMethod.getInstance());
        if (!TextUtils.isEmpty(rightStr)){
            rightTv.setText(rightStr);
        }
        if (!TextUtils.isEmpty(leftStr)){
            leftTv.setText(leftStr);
        }else {
            leftTv.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(contentStr)){
            contentTv.setText(contentStr);
        }else{
            contentTv.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(titleStr)){
            titleTv.setText(titleStr);
        }else{
            titleTv.setVisibility(View.GONE);
        }
        rightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (rightOnClickListener != null) {
                    rightOnClickListener.onClick(v);
                }
            }
        });

        leftTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (leftOnClickListener != null) {
                    leftOnClickListener.onClick(v);
                }
            }
        });

//        contentTv.setGravity(isCenter ? Gravity.CENTER : Gravity.START);
    }

    public EnterRoomTipDialog setRightBt(String content, View.OnClickListener onClickListener){
        rightStr = content;
        rightOnClickListener = onClickListener;
        return this;
    }

    public EnterRoomTipDialog setLeftBt(String content, View.OnClickListener onClickListener){
        leftStr = content;
        leftOnClickListener = onClickListener;
        return this;
    }
    public EnterRoomTipDialog setContent(String content){
        contentStr = content;
        return this;
    }

    public EnterRoomTipDialog setContentCenter(boolean isCenter) {
        this.isCenter = isCenter;
        return this;
    }

    public EnterRoomTipDialog setTitle(String content){
        titleStr = content;
        return this;
    }


}