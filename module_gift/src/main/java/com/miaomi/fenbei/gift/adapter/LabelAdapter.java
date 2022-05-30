package com.miaomi.fenbei.gift.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.gift.R;
import com.miaomi.fenbei.base.bean.GiftBean;

import java.util.ArrayList;
import java.util.List;

public class LabelAdapter extends RecyclerView.Adapter<LabelAdapter.ItemHolder> {


    private Context mContext;
    private List<GiftBean.DataBean.Label> datas = new ArrayList<>();
    private int tag;

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public LabelAdapter(Context mContext, List<GiftBean.DataBean.Label> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }


    @Override
    public LabelAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LabelAdapter.ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.gift_item_label, parent, false));
    }

    @Override
    public void onBindViewHolder(final LabelAdapter.ItemHolder holder, final int position) {
        ImgUtil.INSTANCE.loadImg(mContext,datas.get(position).getIcon(),holder.iconIv);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    static class ItemHolder extends RecyclerView.ViewHolder {
        ImageView iconIv;
        ItemHolder(View itemView) {
            super(itemView);
            iconIv = itemView.findViewById(R.id.iv_label);
        }
    }

}
