package com.miaomi.fenbei.room.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.miaomi.fenbei.base.bean.UserInfo;
import com.miaomi.fenbei.room.R;

/**
 * 红包-转账
 */
public class RedEnvelopeDialog extends Dialog {
    private final UserInfo userInfo;
    private final int accountBalance;
    private final OnSendRedEnvelope onSendRedEnvelope;

    public RedEnvelopeDialog(Context context, UserInfo userInfo, int accountBalance, OnSendRedEnvelope onSendRedEnvelope) {
        super(context, R.style.common_dialog);
        this.userInfo = userInfo;
        this.accountBalance = accountBalance;
        this.onSendRedEnvelope = onSendRedEnvelope;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setWindowAnimations(R.style.dialogAnimStyle);//添加动画
        setContentView(R.layout.room_dialog_red_envelope);
        TextView tv_user_name = findViewById(R.id.tv_user_name);
        TextView tv_balance = findViewById(R.id.tv_balance);
        final TextView tv_input = findViewById(R.id.tv_input);
        EditText et_input = findViewById(R.id.et_input);
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        findViewById(R.id.tv_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(tv_input.getText())) {
                    Toast.makeText(getContext(), "请输入金额", Toast.LENGTH_LONG).show();
                    return;
                }
                int amount = Integer.parseInt(tv_input.getText().toString());
                if (amount == 0) {
                    Toast.makeText(getContext(), "金额不能为0", Toast.LENGTH_LONG).show();
                    return;
                }
                onSendRedEnvelope.onSend(amount);
                dismiss();
            }
        });
        et_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_input.setText(s);
            }
        });
        tv_user_name.setText("用户：" + userInfo.getNickname() + "(ID:" + userInfo.getUser_id() + ")");
        tv_balance.setText("账户余额：" + accountBalance + "钻石");
    }

    public interface OnSendRedEnvelope {
        void onSend(int amount);
    }
}
