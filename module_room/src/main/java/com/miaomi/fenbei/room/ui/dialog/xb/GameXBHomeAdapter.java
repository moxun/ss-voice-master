package com.miaomi.fenbei.room.ui.dialog.xb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.XBItemBean;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.TimeUtil;
import com.miaomi.fenbei.room.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GameXBHomeAdapter extends RecyclerView.Adapter<GameXBHomeAdapter.ViewHolder> {

    private List<XBItemBean> mList = new ArrayList<>();
    private Context context;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public GameXBHomeAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<XBItemBean> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GameXBHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GameXBHomeAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.room_item_xb_index,parent,false));
    }



    @Override
    public void onBindViewHolder(@NonNull final GameXBHomeAdapter.ViewHolder holder, final int position) {
        ImgUtil.INSTANCE.loadFaceIcon(context,mList.get(position).getFace(),holder.iconIv);
        holder.nameTv.setText(mList.get(position).getPrice()+"钻石");
        holder.digPriceTv.setText("单次消耗"+mList.get(position).getDig_price()+"钻石");
        if (mList.get(position).getExcept_time()-System.currentTimeMillis()/1000 > 0){
            holder.timeTv.setText(TimeUtil.getTimeSFM(mList.get(position).getExcept_time()-System.currentTimeMillis()/1000));
        }else{
            holder.timeTv.setText("已过期");

        }
//        if (mList.get(position).)
        if ("1".equals(mList.get(position).getType())){
            holder.allServiceTv.setVisibility(View.VISIBLE);
        }else{
            holder.allServiceTv.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(mList.get(position));
                }
            }
        });
    }

    public interface OnItemClickListener{
        void onItemClick(XBItemBean item);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iconIv;
        private TextView nameTv;
        private TextView digPriceTv;
        private TextView timeTv;
        private TextView allServiceTv;
        public ViewHolder(View itemView) {
            super(itemView);
            iconIv = itemView.findViewById(R.id.iv_icon);
            nameTv = itemView.findViewById(R.id.tv_name);
            digPriceTv = itemView.findViewById(R.id.tv_dig_price);
            timeTv = itemView.findViewById(R.id.tv_time);
            allServiceTv = itemView.findViewById(R.id.tv_all_service);
        }
    }
}

