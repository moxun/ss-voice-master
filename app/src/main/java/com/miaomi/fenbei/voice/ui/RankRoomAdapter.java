package com.miaomi.fenbei.voice.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miaomi.fenbei.base.bean.RankRoomBean;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.voice.R;

import java.util.List;


public class RankRoomAdapter extends RecyclerView.Adapter<RankRoomAdapter.ViewHolder> {

    private List<RankRoomBean.DataBean> mList;
    private Context context;
    private int type;
//    private int roomType;
    public RankRoomAdapter(List<RankRoomBean.DataBean> mList, Context context, int type) {
        this.mList = mList;
        this.context = context;
        this.type = type;
//        this.roomType = roomType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.main_item_room_rank,parent,false));
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final RankRoomBean.DataBean bean = mList.get(position);
        holder.numberTv.setText("NO."+String.valueOf(position+4));
        if (!TextUtils.isEmpty(bean.getFace())) {
            ImgUtil.INSTANCE.loadHomeHotImg(context, bean.getFace(), holder.iconIv,6f,-1);
        }
        holder.nameTv.setText(bean.getNickname());
        holder.totalTv.setText("距上 "+ bean.getDistance_total1());
        holder.itemView.setOnClickListener(v -> {
//            ARouter.getInstance().build("/app/userhomepage")
//                    .withString("user_id",bean.getUser_id()+"")
//                    .withString("user_name",bean.getNickname())
//                    .withString("user_face",bean.getFace())
//                    .navigation();
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView numberTv;
        ImageView iconIv;
        TextView nameTv;

        TextView totalTv;
        public ViewHolder(View itemView) {
            super(itemView);
            numberTv = itemView.findViewById(R.id.tv_number);
            iconIv = itemView.findViewById(R.id.tv_icon);
            nameTv = itemView.findViewById(R.id.tv_name);

            totalTv = itemView.findViewById(R.id.tv_total);

        }
    }
}
