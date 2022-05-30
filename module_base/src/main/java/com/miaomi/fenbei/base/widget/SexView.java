package com.miaomi.fenbei.base.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;


import com.miaomi.fenbei.base.R;
import com.miaomi.fenbei.base.config.BaseConfig;

import androidx.annotation.Nullable;

@SuppressLint("AppCompatCustomView")
public class SexView extends TextView {
    private Context context;
    public SexView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SexView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public SexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }
    private void init(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setBackground(context.getDrawable(R.drawable.base_bg_sex));

        }

    }

    public void setSeleted(boolean isMan){
        setSelected(isMan);
    }

    public void setSeleted(int sexType){
        setSelected(sexType == BaseConfig.USER_INFO_GENDER_MAN);
    }
}
