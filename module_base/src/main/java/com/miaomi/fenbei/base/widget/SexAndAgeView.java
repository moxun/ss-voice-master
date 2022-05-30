package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;


import com.miaomi.fenbei.base.R;
import com.miaomi.fenbei.base.config.BaseConfig;

import androidx.annotation.Nullable;

public class SexAndAgeView extends TextView {
    private Context context;
    public SexAndAgeView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SexAndAgeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public SexAndAgeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }
    private void init(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setBackground(context.getDrawable(R.drawable.base_bg_sex_and_age));
        }
        setGravity(Gravity.CENTER);
    }

    public void setContent(boolean isMan,int age){
        setSelected(isMan);
        if(age==0){
            setText("");
        }else{
            setText(String.valueOf(age));
        }


    }

    public void setSeleted(int sexType){
        setSelected(sexType == BaseConfig.USER_INFO_GENDER_MAN);
    }
}
