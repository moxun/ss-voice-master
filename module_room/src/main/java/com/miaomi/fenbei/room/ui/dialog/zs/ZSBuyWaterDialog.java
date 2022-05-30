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

import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.bean.DiamondsBean;
import com.miaomi.fenbei.base.bean.ZSBuyWaterBean;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.room.R;

import org.jetbrains.annotations.NotNull;

public class ZSBuyWaterDialog extends Dialog {

    private EditText numberEt;
    private int waterNumber = 1;
    private TextView waterPriceTv;
    private TextView kTv;
    private boolean isLive;
    private ImageView iv_bug;
    private LinearLayout ll_bg_purchase;
    private TextView tv_water_title;
    private WaterBuyCallback waterBuyCallback;


    public ZSBuyWaterDialog(@NonNull Context context,WaterBuyCallback callback) {
        super(context, R.style.common_dialog);
        this.waterBuyCallback = callback;

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
        waterBuyCallback.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_zs_bug_water);
        numberEt = findViewById(R.id.et_number);
        waterPriceTv = findViewById(R.id.tv_water_price);
        kTv = findViewById(R.id.tv_k);
        ll_bg_purchase=findViewById(R.id.ll_bg_purchase);
        tv_water_title=findViewById(R.id.tv_water_title);
        iv_bug=findViewById(R.id.iv_bug);
        iv_bug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buy();
            }
        });
        findViewById(R.id.tv_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/app/pay").navigation();
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
                    waterNumber = Integer.valueOf(s.toString());
                    waterPriceTv.setText("此次门票需要"+(waterNumber*10)+"钻石");

                }else{
                    waterPriceTv.setText("此次门票需要0钻石");
                    waterNumber = 0;
                }
            }
        });

        getData();
    }

    private void buy(){
        if (waterNumber <= 0){
            return;
        }

        NetService.Companion.getInstance(getContext()).buywater(waterNumber,1, new Callback<ZSBuyWaterBean>() {
            @Override
            public void onSuccess(int nextPage, ZSBuyWaterBean bean, int code) {
                ToastUtil.INSTANCE.i(getContext(),"购买成功");
                getData();
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


    private void getData(){
        NetService.Companion.getInstance(getContext()).getDiamonds( new Callback<DiamondsBean>() {
            @Override
            public void onSuccess(int nextPage, DiamondsBean bean, int code) {
                kTv.setText("钻石："+bean.getBalance());
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
