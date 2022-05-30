package com.miaomi.fenbei.voice.ui.dress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.ShopMallItemBean;
import com.miaomi.fenbei.base.config.BaseConfig;
import com.miaomi.fenbei.base.util.DensityUtil;
import com.miaomi.fenbei.base.widget.GridSpacingItemDecoration;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShoppingMallAdapter extends RecyclerView.Adapter<ShoppingMallAdapter.ViewHolder> {

    private Context context;
    private List<ShopMallItemBean> list = new ArrayList<>();
    private int dressType;

    private OnDressItemClickListener onDressItemClickListener;

    public void setOnDressItemClickListener(OnDressItemClickListener onDressItemClickListener) {
        this.onDressItemClickListener = onDressItemClickListener;
    }

    public ShoppingMallAdapter(Context context,int dressType) {
        this.context = context;
        this.dressType = dressType;
    }

    public void setData(List<ShopMallItemBean> data){
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShoppingMallAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.user_item_shop_mall,parent,false);
        return new ShoppingMallAdapter.ViewHolder(itemView,dressType);
    }



    @Override
    public void onBindViewHolder(@NonNull ShoppingMallAdapter.ViewHolder holder, final int position) {
        holder.titleTv.setText(list.get(position).getName());
        if (dressType == BaseConfig.DRESS_TYPE_ZWK){
            DressSeatAdapter adapter = new DressSeatAdapter(context);
            adapter.setOnDressItemClickListener(onDressItemClickListener);
            holder.recyclerView.setAdapter(adapter);
            adapter.setData(list.get(position).getList());
        }
        if (dressType == BaseConfig.DRESS_TYPE_JCTX){
            DressJCTXAdapter adapter = new DressJCTXAdapter(context);
            adapter.setOnDressItemClickListener(onDressItemClickListener);
            holder.recyclerView.setAdapter(adapter);
            adapter.setData(list.get(position).getList());
        }
        if (dressType == BaseConfig.DRESS_TYPE_ZJ){
            DressZJAdapter adapter = new DressZJAdapter(context);
            adapter.setOnDressItemClickListener(onDressItemClickListener);
            holder.recyclerView.setAdapter(adapter);
            adapter.setData(list.get(position).getList());
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titleTv;
        private RecyclerView recyclerView;
        public ViewHolder(View itemView,int dressType) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.tv_title);
            recyclerView = itemView.findViewById(R.id.rv_list);
            if (dressType == BaseConfig.DRESS_TYPE_ZWK){
                recyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(),3));
                recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, DensityUtil.INSTANCE.dp2px(itemView.getContext(),10f),false));
            }
            if (dressType == BaseConfig.DRESS_TYPE_JCTX){
                recyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(),2));
                recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, DensityUtil.INSTANCE.dp2px(itemView.getContext(),10f),false));
            }
            if (dressType == BaseConfig.DRESS_TYPE_ZJ){
                recyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(),2));
                recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, DensityUtil.INSTANCE.dp2px(itemView.getContext(),10f),false));
            }
        }
    }
}