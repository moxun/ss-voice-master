package com.miaomi.fenbei.voice.ui.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.bean.RankBean;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.widget.LevelView;
import com.miaomi.fenbei.base.widget.SexView;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.RankTypeFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {

    private List<RankBean> mList;
    private Context context;
    private int type;

    //    private int roomType;
    public RankAdapter(List<RankBean> mList, Context context, int type) {
        this.mList = mList;
        this.context = context;
        this.type = type;
//        this.roomType = roomType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.main_item_rank, parent, false));
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final RankBean bean = mList.get(position);
        holder.numberTv.setText("NO." + String.valueOf(position + 4));
        ImgUtil.INSTANCE.loadFaceIcon(context, bean.getFace(), holder.iconIv);
        holder.nameTv.setText(bean.getNickname());
        holder.totalTv.setText("距上 " + bean.getDistance_total1());
        holder.itemView.setOnClickListener(v -> {
            ARouter.getInstance().build("/app/userhomepage")
                    .withString("user_id", bean.getUser_id() + "")
                    .withString("user_name", bean.getNickname())
                    .withString("user_face", bean.getFace())
                    .navigation();
        });
        //        1：男，2：女
        holder.ageTv.setSeleted(bean.getGender());
        if (type == RankTypeFragment.RANK_TYPE_ML) {
            holder.gongxianIv.setCharmLevel(bean.getCharm_level().getGrade());
            holder.numberTv.setSelected(false);
        } else {
            holder.numberTv.setSelected(true);
            holder.gongxianIv.setWealthLevel(bean.getWealth_level().getGrade());
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView numberTv;
        ImageView iconIv;
        TextView nameTv;
        SexView ageTv;
        LevelView gongxianIv;
        TextView totalTv;

        public ViewHolder(View itemView) {
            super(itemView);
            numberTv = itemView.findViewById(R.id.tv_number);
            iconIv = itemView.findViewById(R.id.tv_icon);
            nameTv = itemView.findViewById(R.id.tv_name);
            ageTv = itemView.findViewById(R.id.tv_age);
            totalTv = itemView.findViewById(R.id.tv_total);
            gongxianIv = itemView.findViewById(R.id.iv_gongxian);
        }
    }
}
