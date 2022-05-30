package com.miaomi.fenbei.voice.ui.family;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miaomi.fenbei.base.bean.DividedUserBean;
import com.miaomi.fenbei.base.bean.FamilyMemberBean;
import com.miaomi.fenbei.base.config.BaseConfig;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.widget.LevelView;
import com.miaomi.fenbei.base.widget.SexAndAgeView;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;

public class PartAdapter extends RecyclerView.Adapter<PartAdapter.ItemHodler> {
    private Context context;
    private List<DividedUserBean.ListBean> list = new ArrayList<>();
    private PopupWindow popupBigPhoto;
    private int familyType;
    public PartAdapter(Context context,int familyType) {
        this.context = context;
        this.familyType=familyType;
    }

    public void setData(List<DividedUserBean.ListBean> data){
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHodler(LayoutInflater.from(context).inflate(R.layout.item_family_part, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PartAdapter.ItemHodler holder, final int position) {
        bindItem((ItemHodler) holder,position);
    }

    private void bindItem(ItemHodler holder, final int position){
        View popView =LayoutInflater.from(context).inflate(R.layout.popupwindow_part, null);
        TextView partnumTv_1=  popView.findViewById(R.id.tv_part_num_1);
        TextView partnumTv_2= popView.findViewById(R.id.tv_part_num_2);
        int partnum1,partnum2;
        if(familyType==70){
            partnumTv_1.setText("移动至60%");
            partnumTv_2.setText("移动至50%");
            partnum1=60;
            partnum2=50;
        }else if(familyType==60){
            partnumTv_1.setText("移动至70%");
            partnumTv_2.setText("移动至50%");
            partnum1=70;
            partnum2=50;
        }else{
            partnumTv_1.setText("移动至70%");
            partnumTv_2.setText("移动至60%");
            partnum1=70;
            partnum2=60;
        }
        partnumTv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOprateListener != null){
                    popupBigPhoto.dismiss();
                    onOprateListener.onSetManeger(String.valueOf(list.get(position).getUser_id()),partnum1);
                }
            }
        });
        partnumTv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOprateListener != null){
                    popupBigPhoto.dismiss();
                    onOprateListener.onSetManeger(String.valueOf(list.get(position).getUser_id()),partnum2);
                }
            }
        });
        if (list.get(position).getUser_id() == 1){
            holder.idTv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.common_user_icon_liang,0,0,0);
        }else{
            holder.idTv.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        }
        ImgUtil.INSTANCE.loadCircleImg(context,list.get(position).getFace(),holder.iconIv,R.drawable.common_avter_placeholder);
        holder.nickTv.setText(list.get(position).getNickname());
        holder.idTv.setText("ID:"+list.get(position).getUser_id());



        holder.ageTv.setContent(list.get(position).getGender() == BaseConfig.USER_INFO_GENDER_MAN,list.get(position).getAge());





        holder.meiliIv.setCharmLevel(list.get(position).getCharm_level().getGrade());
        holder.gongxianIv.setWealthLevel(list.get(position).getWealth_level().getGrade());
        holder.adminLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                LinearLayout.LayoutParams.WRAP_CONTENT
                popupBigPhoto = new PopupWindow(popView, 380 , 210);
                popupBigPhoto.setOutsideTouchable(true);
                popupBigPhoto.showAsDropDown(holder.iv_admin, -300 , 0);

            }
        });
    }



    OnOprateListener onOprateListener;
    public void setOnOprateListener(OnOprateListener onOprateListener) {
        this.onOprateListener = onOprateListener;
    }

    public interface OnOprateListener{
        void onSetManeger(String uid, int isManager);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemHodler extends RecyclerView.ViewHolder{
        ImageView iconIv;
        TextView nickTv;
        TextView idTv;

        SexAndAgeView ageTv;
        LevelView meiliIv;
        LevelView gongxianIv;
        LinearLayout adminLl;
        ImageView iv_admin;
        public ItemHodler(View itemView) {
            super(itemView);
            iconIv = itemView.findViewById(R.id.user_icon);
            nickTv = itemView.findViewById(R.id.user_nick);
            idTv = itemView.findViewById(R.id.user_id);

            ageTv = itemView.findViewById(R.id.user_age);
            meiliIv = itemView.findViewById(R.id.iv_meili);
            gongxianIv = itemView.findViewById(R.id.iv_gongxian);
            adminLl=itemView.findViewById(R.id.ll_admin);
            iv_admin=itemView.findViewById(R.id.iv_admin);
        }
    }
}
