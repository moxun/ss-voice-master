package com.miaomi.fenbei.voice.ui.main;

import android.content.Context;
import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.bean.UserInfo;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.soulplanets.SoulPlanetsView;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.main.adapter.MatchUserAdapter;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;

public class MatchUserActivity extends BaseActivity {



    public static void start(@NonNull Context context) {
        final Intent intent = new Intent(context, MatchUserActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_match_user;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        final SoulPlanetsView soulPlanet = findViewById(R.id.soulPlanetView);
        soulPlanet.setAdapter(new MatchUserAdapter());

        soulPlanet.setOnTagClickListener((parent, view, position) -> {
        });
        findViewById(R.id.tv_match).setOnClickListener(v -> getRecommandUser());
    }

    private void getRecommandUser(){

        NetService.Companion.getInstance(this).getRecommandUser(DataHelper.INSTANCE.getUserInfo().getGender(),new Callback<UserInfo>() {
            @Override
            public void onSuccess(int nextPage, UserInfo bean, int code) {
//                ARouter.getInstance().build("/imkit/privatechat")
//                        .withString("CHAT_ID", String.valueOf(bean.getUser_id()))
//                        .withString("FROM_USER_NICKNAME", bean.getNickname())
//                        .withString("FROM_USER_AVTER", bean.getFace())
//                        .navigation();
                ARouter.getInstance().build("/app/userhomepage")
                        .withString("user_id",String.valueOf(bean.getUser_id()))
                        .withString("user_name",bean.getNickname())
                        .withString("user_face",bean.getFace())
                        .navigation();
                finish();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(MatchUserActivity.this,msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }
}
