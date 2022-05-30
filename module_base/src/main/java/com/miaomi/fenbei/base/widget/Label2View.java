package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.miaomi.fenbei.base.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Label2View extends androidx.appcompat.widget.AppCompatTextView {
    private Context mContext;
    public Label2View(@NonNull Context context) {
        super(context);
    }

    public Label2View(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Label2View(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLevel(int type){
        if (type == 7){
            setText("女神");
            setBackgroundResource(R.drawable.bg_room_label);
        }
        if (type == 8){
            setText("男神");
            setBackgroundResource(R.drawable.bg_room_label_1);
        }
        if (type == 9){
            setText("情感点唱");
            setBackgroundResource(R.drawable.bg_room_label_2);
        }
        if (type == 10){
            setText("陪玩交友");
            setBackgroundResource(R.drawable.bg_room_label_3);
        }
    }
}
