package com.miaomi.fenbei.voice.ui.room;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.UserInfo;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.widget.LevelView;
import com.miaomi.fenbei.base.widget.SexView;
import com.miaomi.fenbei.voice.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddManagerAdapter extends RecyclerView.Adapter<AddManagerAdapter.ItemHolder> {

    private Context context;
    private List<UserInfo> list;
    private OnSettingListener onSettingListener;

    AddManagerAdapter(Context context, List<UserInfo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(context).inflate(R.layout.chatting_item_add_manager, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, final int position) {
        holder.user_id.setText("ID:" + list.get(position).getUser_id());
        holder.ageTv.setSeleted(list.get(position).getGender());
        //是否为管理员
        if (list.get(position).getUser_role() == 0) {
            holder.settingTv.setText("设置");
        } else {
            holder.settingTv.setText("解除");
        }
        holder.iv_gongxian.setWealthLevel(list.get(position).getWealth_level().getGrade());
        holder.iv_gongxian.setCharmLevel(list.get(position).getCharm_level().getGrade());
        ImgUtil.INSTANCE.loadCircleImg(context, list.get(position).getFace(), holder.user_icon,R.drawable.common_avter_placeholder);
        holder.user_nick.setText(list.get(position).getNickname());
        holder.settingTv.setOnClickListener(v -> {
            if (onSettingListener != null){
                onSettingListener.onSetting(String.valueOf(list.get(position).getUser_id()));
            }
        });
    }

    public void setOnSettingListener(OnSettingListener onSettingListener) {
        this.onSettingListener = onSettingListener;
    }


    public interface OnSettingListener{
        void onSetting(String uid);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder{
        TextView user_id;
        SexView ageTv;
        TextView user_nick;
        ImageView user_icon;
        TextView settingTv;
        LevelView iv_gongxian;
        LevelView iv_meili;
        ItemHolder(View itemView) {
            super(itemView);
            user_icon = itemView.findViewById(R.id.user_icon);
            user_nick = itemView.findViewById(R.id.user_nick);
            ageTv = itemView.findViewById(R.id.user_sex);
            user_id = itemView.findViewById(R.id.user_id);
            settingTv = itemView.findViewById(R.id.tv_setting);
            iv_gongxian = itemView.findViewById(R.id.iv_gongxian);
            iv_meili = itemView.findViewById(R.id.iv_meili);
        }
    }
}
