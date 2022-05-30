package com.miaomi.fenbei.voice.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

public class BindAliActivity extends BaseActivity {

    private EditText aliAccountEt;
    private EditText realNameEt;
    private TextView authenticationBt;

    public static void startActivity(Context context){
        Intent intent = new Intent(context,BindAliActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_ali;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        authenticationBt = findViewById(R.id.tv_authentication);
        aliAccountEt = findViewById(R.id.et_ali_account);
        realNameEt = findViewById(R.id.et_real_name);
        authenticationBt.setOnClickListener(v -> submit());
    }
    private void submit(){
        String name = realNameEt.getText().toString();
        String aliAccount = aliAccountEt.getText().toString();
        NetService.Companion.getInstance(BindAliActivity.this).bindAliAccount(name, aliAccount, new Callback<BaseBean>() {
            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                ToastUtil.INSTANCE.suc(BindAliActivity.this,"提交成功");
                finish();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.suc(BindAliActivity.this,msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }
}
