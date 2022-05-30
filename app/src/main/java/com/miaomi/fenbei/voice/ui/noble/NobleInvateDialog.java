package com.miaomi.fenbei.voice.ui.noble;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.miaomi.fenbei.voice.R;

import androidx.annotation.NonNull;

public class NobleInvateDialog extends Dialog{

    private TextView leftTv;
    private TextView rightTv;
    private EditText contentEt;
    private TextView titleTv;
    OnEditListener onEditListener;

    public NobleInvateDialog(@NonNull Context context) {
        super(context, R.style.common_dialog);
    }

    public void setOnEditListener(OnEditListener onEditListener) {
        this.onEditListener = onEditListener;
    }

    public void setTitle(String str){
        titleTv.setText(str);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_noble_invate);
        leftTv = findViewById(R.id.tv_left);
        rightTv = findViewById(R.id.tv_right);
        contentEt = findViewById(R.id.et_content);
        titleTv = findViewById(R.id.tv_title);
        contentEt.setVisibility(View.GONE);
        contentEt.setVisibility(View.GONE);
        rightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEditListener!=null){
                    String code = contentEt.getText().toString();
                    onEditListener.onSuccess(code);
                    dismiss();
                }
            }
        });
        leftTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public interface OnEditListener{
        void onSuccess(String code);
    }
}
