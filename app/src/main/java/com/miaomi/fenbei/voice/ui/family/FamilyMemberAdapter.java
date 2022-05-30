package com.miaomi.fenbei.voice.ui.family;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.bean.FamilyMemberBean;
import com.miaomi.fenbei.base.config.BaseConfig;
import com.miaomi.fenbei.base.core.JoinChatCallBack;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.LevelView;
import com.miaomi.fenbei.base.widget.SexAndAgeView;
import com.miaomi.fenbei.room.ChatRoomManager;
import com.miaomi.fenbei.voice.R;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FamilyMemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<FamilyMemberBean> list = new ArrayList<>();
    private int viewType;

    public void setViewType(int viewType) {
        this.viewType = viewType;

    }

    public FamilyMemberAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<FamilyMemberBean> data){
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //能否左滑操作
        if (viewType == 3){

            return new LeaderItemHodler(LayoutInflater.from(context).inflate(R.layout.item_family_member_for_leader, parent, false));
        }else{

            return new  ItemHodler(LayoutInflater.from(context).inflate(R.layout.item_family_member,parent,false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        //族长取消左滑
//        if (list.get(position).getType() == 2){
//            return 0;
//        }
        //管理员能对普通用户进行操作
        if (viewType == 2 && list.get(position).getType() == 0){
            return 2;
        }
        return viewType;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemHodler){
            bindItem((ItemHodler) holder,position);
        }
        if (holder instanceof LeaderItemHodler){
            bindLeaderItem((LeaderItemHodler) holder,position);
        }
    }
    private PopupWindow popupBigPhoto ;
    private void bindLeaderItem( LeaderItemHodler holder, final int position){

         View popView =LayoutInflater.from(context).inflate(R.layout.popupwindow_member, null);
        TextView delTv=  popView.findViewById(R.id.delete_tv);
        TextView oprateTv= popView.findViewById(R.id.oprate_tv);
        ImgUtil.INSTANCE.loadCircleImg(context,list.get(position).getFace(),holder.iconIv,R.drawable.common_avter_placeholder);
        holder.nickTv.setText(list.get(position).getNickname());
        holder.idTv.setText("ID:"+list.get(position).getUser_id());
        holder.signTv.setText(list.get(position).getSignature());
        holder.dividedTv.setText(list.get(position).getDivided()+"%");
        if (list.get(position).getGood_number_state() == 1){
            holder.idTv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.common_user_icon_liang,0,0,0);
        }else{
            holder.idTv.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        }

        if (list.get(position).getType() == 1){
            delTv.setVisibility(View.VISIBLE);
            oprateTv.setVisibility(View.VISIBLE);
            holder.idtyTv.setVisibility(View.VISIBLE);
            holder.iviconbarIv.setBackgroundResource(R.drawable.bg_find_admin_bar);
            holder.idtyTv.setVisibility(View.VISIBLE);
            holder.idtyTv.setText("管理");
            holder.idtyTv.setBackgroundResource(R.drawable.bg_administration);
//            ImgUtil.INSTANCE.loadImg(context,R.drawable.bg_family_member_maneger,holder.idtyIv, -1);
            oprateTv.setText("取消管理");

            oprateTv.setOnClickListener(v -> {
                if (onOprateListener != null){
                    popupBigPhoto.dismiss();
                    onOprateListener.onSetManeger(String.valueOf(list.get(position).getUser_id()),true);
                }
            });
        }else if (list.get(position).getType() == 2){
//            holder.swipeMenuLayout.setCloseTranslation(false);
            oprateTv.setVisibility(View.GONE);
            delTv.setVisibility(View.GONE);
            holder.idtyTv.setVisibility(View.VISIBLE);
            holder.iv_admin.setVisibility(View.INVISIBLE);
            holder.iviconbarIv.setVisibility(View.VISIBLE);
            holder.iviconbarIv.setBackgroundResource(R.drawable.bg_find_patriarch_bar);
            holder.idtyTv.setText("族长");
            holder.idtyTv.setBackgroundResource(R.drawable.bg_patriarch);
//            ImgUtil.INSTANCE.loadImg(context,R.drawable.bg_family_member_leader,holder.idtyIv, -1);
        }else{
            oprateTv.setOnClickListener(v -> {
                if (onOprateListener != null){
                    popupBigPhoto.dismiss();
                    onOprateListener.onSetManeger(String.valueOf(list.get(position).getUser_id()),false);
                }
            });
//            holder.swipeMenuLayout.setCloseTranslation(false);
            oprateTv.setVisibility(View.VISIBLE);
            delTv.setVisibility(View.VISIBLE);
            oprateTv.setText("设为管理");
            holder.iviconbarIv.setVisibility(View.GONE);
            holder.idtyTv.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(list.get(position).getRoom_id()) || list.get(position).getRoom_id().equals("0")){
            holder.statusTv.setVisibility(View.GONE);
        }else{
            holder.statusTv.setVisibility(View.GONE);
        }
        holder.statusTv.setOnClickListener(v -> {
            ChatRoomManager.INSTANCE.joinChat(context, list.get(position).getRoom_id(), new JoinChatCallBack() {
                @Override
                public void onSuc() {

                }
                @Override
                public void onFail(@NotNull String msg) {
                    ToastUtil.INSTANCE.error(context,msg);
                }
            });
        });
        holder.itemView.setOnClickListener(v -> ARouter.getInstance().build("/app/userhomepage")
                .withString("user_id",String.valueOf(list.get(position).getUser_id()))
                .navigation());
        delTv.setOnClickListener(v -> {
            if (onOprateListener != null){
                popupBigPhoto.dismiss();
                onOprateListener.onShortOff(String.valueOf(list.get(position).getUser_id()));
            }
        });
        //        1：男，2：女
        holder.ageTv.setContent(list.get(position).getGender() == BaseConfig.USER_INFO_GENDER_MAN,list.get(position).getAge());

        //管理员只有踢出操作
        if (viewType == 2){
//            holder.oprateTv.setVisibility(View.GONE);
        }

        holder.meiliIv.setCharmLevel(list.get(position).getCharm_level().getGrade());
        holder.gongxianIv.setWealthLevel(list.get(position).getWealth_level().getGrade());

        holder.adminLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupBigPhoto = new PopupWindow(popView,  LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                popupBigPhoto.setOutsideTouchable(true);
//                popupBigPhoto.update();
//                popView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                popupBigPhoto.showAsDropDown(holder.iv_admin, -300 , 0);
//                popupBigPhoto.showAsDropDown(v,);
            }
        });
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

