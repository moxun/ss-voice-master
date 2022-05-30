package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.miaomi.fenbei.base.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class EnterRoomView extends androidx.appcompat.widget.AppCompatTextView {
    private Context mContext;
    public EnterRoomView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public EnterRoomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EnterRoomView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.mContext = context;
    }

    public void setLevel(int type){
        if (type == 7){
            setTextColor(Color.parseColor("#FF6995"));
            setBackgroundResource(R.drawable.bg_person_enter_room);
        }
        if (type == 8){
            setTextColor(Color.parseColor("#46C4EF"));
            setBackgroundResource(R.drawable.bg_person_enter_room_1);
        }
        if (type == 9){
            setTextColor(Color.parseColor("#F233FE"));
            setBackgroundResource(R.drawable.bg_person_enter_room_2);
        }
        if (type == 10){
            setTextColor(Color.parseColor("#F9272E"));
            setBackgroundResource(R.drawable.bg_person_enter_room_3);
        }
    }
}
