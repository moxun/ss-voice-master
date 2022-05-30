package com.miaomi.fenbei.voice.ui.family;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.bean.FamilyRankBean;
import com.miaomi.fenbei.base.config.BaseConfig;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.widget.LevelView;
import com.miaomi.fenbei.base.widget.SexAndAgeView;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FamilyRankAdapter extends RecyclerView.Adapter<FamilyRankAdapter.ItemHodler> {
    private Context context;
    private List<FamilyRankBean> list = new ArrayList<>();

    public FamilyRankAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<FamilyRankBean> data){
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FamilyRankAdapter.ItemHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHodler(LayoutInflater.from(context).inflate(R.layout.item_family_rank, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyRankAdapter.ItemHodler holder, final int position) {
        if (position == 0){
            holder.positionTv.setText("");
            holder.positionTv.setBackgroundResource(R.drawable.icon_family_rank_one);
        }else if (position == 1){
            holder.positionTv.setText("");
            holder.positionTv.setBackgroundResource(R.drawable.icon_family_rank_two);
        } else if (position == 2){
            holder.positionTv.setText("");
            holder.positionTv.setBackgroundResource(R.drawable.icon_family_rank_three);
        } else{
            holder.positionTv.setBackground(null);
            holder.positionTv.setText(String.valueOf(position+1));
        }
        holder.contentTv.setText(list.get(position).getEarning_total());
        holder.nameTv.setText(list.get(position).getNickname());
        ImgUtil.INSTANCE.loadCircleImg(context,list.get(position).getFace(),holder.avterIv, R.drawable.common_avter_placeholder);

        //        1：男，2：女
        holder.ageTv.setText(String.valueOf(list.get(position).getAge()));
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
        holder.ageTv.setContent(list.get(position).getGender() == BaseConfig.USER_INFO_GENDER_MAN,list.get(position).getAge());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ARouter.getInstance().build("/app/userhomepage")
//                        .withString("user_id",String.valueOf(list.get(position).getUser_id()))
//                        .navigation();
                ARouter.getInstance().build("/app/userhomepage")
                        .withString("user_id",String.valueOf(list.get(position).getUser_id()))
                        .withString("user_name",list.get(position).getNickname())
                        .withString("user_face",list.get(position).getFace())
                        .navigation();
            }
        });
//        if (list.get(position).getCharm_level().getGrade() == 1){
//            ImgUtil.INSTANCE.loadImg(context,list.get(position).getCharm_level().getIcon(),holder.meiliIv, R.drawable.common_charm_icon_placeholder_m);
//        }else if (list.get(position).getCharm_level().getGrade() == 19){
//            ImgUtil.INSTANCE.loadImg(context,list.get(position).getCharm_level().getIcon(),holder.meiliIv, R.drawable.common_charm_icon_placeholder_m);
//        }else{
//            ImgUtil.INSTANCE.loadImg(context,list.get(position).getCharm_level().getIcon(),holder.meiliIv, R.drawable.common_charm_icon_placeholder_m);
//        }
//        if (list.get(position).getWealth_level().getGrade() == 1){
//            ImgUtil.INSTANCE.loadImg(context,list.get(position).getWealth_level().getIcon(),holder.gongxianIv, R.drawable.common_charm_icon_placeholder_m);
//        }else if (list.get(position).getWealth_level().getGrade() == 19){
//            ImgUtil.INSTANCE.loadImg(context,list.get(position).getWealth_level().getIcon(),holder.gongxianIv, R.drawable.common_charm_icon_placeholder_m);
//        }else{
//            ImgUtil.INSTANCE.loadImg(context,list.get(position).getWealth_level().getIcon(),holder.gongxianIv, R.drawable.common_charm_icon_placeholder_m);
//        }
        holder.meiliIv.setCharmLevel(list.get(position).getCharm_level().getGrade());
        holder.gongxianIv.setWealthLevel(list.get(position).getWealth_level().getGrade());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ItemHodler extends RecyclerView.ViewHolder{
        ImageView avterIv;
        TextView contentTv;
        TextView nameTv;
        SexAndAgeView ageTv;
        LevelView meiliIv;
        LevelView gongxianIv;
        TextView positionTv;
        public ItemHodler(View itemView) {
            super(itemView);
            positionTv = itemView.findViewById(R.id.iv_position);
            avterIv = itemView.findViewById(R.id.iv_avter);
            contentTv = itemView.findViewById(R.id.tv_content);
            nameTv = itemView.findViewById(R.id.tv_name);
            ageTv = itemView.findViewById(R.id.tv_age);
            meiliIv = itemView.findViewById(R.id.iv_meili);
            gongxianIv = itemView.findViewById(R.id.iv_gongxian);
        }
    }
}