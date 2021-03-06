package com.miaomi.fenbei.voice.ui.pay;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alipay.sdk.app.PayTask;
import com.google.gson.JsonObject;
import com.miaomi.fenbei.base.bean.AliPayBean;
import com.miaomi.fenbei.base.bean.DiamondsBean;
import com.miaomi.fenbei.base.bean.GoodsBean;
import com.miaomi.fenbei.base.bean.PayResult;
import com.miaomi.fenbei.base.bean.RefreshRoomBean;
import com.miaomi.fenbei.base.bean.WechatPayBean;
import com.miaomi.fenbei.base.bean.WxMiniPayBean;
import com.miaomi.fenbei.base.bean.WxPayWayBean;
import com.miaomi.fenbei.base.config.BaseConfig;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.CommonUtils;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.DensityUtil;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.base.util.StatusBarUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.web.WebActivity;
import com.miaomi.fenbei.base.widget.GridSpacingItemDecoration;
import com.miaomi.fenbei.voice.R;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@Route(path = "/app/pay")
public class PayActivity extends BaseActivity implements OnClickListener, PayGoodsAdapter.OnSelectedGoodsListener {
    public final static int PAY_WAY_WECHAT = 1;
    public final static int PAY_WAY_ALI = 2;
    private static final int WX_PAY_SUCCESS = 0;
    private static final int WX_PAY_FAIL = -1;
    private static final int WX_PAY_CANCEL = -2;
    //    public static final int PAY_SUC = 1;
//    public static final int PAY_CANCEL = 0;
//    public static final int PAY_FAIL = -1;
//    public static final String EXTRA_START_FLAG = "start_flag";
//    public static final int PAY_FINISH = 2;
//    public static final String EXTRA_CODE = "code";
//    public static final String EXTRA_MSG = "msg";
    private int selectedPayWay = 1;
    RecyclerView mPayRecyclerView;
    PayGoodsAdapter mPayGoodsAdapter;
    LinearLayout rootView;
    private List<GoodsBean> mGoodsList = new ArrayList<>();
    TextView mPayHistory;
    CheckBox mAliSelectTv;
    CheckBox mWechatSelectTv;
    TextView mPayTv;
    ImageView mBackIv;
    GoodsBean mGoodsBean;
    TextView diamondTv;
    TextView agreementTv;
    CheckBox agreementCk;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private IWXAPI msgApi;
//    public final static String WECHAT_APP_ID = "wxbde4b140f5fec89b";
    private LoadHelper loadHelper;
    private int selectedPosition;
    public final static String TAG_SELECTED_POSITION = "TAG_SELECTED_POSITION";

