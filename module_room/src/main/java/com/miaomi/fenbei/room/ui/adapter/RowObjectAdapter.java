package com.miaomi.fenbei.room.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miaomi.fenbei.base.bean.RowBean;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.room.R;

import java.util.ArrayList;
import java.util.List;

public class RowObjectAdapter extends RecyclerView.Adapter<RowObjectAdapter.ItemHolder>{

    private List<RowBean.ZhuListBean> mList = new ArrayList<>();
    private Context mContext;
    private CheckItemListener mCheckListener;
    private int type;
    private int select=0;
    public RowObjectAdapter(Context mContext, CheckItemListener mCheckListener) {
        this.mCheckListener=mCheckListener;
        this.mContext = mContext;
    }

    public void setData(List<RowBean.ZhuListBean> list,int type){
        mList.clear();
        mList.addAll(list);
        this.type=type;
        notifyDataSetChanged();
    }
    public void onSelect(int select){
        this.select=select;

    }
    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_rowwheat_tab,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, final int position) {
        if (!TextUtils.isEmpty(mList.get(position).getFace())){
            ImgUtil.INSTANCE.loadFaceIcon(mContext,mList.get(position).getFace(), holder.tabiconIv);
        }else{
            holder.tabiconIv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_pop_shangmai_seat_object));

        }

        holder.tabtitleTv.setText(mList.get(position).getNickname());

        if(mList.get(position).isSelected()){
          holder.bgIv.setVisibility(View.VISIBLE);
        }else{
            holder.bgIv.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mList.get(position).getUser_id()!=0&&type>0&&select==1){
                    for (RowBean.ZhuListBean bean : mList){
                        bean.setSelected(false);
                    }
                    mList.get(position).setSelected(true);
                    if (mCheckListener != null){
                        mCheckListener.itemChecked(""+mList.get(position).getUser_id());
                    }
                    notifyDataSetChanged();
                }

            }
        });

    }
    public interface CheckItemListener {
        void itemChecked(String id);
    }
    @Override
    public int getItemCount() {
        return mList.size();

    }

    static class ItemHolder extends RecyclerView.ViewHolder{
        private ImageView tabiconIv;
        private ImageView bgIv;
        private TextView tabtitleTv;
        ItemHolder(View itemView) {
            super(itemView);
            tabiconIv = itemView.findViewById(R.id.iv_objetc_icon);
            tabtitleTv=itemView.findViewById(R.id.tv_object_title);
            bgIv=itemView.findViewById(R.id.iv_bg);
        }
    }
}
