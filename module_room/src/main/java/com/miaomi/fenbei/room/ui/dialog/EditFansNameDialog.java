package com.miaomi.fenbei.room.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.miaomi.fenbei.room.R;
import com.miaomi.fenbei.room.ui.callback.OnEditFansNameListener;

public class EditFansNameDialog extends Dialog {
    private OnEditFansNameListener onEditFansNameListener;
    private EditText contentEt;

    public void setOnEditFansNameListener(OnEditFansNameListener onEditFansNameListener) {
        this.onEditFansNameListener = onEditFansNameListener;
    }

    public EditFansNameDialog(@NonNull Context context) {
        super(context, R.style.common_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_fans_name);
        contentEt = findViewById(R.id.et_content);
        findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEditFansNameListener != null){
                    onEditFansNameListener.onChange(contentEt.getText().toString());
                }
            }
        });
    }
}
