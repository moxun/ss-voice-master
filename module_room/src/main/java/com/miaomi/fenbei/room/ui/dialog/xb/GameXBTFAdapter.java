package com.miaomi.fenbei.room.ui.dialog.xb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.XBHideGiftBean;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.room.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GameXBTFAdapter extends RecyclerView.Adapter<GameXBTFAdapter.ViewHolder> {

    private List<XBHideGiftBean> mList = new ArrayList<>();
    private Context context;
    private OnItemSeletedListener onItemSeletedListener;

    public void setOnItemSeletedListener(OnItemSeletedListener onItemSeletedListener) {
        this.onItemSeletedListener = onItemSeletedListener;
    }

    public GameXBTFAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<XBHideGiftBean> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<XBHideGiftBean> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GameXBTFAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GameXBTFAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.room_item_xb_gift,parent,false));
    }



    @Override
    public void onBindViewHolder(@NonNull final GameXBTFAdapter.ViewHolder holder, final int position) {
        holder.priceTv.setText("价值："+mList.get(position).getPrice()+"钻石");
        ImgUtil.INSTANCE.loadGiftImg(context,mList.get(position).getIcon(),holder.iconIv);
        holder.iconIv.setSelected(mList.get(position).isSelected());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (XBHideGiftBean item : mList){
                    item.setSelected(false);
                }
                if (onItemSeletedListener !=null){
                    onItemSeletedListener.onItemSelected(mList.get(position));
                }
                mList.get(position).setSelected(true);
                notifyDataSetChanged();
            }
        });
    }

    public interface OnItemSeletedListener{
        void onItemSelected(XBHideGiftBean item);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView priceTv;
        private ImageView iconIv;
        public ViewHolder(View itemView) {
            super(itemView);
            iconIv = itemView.findViewById(R.id.iv_icon);
            priceTv = itemView.findViewById(R.id.tv_price);
        }
    }
}
