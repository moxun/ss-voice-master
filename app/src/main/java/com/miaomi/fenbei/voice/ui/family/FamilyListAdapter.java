package com.miaomi.fenbei.voice.ui.family;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.miaomi.fenbei.base.bean.FamilyBean;
import com.miaomi.fenbei.base.dialog.ApplyDialog;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.DCBTextView;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FamilyListAdapter extends RecyclerView.Adapter<FamilyListAdapter.ItemHodler> {
    private Context context;
    private List<FamilyBean.ListBean> list = new ArrayList<>();

    public FamilyListAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<FamilyBean.ListBean> data){
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }
    public void addData(List<FamilyBean.ListBean> data){
        list.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FamilyListAdapter.ItemHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHodler(LayoutInflater.from(context).inflate(R.layout.item_family, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyListAdapter.ItemHodler holder, final int position) {


        int index= position+1;

        if(index==1){
            holder.familyIndexTv.setBackground(context.getResources().getDrawable(R.drawable.icon_family_no_1));
            holder.familyIndexTv.setText("");
      }else if(index==2){
            holder.familyIndexTv.setBackground(context.getResources().getDrawable(R.drawable.icon_family_no_2));
            holder.familyIndexTv.setText("");
      }else if(index==3){
            holder.familyIndexTv.setBackground(context.getResources().getDrawable(R.drawable.icon_family_no_3));
            holder.familyIndexTv.setText("");
      }else{
            holder.familyIndexTv.setBackground(context.getResources().getDrawable(R.color.text_color_b200));
            holder.familyIndexTv.setText(""+index);
        }
        ImgUtil.INSTANCE.loadCircleImg(context,list.get(position).getIcon(),holder.iconIv,R.drawable.common_avter_placeholder);
        holder.totalTv.setText(""+list.get(position).getMoney_total());
        holder.fmNameTv.setText(list.get(position).getFamily_name());
        holder.nameTv.setText("族长："+list.get(position).getNickname());
        holder.idTv.setText("家族ID："+list.get(position).getFamily_id());
        if(list.get(position).getFamily_type()==0){
            holder.applyTv.setVisibility(View.GONE);
            holder.applyTv.setClickable(true);
        }else if(list.get(position).getFamily_type()==5){
            holder.applyTv.setTextColor(Color.parseColor("#FFFFFF"));
            holder.applyTv.setBackground(context.getResources().getDrawable(R.drawable.bg_out_family));
            holder.applyTv.setText("审核中");
            holder.applyTv.setClickable(false);
        }else{
            holder.applyTv.setVisibility(View.GONE);
        }
        holder.applyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onFamilyListClickListener != null){
                    onFamilyListClickListener.onApply(String.valueOf(list.get(position).getFamily_id()));
                }

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.get(position).getFamily_type()==3||list.get(position).getFamily_type()==2){
                    FamilyAdminCenterActivity.start(context,String.valueOf(list.get(position).getFamily_id()));
                }else{
                    FamilyCenterActivity.start(context,String.valueOf(list.get(position).getFamily_id()));
                }
//                FamilyAdminCenterActivity.start(context,String.valueOf(list.get(position).getFamily_id()));

            }
        });
    }
    OnFamilyListClickListener onFamilyListClickListener;
    public void setOnApplyClickListener(OnFamilyListClickListener onFamilyListClickListener) {
        this.onFamilyListClickListener = onFamilyListClickListener;
    }

    public interface OnFamilyListClickListener{
        void onApply(String uid);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ItemHodler extends RecyclerView.ViewHolder{
        ImageView iconIv;
        TextView fmNameTv;
        TextView nameTv;
        DCBTextView totalTv;
        TextView idTv;
        TextView applyTv;
        TextView  familyIndexTv;
        public ItemHodler(View itemView) {
            super(itemView);
            iconIv = itemView.findViewById(R.id.user_icon);
            fmNameTv = itemView.findViewById(R.id.tv_family_name);
            nameTv = itemView.findViewById(R.id.tv_name);
            totalTv = itemView.findViewById(R.id.tv_total);
            idTv = itemView.findViewById(R.id.tv_family_id);
            applyTv=itemView.findViewById(R.id.tv_apply);
            familyIndexTv= itemView.findViewById(R.id.tv_family_index);
        }
    }
}