//        if (list.get(position).getGender() == 1) {
//            Drawable drawable = context.getResources().getDrawable(com.mier.common.R.drawable.common_user_symbol_male);
//            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//            holder.ageTv.setBackgroundResource(com.mier.common.R.drawable.user_male_bg);
//            holder.ageTv.setCompoundDrawables(drawable, null, null, null);
//        } else {
//            Drawable drawable = context.getResources().getDrawable(com.mier.common.R.drawable.common_user_symbol_female);
//            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//            holder.ageTv.setBackgroundResource(com.mier.common.R.drawable.user_female_bg);
//            holder.ageTv.setCompoundDrawables(drawable, null, null, null);
//        }
        holder.ageTv.setContent(list.get(position).getGender() == BaseConfig.USER_INFO_GENDER_MAN,list.get(position).getAge());
        if (list.get(position).getType() == 1){
            holder.idtyTv.setVisibility(View.VISIBLE);
            holder.iviconbarIv.setBackgroundResource(R.drawable.bg_find_admin_bar);
            holder.idtyTv.setVisibility(View.VISIBLE);
            holder.idtyTv.setText("管理");
            holder.idtyTv.setBackgroundResource(R.drawable.bg_administration);

//            ImgUtil.INSTANCE.loadImg(context,R.drawable.icon_member_manager,holder.idtyIv, -1);

        }else if (list.get(position).getType() == 2){
            holder.idtyTv.setVisibility(View.VISIBLE);
            holder.iviconbarIv.setVisibility(View.VISIBLE);
            holder.iviconbarIv.setBackgroundResource(R.drawable.bg_find_patriarch_bar);
            holder.idtyTv.setText("族长");
            holder.idtyTv.setBackgroundResource(R.drawable.bg_patriarch);
//            ImgUtil.INSTANCE.loadImg(context,R.drawable.icon_member_leader,holder.idtyIv, -1);

        }else{

            holder.iviconbarIv.setVisibility(View.GONE);
            holder.idtyTv.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(list.get(position).getRoom_id()) || list.get(position).getRoom_id().equals("0")){
            holder.statusTv.setVisibility(View.GONE);
        }else{
            holder.statusTv.setVisibility(View.VISIBLE);
        }

        holder.statusTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatRoomManager.INSTANCE.joinChat(context, list.get(position).getRoom_id(), new JoinChatCallBack() {
                    @Override
                    public void onSuc() {

                    }

                    @Override
                    public void onFail(@NotNull String msg) {
                        ToastUtil.INSTANCE.error(context,msg);
                    }
                });
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
//        if (list.get(position).getCharm_level().getGrade() == 1){
//            ImgUtil.INSTANCE.loadImg(context,list.get(position).getCharm_level().getIcon(),holder.meiliIv, com.mier.common.R.drawable.common_charm_icon_placeholder_m);
//        }else if (list.get(position).getCharm_level().getGrade() == 19){
//            ImgUtil.INSTANCE.loadImg(context,list.get(position).getCharm_level().getIcon(),holder.meiliIv, com.mier.common.R.drawable.common_charm_icon_placeholder_m);
//        }else{
//            ImgUtil.INSTANCE.loadImg(context,list.get(position).getCharm_level().getIcon(),holder.meiliIv, com.mier.common.R.drawable.common_charm_icon_placeholder_m);
//        }
//        if (list.get(position).getWealth_level().getGrade() == 1){
//            ImgUtil.INSTANCE.loadImg(context,list.get(position).getWealth_level().getIcon(),holder.gongxianIv, com.mier.common.R.drawable.common_charm_icon_placeholder_m);
//        }else if (list.get(position).getWealth_level().getGrade() == 19){
//            ImgUtil.INSTANCE.loadImg(context,list.get(position).getWealth_level().getIcon(),holder.gongxianIv, com.mier.common.R.drawable.common_charm_icon_placeholder_m);
//        }else{
//            ImgUtil.INSTANCE.loadImg(context,list.get(position).getWealth_level().getIcon(),holder.gongxianIv, com.mier.common.R.drawable.common_charm_icon_placeholder_m);
//        }
        holder.meiliIv.setCharmLevel(list.get(position).getCharm_level().getGrade());
        holder.gongxianIv.setWealthLevel(list.get(position).getWealth_level().getGrade());

    }

    OnOprateListener onOprateListener;
    public void setOnOprateListener(OnOprateListener onOprateListener) {
        this.onOprateListener = onOprateListener;
    }

    public interface OnOprateListener{
        void onSetManeger(String uid, boolean isManager);
        void onShortOff(String uid);
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
        TextView statusTv;
        SexAndAgeView ageTv;
        LevelView meiliIv;
        LevelView gongxianIv;
        TextView idtyTv;
        ImageView iviconbarIv;

        public ItemHodler(View itemView) {
            super(itemView);
            idtyTv = itemView.findViewById(R.id.tv_idty);
            iconIv = itemView.findViewById(R.id.user_icon);
            nickTv = itemView.findViewById(R.id.user_nick);
            idTv = itemView.findViewById(R.id.user_id);
            signTv = itemView.findViewById(R.id.user_sign);
            statusTv = itemView.findViewById(R.id.tv_status);
            ageTv = itemView.findViewById(R.id.user_age);
            meiliIv = itemView.findViewById(R.id.iv_meili);
            gongxianIv = itemView.findViewById(R.id.iv_gongxian);
            iviconbarIv=itemView.findViewById(R.id.iv_icon_bar);

        }
    }
    static class LeaderItemHodler extends RecyclerView.ViewHolder{
        ImageView iconIv;
        TextView nickTv;
        TextView idTv;
        TextView signTv;
        TextView statusTv;
        SexAndAgeView ageTv;
//        TextView oprateTv;
//        TextView deltetTv;
        LevelView meiliIv;
        LevelView gongxianIv;
//        SwipeMenuLayout swipeMenuLayout;
        TextView idtyTv;
        ImageView iv_admin;
        LinearLayout adminLl;
        TextView dividedTv;
        ImageView iviconbarIv;
        public LeaderItemHodler(View itemView) {
            super(itemView);
            iconIv = itemView.findViewById(R.id.user_icon);
            nickTv = itemView.findViewById(R.id.user_nick);
            idTv = itemView.findViewById(R.id.user_id);
            signTv = itemView.findViewById(R.id.user_sign);
            statusTv = itemView.findViewById(R.id.tv_status);
            ageTv = itemView.findViewById(R.id.user_age);
            idtyTv = itemView.findViewById(R.id.tv_idty);
//            oprateTv = itemView.findViewById(R.id.oprate_tv);
//            deltetTv = itemView.findViewById(R.id.delete_tv);
//            swipeMenuLayout = itemView.findViewById(R.id.sm_layout);
            meiliIv = itemView.findViewById(R.id.iv_meili);
            gongxianIv = itemView.findViewById(R.id.iv_gongxian);
            iv_admin=itemView.findViewById(R.id.iv_admin);
            adminLl=itemView.findViewById(R.id.ll_admin);
            iviconbarIv=itemView.findViewById(R.id.iv_icon_bar);
            dividedTv=itemView.findViewById(R.id.tv_divided);
        }
    }
}
