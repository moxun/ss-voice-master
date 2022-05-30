package com.miaomi.fenbei.voice.ui.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

public class FindPwdActivity extends BaseActivity {

    TextView changeTv;
    TextView sendVCodeTv;
    CountDownTimer countDownTimer;
    EditText phoneEt;
    EditText vcodeEt;
    EditText userIdEt;
    EditText pwdEt;
    EditText rePwdEt;

    public static void start(Context context){
        Intent intent = new Intent(context,FindPwdActivity.class);
        context.startActivity(intent);
    }
    @Override
    public int getLayoutId() {
        return R.layout.login_activity_find_pwd;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        changeTv = findViewById(R.id.tv_save);
        sendVCodeTv = findViewById(R.id.send_vcode_btn);
        phoneEt = findViewById(R.id.et_phone_number);
        vcodeEt = findViewById(R.id.vcode_edit);
        userIdEt = findViewById(R.id.et_user_id);
        pwdEt = findViewById(R.id.et_pwd);
        rePwdEt = findViewById(R.id.et_re_pwd);
        sendVCodeTv.setOnClickListener(v -> sendCode());
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                sendVCodeTv.setText("(" + (millisUntilFinished / 1000)+ "s后重新发送)");
                sendVCodeTv.setTextColor(Color.parseColor("#AAAAAA"));
                sendVCodeTv.setClickable(false);
            }

            @Override
            public void onFinish() {
                sendVCodeTv.setTextColor(Color.parseColor("#FD7F8F"));
                sendVCodeTv.setText("获取验证码");
                sendVCodeTv.setClickable(true);
            }
        };
        changeTv.setOnClickListener(v -> changePwd());
    }

    private void sendCode(){
        if (TextUtils.isEmpty(phoneEt.getText().toString())){
            ToastUtil.INSTANCE.suc(FindPwdActivity.this, "请输入手机号");
            return;
        }
        NetService.Companion.getInstance(this).sendCode(3,phoneEt.getText().toString(), new Callback<BaseBean>() {
            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                ToastUtil.INSTANCE.suc(FindPwdActivity.this, "验证码发送成功");
                countDownTimer.start();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.suc(FindPwdActivity.this, msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    private void changePwd(){
        String phone =phoneEt.getText().toString();
        String vcode = vcodeEt.getText().toString();
        String userId = userIdEt.getText().toString();
        String pwd = pwdEt.getText().toString();
        String rePwd = rePwdEt.getText().toString();
        if (TextUtils.isEmpty(phone)){
            ToastUtil.INSTANCE.suc(FindPwdActivity.this, "请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(vcode)){
            ToastUtil.INSTANCE.suc(FindPwdActivity.this, "请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(userId)){
            ToastUtil.INSTANCE.suc(FindPwdActivity.this, "请输入用户ID");
            return;
        }
        if (TextUtils.isEmpty(pwd)){
            ToastUtil.INSTANCE.suc(FindPwdActivity.this, "请输入新密码");
            return;
        }
        if (!TextUtils.isEmpty(rePwd)&&!rePwd.equals(pwd)){
            ToastUtil.INSTANCE.suc(FindPwdActivity.this, "密码不一致");
            return;
        }
        NetService.Companion.getInstance(this).findPwd(userId,phone,
                vcode, pwd,rePwd,new Callback<BaseBean>() {
            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                ToastUtil.INSTANCE.suc(FindPwdActivity.this,"修改成功");
                finish();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.suc(FindPwdActivity.this,msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

}
