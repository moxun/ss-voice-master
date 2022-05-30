package com.miaomi.fenbei.voice.ui.mine.identity_authentication;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.bean.IdentifyInfoBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.RouterUrl;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

@Route(path = "/app/notcertified")
public class NotCertifiedActivity extends BaseActivity {

    private LinearLayout llParent;
    private TextView tvName;
    private TextView tvId;
    private TextView tvPhone;
//    private LoadHelper loadHelper;
    private TextView btnTv;

    public static Intent getIntent(Context context) {
        return new Intent(context, NotCertifiedActivity.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_activity_not_certified;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false, false);
        btnTv=findViewById(R.id.tv_btn);
        btnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RouterUrl.identityAuthentication).navigation();
            }
        });
    }



}
