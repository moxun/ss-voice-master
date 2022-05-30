package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.base.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AllRoomLableView extends androidx.appcompat.widget.AppCompatTextView {
    private Context mContext;
    private ImageView bgIv;
    private TextView contentTv;
    public AllRoomLableView(@NonNull Context context) {
        super(context);
    }

    public AllRoomLableView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AllRoomLableView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLevel(int type){
        if (type == 7){
//            setText("女神");
            setBackgroundResource(R.drawable.bg_new_all_room_label_1);
        }
        if (type == 8){
//            setText("男神");
            setBackgroundResource(R.drawable.bg_new_all_room_label_2);
        }
        if (type == 9){
//            setText("电台");
            setBackgroundResource(R.drawable.bg_new_all_room_label_3);
        }
        if (type == 10){
//            setText("点唱");
            setBackgroundResource(R.drawable.bg_new_all_room_label_4);
        }
        if (type == 17){
//            setText("相亲");
            setBackgroundResource(R.drawable.bg_new_all_room_label_5);
        }
    }
}
