package com.miaomi.fenbei.voice.ui.mine.cash_withdrawal;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.bean.CashOutBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.core.dialog.CommonDialog;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.mine.BindAliActivity;
import com.miaomi.fenbei.voice.ui.mine.BindBankCardActivity;

import org.jetbrains.annotations.NotNull;

public class CashWithdrawalActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvRecord;
    private TextView tvMoney;
    private EditText etExtractNumber;
    private TextView tvAllExtract;
    private TextView tvName;
    private TextView tvBankCard;
    //    private TextView tvOpeningBank;
    private TextView tvBankName;
    private TextView tvBankAress;
    private TextView tvAlipay;
    private TextView tvStart;
    //    private LinearLayout llParentInfo;
//    private LinearLayout llParent;
//    private LoadHelper loadHelper;
    private CommonDialog mCommonDialog;
//    private CheckBox agreementCheck;
//    private TextView agreementContentTv;
    private boolean isBindAli;


    public static void start(Context context ,boolean isBindAli) {
        Intent intent = new Intent(context, CashWithdrawalActivity.class);
        intent.putExtra("isBindAli",isBindAli);
        context.startActivity(intent);
    }
    @Override
    public int getLayoutId() {
        return R.layout.user_activity_cash_withdrawal;
    }

    @Override
    public void initView() {
//        loadHelper = new LoadHelper();
//        llParentInfo = findViewById(R.id.ll_parent_info);
        setBaseStatusBar(false,false);
        isBindAli = getIntent().getBooleanExtra("isBindAli",true);
        tvRecord = findViewById(R.id.right_tv);
        tvBankName = findViewById(R.id.tv_name_bank);
        tvBankCard = findViewById(R.id.tv_bank_card);
        tvBankAress = findViewById(R.id.tv_bank_address);
        tvMoney = findViewById(R.id.tv_money);
        etExtractNumber =  findViewById(R.id.et_extract_number);
        tvAllExtract = findViewById(R.id.tv_all_extract);
        tvName = findViewById(R.id.tv_name);
//        tvBankCard = findViewById(R.id.tv_bank_card);
//        tvOpeningBank = findViewById(R.id.tv_opening_bank);
//        tvBankBranch = findViewById(R.id.tv_bank_branch);
        tvAlipay = findViewById(R.id.tv_alipay);
        tvStart = findViewById(R.id.tv_start);
//        llParent = findViewById(R.id.ll_parent);
//        agreementCheck = findViewById(R.id.agreement_check);
//        agreementContentTv = findViewById(R.id.agreement_content);
        mCommonDialog = new CommonDialog(this);
//        loadHelper.registerLoad(llParent);

//        agreementContentTv.setOnClickListener(this);
        tvRecord.setOnClickListener(this);
        tvAllExtract.setOnClickListener(this);
        tvStart.setOnClickListener(this);

        tvRecord.setText("提现记录");
        findViewById(R.id.tv_edit_ali).setOnClickListener(v -> BindAliActivity.startActivity(this));
        findViewById(R.id.tv_edit_bank).setOnClickListener(v -> BindBankCardActivity.startActivity(this));
//        findViewById(R.id.agreement_content1).setOnClickListener(v -> WebActivity.start(CashWithdrawalActivity.this,DataHelper.INSTANCE.getIMDevelop().getNew_main()+"/html/activity/economy_rule","自由职业者服务合作协议"));
        if (!isBindAli){
            CommonDialog addBlackDialog =new CommonDialog(CashWithdrawalActivity.this);
            addBlackDialog.setContent("检测到您的账号当前未绑定支付宝");
            addBlackDialog.setTitle("友情提示");
            addBlackDialog.setLeftBt("取消", v -> addBlackDialog.dismiss());
            addBlackDialog.setRightBt("去绑定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BindAliActivity.startActivity(CashWithdrawalActivity.this);
                }
            });
            addBlackDialog.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void initData(CashOutBean bean){
        tvMoney.setText(bean.getEarning() + "");
        tvName.setText("姓名：" + bean.getName());
        tvAlipay.setText("支付宝账号：" + bean.getAlipay_account());
        tvBankName.setText("开户行："+bean.getBank_name());
        tvBankAress.setText("开户行地址："+bean.getBank_address());
        tvBankCard.setText("卡号："+bean.getBank_account());
        tvAlipay.setVisibility(View.VISIBLE);
//        llParentInfo.setVisibility(View.GONE);
    }

    private void getData(){
        NetService.Companion.getInstance(this).getCashOutInfo(DataHelper.INSTANCE.getLoginToken(), new Callback<CashOutBean>() {
            @Override
            public void onSuccess(int nextPage, CashOutBean bean, int code) {
//                loadHelper.bindView(code);
                initData(bean);
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
//                loadHelper.setErrorCallback(code,new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        getData();
//                    }
//                });
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    private void start(){
        String money = etExtractNumber.getText().toString();
        NetService.Companion.getInstance(this).CashWithdrawal(DataHelper.INSTANCE.getLoginToken(),money, new Callback<BaseBean>() {
            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                ToastUtil.INSTANCE.suc(CashWithdrawalActivity.this,getString(R.string.app_cash_out_success));
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(CashWithdrawalActivity.this,msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.right_tv) {
            startActivity(CashOutRecordActivity.getIntent(this));
        }else if (i == R.id.tv_all_extract){
            etExtractNumber.setText(tvMoney.getText());
            etExtractNumber.setSelection(tvMoney.getText().length());
        }else if (i == R.id.tv_start){
//            if (!agreementCheck.isChecked()){
//                ToastUtil.INSTANCE.error(this,"请先同意共享经济合作伙伴协议");
//                return;
//            }
            String money = etExtractNumber.getText().toString();
            if (money.isEmpty()){
                ToastUtil.INSTANCE.error(this,"请输入提现金额");
                return;
            }
            if (Double.parseDouble(money) >= 110 ){
                mCommonDialog.setTitle("提现");
                mCommonDialog.setContent("发起提现后，提现金额将在1-3个工作日到账。");
                mCommonDialog.setLeftBt("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCommonDialog.dismiss();
                    }
                });
                mCommonDialog.setRightBt("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        start();
                        mCommonDialog.dismiss();
                    }
                });
                mCommonDialog.show();
            }else {
                ToastUtil.INSTANCE.error(this,"提现金额需大于等于110元，请重新输入");
            }
        }else if (R.id.agreement_content == i){
//            AgreementActivity.startActivity(CashWithdrawalActivity.this,AgreementActivity.AGREE_TYPE_GXJJ);
        }
    }
}
