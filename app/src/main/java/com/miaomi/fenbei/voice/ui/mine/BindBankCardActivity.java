package com.miaomi.fenbei.voice.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.miaomi.fenbei.base.bean.BankListBean;
import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BindBankCardActivity extends BaseActivity {

    private EditText aliAccountEt;
    private EditText realNameEt;
    private EditText bankAddressEt;
    private TextView authenticationBt;
    private TextView bankNameEt;

    public static void startActivity(Context context){
        Intent intent = new Intent(context,BindBankCardActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_bank_card;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        authenticationBt = findViewById(R.id.tv_authentication);
        aliAccountEt = findViewById(R.id.et_ali_account);
        realNameEt = findViewById(R.id.et_real_name);
        bankAddressEt = findViewById(R.id.et_bankdress);
        bankNameEt = findViewById(R.id.et_bank_name);
        authenticationBt.setOnClickListener(v -> submit());
        bankNameEt.setOnClickListener(v -> getData());
    }
    private void submit(){
        String name = realNameEt.getText().toString();
        String bankName = bankNameEt.getText().toString();
        String address = bankAddressEt.getText().toString();
        String bankAccount = aliAccountEt.getText().toString();
        if (TextUtils.isEmpty(name)){
            ToastUtil.INSTANCE.error(BindBankCardActivity.this,"请输入真实姓名");
            return;
        }
        if (TextUtils.isEmpty(bankName)){
            ToastUtil.INSTANCE.error(BindBankCardActivity.this,"请输入开户银行名称");
            return;
        }
        if (TextUtils.isEmpty(address)){
            ToastUtil.INSTANCE.error(BindBankCardActivity.this,"请输入银行地址");
            return;
        }
        if (TextUtils.isEmpty(bankAccount)){
            ToastUtil.INSTANCE.error(BindBankCardActivity.this,"请输入银行卡号");
            return;
        }
        NetService.Companion.getInstance(BindBankCardActivity.this).bindBankCardAccount(name,bankAccount,bankName,address, new Callback<BaseBean>() {
            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                ToastUtil.INSTANCE.suc(BindBankCardActivity.this,"提交成功");
                finish();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.suc(BindBankCardActivity.this,msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    private void showBankCardList(List<BankListBean> options1Items){
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(BindBankCardActivity.this, (options1, option2, options3, v) -> {
            //返回的分别是三个级别的选中位置
            String tx = options1Items.get(options1).getBank();
            bankNameEt.setText(tx);
        }).build();
        pvOptions.setPicker(options1Items);
        pvOptions.show();
    }

    private void getData(){
        NetService.Companion.getInstance(BindBankCardActivity.this).getBankList(new Callback<List<BankListBean>>() {
            @Override
            public void onSuccess(int nextPage, List<BankListBean> list, int code) {
                showBankCardList(list);
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.suc(BindBankCardActivity.this,msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }
}
