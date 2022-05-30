package com.miaomi.fenbei.base.widget.search;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.miaomi.fenbei.base.R;


public class SearchView extends FrameLayout {
    private View view;
    private EditText searchEt;
    private ImageView searchIv;
//    private ImageView clearIv;
    public SearchView(Context context) {
        super(context);
        init(context);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context){
        view = LayoutInflater.from(context).inflate(R.layout.common_view_search,null,false);
        searchEt = view.findViewById(R.id.et_search);
        searchIv=view.findViewById(R.id.iv_search);
//        clearIv = view.findViewById(R.id.iv_clear);
//        clearIv.setVisibility(GONE);
        searchIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEt.setText(null);
//                if (onSearchListener != null){
//                    searchEt.setText(null);
//                    onSearchListener.onSearch(searchEt.getText().toString());
//                }
            }
        });
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.length()>0){
//                    clearIv.setVisibility(VISIBLE);
//                }else{
//                    clearIv.setVisibility(GONE);
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        clearIv.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                searchEt.setText("");
//            }
//        });
        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (onSearchListener != null){
                        onSearchListener.onSearch(searchEt.getText().toString());
                    }
                }
                return false;
            }
        });
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())){
                    if (onClearListener != null){
                        onClearListener.onClear();
                    }
                }
            }
        });

        addView(view);
    }
    public void setFocusable(boolean isFocusable){
        searchEt.setFocusable(isFocusable);
    }
    public void setFocusableInTouchMode(boolean isFocusable){
        searchEt.setFocusableInTouchMode(isFocusable);
        searchEt.requestFocus();
    }
    public String getKeyword(){
        return searchEt.getText().toString();
    }

    public void setHint(String str){
        searchEt.setHint(str);
    }
    public void seKeyword(String str){
        searchEt.setText(str);
        searchEt.setSelection(str.length());//光标在最后
    }
    OnSearchListener onSearchListener;

    public void setOnSearchListener(OnSearchListener onSearchListener) {
        this.onSearchListener = onSearchListener;
    }

    public interface OnSearchListener{
        void onSearch(String keyWord);
    }

    OnClearListener onClearListener;

    public void setOnClearListener(OnClearListener onClearListener) {
        this.onClearListener = onClearListener;
    }

    public interface OnClearListener{
        void onClear();
    }
}
