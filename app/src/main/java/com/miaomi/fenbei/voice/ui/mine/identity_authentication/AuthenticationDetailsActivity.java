package com.miaomi.fenbei.voice.ui.mine.identity_authentication;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.miaomi.fenbei.base.bean.IdentifyInfoBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

@Route(path = "/mine/authenticationDetail")
public class AuthenticationDetailsActivity extends BaseActivity {

    private LinearLayout llParent;
    private TextView tvName;
    private TextView tvId;
    private TextView tvPhone;
//    private LoadHelper loadHelper;

    public static Intent getIntent(Context context) {
        return new Intent(context, AuthenticationDetailsActivity.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_activity_authentication_details;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false, false);
//        loadHelper = new LoadHelper();
        llParent = findViewById(R.id.ll_parent);
        tvName = findViewById(R.id.tv_name);
        tvId = findViewById(R.id.tv_id);

        tvPhone = findViewById(R.id.tv_phone);
//        loadHelper.registerLoad(llParent);
        getData();
    }

    private void initData(IdentifyInfoBean bean) {
        tvName.setText(bean.getName());
        tvId.setText(bean.getIdcard());
        tvPhone.setText(bean.getMobile());
    }

    private void getData() {
        NetService.Companion.getInstance(this).getIdentifyInfo(DataHelper.INSTANCE.getLoginToken(), new Callback<IdentifyInfoBean>() {
            @Override
            public void onSuccess(int nextPage, IdentifyInfoBean bean, int code) {
//                loadHelper.bindView(code);
                initData(bean);
            }


            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
//                loadHelper.setErrorCallback(code, v -> {
//                    loadHelper.bindView(Data.CODE_LOADING);
//                    getData();
//                });
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }
}
