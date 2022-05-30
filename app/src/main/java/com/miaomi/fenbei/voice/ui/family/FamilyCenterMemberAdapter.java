package com.miaomi.fenbei.voice.ui.family;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.bean.FamilyCenterInfoBean;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FamilyCenterMemberAdapter extends RecyclerView.Adapter<FamilyCenterMemberAdapter.ItemHodler> {
    private Context context;
    private List<FamilyCenterInfoBean.FamilyInfoBean> list = new ArrayList<>();

    public FamilyCenterMemberAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<FamilyCenterInfoBean.FamilyInfoBean> data){
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FamilyCenterMemberAdapter.ItemHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FamilyCenterMemberAdapter.ItemHodler(LayoutInflater.from(context).inflate(R.layout.item_family_member_in_center,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyCenterMemberAdapter.ItemHodler holder, final int position) {
        if (list.get(position).getType() == 2){
            holder.statusTv.setVisibility(View.VISIBLE);
            holder.statusTv.setText("族长");
            holder.statusTv.setBackgroundResource(R.drawable.bg_family_member_leader);
        }else if (list.get(position).getType() == 1){
            holder.statusTv.setVisibility(View.VISIBLE);
            holder.statusTv.setText("管理");
            holder.statusTv.setBackgroundResource(R.drawable.bg_family_member_maneger);
        }else{
            holder.statusTv.setVisibility(View.GONE);
        }
        holder.nameTv.setText(list.get(position).getNickname());
        ImgUtil.INSTANCE.loadCircleImg(context,list.get(position).getFace(),holder.iconIv,R.drawable.common_avter_placeholder);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/app/userhomepage")
                        .withString("user_id",String.valueOf(list.get(position).getUser_id()))
                        .navigation();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list.size() < 4){
            return list.size();
        }else{
            return 4;
        }
    }

    class ItemHodler extends RecyclerView.ViewHolder{
        ImageView iconIv;
        TextView nameTv;
        TextView statusTv;
        public ItemHodler(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.tv_name);
            iconIv = itemView.findViewById(R.id.iv_icon);
            statusTv = itemView.findViewById(R.id.tv_status);
        }
    }
}
