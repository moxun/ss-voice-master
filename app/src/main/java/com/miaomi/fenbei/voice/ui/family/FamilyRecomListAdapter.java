package com.miaomi.fenbei.voice.ui.family;

import android.content.Context;
import android.graphics.Color;
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
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class FamilyRecomListAdapter extends RecyclerView.Adapter<FamilyRecomListAdapter.ItemHodler> {
    private Context context;
    private List<FamilyBean.ListBean> list = new ArrayList<>();

    public FamilyRecomListAdapter(Context context) {
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
    public FamilyRecomListAdapter.ItemHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHodler(LayoutInflater.from(context).inflate(R.layout.item_recommed_family, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyRecomListAdapter.ItemHodler holder, final int position) {
        ImgUtil.INSTANCE.loadCircleImg(context,list.get(position).getIcon(),holder.iconIv,R.drawable.common_avter_placeholder);
        holder.totalTv.setText(""+list.get(position).getMoney_total());
        holder.fmNameTv.setText(list.get(position).getFamily_name());
        holder.nameTv.setText("族长："+list.get(position).getNickname());
        holder.idTv.setText("家族ID："+list.get(position).getFamily_id());
        if(list.get(position).getFamily_type()==0){
            holder.applyTv.setVisibility(View.VISIBLE);
            holder.applyTv.setClickable(true);
        }else if(list.get(position).getFamily_type()==5){
            holder.applyTv.setTextColor(Color.parseColor("#FFFFFF"));
            holder.applyTv.setBackground(context.getResources().getDrawable(R.drawable.bg_out_family));
            holder.applyTv.setText("审核中");
            holder.applyTv.setClickable(false);
        }else{
            holder.applyTv.setVisibility(View.GONE);
        }
        holder.applyTv.setVisibility(View.GONE);
        holder.applyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ApplyDialog outDialog = new ApplyDialog(context);
                outDialog.setContent("是否确认加入家族？");
                outDialog.setLeftBt("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        outDialog.dismiss();
                    }
                });
                outDialog.setRightBt("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        outDialog.dismiss();
                        joinFamily(""+list.get(position).getFamily_id());
                    }
                });
                outDialog.show();
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
    private void joinFamily(String familyId){
        NetService.Companion.getInstance(context).joinFamily(familyId, new Callback<Object>() {
            @Override
            public void onSuccess(int nextPage, Object bean, int code) {
                ToastUtil.INSTANCE.i(context,"申请已提交，请等待审核");
                notifyDataSetChanged();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.i(context,msg);
            }

            @Override
            public boolean isAlive() {
                return true;
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ItemHodler extends RecyclerView.ViewHolder{
        ImageView iconIv;
        TextView fmNameTv;
        TextView nameTv;
        TextView totalTv;
        TextView idTv;
        TextView applyTv;
        public ItemHodler(View itemView) {
            super(itemView);
            iconIv = itemView.findViewById(R.id.user_icon);
            fmNameTv = itemView.findViewById(R.id.tv_family_name);
            nameTv = itemView.findViewById(R.id.tv_name);
            totalTv = itemView.findViewById(R.id.tv_total);
           idTv = itemView.findViewById(R.id.tv_family_id);
            applyTv=itemView.findViewById(R.id.tv_apply);
        }
    }
}
