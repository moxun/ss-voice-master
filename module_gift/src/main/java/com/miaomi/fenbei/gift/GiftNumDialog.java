package com.miaomi.fenbei.gift;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 礼物数量
 */
public class GiftNumDialog extends Dialog {
    private final OnGiftNum onGiftNum;

    public GiftNumDialog(Context context,  OnGiftNum onGiftNum) {
        super(context, R.style.common_dialog);
        this.onGiftNum = onGiftNum;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setWindowAnimations(R.style.dialogAnimStyle);//添加动画
        setContentView(R.layout.room_dialog_gift_num);
        final EditText et_input = findViewById(R.id.et_input);
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        findViewById(R.id.tv_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_input.getText().toString())) {
                    Toast.makeText(getContext(), "请输入礼物数量", Toast.LENGTH_LONG).show();
                    return;
                }
                int num = Integer.parseInt(et_input.getText().toString());
                if (num == 0) {
                    Toast.makeText(getContext(), "礼物数量不能为0", Toast.LENGTH_LONG).show();
                    return;
                }
                onGiftNum.onSend(num);
                dismiss();
            }
        });
    }

    public interface OnGiftNum {
        void onSend(int sendGiftNum);
    }
}
