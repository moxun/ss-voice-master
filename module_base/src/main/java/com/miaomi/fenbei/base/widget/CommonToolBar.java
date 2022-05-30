package com.miaomi.fenbei.base.widget;

import android.content.Context;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miaomi.fenbei.base.R;

public class CommonToolBar extends Toolbar {
    ImageButton mBackImageView;
    TextView mTitleTextView;
    LinearLayout mTitleBar;

    private ToolGameDetailBarListener mListener;

    public interface ToolGameDetailBarListener {
        void onBackBtnClick();
    }

    public CommonToolBar(Context context) {
        this(context, null);
    }

    public CommonToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.common_layout_toolbar, null);

        mBackImageView = view.findViewById(R.id.back_image_view);
        mTitleBar = view.findViewById(R.id.title_bar);
        mTitleTextView = view.findViewById(R.id.title_text_view);
        mBackImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onBackBtnClick();
                }
            }
        });

//        mIbShare.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mListener != null){
//                    mListener.onShareBtnClick();
//                }
//            }
//        });

        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER_HORIZONTAL);
        addView(view, lp);
    }


    public void setExpanded(){
        mTitleTextView.setText("");
        mBackImageView.setVisibility(INVISIBLE);
    }

    public void setCollapsed(String gameName){
        if (!TextUtils.isEmpty(gameName)){
            mTitleTextView.setText(gameName);
        }
        mBackImageView.setVisibility(VISIBLE);
        mBackImageView.setImageResource(R.drawable.common_back_arrow_black);
    }

    public void addBtnClickListener(ToolGameDetailBarListener listener){
        mListener = listener;
    }

    public void setShareShow(boolean isShow){
//        if (isShow) {
//            mIbShare.setVisibility(VISIBLE);
//        }else {
//            mIbShare.setVisibility(GONE);
//        }
    }

//    public void startAnimator() {
//        ObjectAnimator rotationAnimator = new ObjectAnimator();
//        rotationAnimator.setDuration(350);
//        rotationAnimator.setFloatValues(0, -15, 15, 0);
//        rotationAnimator.setPropertyName("rotation");
//        rotationAnimator.setRepeatCount(16);
//        rotationAnimator.setTarget(mIbShare);
//        rotationAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//        rotationAnimator.start();
//    }
}
