package com.miaomi.fenbei.voice.ui.mine.identity_authentication;

import android.content.Context;
import android.content.Intent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.voice.R;

@Route(path = "/app/examine")
public class ExamineActivity extends BaseActivity {

    public static Intent getIntent(Context context) {
        return new Intent(context, ExamineActivity.class);
    }
    @Override
    public int getLayoutId() {
        return R.layout.user_activity_examine;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
    }
}
