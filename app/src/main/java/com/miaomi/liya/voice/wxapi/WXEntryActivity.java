package com.miaomi.liya.voice.wxapi;


import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

public class WXEntryActivity extends WXCallbackActivity {

//    private IWXAPI api;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        api = WXAPIFactory.createWXAPI(this, BaseConfig.APPID_WX,false);
//        api.registerApp(BaseConfig.APPID_WX);
//        api.handleIntent(getIntent(), this);
//    }
//
//    @Override
//    public void onReq(BaseReq baseReq) {
//    }
//
    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
            WXLaunchMiniProgram.Resp launchMiniProResp = (WXLaunchMiniProgram.Resp) baseResp;
            String extraData =launchMiniProResp.extMsg;
            String showMsg;
            if (extraData.equals("success")){
                showMsg = "支付成功";
            }else if (extraData.equals("cancel")){
                showMsg = "支付取消";
            }else{
                showMsg = "支付失败";
            }
            ToastUtil.INSTANCE.suc(WXEntryActivity.this,showMsg);
            ARouter.getInstance().build("/app/pay").navigation();
            finish();
            return;
        }
        super.onResp(baseResp);
    }
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//        api.handleIntent(intent, this);
//    }
}