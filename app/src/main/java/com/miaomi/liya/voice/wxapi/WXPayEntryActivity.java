package com.miaomi.liya.voice.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.miaomi.fenbei.base.config.BaseConfig;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by Toast-pc on 2016/5/31.
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, BaseConfig.APPID_WX);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            closeSelf();
            Intent intent = new Intent();
            intent.setAction(this.getPackageName() + ".WeChatPay");
            intent.putExtra("result", baseResp.errCode);
            sendBroadcast(intent);
        }
    }

    public void closeSelf() {
        finish();
        overridePendingTransition(0, 0);
    }
}
