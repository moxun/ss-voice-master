package com.miaomi.fenbei.gift.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.gift.R;
import com.miaomi.fenbei.base.bean.GiftInfoBean;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHodler> {
    private Context context;
    private List<GiftInfoBean.ListBean> mList = new ArrayList<>();

    public UserListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<GiftInfoBean.ListBean> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }
    public void setAllSeleted(){
        for (GiftInfoBean.ListBean bean :mList){
            bean.setSelected(true);
        }
        notifyDataSetChanged();
    }
    public void setAllUnSeleted(){
        for (GiftInfoBean.ListBean bean :mList){
            bean.setSelected(false);
        }
        notifyDataSetChanged();
    }

    public boolean isAllSeleted(){
        for (GiftInfoBean.ListBean bean :mList){
            if (!bean.isSelected()){
                return false;
            }
        }
        return true;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHodler(LayoutInflater.from(context).inflate(R.layout.gift_item_user, parent, false));
    }

    private List<GiftInfoBean.ListBean> getSeletedList(){
        List<GiftInfoBean.ListBean> seletedList = new ArrayList<>();
        for (GiftInfoBean.ListBean bean :mList){
            if (bean.isSelected()){
                seletedList.add(bean);
            }
        }
        return seletedList;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, final int position) {
        if (mList.get(position).getType() == 0){
            holder.seletedTv.setText("房主");
        }else if (mList.get(position).getType() == -1){
            holder.seletedTv.setText(mList.get(position).getNickname());
        }else{
            holder.seletedTv.setText(mList.get(position).getType()+"麦");
        }
        holder.seletedTv.setSelected(mList.get(position).isSelected());
        holder.avterIv.setSelected(mList.get(position).isSelected());
        ImgUtil.INSTANCE.loadCircleImg(context,mList.get(position).getFace(),holder.avterIv,R.drawable.common_avter_placeholder);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mList.get(position).isSelected()){
                    mList.get(position).setSelected(false);
                }else{
                    mList.get(position).setSelected(true);
                }
                notifyDataSetChanged();
                if (onAllSeletedListener != null){
                    onAllSeletedListener.onSeletedChange(getSeletedList());
                }
            }
        });
    }
    private OnSeletedChangeListener onAllSeletedListener;

    public void setOnSeletedChangeListener(OnSeletedChangeListener onAllSeletedListener) {
        this.onAllSeletedListener = onAllSeletedListener;
    }

    public interface OnSeletedChangeListener {
        void onSeletedChange(List<GiftInfoBean.ListBean> seletedList);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHodler extends RecyclerView.ViewHolder{
        ImageView avterIv;
        TextView seletedTv;
        ViewHodler(View itemView) {
            super(itemView);
            avterIv = itemView.findViewById(R.id.iv_avter);
            seletedTv = itemView.findViewById(R.id.tv_selected);
        }
    }
}
