package com.miaomi.fenbei.base.core.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import com.miaomi.fenbei.base.R;

public class CommonDialog extends Dialog {
    private String leftStr;
    private String rightStr;
    private String contentStr;
    private String titleStr;
    private boolean isCenter = true;
    private View.OnClickListener leftOnClickListener;
    private View.OnClickListener rightOnClickListener;
    public CommonDialog(@NonNull Context context) {
        super(context,R.style.common_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_dialog);
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

        contentTv.setGravity(isCenter ? Gravity.CENTER : Gravity.START);
    }

    public CommonDialog setRightBt(String content, View.OnClickListener onClickListener){
        rightStr = content;
        rightOnClickListener = onClickListener;
        return this;
    }

    public CommonDialog setLeftBt(String content, View.OnClickListener onClickListener){
        leftStr = content;
        leftOnClickListener = onClickListener;
        return this;
    }
    public CommonDialog setContent(String content){
        contentStr = content;
        return this;
    }

    public CommonDialog setContentCenter(boolean isCenter) {
        this.isCenter = isCenter;
        return this;
    }

    public CommonDialog setTitle(String content){
        titleStr = content;
        return this;
    }


}
