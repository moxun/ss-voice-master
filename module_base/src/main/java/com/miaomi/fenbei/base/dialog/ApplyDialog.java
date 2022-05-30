package com.miaomi.fenbei.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.miaomi.fenbei.base.R;


public class ApplyDialog extends Dialog {
    private String leftStr;
    private String rightStr;
    private String contentStr;
    private String titleStr;
    private String msg;
    private String outType="1";
    private boolean isCenter = true;
    private View.OnClickListener leftOnClickListener;
    private View.OnClickListener rightOnClickListener;
    private Context context;
    public ApplyDialog(@NonNull Context context) {
        super(context, R.style.common_dialog);
        this.context=context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_dialog);
        TextView leftTv = findViewById(R.id.tv_left);
        TextView rightTv = findViewById(R.id.tv_right);
        TextView contentTv = findViewById(R.id.tv_content);
        TextView titleTv = findViewById(R.id.tv_title);
        ImageView familyoutImg=findViewById(R.id.img_family_out);
        TextView msgTv=findViewById(R.id.tv_msg);
        contentTv.setMovementMethod(ScrollingMovementMethod.getInstance());
        if (!TextUtils.isEmpty(outType)){
            if(outType.equals("2")){
                familyoutImg.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_out_family));
            }else if(outType.equals("1")){
                familyoutImg.setImageDrawable(context.getResources().getDrawable(R.drawable.p_icon_pic_add));
            }else if(outType.equals("3")){
                familyoutImg.setImageDrawable(context.getResources().getDrawable(R.drawable.p_pic_dismiss));
            }else{
                familyoutImg.setVisibility(View.GONE);
            }

        }
        if (!TextUtils.isEmpty(msg)){
            msgTv.setText(msg);
        }else {
            msgTv.setVisibility(View.GONE);
        }
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

    public ApplyDialog setRightBt(String content, View.OnClickListener onClickListener){
        rightStr = content;
        rightOnClickListener = onClickListener;
        return this;
    }

    public ApplyDialog setLeftBt(String content, View.OnClickListener onClickListener){
        leftStr = content;
        leftOnClickListener = onClickListener;
        return this;
    }
    public  ApplyDialog setContent(String content){
        contentStr = content;
        return this;
    }
    public  ApplyDialog setOutIcon(String outType) {
        this.outType = outType;
        return this;
    }
    public  ApplyDialog setContentCenter(boolean isCenter) {
        this.isCenter = isCenter;
        return this;
    }
    public  ApplyDialog setMsg(String msg){
        this.msg = msg;
        return this;
    }
    public  ApplyDialog setTitle(String content){
        titleStr = content;
        return this;
    }


}
