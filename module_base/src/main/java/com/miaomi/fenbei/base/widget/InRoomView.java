package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.miaomi.fenbei.base.R;

import androidx.annotation.Nullable;

public class InRoomView extends FrameLayout {

    public InRoomView(Context context) {
        super(context);
        init(context);
    }

    public InRoomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public InRoomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.base_layout_in_room,this,false);
        ImageView imageView = view.findViewById(R.id.iv_online);
//        ImgUtil.INSTANCE.loadGif(context,R.drawable.base_icon_room_online,imageView);
        addView(view);
    }
}
