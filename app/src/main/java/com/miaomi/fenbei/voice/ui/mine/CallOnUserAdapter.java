package com.miaomi.fenbei.voice.ui.mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.CallOnBean;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.TimeUtil;
import com.miaomi.fenbei.base.widget.LevelView;
import com.miaomi.fenbei.base.widget.SexView;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.mine.user_homepage.UserHomepageActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CallOnUserAdapter extends RecyclerView.Adapter<CallOnUserAdapter.ItemHolder> {

    private List<CallOnBean> mList = new ArrayList<>();
    private Context context;

    public CallOnUserAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CallOnBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<CallOnBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CallOnUserAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CallOnUserAdapter.ItemHolder(LayoutInflater.from(context).inflate(R.layout.item_call_on_user, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull final CallOnUserAdapter.ItemHolder holder, int position) {
        holder.timeTv.setText(TimeUtil.longFormatTime(mList.get(position).getCreate_time()));
        holder.nameTv.setText(mList.get(position).getNickname());
        holder.contentTv.setText("ID:"+mList.get(position).getUser_id());
        holder.gongxianIv.setWealthLevel(mList.get(position).getWealth_level().getGrade());
        holder.sexTv.setSeleted(mList.get(position).getGender());
        ImgUtil.INSTANCE.loadFaceIcon(context,mList.get(position).getFace(),holder.avterIv);
        holder.meiliIv.setCharmLevel(mList.get(position).getCharm_level().getGrade());
        holder.itemView.setOnClickListener(v -> context.startActivity(UserHomepageActivity.getIntent(context,String.valueOf(mList.get(position).getUser_id()))));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        ImageView avterIv;
        TextView contentTv;
        TextView nameTv;
        TextView statusTv;
        SexView sexTv;
        LevelView gongxianIv;
        LevelView meiliIv;
        private TextView timeTv;
        public ItemHolder(View itemView) {
            super(itemView);
            avterIv = itemView.findViewById(R.id.iv_avter);
            contentTv = itemView.findViewById(R.id.tv_id);
            nameTv = itemView.findViewById(R.id.tv_name);
            statusTv = itemView.findViewById(R.id.tv_status);
            sexTv = itemView.findViewById(R.id.tv_sex);
            gongxianIv = itemView.findViewById(R.id.iv_gongxian);
            timeTv = itemView.findViewById(R.id.tv_time);
            meiliIv = itemView.findViewById(R.id.iv_meili);
        }
    }
}
