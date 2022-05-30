package com.miaomi.fenbei.voice.ui.pyq;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.voice.R;
import com.ypx.imagepicker.bean.ImageItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PublishPyqImgAdapter extends RecyclerView.Adapter<PublishPyqImgAdapter.Viewholder> {

    private Context context;
    private OnOprateListener onSelectedListener;

    private List<ImageItem> mList = new ArrayList<>();

    public PublishPyqImgAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ImageItem> list) {
        mList.clear();
        mList.addAll(list);
        if (list.size() < 9){
            mList.add(new ImageItem());
        }
        notifyDataSetChanged();
    }

    public void addData(List<ImageItem> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(context).inflate(R.layout.item_publish_img, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        if (TextUtils.isEmpty(mList.get(position).getPath())){
            ImgUtil.INSTANCE.loadRoundImg(context,R.drawable.icon_publish_add_pic,holder.photoIv,8f,-1);
            holder.closeIv.setVisibility(View.GONE);
        }else{
            holder.closeIv.setVisibility(View.VISIBLE);
            ImgUtil.INSTANCE.loadRoundImg(context,mList.get(position).getPath(),holder.photoIv,8f,-1);
        }
        holder.itemView.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mList.get(position).getPath())){
                if (onSelectedListener != null){
                    onSelectedListener.onAdd();
                }
            }
        });

        holder.closeIv.setOnClickListener(v -> {
            if (onSelectedListener != null){
                onSelectedListener.onDelete(position);
            }
        });
    }


    public void setOnOprateListener(OnOprateListener onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
    }

    public interface OnOprateListener{
        void onAdd();
        void onDelete(int position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class Viewholder extends RecyclerView.ViewHolder{
        ImageView photoIv;
        ImageView closeIv;
        public Viewholder(View itemView) {
            super(itemView);
            photoIv = itemView.findViewById(R.id.iv_phoho);
            closeIv = itemView.findViewById(R.id.iv_close);
        }
    }
}
