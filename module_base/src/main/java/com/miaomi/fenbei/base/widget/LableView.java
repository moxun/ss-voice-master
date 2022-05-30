package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.miaomi.fenbei.base.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LableView extends androidx.appcompat.widget.AppCompatTextView {
    private Context mContext;
    public LableView(@NonNull Context context) {
        super(context);
    }

    public LableView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LableView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLevel(int type){
        if (type == 7){
            setText("女神");
            setBackgroundResource(R.drawable.bg_hot_room_label_1);
        }
        if (type == 8){
            setText("男神");
            setBackgroundResource(R.drawable.bg_hot_room_label_2);
        }
        if (type == 9){
            setText("情感点唱");
            setBackgroundResource(R.drawable.bg_hot_room_label_3);
        }
        if (type == 10){
            setText("陪玩交友");
            setBackgroundResource(R.drawable.bg_hot_room_label_4);
        }
    }
}
