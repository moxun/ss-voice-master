package com.miaomi.fenbei.voice.ui.mine.about;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.miaomi.fenbei.base.config.BaseConfig;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.util.AppUtils;
import com.miaomi.fenbei.base.util.CopyUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.web.WebActivity;
import com.miaomi.fenbei.voice.R;

public class AboutActivity extends BaseActivity {

    public static Intent getIntent(Context context, String customServiceQq,String familyEntryQq) {
        Intent intent = new Intent(context, AboutActivity.class);
        intent.putExtra("custom_service_qq",customServiceQq);
        intent.putExtra("family_entry_qq",familyEntryQq);
        return intent;
    }
    @Override
    public int getLayoutId() {
        return R.layout.user_activity_about;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        TextView tvVersion = findViewById(R.id.tv_version);
        TextView agreementTv = findViewById(R.id.tv_agreement);
        TextView tvQq = findViewById(R.id.tv_qq);
        TextView concealTv = findViewById(R.id.tv_conceal);
        String customServiceQq = getIntent().getStringExtra("custom_service_qq");
        String familyEntryQq = getIntent().getStringExtra("family_entry_qq");
        tvVersion.setText("版本号："+AppUtils.INSTANCE.getVersionName());
        String customServiceQqColor = customServiceQq +"   复制";
        String familyEntryQqColor = familyEntryQq +"   复制";
        SpannableString customServiceSpannableString = new SpannableString(customServiceQqColor);
        SpannableString familyEntrySpannableString = new SpannableString(familyEntryQqColor);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(
                Color.parseColor("#FD7F8F"));
        customServiceSpannableString.setSpan(foregroundColorSpan, customServiceQq.length(), customServiceQqColor.length(),
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        familyEntrySpannableString.setSpan(foregroundColorSpan, familyEntryQq.length(), familyEntryQqColor.length(),
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        agreementTv.setOnClickListener(v -> WebActivity.start(AboutActivity.this, BaseConfig.XY_YHXY,"用户协议")
        );
        concealTv.setOnClickListener(v -> WebActivity.start(AboutActivity.this,BaseConfig.XY_YSXY,"隐私政策"));
        tvQq.setOnClickListener(v -> {
            CopyUtil.copy("山水语音",AboutActivity.this);
            ToastUtil.INSTANCE.suc(AboutActivity.this,"复制成功");
        });
    }
}
