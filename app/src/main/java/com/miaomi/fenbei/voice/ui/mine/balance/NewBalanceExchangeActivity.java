package com.miaomi.fenbei.voice.ui.mine.balance;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.miaomi.fenbei.base.VerifyPwdDialog;
import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.bean.ExchangeDiamondsBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.CommonUtils;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.StringUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.ExchangePwdSettingActivity;

import org.jetbrains.annotations.NotNull;

public class NewBalanceExchangeActivity extends BaseActivity implements View.OnClickListener{

    private TextView moneyTv;
    private EditText numberEt;
    private TextView setPedTv;
    private TextView getKBTv;

    public static Intent getIntent(Context context) {
        return new Intent(context, NewBalanceExchangeActivity.class);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_exchange_history){
            startActivity(BalanceHistoryActivity.getIntent(this));
        }
        if (id == R.id.tv_all){
            numberEt.setText(moneyTv.getText());
            numberEt.setSelection(moneyTv.getText().length());
        }
        if (id == R.id.tv_exchage){
            String str = numberEt.getText().toString();
            if (TextUtils.isEmpty(str)){
                ToastUtil.INSTANCE.error(NewBalanceExchangeActivity.this,"请输入需要兑换的收益");
                return;
            }
            if (!CommonUtils.isNumeric(str)){
                ToastUtil.INSTANCE.error(NewBalanceExchangeActivity.this,"请输入整数");
                return;
            }
            if (Integer.parseInt(str) == 0){
                ToastUtil.INSTANCE.error(NewBalanceExchangeActivity.this,"请大于0的整数");
                return;
            }
            if (DataHelper.INSTANCE.getUserInfo().getExchange_pwd() == ExchangePwdSettingActivity.EXCHANGE_PWD_SETTING){
                submit("",str);
            }else{
                new VerifyPwdDialog(NewBalanceExchangeActivity.this).setOnClickListener(new VerifyPwdDialog.OnClickListener() {
                    @Override
                    public void onRefuseClick() {

                    }

                    @Override
                    public void onAgreeClick(String pwd) {
                        submit(pwd,str);
                    }
                }).show();
            }
        }
        if (id == R.id.tv_set_pwd){
            ExchangePwdSettingActivity.start(this,DataHelper.INSTANCE.getUserInfo().getExchange_pwd());
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_activity_balance_exchange_new;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        moneyTv = findViewById(R.id.tv_money);
        numberEt = findViewById(R.id.et_number);
        setPedTv = findViewById(R.id.tv_set_pwd);
        getKBTv = findViewById(R.id.tv_get_kb);
        findViewById(R.id.tv_exchange_history).setOnClickListener(this);
        findViewById(R.id.tv_all).setOnClickListener(this);
        findViewById(R.id.tv_exchage).setOnClickListener(this);
        setPedTv.setOnClickListener(this);
        numberEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (CommonUtils.isNumeric(s.toString())){
                    if (s.toString().isEmpty()){
                        getKBTv.setText("");
                    }else{
                        getKBTv.setText("可获得"+(Integer.valueOf(s.toString())*10) + "钻石");
                    }
                }else{
                    getKBTv.setText("");
                }
            }
        });
        getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (DataHelper.INSTANCE.getUserInfo().getExchange_pwd() == ExchangePwdSettingActivity.EXCHANGE_PWD_SETTING){
            setPedTv.setText("设置密码");
        }else {
            setPedTv.setText("修改密码");
        }
    }

    private void getData() {
        NetService.Companion.getInstance(this).getIncomeInfo(DataHelper.INSTANCE.getLoginToken(), new Callback<ExchangeDiamondsBean>() {
            @Override
            public boolean isAlive() {
                return isLive();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
            }

            @Override
            public void onSuccess(int nextPage, ExchangeDiamondsBean bean, int code) {
                moneyTv.setText(StringUtil.formatDouble(bean.getEarning()));
            }
        });
    }
    private void submit(String pwd,String number){
        NetService.Companion.getInstance(this).exchangeDiamonds(pwd, number, new Callback<BaseBean>() {

            @Override
            public boolean isAlive() {
                return isLive();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(NewBalanceExchangeActivity.this, msg);
            }

            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                getData();
                ToastUtil.INSTANCE.suc(NewBalanceExchangeActivity.this, "兑换成功");
            }
        });
    }
}
