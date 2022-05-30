package com.miaomi.fenbei.voice.ui.mine.user_homepage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.PreviewBean;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PreviewAdapter extends RecyclerView.Adapter<PreviewAdapter.ItemHolder> {

    private List<PreviewBean> mData = new ArrayList<>();
    private Context mContext;



    public PreviewAdapter(Context context) {
        this.mContext = context;
    }

    public void setPreviewBeanData(List<PreviewBean> data ) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void setData(List<String> data ) {
        for (String item : data){
            this.mData.add(new PreviewBean(item,1));
        }
//        this.mData = data;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public PreviewAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img_preview, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviewAdapter.ItemHolder holder, final int position) {
        ImgUtil.INSTANCE.loadImg(mContext, mData.get(position).getUrl(), holder.ivPhoto);
        holder.numTv.setText((position+1) + "/"+mData.size());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null){
                    onItemClickListener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;
        private TextView numTv;
        private ItemHolder(View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.iv_content);
            numTv = itemView.findViewById(R.id.tv_num);
        }
    }
    PreviewAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(PreviewAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(int position);
        void onAdd();
    }
}
