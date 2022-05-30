//package com.miaomi.fenbei.voice.ui.mine.balance;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.miaomi.fenbei.common.bean.BalanceInfoBean;
//import com.miaomi.fenbei.common.core.BaseActivity;
//import com.miaomi.fenbei.common.core.dialog.CommonDialog;
//import com.miaomi.fenbei.common.net.Callback;
//import com.miaomi.fenbei.common.net.Data;
//import com.miaomi.fenbei.common.net.NetService;
//import com.miaomi.fenbei.common.util.DataHelper;
//import com.miaomi.fenbei.common.util.LoadHelper;
//import com.miaomi.fenbei.common.util.StringUtil;
//import com.miaomi.fenbei.voice.R;
//import com.miaomi.fenbei.voice.ui.mine.BindAliActivity;
//import com.miaomi.fenbei.voice.ui.mine.IncomeDetailsActivity;
//import com.miaomi.fenbei.voice.ui.mine.cash_withdrawal.CashWithdrawalActivity;
//import com.miaomi.fenbei.voice.ui.mine.identity_authentication.ExamineActivity;
//import com.miaomi.fenbei.voice.ui.mine.identity_authentication.IdentityAuthenticationActivity;
//
//import org.jetbrains.annotations.NotNull;
//
//public class BalanceActivity extends BaseActivity implements View.OnClickListener {
//
//    private TextView tvMoney;
//    private TextView tvExtract;
//    private TextView tvExchange;
//    private LinearLayout llParent;
//    private LoadHelper loadHelper;
//    private CommonDialog authenticationCommonDialog;
//    private CommonDialog examineCommonDialog;
//    private boolean isBindAli;
//    private TextView exchangeHistoryTv;
//    private TextView incomeHistoryTv;
//    private TextView incomeDayTv;
//    private TextView incomeWeekTv;
//    private TextView incomeMonthTv;
//    private TextView incomeLastMonthTv;
//    private ImageView backIv;
//    private TextView aliTv;
//    public final static int REQUEST_CODE_EXTRACT = 202;
//    public final static int REQUEST_CODE_AUTHENTICATION = 203;
//    public final static int REQUEST_CODE_EXCHANGE = 204;
//    private int mIdentifyStatus ;
//
//    public static Intent getIntent(Context context) {
//        return new Intent(context, BalanceActivity.class);
//    }
//
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.user_activity_balance;
//    }
//
//    @Override
//    public void initView() {
//        setBaseStatusBar(false, false);
//        loadHelper = new LoadHelper();
//        backIv = findViewById(R.id.iv_back);
//        aliTv = findViewById(R.id.tv_ali);
//        incomeDayTv = findViewById(R.id.tv_income_day);
//        incomeWeekTv = findViewById(R.id.tv_income_week);
//        incomeMonthTv = findViewById(R.id.tv_income_month);
//        incomeLastMonthTv = findViewById(R.id.tv_income_last_month);
//        tvMoney =  findViewById(R.id.tv_money);
//        tvExtract = findViewById(R.id.tv_extract);
//        tvExchange = findViewById(R.id.tv_exchange);
//        llParent =  findViewById(R.id.ll_parent);
//        incomeHistoryTv = findViewById(R.id.tv_income_history);
//        backIv.setOnClickListener(this);
//        aliTv.setOnClickListener(this);
//        incomeHistoryTv.setOnClickListener(this);
//        exchangeHistoryTv.setOnClickListener(this);
//        tvExtract.setOnClickListener(this);
//        tvExchange.setOnClickListener(this);
//        authenticationCommonDialog = new CommonDialog(this);
//        examineCommonDialog = new CommonDialog(this);
//        loadHelper.registerLoad(llParent);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        getData();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_EXTRACT) {
//            if (resultCode == RESULT_OK) {
//                setResult(RESULT_OK);
//                getData();
//            }
//        } else if (requestCode == REQUEST_CODE_AUTHENTICATION) {
//            if (resultCode == RESULT_OK) {
//                setResult(RESULT_OK);
//                getData();
//            }
//        } else if (requestCode == REQUEST_CODE_EXCHANGE) {
//            getData();
//        }
//    }
//
//    private void initData(final BalanceInfoBean bean) {
//        mIdentifyStatus = bean.getIdentify_status();
//        tvMoney.setText(bean.getEarning());
//        incomeDayTv.setText(StringUtil.formatDouble(bean.getToday_pending()));
//        incomeWeekTv.setText(StringUtil.formatDouble(bean.getToday_rent()));
//        incomeMonthTv.setText(StringUtil.formatDouble(bean.getMonth_pending()));
//        incomeLastMonthTv.setText(StringUtil.formatDouble(bean.getMonth_rent()));
//        isBindAli = bean.isBind_status();
//        if (bean.isBind_status()){
//            aliTv.setText("提现支付宝："+bean.getAlipay_account()+" 修改>>");
//        }else{
//            aliTv.setText("未绑定提现支付宝>>");
//        }
//    }
//
//    private void getData() {
//        NetService.Companion.getInstance(this).getBalanceInfo(DataHelper.INSTANCE.getLoginToken(), new Callback<BalanceInfoBean>() {
//            @Override
//            public void onSuccess(int nextPage, BalanceInfoBean bean, int code) {
//                loadHelper.bindView(code);
//                initData(bean);
//            }
//
//            @Override
//            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
//                loadHelper.setErrorCallback(code, v -> {
//                    loadHelper.bindView(Data.CODE_LOADING);
//                    getData();
//                });
//
//            }
//
//            @Override
//            public boolean isAlive() {
//                return isLive();
//            }
//        });
//    }
//
//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        if (id == R.id.tv_income_history){
//            IncomeDetailsActivity.start(this);
//        }
//        if (id == R.id.iv_back){
//            finish();
//        }
//        if (id == R.id.tv_exchange) {
//            startActivityForResult(BalanceExchangeActivity.getIntent(this), REQUEST_CODE_EXCHANGE);
//        }
//        if (id == R.id.tv_ali){
//            if (mIdentifyStatus == 2){
//                BindAliActivity.startActivity(this);
//            }else{
//                showAuthenDialog();
//            }
//        }
//        if (id == R.id.tv_extract) {
//            if (isBindAli) {
//                startActivityForResult(CashWithdrawalActivity.getIntent(BalanceActivity.this), REQUEST_CODE_EXTRACT);
//            } else {
//                if (mIdentifyStatus == 2){
//                    BindAliActivity.startActivity(this);
//                }else{
//                    showAuthenDialog();
//                }
//            }
//
//        }
////            switch (mIdentifyStatus) {
////
////                case 0:
////                    authenticationCommonDialog.setTitle("身份认证");
////                    authenticationCommonDialog.setContent("为确保资金安全，需身份认证后方可提现");
////                    authenticationCommonDialog.setLeftBt("取消", new View.OnClickListener() {
////                        @Override
////                        public void onAdd(View v) {
////                            authenticationCommonDialog.dismiss();
////                        }
////                    });
////                    authenticationCommonDialog.setRightBt("去认证", new View.OnClickListener() {
////                        @Override
////                        public void onAdd(View v) {
////                            startActivityForResult(IdentityAuthenticationActivity.getIntent(BalanceActivity.this), REQUEST_CODE_AUTHENTICATION);
////                            authenticationCommonDialog.dismiss();
////                        }
////                    });
////                    authenticationCommonDialog.show();
////                    break;
////                case 1:
////                    examineCommonDialog.setTitle("身份认证");
////                    examineCommonDialog.setContent("为确保资金安全，需身份认证后方可提现");
////                    examineCommonDialog.setLeftBt("取消", new View.OnClickListener() {
////                        @Override
////                        public void onAdd(View v) {
////                            examineCommonDialog.dismiss();
////                        }
////                    });
////                    examineCommonDialog.setRightBt("去认证", new View.OnClickListener() {
////                        @Override
////                        public void onAdd(View v) {
////                            startActivity(ExamineActivity.getIntent(BalanceActivity.this));
////                            examineCommonDialog.dismiss();
////                        }
////                    });
////                    examineCommonDialog.show();
////                    break;
////                case 2:
////                    startActivityForResult(CashWithdrawalActivity.getIntent(BalanceActivity.this), REQUEST_CODE_EXTRACT);
////                    break;
////                default:
////            }
//    }
//
//    private void showAuthenDialog(){
//        authenticationCommonDialog.setTitle("身份认证");
//        authenticationCommonDialog.setContent("为确保资金安全，需身份认证后方可提现");
//        authenticationCommonDialog.setLeftBt("取消", v -> authenticationCommonDialog.dismiss());
//        authenticationCommonDialog.setRightBt("去认证", v -> {
//            if (mIdentifyStatus == 0){
//                startActivityForResult(IdentityAuthenticationActivity.getIntent(BalanceActivity.this), REQUEST_CODE_AUTHENTICATION);
//            }else{
//                startActivity(ExamineActivity.getIntent(BalanceActivity.this));
//            }
//            authenticationCommonDialog.dismiss();
//        });
//        authenticationCommonDialog.show();
//    }
//}
