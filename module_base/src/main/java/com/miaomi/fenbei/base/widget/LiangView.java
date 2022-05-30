package com.miaomi.fenbei.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import com.miaomi.fenbei.base.R;
import com.miaomi.fenbei.base.util.CopyUtil;
import com.miaomi.fenbei.base.util.DensityUtil;

import androidx.annotation.Nullable;

public class LiangView extends DCBTextView {
    private Context mContext;
    public LiangView(Context context) {
        super(context);
        init(context);
    }

    public LiangView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LiangView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context){
        mContext = context;
//        setBackgroundResource(R.drawable.base_bg_liang);
        setGravity(Gravity.CENTER);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CopyUtil.copy(getText().toString()+ "", context);
            }
        });
    }

    public void show(String content,boolean isLiang){
        if (isLiang){
            setText(content);
            setCompoundDrawablesWithIntrinsicBounds(R.drawable.common_user_icon_liang,0,0,0);
            setCompoundDrawablePadding(DensityUtil.INSTANCE.dp2px(mContext,4f));
        }else{
            setText(content);
            setCompoundDrawablesWithIntrinsicBounds(R.drawable.room_icon_small_id,0,0,0);
            setCompoundDrawablePadding(DensityUtil.INSTANCE.dp2px(mContext,4f));

        }
        setSelected(isLiang);
    }
}
