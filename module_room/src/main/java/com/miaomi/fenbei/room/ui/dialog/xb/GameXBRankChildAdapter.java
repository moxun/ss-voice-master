package com.miaomi.fenbei.room.ui.dialog.xb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.XBRankBean;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.room.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GameXBRankChildAdapter extends RecyclerView.Adapter<GameXBRankChildAdapter.ViewHolder> {

    private List<XBRankBean> mList = new ArrayList<>();
    private Context context;

    public GameXBRankChildAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<XBRankBean> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<XBRankBean> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GameXBRankChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GameXBRankChildAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.room_item_xb_rank,parent,false));
    }



    @Override
    public void onBindViewHolder(@NonNull final GameXBRankChildAdapter.ViewHolder holder, final int position) {
        ImgUtil.INSTANCE.loadFaceIcon(context,mList.get(position).getFace(),holder.faceIv);
        holder.nameTv.setText(mList.get(position).getNickname());
        holder.priceTv.setText("宝藏价值："+mList.get(position).getPrice()+"钻石");
        holder.numTv.setText(""+(position+1));
        holder.seatIv.setImageLevel(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTv;
        private ImageView faceIv;
        private ImageView seatIv;
        private TextView priceTv;
        private TextView numTv;
        public ViewHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.tv_name);
            faceIv = itemView.findViewById(R.id.iv_face);
            seatIv = itemView.findViewById(R.id.iv_seat);
            priceTv = itemView.findViewById(R.id.tv_price);
            numTv = itemView.findViewById(R.id.tv_num);
        }
    }
}
