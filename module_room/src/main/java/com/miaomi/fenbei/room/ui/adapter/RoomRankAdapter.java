package com.miaomi.fenbei.room.ui.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.room.R;
import com.miaomi.fenbei.room.ui.fragment.RoomRankListFragment;
import com.miaomi.fenbei.base.bean.RankBean;
import com.miaomi.fenbei.base.bean.event.UserCardEvent;
import com.miaomi.fenbei.base.bean.UserInfo;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.widget.DCBTextView;
import com.miaomi.fenbei.base.widget.LevelView;
import com.miaomi.fenbei.base.widget.SexView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class RoomRankAdapter  extends RecyclerView.Adapter<RoomRankAdapter.ViewHolder> {

    private List<RankBean> mList;
    private Context context;
    private int type;

    public RoomRankAdapter(List<RankBean> mList, Context context,int type) {
        this.mList = mList;
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.room_item_room_rank,parent,false));
    }



    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final RankBean bean = mList.get(position);
        holder.numberTv.setText("No."+String.valueOf(position+4));
        ImgUtil.INSTANCE.loadFaceIcon(context,bean.getFace(),holder.iconIv);
        holder.contentTv.setText(bean.getNickname());
        holder.totalTv.setText(bean.getEarning_total());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo userInfo = new UserInfo();
                userInfo.setNickname(mList.get(position).getNickname());
                userInfo.setUser_id(mList.get(position).getUser_id());
                EventBus.getDefault().post(new UserCardEvent(userInfo));
            }
        });
        holder.sexTv.setSeleted(bean.getGender());
        if (type == RoomRankListFragment.RANK_TYPE_ML){
            holder.gongxianIv.setCharmLevel(bean.getCharm_level().getGrade());
        }else{
            holder.gongxianIv.setWealthLevel(bean.getWealth_level().getGrade());
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        DCBTextView numberTv;
        ImageView iconIv;
        TextView contentTv;
        SexView sexTv;
        LevelView gongxianIv;
        TextView totalTv;
        public ViewHolder(View itemView) {
            super(itemView);
            numberTv = itemView.findViewById(R.id.tv_number);
            iconIv = itemView.findViewById(R.id.tv_icon);
            contentTv = itemView.findViewById(R.id.tv_content);
            sexTv = itemView.findViewById(R.id.tv_sex);
            totalTv = itemView.findViewById(R.id.tv_total);
            gongxianIv = itemView.findViewById(R.id.iv_gongxian);
        }
    }
}
