package com.miaomi.fenbei.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.miaomi.fenbei.base.R;
import com.miaomi.fenbei.base.util.ImgUtil;


public class CreateFamilyDialog extends Dialog {
    private String leftStr;
    private String rightStr;
    private String familyname;
    private String url;
    private String notice;
    private String patriarch;
    private boolean isCenter = true;
    private Context context;
    private View.OnClickListener leftOnClickListener;
    private View.OnClickListener rightOnClickListener;
    public CreateFamilyDialog(@NonNull Context context) {
        super(context, R.style.common_dialog);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_family_dialog);
        TextView leftTv = findViewById(R.id.tv_left);
        TextView rightTv = findViewById(R.id.tv_right);
        TextView familynameTv = findViewById(R.id.tv_family_name);
        ImageView iconIv=findViewById(R.id.iv_icon);
        TextView noticeTv= findViewById(R.id.tv_notice);
        TextView  patriarchTv=findViewById(R.id.tv_patriarch);


        if (!TextUtils.isEmpty(rightStr)){
            rightTv.setText(rightStr);
        }
        if (!TextUtils.isEmpty(leftStr)){
            leftTv.setText(leftStr);
        }else {
            leftTv.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(familyname)){
            familynameTv.setText(familyname);
        }else{
            familynameTv.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(url)){
            ImgUtil.INSTANCE.loadCircleImg(context,url,iconIv, R.drawable.common_avter_placeholder);
        }else{
            iconIv.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(notice)){
            noticeTv.setText(notice);
        }else{
            noticeTv.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(patriarch)){
            patriarchTv.setText(patriarch);
        }else{
            patriarchTv.setVisibility(View.GONE);
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


    }

    public CreateFamilyDialog setRightBt(String content, View.OnClickListener onClickListener){
        rightStr = content;
        rightOnClickListener = onClickListener;
        return this;
    }

    public CreateFamilyDialog setLeftBt(String content, View.OnClickListener onClickListener){
        leftStr = content;
        leftOnClickListener = onClickListener;
        return this;
    }
    public  CreateFamilyDialog setFamilyName(String familyname){
        this.familyname = familyname;
        return this;
    }
    public CreateFamilyDialog setIconUrl(String url){
        this.url = url;
        return this;
    }
    public  CreateFamilyDialog setNotice(String notice){
        this.notice = notice;
        return this;
    }
    public  CreateFamilyDialog setPatriarch(String patriarch){
        this.patriarch = patriarch;
        return this;
    }
    public  CreateFamilyDialog setContentCenter(boolean isCenter) {
        this.isCenter = isCenter;
        return this;
    }




}
