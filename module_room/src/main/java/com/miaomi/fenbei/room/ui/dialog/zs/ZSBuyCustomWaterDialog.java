package com.miaomi.fenbei.room.ui.dialog.zs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.miaomi.fenbei.base.bean.ZSIndexBean;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.room.R;

import org.jetbrains.annotations.NotNull;

public class ZSBuyCustomWaterDialog extends Dialog {

    private EditText numberEt;
    private int waterNumber = 1;

    private TextView kTv;
    private boolean isLive;
    private ImageView bugIv;

    private TextView twaternumTv;
    private TextView  customNumTv;
    private WaterBuyCallback waterBuyCallback;
    private int watertype;
    private Boolean purchaseS;
    public ZSBuyCustomWaterDialog(@NonNull Context context,int watertype, WaterBuyCallback callback) {
        super(context, R.style.common_dialog);
        this.waterBuyCallback = callback;
        this.watertype=watertype;
    }

    @Override
    protected void onStart() {
        super.onStart();
        isLive = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isLive = false;
    }

    @Override
    public void dismiss() {
        super.dismiss();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_zs_bug_custom_water);
        numberEt = findViewById(R.id.et_number);
        kTv = findViewById(R.id.tv_k);
        bugIv=findViewById(R.id.iv_bug);
        customNumTv=findViewById(R.id.tv_custom_num);
        twaternumTv=findViewById(R.id.tv_water_num);
        if(watertype>1){
            twaternumTv.setText("x"+waterNumber*5);
            customNumTv.setText("门票数量不能超过5000");
        }else{
            twaternumTv.setText("x"+waterNumber*2);
            customNumTv.setText("门票数量不能超过2000");
        }
        bugIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buy();
            }
        });
        findViewById(R.id.tv_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ARouter.getInstance().build("/app/pay").navigation();
                new ZSBuyWaterDialog(getContext(), new WaterBuyCallback() {
                    @Override
                    public void dismiss() {
                        getData();
                    }
                    @Override
                    public void dismisswatering(int waterNumber,int watertype) {
                    }
                }).show();
            }
        });
        findViewById(R.id.tv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waterNumber = waterNumber + 1;
                numberEt.setText(String.valueOf(waterNumber));
            }
        });
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        findViewById(R.id.tv_cut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(waterNumber>0){
                    waterNumber = waterNumber - 1;
                    numberEt.setText(String.valueOf(waterNumber));
                }

            }
        });
        numberEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())){
                    if(watertype>1){
                        waterNumber = Integer.valueOf(s.toString());
                        twaternumTv.setText("x"+waterNumber*5);
                    }else{
                        waterNumber = Integer.valueOf(s.toString());
                        twaternumTv.setText("x"+waterNumber*2);
                    }

                }else{
                    waterNumber = 0;
                }

            }
        });
        getData();
    }

    private void buy(){
        if (waterNumber <= 0){
            ToastUtil.INSTANCE.suc(getContext(),"请添加次数");
            return;
        }
        if (waterNumber > 1000){
            ToastUtil.INSTANCE.suc(getContext(),"游园不能超过1000次");
            return;
        }
        dismiss();
        waterBuyCallback.dismisswatering(waterNumber,watertype);
//        NetService.Companion.getInstance(getContext()).buywater(waterNumber, new Callback<ZSBuyWaterBean>() {
//            @Override
//            public void onSuccess(int nextPage, ZSBuyWaterBean bean, int code) {
//                ToastUtil.INSTANCE.i(getContext(),"购买成功");
//                getData();
//            }
//
//            @Override
//            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
//                ToastUtil.INSTANCE.error(getContext(),msg);
//            }
//
//            @Override
//            public boolean isAlive() {
//                return isLive;
//            }
//        });
    }


    private void getData(){
        NetService.Companion.getInstance(getContext()).getZSIndex(new Callback<ZSIndexBean>() {
            @Override
            public void onSuccess(int nextPage, ZSIndexBean bean, int code) {

                    kTv.setText("门票："+bean.getWater());

            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(getContext(),msg);
            }

            @Override
            public boolean isAlive() {
                return isLive;
            }
        });
    }
}