    private int formType;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * ???????????????????????????w????????????????????????????????????????????????????????????????????????????????????????????????
                     */
                    String resultInfo = payResult.getResult();// ?????????????????????????????????
                    String resultStatus = payResult.getResultStatus();
                    // ??????resultStatus ???9000?????????????????????
                    if (TextUtils.equals(resultStatus, "9000")) {
                        getDiamonds();
                        ToastUtil.INSTANCE.suc(PayActivity.this,"????????????");
                        EventBus.getDefault().post(new RefreshRoomBean());
                        back();
//                        setResult(RESULT_OK);
//                        finish();
                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        ToastUtil.INSTANCE.i(PayActivity.this,"????????????");
                    } else if (TextUtils.equals(resultStatus, "8000")) {
                        ToastUtil.INSTANCE.i(PayActivity.this,"?????????????????????");
                    } else {
                        ToastUtil.INSTANCE.error(PayActivity.this,"????????????");
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
//                    @SuppressWarnings("unchecked")
//                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
//                    String resultStatus = authResult.getResultStatus();
//
//                    // ??????resultStatus ??????9000??????result_code
//                    // ??????200?????????????????????????????????????????????????????????????????????????????????
//                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
//                        // ??????alipay_open_id???????????????????????????extern_token ???value
//                        // ??????????????????????????????????????????
//                        showAlert(PayDemoActivity.this, getString(R.string.auth_success) + authResult);
//                    } else {
//                        // ?????????????????????????????????
//                        showAlert(PayDemoActivity.this, getString(R.string.auth_failed) + authResult);
//                    }
//                    break;
                }
                default:
                    break;
            }
        };
    };

    @Override
    protected void onResume() {
        super.onResume();
        getDiamonds();
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_activity_pay;
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarTextColor(this,true);
        formType = getIntent().getIntExtra("formType",0);
        selectedPosition = getIntent().getIntExtra(TAG_SELECTED_POSITION,0);
        rootView = findViewById(R.id.ll_root);
        mPayRecyclerView = findViewById(R.id.rv_pay);
        mPayHistory = findViewById(R.id.tv_pay_history);
        mWechatSelectTv = findViewById(R.id.select_wechat);
        mAliSelectTv = findViewById(R.id.select_ali);
        mPayTv = findViewById(R.id.tv_pay);
        mBackIv = findViewById(R.id.iv_back);
        diamondTv = findViewById(R.id.tv_diamond);
        agreementTv = findViewById(R.id.agreement_content);
        agreementCk = findViewById(R.id.agreement_check);
        agreementTv.setOnClickListener(this);
        mBackIv.setOnClickListener(this);
        mPayTv.setOnClickListener(this);
        mWechatSelectTv.setOnClickListener(this);
        mAliSelectTv.setOnClickListener(this);
        mPayHistory.setOnClickListener(this);
        mPayRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mPayRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3, DensityUtil.INSTANCE.dp2px(this,10f), false));
        mPayGoodsAdapter = new PayGoodsAdapter(mGoodsList,this);
        mPayGoodsAdapter.setOnSelectedGoodsListener(this);
        mPayRecyclerView.setAdapter(mPayGoodsAdapter);

        IntentFilter filter = new IntentFilter();
        filter.addAction(this.getPackageName() + ".WeChatPay");
        broadcastReceiver = new WeChatPayBroadcastReceiver();
        registerReceiver(broadcastReceiver,filter);
        selectedPayWay = PAY_WAY_WECHAT;
        mWechatSelectTv.setChecked(true);
        mAliSelectTv.setChecked(false);
        loadHelper = new LoadHelper();
        loadHelper.registerLoad(rootView);
        getGoodsList();
    }

    private void getGoodsList(){
        NetService.Companion.getInstance(this).getGoodsList(new Callback<List<GoodsBean>>() {
            @Override
            public void onSuccess(int nextPage, List<GoodsBean> bean, int code) {
                loadHelper.bindView(code);
                mGoodsList.clear();
                mGoodsList.addAll(bean);
                mGoodsBean = mGoodsList.get(selectedPosition);
                mGoodsBean.setSelected(true);
                mPayTv.setText(String.format("??????%s???",mGoodsBean.getPay_money()));
                mPayGoodsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.i(PayActivity.this,msg);
                loadHelper.setErrorCallback(code, new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getGoodsList();
                    }
                });
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

//    private void createGoods(){
//        GoodsBean bean1 = new GoodsBean();
//        bean1.setPrice("100");
//        bean1.setPay_money("10");
//        bean1.setTrade_name("100???");
//        bean1.setSelected(true);
//        mGoodsBean = bean1;
//        mPayTv.setText(String.format("??????%s???",mGoodsBean.getPay_money()));
//        mGoodsList.add(bean1);
//
//        GoodsBean bean2 = new GoodsBean();
//        bean2.setPrice("300");
//        bean2.setPay_money("30");
//        bean2.setTrade_name("300???");
//        mGoodsList.add(bean2);
//
//        GoodsBean bean3 = new GoodsBean();
//        bean3.setPrice("500");
//        bean3.setPay_money("50");
//        bean3.setTrade_name("500???");
//        mGoodsList.add(bean3);
//
//        GoodsBean bean4 = new GoodsBean();
//        bean4.setPrice("1000");
//        bean4.setPay_money("100");
//        bean4.setTrade_name("1000???");
//        mGoodsList.add(bean4);
//
//        GoodsBean bean5 = new GoodsBean();
//        bean5.setPrice("5000");
//        bean5.setPay_money("500");
//        bean5.setTrade_name("5000???");
//        mGoodsList.add(bean5);
//
//        GoodsBean bean6 = new GoodsBean();
//        bean6.setPrice("10000");
//        bean6.setPay_money("1000");
//        bean6.setTrade_name("10000???");
//        mGoodsList.add(bean6);
//    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_pay_history){
            ARouter.getInstance().build("/app/payhistroy").navigation();
        }
        if (id == R.id.select_wechat){
            selectedPayWay = PAY_WAY_WECHAT;
            mWechatSelectTv.setChecked(true);
            mAliSelectTv.setChecked(false);
        }
        if (id == R.id.select_ali){
            selectedPayWay = PAY_WAY_ALI;
            mWechatSelectTv.setChecked(false);
            mAliSelectTv.setChecked(true);
        }
        if (id == R.id.tv_pay){
            if (!agreementCk.isChecked()){
                return;
            }
            if (DataHelper.INSTANCE.getIsYoungModelSetting() == 1) {
                ToastUtil.INSTANCE.i(this, "????????????????????????????????????~");
                return;
            }
            if (selectedPayWay ==  PAY_WAY_ALI ){
                if (mGoodsBean!= null){
                    alipay(mGoodsBean);
                }
            }
            if (selectedPayWay ==  PAY_WAY_WECHAT ){
                if (mGoodsBean!= null){
                    distributePayWay(mGoodsBean);
                }
            }

        }
        if (id == R.id.iv_back){
            finish();
        }
        if (id == R.id.agreement_content){
            WebActivity.start(PayActivity.this,BaseConfig.XY_YHCZXY,"??????????????????");
        }
    }

    @Override
    public void onSelectedGoods(GoodsBean bean) {
        mGoodsBean = bean;
        mPayTv.setText(String.format("??????%s???",bean.getPay_money()));
    }

    private void getDiamonds(){
        NetService.Companion.getInstance(this).getDiamonds(new Callback<DiamondsBean>() {
            @Override
            public void onSuccess(int nextPage, final DiamondsBean bean, int code) {
                diamondTv.setText(bean.getBalance()+"");
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });

    }

    public static boolean isAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }


    private void alipay(GoodsBean goodsBean){
        if (!isAliPayInstalled(this)){
            ToastUtil.INSTANCE.i(this,"?????????????????????????????????");
            return;
        }
        NetService.Companion.getInstance(this).aliPay(goodsBean.getPrice(), goodsBean.getPay_money(), goodsBean.getTrade_name(), new Callback<AliPayBean>() {
            @Override
            public void onSuccess(int nextPage, final AliPayBean bean, int code) {
                final Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(PayActivity.this);
                        Map<String, String> result = alipay.payV2(bean.getOrder_info(), true);
                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };

                // ??????????????????
                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(PayActivity.this,msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    private void distributePayWay(final GoodsBean goodsBean){
        if (!CommonUtils.isAppInstall(this,"com.tencent.mm")){
            ToastUtil.INSTANCE.error(PayActivity.this,"??????????????????????????????");
            return;
        }
        NetService.Companion.getInstance(this).getPayWay(new Callback<WxPayWayBean>() {
            @Override
            public void onSuccess(int nextPage, WxPayWayBean bean, int code) {
                if (bean.getStatus() == 1){
                    wechatPay(goodsBean);
                }else{
                    wxMiniPrigramPay(goodsBean);
                }
//                wxMiniPrigramPay(goodsBean);
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(PayActivity.this,msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });

    }

    private void wechatPay(GoodsBean goodsBean){

        msgApi = WXAPIFactory.createWXAPI(this, null);
        // ??????app???????????????
        msgApi.registerApp(BaseConfig.APPID_WX);
        NetService.Companion.getInstance(this).wechatPay(BaseConfig.APPID_WX,goodsBean.getPrice(), goodsBean.getPay_money(), goodsBean.getTrade_name(), new Callback<WechatPayBean>() {
            @Override
            public void onSuccess(int nextPage, WechatPayBean bean, int code) {
                PayReq request = new PayReq();
                request.appId = BaseConfig.APPID_WX;
                request.partnerId = String.valueOf(bean.getOrder_info().getPartnerid());
                request.prepayId = bean.getOrder_info().getPrepayid();
                request.packageValue = "Sign=WXPay";
                request.nonceStr = bean.getOrder_info().getNoncestr();
                request.timeStamp = String.valueOf(bean.getOrder_info().getTimestamp());
                request.sign = bean.getOrder_info().getSign();
                msgApi.sendReq(request);
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(PayActivity.this,msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    WeChatPayBroadcastReceiver broadcastReceiver;

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    private void back(){
        if(selectedPosition > 0){
            finish();
        }
        if (formType > 0){
            finish();
        }
    }

    private void wxMiniPrigramPay(GoodsBean goodsBean) {
        msgApi = WXAPIFactory.createWXAPI(this, null);
        msgApi.registerApp(BaseConfig.APPID_WX);
        NetService.Companion.getInstance(this).wxNewMiniPay(goodsBean.getPay_money(), new Callback<WxMiniPayBean>() {
            @Override
            public void onSuccess(int nextPage, WxMiniPayBean bean, int code) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("app_id",bean.getXcx_url().getApp_id());
                    jsonObject.put("product_name",bean.getXcx_url().getProduct_name());
                    jsonObject.put("payee_name",bean.getXcx_url().getPayee_name());
                    jsonObject.put("trx_no",bean.getXcx_url().getTrx_no());
                    jsonObject.put("order_no",bean.getXcx_url().getOrder_no());
                    jsonObject.put("order_amout",bean.getXcx_url().getOrder_amout());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
                req.userName = bean.getOriginal_id(); // ??????????????????id
                req.path = "/pages/payIndex/payIndex?rc_result="+jsonObject.toString();////??????????????????????????????????????????????????????????????????????????????????????????????????????????????? query ????????????????????????????????????????????? "?foo=bar"???
                req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;
                msgApi.sendReq(req);
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(PayActivity.this,msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });

    }

    class WeChatPayBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int result = intent.getIntExtra("result", -1);
            switch (result) {
                case WX_PAY_SUCCESS:
                    //????????????
                    getDiamonds();
                    EventBus.getDefault().post(new RefreshRoomBean());
                    ToastUtil.INSTANCE.suc(PayActivity.this,"????????????");
                    back();
                    break;
                case WX_PAY_FAIL:
                    //????????????
                    ToastUtil.INSTANCE.error(PayActivity.this,"????????????");
                    break;
                case WX_PAY_CANCEL:
                    //????????????
                    ToastUtil.INSTANCE.error(PayActivity.this,"????????????");
                    break;
                default:
                    break;
            }
        }
    }
}
