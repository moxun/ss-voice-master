package com.miaomi.fenbei.voice.ui.family;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.miaomi.fenbei.base.bean.ApplyListBean;
import com.miaomi.fenbei.base.config.BaseConfig;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.widget.LevelView;
import com.miaomi.fenbei.base.widget.SexAndAgeView;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;


public class ApplyListAdapter extends RecyclerView.Adapter<ApplyListAdapter.ItemHodler> {
    private Context context;
    private List<ApplyListBean> list = new ArrayList<>();

    public ApplyListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ApplyListBean> data){
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHodler(LayoutInflater.from(context).inflate(R.layout.item_apply_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ApplyListAdapter.ItemHodler holder, final int position) {
        holder.registerTv.setText("注册时间："+list.get(position).getCreate_time());
        holder.userNickTv.setText(list.get(position).getNickname());
//        holder.incomeTv.setText("收益："+list.get(position).getEarning_total()+"钻石");
//        holder.incomeTv.setVisibility(View.GONE);
        if(list.get(position).getType()==0){
            holder.tv_content.setText("申请加入家族");
        }else{
            holder.tv_content.setText("申请退出家族");
        }
        ImgUtil.INSTANCE.loadFaceIcon(context,list.get(position).getFace(),holder.userIconIv);
        holder.agreeTv.setOnClickListener(v -> {
            if (onApplyClickListener != null){

                if(list.get(position).getType()==0){
                    onApplyClickListener.onAgree(String.valueOf(list.get(position).getUser_id()));
                }else{
                    onApplyClickListener.onOutFamily(String.valueOf(list.get(position).getUser_id()));

                }

            }
        });
        holder.unAgreeTv.setOnClickListener(v -> {
            if (onApplyClickListener != null){
                int type=3;
                onApplyClickListener.onUnAgree(String.valueOf(list.get(position).getUser_id()),type);
            }
        });
        //        1：男，2：女
//        holder.ageTv.setText(String.valueOf(list.get(position).getAge()));
//        if (list.get(position).getGender() == 1) {
//            Drawable drawable = context.getResources().getDrawable(R.drawable.common_user_symbol_male);
//            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//            holder.ageTv.setBackgroundResource(R.drawable.user_male_bg);
//            holder.ageTv.setCompoundDrawables(drawable, null, null, null);
//        } else {
//            Drawable drawable = context.getResources().getDrawable(R.drawable.common_user_symbol_female);
//            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//            holder.ageTv.setBackgroundResource(R.drawable.user_female_bg);
//            holder.ageTv.setCompoundDrawables(drawable, null, null, null);
//        }
        holder.ageTv.setContent(list.get(position).getGender()== BaseConfig.USER_INFO_GENDER_MAN,list.get(position).getAge());
        holder.meiliIv.setCharmLevel(list.get(position).getCharm_level().getGrade());
        holder.gongxianIv.setWealthLevel(list.get(position).getWealth_level().getGrade());
//        holder.meiliIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LevelActivity.start(context);
//            }
//        });
//        holder.gongxianIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LevelActivity.start(context);
//            }
//        });
    }
    OnApplyClickListener onApplyClickListener;

    public void setOnApplyClickListener(OnApplyClickListener onApplyClickListener) {
        this.onApplyClickListener = onApplyClickListener;
    }

    public interface OnApplyClickListener{
        void onAgree(String uid);
        void onUnAgree(String uid,int type);
        void onOutFamily(String uid);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ItemHodler extends RecyclerView.ViewHolder{
        TextView agreeTv;
        TextView unAgreeTv;
        TextView registerTv;
        TextView userNickTv;
        ImageView userIconIv;
        TextView tv_content;
        LevelView meiliIv;
        LevelView gongxianIv;
        SexAndAgeView ageTv;
        public ItemHodler(View itemView) {
            super(itemView);
            agreeTv = itemView.findViewById(R.id.tv_agree);
            unAgreeTv = itemView.findViewById(R.id.tv_unagree);
            registerTv = itemView.findViewById(R.id.user_register_time);
            userNickTv = itemView.findViewById(R.id.user_nick);
            userIconIv = itemView.findViewById(R.id.user_icon);
            tv_content = itemView.findViewById(R.id.tv_content);
            meiliIv = itemView.findViewById(R.id.iv_meili);
            gongxianIv = itemView.findViewById(R.id.iv_gongxian);
            ageTv = itemView.findViewById(R.id.user_age);
        }
    }
}
