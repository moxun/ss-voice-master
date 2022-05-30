package com.miaomi.fenbei.room.ui.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.miaomi.fenbei.base.bean.DtFansBean;
import com.miaomi.fenbei.base.util.DensityUtil;
import com.miaomi.fenbei.room.R;
import com.miaomi.fenbei.room.ui.callback.OnRadioFansSeletedListener;

import java.util.ArrayList;
import java.util.List;

public class GiftNumSelectPopWindow extends PopupWindow implements OnRadioFansSeletedListener {
    private LayoutInflater inflater;
    private RecyclerView mListView;
    private List<DtFansBean.ConfigBean> list = new ArrayList<>();
    private SpinerAdapter mSpinerAdapter;
    private OnRadioFansSeletedListener onGifiNumSeletedListener;
    //    private boolean canSelected = false;
    private View parentView;
    private int popupHeight;
    private int popupWidth;


    public GiftNumSelectPopWindow(Context context, List<DtFansBean.ConfigBean> list, OnRadioFansSeletedListener onGifiNumSeletedListener) {
        super(context);
        inflater = LayoutInflater.from(context);
        this.list = list;
        this.onGifiNumSeletedListener = onGifiNumSeletedListener;
//        this.canSelected = false;
        init(context);
    }

//    public GiftNumSelectPopWindow(Context context, List<DtFansBean.ConfigBean> list, OnSelectedListener onSpinerItemClickListener) {
//        super(context);
//        inflater = LayoutInflater.from(context);
//        this.list = list;
//        this.onSelectedListener = onSpinerItemClickListener;
//        this.canSelected = true;
//        init(context);
//    }

    private void init(Context context) {
        parentView = inflater.inflate(R.layout.gift_spiner_window_layout, null);

        setContentView(parentView);
//        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(DensityUtil.INSTANCE.dp2px(context, 114f));

        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);
        mSpinerAdapter = new SpinerAdapter(context, list);
        mSpinerAdapter.setOnSpinerItemClickListener(this);
//        mSpinerAdapter.setOnSelectedListener(new SpinerAdapter.OnSelectedListener() {
//            @Override
//            public void onSelected(List<GiftInfoBean.ListBean> datas) {
//                if (onSelectedListener != null){
//                    onSelectedListener.onSelected(datas);
//                }
//            }
//        });
        mListView = (RecyclerView) parentView.findViewById(R.id.spiner_rv);
        mListView.setLayoutManager(new LinearLayoutManager(context));
        mListView.setAdapter(mSpinerAdapter);
        parentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupHeight = parentView.getMeasuredHeight();
        popupWidth = parentView.getMeasuredWidth();
    }

//    OnSelectedListener onSelectedLi/stener;

//    public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
//        this.onSelectedListener = onSelectedListener;
//    }
//
//    public interface OnSelectedListener {
//        void onSelected(List<GiftInfoBean.ListBean> datas);
//    }

    public void show(View v) {
        //获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        v.getLocationInWindow(location);
        //在控件上方显示    向上移动y轴是负数
//        showAtLocation(textView, Gravity.NO_GRAVITY, location[0]  - getWidth() / 2, location[1]-100);
//        showAsDropDown(showTv);
        showAtLocation(v, Gravity.NO_GRAVITY, (location[0] + v.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight);
    }


    @Override
    public void onItemClick(DtFansBean.ConfigBean item) {
        if (onGifiNumSeletedListener != null) {
            onGifiNumSeletedListener.onItemClick(item);
        }
        dismiss();
    }
}
