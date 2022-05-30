package com.miaomi.fenbei.voice.ui.main.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.bean.FollowBean;
import com.miaomi.fenbei.base.config.BaseConfig;
import com.miaomi.fenbei.base.core.msg.MsgManager;
import com.miaomi.fenbei.base.core.msg.SendMsgListener;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.SexAndAgeView;
import com.miaomi.fenbei.room.ChatRoomManager;
import com.miaomi.fenbei.base.bean.FriendsBean;
import com.miaomi.fenbei.base.core.JoinChatCallBack;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.widget.LevelView;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.mine.user_homepage.UserHomepageActivity;
import com.tencent.imsdk.TIMMessage;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ItemHolder>{

    private Context context;
    private List<FriendsBean> list;
   private  int friendType;
    public FriendAdapter(Context context, List<FriendsBean> list,int friendType) {
        this.context = context;
        this.list = list;
        this.friendType=friendType;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(context).inflate(R.layout.user_item_friend, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, final int position) {
        holder.contentTv.setText("ID："+list.get(position).getUser_id());
        if(friendType==1||friendType==0){
            holder.nameTv.setText(list.get(position).getNickname());
        }else{
            holder.nameTv.setText(list.get(position).getName());
        }

        if (!TextUtils.isEmpty(list.get(position).getFace())){
            ImgUtil.INSTANCE.loadFaceIcon(context,list.get(position).getFace(),holder.avterIv);
        }
         if(friendType==1){
             holder.fansTv.setText("去找他");
             holder.fansTv.setVisibility(View.VISIBLE);
         }else{
             holder.followTv.setText("去找他");
             holder.followTv.setVisibility(View.VISIBLE);
         }
        if (list.get(position).getState() == 1){
            holder.statusTv.setVisibility(View.GONE);
            holder.statusTv.setText("房间");
            holder.statusTv.setOnClickListener(v -> ChatRoomManager.INSTANCE.joinChat(context, list.get(position).getRoom_id(), new JoinChatCallBack() {
                @Override
                public void onSuc() {

                }

                @Override
                public void onFail(@NotNull String msg) {

                }
            }));
        }else{
            holder.statusTv.setVisibility(View.GONE);
        }
//        holder.followTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ARouter.getInstance().build("/app/userhomepage")
//                        .withString("user_id",list.get(position).getUser_id()+"")
//                        .withString("user_name",list.get(position).getNickname())
//                        .withString("user_face",list.get(position).getFace())
//                        .navigation();
//            }
//        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/app/userhomepage")
                        .withString("user_id",list.get(position).getUser_id()+"")
                        .withString("user_name",list.get(position).getNickname())
                        .withString("user_face",list.get(position).getFace())
                        .navigation();
            }
        });

//        holder.ageTv.setSeleted(list.get(position).getGender());

        holder.ageTv.setContent(list.get(position).getGender() == BaseConfig.USER_INFO_GENDER_MAN,list.get(position).getAge());
        holder.gongxianIv.setVisibility(View.INVISIBLE);
//        if (list.get(position).getWealth_level() != null){
//            holder.gongxianIv.setWealthLevel(list.get(position).getWealth_level().getGrade());
//        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder{

        ImageView avterIv;
        TextView contentTv;
        TextView nameTv;
        TextView statusTv;
        SexAndAgeView ageTv;
        LevelView gongxianIv;
        TextView followTv;
        TextView fansTv;
        public ItemHolder(View itemView) {
            super(itemView);
            avterIv = itemView.findViewById(R.id.iv_avter);
            contentTv = itemView.findViewById(R.id.tv_content);
            nameTv = itemView.findViewById(R.id.tv_name);
            statusTv = itemView.findViewById(R.id.tv_status);
            ageTv = itemView.findViewById(R.id.tv_sex);
            gongxianIv = itemView.findViewById(R.id.iv_gongxian);
            followTv=itemView.findViewById(R.id.tv_follow);
            fansTv=itemView.findViewById(R.id.tv_fans);
        }
    }
}
