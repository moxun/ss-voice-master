package com.miaomi.fenbei.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.miaomi.fenbei.base.R;
import com.miaomi.fenbei.base.config.BaseConfig;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.web.WebActivity;


public class FirstChargeDialog extends Dialog {

    private View.OnClickListener leftOnClickListener;
    private View.OnClickListener rightOnClickListener;
    private Context context;
    private ImageView closeIv;
    private ImageView rechargeIv;
    public FirstChargeDialog(@NonNull Context context) {
        super(context, R.style.common_dialog);
        this.context=context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstcharge_dialog);
        closeIv=findViewById(R.id.iv_close);
        rechargeIv=findViewById(R.id.iv_recharge);
        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (rightOnClickListener != null) {
                    rightOnClickListener.onClick(v);
                }
            }
        });
        rechargeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                WebActivity.start(getContext(), BaseConfig.H5_URL+"recharge_first/homepage?token="+DataHelper.INSTANCE.getLoginToken(),"首充送礼");
            }
        });
//        leftTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//                if (leftOnClickListener != null) {
//                    leftOnClickListener.onClick(v);
//                }
//            }
//        });


    }




}
