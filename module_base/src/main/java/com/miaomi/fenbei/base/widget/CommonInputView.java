package com.miaomi.fenbei.base.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.miaomi.fenbei.base.R;

public class CommonInputView extends FrameLayout {
    private View view;
    private EditText contentEt;
    private ImageView clearIv;
    public CommonInputView(Context context) {
        super(context);
        init(context);
    }

    public CommonInputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CommonInputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context){
        view = LayoutInflater.from(context).inflate(R.layout.commn_view_edit,null,false);
        contentEt = view.findViewById(R.id.et_content);
        clearIv = view.findViewById(R.id.iv_clear);
        clearIv.setVisibility(GONE);
        contentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0){
                    clearIv.setVisibility(VISIBLE);
                }else{
                    clearIv.setVisibility(GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        clearIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                contentEt.setText("");
            }
        });
        contentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addView(view);
    }
    public void setFocusable(boolean isFocusable){
        contentEt.setFocusable(isFocusable);
    }
    public void setFocusableInTouchMode(boolean isFocusable){
        contentEt.setFocusableInTouchMode(isFocusable);
        contentEt.requestFocus();
    }
    public String getContent(){
        return contentEt.getText().toString();
    }

    public void setHint(String str){
        contentEt.setHint(str);
    }

    public void setContent(String str){
        contentEt.setText(str);
    }

}