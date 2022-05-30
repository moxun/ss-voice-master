package com.miaomi.fenbei.voice.ui.mine.pwd;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.bean.LocalUserBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.RouterUrl;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

@Route(path = RouterUrl.pwdSetting)
public class PwdSettingActivity extends BaseActivity {

    TextView changeTv;
    EditText et_pwd_lod;
    EditText pwdEt;
    EditText rePwdEt;

    public static void start(Context context) {
        Intent intent = new Intent(context, PwdSettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_activity_pwd_setting;
    }


    @Override
    public void initView() {
        setBaseStatusBar(false, false);
        changeTv = findViewById(R.id.tv_save);
        et_pwd_lod = findViewById(R.id.et_pwd_lod);
        pwdEt = findViewById(R.id.et_pwd);
        rePwdEt = findViewById(R.id.et_re_pwd);
        changeTv.setOnClickListener(v -> changePwd());
    }

    private void changePwd() {
        String pwdOld = et_pwd_lod.getText().toString();
        String pwd = pwdEt.getText().toString();
        String rePwd = rePwdEt.getText().toString();

        if (TextUtils.isEmpty(pwdOld)) {
            ToastUtil.INSTANCE.suc(PwdSettingActivity.this, "请输入原密码");
            return;
        }

        if (TextUtils.isEmpty(pwd)) {
            ToastUtil.INSTANCE.suc(PwdSettingActivity.this, "请输入新密码");
            return;
        }
        if (!TextUtils.isEmpty(rePwd) && !rePwd.endsWith(pwd)) {
            ToastUtil.INSTANCE.suc(PwdSettingActivity.this, "密码不一致");
            return;
        }
        NetService.Companion.getInstance(this).changePwd(
                pwdOld, pwd, rePwd, new Callback<BaseBean>() {
                    @Override
                    public void onSuccess(int nextPage, BaseBean bean, int code) {
                        LocalUserBean data = DataHelper.INSTANCE.getUserInfo();
                        data.setIs_pwd(1);
                        DataHelper.INSTANCE.updatalUserInfo(data);
                        ToastUtil.INSTANCE.suc(PwdSettingActivity.this, "修改成功");
                        finish();
                    }

                    @Override
                    public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                        ToastUtil.INSTANCE.suc(PwdSettingActivity.this, msg);
                    }

                    @Override
                    public boolean isAlive() {
                        return isLive();
                    }
                });
    }
}
