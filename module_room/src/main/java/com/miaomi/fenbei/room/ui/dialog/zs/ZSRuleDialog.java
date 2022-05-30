package com.miaomi.fenbei.room.ui.dialog.zs;

import android.view.View;
import android.widget.TextView;

import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog;
import com.miaomi.fenbei.room.R;

public class ZSRuleDialog extends BaseBottomDialog {

    private String rule="";
    private TextView ruleTv;

    public ZSRuleDialog(String rule) {
        this.rule = rule;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.room_dialog_zs_rule;
    }

    @Override
    public void bindView(View v) {
        v.findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ruleTv = v.findViewById(R.id.tv_rule);
        ruleTv.setText(rule);
    }
}
