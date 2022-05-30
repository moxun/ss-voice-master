package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.miaomi.fenbei.base.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ContributeView extends androidx.appcompat.widget.AppCompatTextView {
    private Context mContext;
    public ContributeView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ContributeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ContributeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.mContext = context;
    }

    public void setContribute(int type,String contribute){
        setText(contribute);
        if (type == 7){
            setTextColor(Color.parseColor("#FF6995"));
            setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_room_hot,0,0,0);
        }
        if (type == 8){
            setTextColor(Color.parseColor("#46C4EF"));
            setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_room_hot_1,0,0,0);
        }
        if (type == 9){
            setTextColor(Color.parseColor("#F233FE"));
            setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_room_hot_2,0,0,0);
        }
        if (type == 10){
            setTextColor(Color.parseColor("#F9272E"));
            setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_room_hot_3,0,0,0);
        }
    }
}
