package com.miaomi.fenbei.voice.ui.pyq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.widget.SquareRoundImageView;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.mine.user_homepage.BigImgActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FindChildImgAdapter extends RecyclerView.Adapter<FindChildImgAdapter.Viewholder> {

    private Context context;
    private List<String> mList = new ArrayList<>();

    public FindChildImgAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<String> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<String> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(context).inflate(R.layout.item_find_child_img, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        ImgUtil.INSTANCE.loadRoundImg(context,mList.get(position),holder.photoIv,8f,R.drawable.base_placeholder_photo);
        holder.itemView.setOnClickListener(v -> {
            context.startActivity(BigImgActivity.getIntent(context,position,"0", (ArrayList<String>) mList));
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class Viewholder extends RecyclerView.ViewHolder{
        SquareRoundImageView photoIv;
        public Viewholder(View itemView) {
            super(itemView);
            photoIv = itemView.findViewById(R.id.iv_phoho);
        }
    }
}