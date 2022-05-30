package com.miaomi.fenbei.base.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.base.R;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

public class KMToolBar extends Toolbar {
    private Context mContext;
    private ImageView backIv;
    private ImageView editIv;
    private TextView titleTv;
    private ImageView moreIv;
    private OnToolbarOparate onToolbarOparate;

    public void setOnToolbarOparate(OnToolbarOparate onToolbarOparate) {
        this.onToolbarOparate = onToolbarOparate;
    }

    public KMToolBar(Context context) {
        super(context);
        init(context);
    }

    public KMToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public KMToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.base_toolbar_km,this,true);
        backIv = view.findViewById(R.id.iv_back);
        backIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onToolbarOparate.onBack();
            }
        });
        editIv = view.findViewById(R.id.iv_edit);
        editIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onToolbarOparate.onEdit();
            }
        });
        titleTv = view.findViewById(R.id.tv_title);
        moreIv = view.findViewById(R.id.iv_more);
        moreIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onToolbarOparate.onMore(v);
            }
        });
    }

    public void changeToolbarStatus(boolean isExpanded){
        if (isExpanded){
            backIv.setSelected(false);
            editIv.setSelected(false);
            moreIv.setSelected(false);
            titleTv.setVisibility(View.GONE);
        }else{
            backIv.setSelected(true);
            editIv.setSelected(true);
            moreIv.setSelected(true);
            titleTv.setVisibility(View.VISIBLE);
        }
    }

    public void changeToolbarRightStatus(boolean isSelf){
        if (isSelf){
            moreIv.setVisibility(View.GONE);
            editIv.setVisibility(View.VISIBLE);
        }else{
            moreIv.setVisibility(View.VISIBLE);
            editIv.setVisibility(View.GONE);
        }
    }

    public void setTitle(String text){
        titleTv.setText(text);
    }

    public interface OnToolbarOparate{
        void onBack();
        void onEdit();
        void onMore(View view);
    }
}
