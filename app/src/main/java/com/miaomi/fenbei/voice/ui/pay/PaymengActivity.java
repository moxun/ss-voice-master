package com.miaomi.fenbei.voice.ui.pay;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.bean.BalanceInfoBean;
import com.miaomi.fenbei.base.bean.DiamondsBean;
import com.miaomi.fenbei.base.config.BaseConfig;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.core.dialog.CommonDialog;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.Data;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.base.web.WebActivity;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.mine.BindAliActivity;
import com.miaomi.fenbei.voice.ui.mine.BindBankCardActivity;
import com.miaomi.fenbei.voice.ui.mine.IncomeDetailsActivity;
import com.miaomi.fenbei.voice.ui.mine.balance.NewBalanceExchangeActivity;
import com.miaomi.fenbei.voice.ui.mine.cash_withdrawal.CashWithdrawalActivity;
import com.miaomi.fenbei.voice.ui.mine.identity_authentication.ExamineActivity;
import com.miaomi.fenbei.voice.ui.mine.identity_authentication.IdentityAuthenticationActivity;

import org.jetbrains.annotations.NotNull;

public class PaymengActivity extends BaseActivity implements View.OnClickListener {

    private TextView payTv;
    private TextView exchangeTv;
    private TextView cashOutTv;
    private TextView bindAliTv;
    private TextView incomeTv;
    private int mIdentifyStatus;
    private boolean isBindAli;
    private boolean isBindBackCard;
    private LoadHelper loadHelper;
    private LinearLayout parentLL;
    private TextView kdNumTv;
    private TextView diamondTv;
    public static void start(Context context) {
        Intent intent = new Intent(context, PaymengActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_payment;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        parentLL = findViewById(R.id.ll_parent);
        payTv = findViewById(R.id.tv_pay);
        exchangeTv = findViewById(R.id.tv_exchange);
        cashOutTv = findViewById(R.id.tv_cash_out);
        bindAliTv = findViewById(R.id.tv_bing);
        incomeTv = findViewById(R.id.tv_incom);
        kdNumTv = findViewById(R.id.tv_num_kd);
        diamondTv=findViewById(R.id.tv_text_diamons);
        findViewById(R.id.tv_incom_history).setOnClickListener(this);
        findViewById(R.id.tv_recharge_history).setOnClickListener(this);
        payTv.setOnClickListener(this);
        exchangeTv.setOnClickListener(this);
        cashOutTv.setOnClickListener(this);
        bindAliTv.setOnClickListener(this);
        diamondTv.setOnClickListener(this);
        loadHelper = new LoadHelper();
        loadHelper.registerLoad(parentLL);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
        getDiamonds();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.tv_text_diamons){
            WebActivity.start(this, BaseConfig.H5_URL +"recharge_first/homepage?token="+DataHelper.INSTANCE.getLoginToken(),"????????????");
        }
        if (id == R.id.tv_pay){
            ARouter.getInstance().build("/app/pay").navigation();
        }
        if (id == R.id.tv_exchange){
            startActivity(NewBalanceExchangeActivity.getIntent(this));
        }
        if (id == R.id.tv_cash_out){
            if (isBindBackCard) {
                CashWithdrawalActivity.start(PaymengActivity.this,isBindAli);
            } else {
                if (mIdentifyStatus == 2){
                    BindBankCardActivity.startActivity(this);
                }else{
                    showAuthenDialog();
                }
            }
        }
        if (id == R.id.tv_bing){
            if (mIdentifyStatus == 2){
                BindAliActivity.startActivity(this);
            }else{
                showAuthenDialog();
            }
        }
        if (id == R.id.tv_recharge_history){
            IncomeDetailsActivity.start(this);
        }
        if (id == R.id.tv_pay_history){
            ARouter.getInstance().build("/app/payhistroy").navigation();
        }
    }

    private void showAuthenDialog(){
        CommonDialog authenticationCommonDialog = new CommonDialog(this);
        authenticationCommonDialog.setTitle("????????????");
        authenticationCommonDialog.setContent("??????????????????????????????????????????????????????");
        authenticationCommonDialog.setLeftBt("??????", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticationCommonDialog.dismiss();
            }
        });
        authenticationCommonDialog.setRightBt("?????????", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIdentifyStatus == 0){
                    startActivity(IdentityAuthenticationActivity.getIntent(PaymengActivity.this));
                }else{
                    startActivity(ExamineActivity.getIntent(PaymengActivity.this));
                }
                authenticationCommonDialog.dismiss();
            }
        });
        authenticationCommonDialog.show();
    }
    private void getData() {
        NetService.Companion.getInstance(this).getBalanceInfo(DataHelper.INSTANCE.getLoginToken(), new Callback<BalanceInfoBean>() {
            @Override
            public void onSuccess(int nextPage, BalanceInfoBean bean, int code) {
                loadHelper.bindView(code);
                mIdentifyStatus = bean.getIdentify_status();
                isBindAli = bean.isBind_status();
                isBindBackCard = bean.isBank_status();
                incomeTv.setText(bean.getEarning());
                if (bean.isBind_status()){
                    bindAliTv.setText("??????????????????"+bean.getAlipay_account()+" ??????>>");
                }else{
                    bindAliTv.setText("????????????????????????>>");
                }
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                loadHelper.setErrorCallback(code, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadHelper.bindView(Data.CODE_LOADING);
                        getData();
                    }
                });

            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    private void getDiamonds(){
        NetService.Companion.getInstance(this).getDiamonds(new Callback<DiamondsBean>() {
            @Override
            public void onSuccess(int nextPage, final DiamondsBean bean, int code) {
                kdNumTv.setText(""+bean.getBalance());
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });

    }
}
