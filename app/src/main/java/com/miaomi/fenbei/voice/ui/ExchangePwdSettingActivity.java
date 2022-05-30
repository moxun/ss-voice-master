package com.miaomi.fenbei.voice.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.LocalUserBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.PayPwdEditText;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.dialog.ExchangeClosePwdDialog;

import org.jetbrains.annotations.NotNull;

public class ExchangePwdSettingActivity extends BaseActivity {

    public final static int EXCHANGE_PWD_SETTING = 0;
    public final static int EXCHANGE_PWD_CHANGE = 1;
    public final static String TAG_TYPE = "TAG_TYPE";

    private int settingType;
    private TextView newPwdTv;
    private TextView oldPwdTv;
    private PayPwdEditText newPwdEt;
    private PayPwdEditText oldPwdEt;
    private TextView closePwdTv;
    private TextView submitTv;
    private TextView tipTv;
    private ImageView bakcIv;

    public static void start(Context context,int type){
        Intent intent = new Intent(context,ExchangePwdSettingActivity.class);
        intent.putExtra(TAG_TYPE,type);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_exchange_pwd_setting;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        settingType = getIntent().getIntExtra(TAG_TYPE,1);
        newPwdTv = findViewById(R.id.tv_new_pwd);
        newPwdEt = findViewById(R.id.et_new_pwd);
        oldPwdTv = findViewById(R.id.tv_old_pwd);
        oldPwdEt = findViewById(R.id.et_old_pwd);
        bakcIv = findViewById(R.id.iv_back);

        newPwdEt.initStyle(R.drawable.common_bg_pwd_room, 4, 25f, R.color.white, R.color.text_color_55, 16);
        newPwdEt.setShowPwd(true);

        oldPwdEt.initStyle(R.drawable.common_bg_pwd_room, 4, 25f, R.color.white, R.color.text_color_55, 16);
        oldPwdEt.setShowPwd(true);

        closePwdTv = findViewById(R.id.tv_close_pwd);
        tipTv = findViewById(R.id.tv_tip);
        bakcIv.setOnClickListener(v -> finish());
        closePwdTv.setOnClickListener(v -> new ExchangeClosePwdDialog(ExchangePwdSettingActivity.this).setOnClickListener(new ExchangeClosePwdDialog.OnClickListener() {
            @Override
            public void onRefuseClick() {

            }

            @Override
            public void onAgreeClick(String pwd) {
                closePwd(pwd);
            }
        }).show());
        submitTv = findViewById(R.id.tv_submit);
        submitTv.setOnClickListener(v -> submit());

        if (settingType == EXCHANGE_PWD_SETTING){
            showPwdSetting();
        }
        if (settingType == EXCHANGE_PWD_CHANGE){
            showChangePwd();
        }

    }

    private void showPwdSetting(){
        newPwdTv.setVisibility(View.GONE);
        oldPwdTv.setVisibility(View.GONE);
        oldPwdEt.setVisibility(View.GONE);
        closePwdTv.setVisibility(View.GONE);
        submitTv.setText("设置密码");

    }
    private void showChangePwd(){
        tipTv.setVisibility(View.GONE);
        submitTv.setText("修改密码");
    }


    private void submit(){
        if (settingType == EXCHANGE_PWD_SETTING){
            String pwd = newPwdEt.getPwdText();
            NetService.Companion.getInstance(this).exchangePwdSet(pwd, new Callback<Object>() {
                @Override
                public boolean isAlive() {
                    return isLive();
                }

                @Override
                public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                    ToastUtil.INSTANCE.suc(ExchangePwdSettingActivity.this,msg);
                }

                @Override
                public void onSuccess(int nextPage, Object bean, int code) {
                    ToastUtil.INSTANCE.suc(ExchangePwdSettingActivity.this,"设置成功");
                    LocalUserBean data = DataHelper.INSTANCE.getUserInfo();
                    data.setExchange_pwd(ExchangePwdSettingActivity.EXCHANGE_PWD_CHANGE);
                    DataHelper.INSTANCE.updatalUserInfo(data);
                    finish();
                }
            });
        }
        if (settingType == EXCHANGE_PWD_CHANGE){
            String pwd = oldPwdEt.getPwdText();
            String newpwd = newPwdEt.getPwdText();
            NetService.Companion.getInstance(this).exchangePwdUpdate(pwd,newpwd, new Callback<Object>() {
                @Override
                public boolean isAlive() {
                    return isLive();
                }

                @Override
                public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                    ToastUtil.INSTANCE.suc(ExchangePwdSettingActivity.this,msg);
                }

                @Override
                public void onSuccess(int nextPage, Object bean, int code) {
                    ToastUtil.INSTANCE.suc(ExchangePwdSettingActivity.this,"修改成功");
                    LocalUserBean data = DataHelper.INSTANCE.getUserInfo();
                    data.setExchange_pwd(ExchangePwdSettingActivity.EXCHANGE_PWD_CHANGE);
                    DataHelper.INSTANCE.updatalUserInfo(data);
                    finish();
                }
            });
        }

    }

    private void closePwd(String pwd){
        NetService.Companion.getInstance(this).exchangePwdClose(pwd, new Callback<Object>() {
            @Override
            public boolean isAlive() {
                return isLive();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.suc(ExchangePwdSettingActivity.this,msg);
            }

            @Override
            public void onSuccess(int nextPage, Object bean, int code) {
                ToastUtil.INSTANCE.suc(ExchangePwdSettingActivity.this,"关闭成功");
                LocalUserBean data = DataHelper.INSTANCE.getUserInfo();
                data.setExchange_pwd(ExchangePwdSettingActivity.EXCHANGE_PWD_SETTING);
                DataHelper.INSTANCE.updatalUserInfo(data);
                finish();
            }
        });
    }
}
