//package com.miaomi.fenbei.voice.ui.mine.user_homepage.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.miaomi.fenbei.base.bean.GiftWallBean;
//import com.miaomi.fenbei.voice.ImgUtil;
//import com.miaomi.fenbei.voice.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//public class GiftAdapter extends RecyclerView.Adapter<GiftAdapter.ItemHolder> {
//
//    private List<GiftWallBean.ListBean> mData = new ArrayList<>();
//    private Context mContext;
//
//    public GiftAdapter(Context mContext) {
//        this.mContext = mContext;
//    }
//
//    public void setData(List<GiftWallBean.ListBean> data) {
//        this.mData.clear();
//        this.mData.addAll(data);
//        notifyDataSetChanged();
//    }
//
//    public void addData(List<GiftWallBean.ListBean> data) {
//        this.mData.addAll(data);
//        notifyDataSetChanged();
//    }
//
//
//    @NonNull
//    @Override
//    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_gift, parent, false);
//        return new ItemHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
//        GiftWallBean.ListBean bean = mData.get(position);
//        ImgUtil.INSTANCE.loadGiftImg(mContext,bean.getIcon(),holder.ivGiftIcon);
//        holder.tvName.setText(bean.getName());
//        holder.tvNumber.setText("x" + bean.getNumber());
//    }
//
//    @Override
//    public int getItemCount() {
//        return mData.size();
//    }
//
//    static class ItemHolder extends RecyclerView.ViewHolder {
//        private ImageView ivGiftIcon;
//        private TextView tvNumber;
//        private TextView tvName;
//        public ItemHolder(View itemView) {
//            super(itemView);
//            ivGiftIcon = itemView.findViewById(R.id.iv_gift_icon);
//            tvNumber =  itemView.findViewById(R.id.tv_number);
//            tvName =  itemView.findViewById(R.id.tv_name);
//        }
//    }
//}
