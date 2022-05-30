package com.miaomi.fenbei.voice.ui.family;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.bean.FamilyMemberBean;
import com.miaomi.fenbei.base.config.BaseConfig;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.widget.LevelView;
import com.miaomi.fenbei.base.widget.SexAndAgeView;
import com.miaomi.fenbei.voice.R;


import java.util.ArrayList;
import java.util.List;

public class AddAdminAdapter extends RecyclerView.Adapter<AddAdminAdapter.ItemHodler> {
    private Context context;
    private List<FamilyMemberBean> list = new ArrayList<>();
    private CheckItemListener mCheckListener;
    public AddAdminAdapter(Context context, CheckItemListener mCheckListener) {
        this.context = context;
        this.mCheckListener=mCheckListener;
    }

    public void setData(List<FamilyMemberBean> data){
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHodler(LayoutInflater.from(context).inflate(R.layout.item_family_add_admin_member, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddAdminAdapter.ItemHodler holder, final int position) {
        bindItem((ItemHodler) holder,position);
    }

    private void bindItem(ItemHodler holder, final int position){

        if (list.get(position).getGood_number_state() == 1){
            holder.idTv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.common_user_icon_liang,0,0,0);
        }else{
            holder.idTv.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        }
        ImgUtil.INSTANCE.loadCircleImg(context,list.get(position).getFace(),holder.iconIv,R.drawable.common_avter_placeholder);
        holder.nickTv.setText(list.get(position).getNickname());
        holder.idTv.setText("ID:"+list.get(position).getGood_number());
        holder.signTv.setText(list.get(position).getSignature());
        holder.check_box_all.setChecked(list.get(position).isChecked());
        holder.ageTv.setContent(list.get(position).getGender() == BaseConfig.USER_INFO_GENDER_MAN,list.get(position).getAge());
        if (list.get(position).getType() == 1){
            holder.idtyIv.setVisibility(View.VISIBLE);
            ImgUtil.INSTANCE.loadImg(context,R.drawable.icon_member_manager,holder.idtyIv, -1);

        }else if (list.get(position).getType() == 2){
            holder.idtyIv.setVisibility(View.VISIBLE);
            ImgUtil.INSTANCE.loadImg(context,R.drawable.icon_member_leader,holder.idtyIv, -1);

        }else{
            holder.idtyIv.setVisibility(View.GONE);
        }
//        if (TextUtils.isEmpty(list.get(position).getRoom_id()) || list.get(position).getRoom_id().equals("0")){
//            holder.statusTv.setVisibility(View.GONE);
//        }else{
//            holder.statusTv.setVisibility(View.VISIBLE);
//        }

        holder.check_box_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).setChecked(!list.get(position).isChecked());
                holder.check_box_all.setChecked(list.get(position).isChecked());
                if (null != mCheckListener) {
                    mCheckListener .itemChecked(""+list.get(position).getUser_id(), holder.check_box_all.isChecked());
                }
                notifyDataSetChanged();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/app/userhomepage")
                        .withString("user_id",String.valueOf(list.get(position).getUser_id()))
                        .navigation();
            }
        });

        holder.meiliIv.setCharmLevel(list.get(position).getCharm_level().getGrade());
        holder.gongxianIv.setWealthLevel(list.get(position).getWealth_level().getGrade());

    }




    public interface CheckItemListener {
        void itemChecked(String userid, boolean isChecked);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemHodler extends RecyclerView.ViewHolder{
        ImageView iconIv;
        TextView nickTv;
        TextView idTv;
        TextView signTv;
        SexAndAgeView ageTv;
        LevelView meiliIv;
        LevelView gongxianIv;
        ImageView idtyIv;
        CheckBox check_box_all;
        public ItemHodler(View itemView) {
            super(itemView);
            idtyIv = itemView.findViewById(R.id.iv_idty);
            iconIv = itemView.findViewById(R.id.user_icon);
            nickTv = itemView.findViewById(R.id.user_nick);
            idTv = itemView.findViewById(R.id.user_id);
            signTv = itemView.findViewById(R.id.user_sign);
            ageTv = itemView.findViewById(R.id.user_age);
            meiliIv = itemView.findViewById(R.id.iv_meili);
            gongxianIv = itemView.findViewById(R.id.iv_gongxian);
            check_box_all=itemView.findViewById(R.id.check_box_all);

        }
    }
}
