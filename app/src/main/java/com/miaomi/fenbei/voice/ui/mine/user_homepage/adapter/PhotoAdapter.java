package com.miaomi.fenbei.voice.ui.mine.user_homepage.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.widget.SquareImageView;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ItemHolder> {

    private List<String> mData = new ArrayList<>();
    private Context mContext;


    public PhotoAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<String> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_photo, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, final int position) {
        if (TextUtils.isEmpty(mData.get(position))) {
            ImgUtil.INSTANCE.loadImg(mContext, R.drawable.icon_user_info_updata_photo, holder.ivPhoto, R.drawable.common_default);
        } else {
            ImgUtil.INSTANCE.loadAnimRoundImg(mContext, mData.get(position), holder.ivPhoto, 6, R.drawable.common_default);
        }
        holder.ivPhoto.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                if (TextUtils.isEmpty(mData.get(position))) {
                    onItemClickListener.onAdd();
                } else {
                    onItemClickListener.onClick(position);
                }
            }
        });
        holder.ivPhoto.setOnLongClickListener(v -> {
            if (onItemClickListener != null) {
                if (TextUtils.isEmpty(mData.get(position))) {
                    onItemClickListener.onAdd();
                } else {
                    onItemClickListener.onLongClick(mData.get(position));
                }
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        if (mData.size() < 4) {
            return mData.size();
        } else {
            return 4;
        }
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        private SquareImageView ivPhoto;

        private ItemHolder(View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.iv_photo);
        }
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(int position);

        void onAdd();

        void onLongClick(String url);
    }
}
