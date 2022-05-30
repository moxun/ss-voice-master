package com.miaomi.fenbei.voice.ui.mine.phone_bind;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

@Route(path = "/mine/phoneChange")
public class PhoneChangeActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvObtainCode;
    private EditText etPhoneNumber;
    private EditText etCode;
    private TextView tvModify;
    private CountDownTimer mCountDownTimer;
    private String phoneSign = "";
    private TextView mainTitleTv;


    public static Intent getIntent(Context context) {
        return new Intent(context, PhoneChangeActivity.class);
    }
    @Override
    public int getLayoutId() {
        return R.layout.user_activity_phone_bind;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        tvObtainCode = findViewById(R.id.tv_obtain_code);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        etCode = findViewById(R.id.vcode_edit);
        tvModify = findViewById(R.id.tv_modify);
        mainTitleTv = findViewById(R.id.main_tv);
        tvObtainCode.setOnClickListener(this);
        tvModify.setOnClickListener(this);
        mCountDownTimer = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvObtainCode.setTextColor(Color.parseColor("#aaaaaa"));
                tvObtainCode.setText((millisUntilFinished/1000) + "s后重新发送");
                tvObtainCode.setClickable(false);
            }

            @Override
            public void onFinish() {
                tvObtainCode.setTextColor(Color.parseColor("#FD7F8F"));
                tvObtainCode.setText("获取验证码");
                tvObtainCode.setClickable(true);
            }
        };
        etPhoneNumber.setText(DataHelper.INSTANCE.getUserInfo().getMobile());
        etPhoneNumber.setEnabled(false);
        mainTitleTv.setText("手机验证");
        tvModify.setText("下一步");
    }

    private void bindPhone(){
        if (TextUtils.isEmpty(phoneSign)){
            String phone = etPhoneNumber.getText().toString();
            String code = etCode.getText().toString();
            if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(code)) {
                ToastUtil.INSTANCE.error(PhoneChangeActivity.this, "手机号和验证码不可为空");
                return ;
            }
            NetService.Companion.getInstance(this).mobileVerify(code , 2,new Callback<String>() {
                @Override
                public void onSuccess(int nextPage, String bean, int code) {
                    ToastUtil.INSTANCE.suc(PhoneChangeActivity.this, "验证完成");
                    etPhoneNumber.setText("");
                    etCode.setText("");
                    tvModify.setText("确认");
                    mainTitleTv.setText("手机绑定");
                    mCountDownTimer.cancel();
                    mCountDownTimer.onFinish();
                    etPhoneNumber.setEnabled(true);
                    phoneSign = bean;
                }

                @Override
                public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                    ToastUtil.INSTANCE.error(PhoneChangeActivity.this, msg);
                }

                @Override
                public boolean isAlive() {
                    return isLive();
                }
            });
        }else{
            String phone = etPhoneNumber.getText().toString();
            String code = etCode.getText().toString();
            if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(code)) {
                ToastUtil.INSTANCE.error(PhoneChangeActivity.this, "手机号和验证码不可为空");
                return ;
            }
            NetService.Companion.getInstance(this).mobileChange(phone,code ,phoneSign, new Callback<Object>() {
                @Override
                public void onSuccess(int nextPage, Object bean, int code) {
                    ToastUtil.INSTANCE.suc(PhoneChangeActivity.this, "修改成功");
                    setResult(RESULT_OK);
                    finish();
                }

                @Override
                public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                    ToastUtil.INSTANCE.error(PhoneChangeActivity.this, msg);
                }

                @Override
                public boolean isAlive() {
                    return isLive();
                }
            });
        }

    }

    private void sendCode(){
        String phone = etPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(phone) || phone.length() != 11 ) {
            ToastUtil.INSTANCE.error(PhoneChangeActivity.this,"手机号码格式错误，请重新输入");
            return ;
        }
//        type|验证码类型|1登录-2绑定
        NetService.Companion.getInstance(this).getCode(phone, 2, new Callback<BaseBean>() {
            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                ToastUtil.INSTANCE.suc(PhoneChangeActivity.this, "验证码发送成功");
                mCountDownTimer.start();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(PhoneChangeActivity.this, msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_modify){
            bindPhone();
        }else if (i == R.id.tv_obtain_code){
            sendCode();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCountDownTimer.cancel();
    }
}
