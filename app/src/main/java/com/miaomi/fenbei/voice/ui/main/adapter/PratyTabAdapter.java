package com.miaomi.fenbei.voice.ui.main.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.miaomi.fenbei.base.bean.RoomlabelBean;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.voice.R;
import java.util.ArrayList;
import java.util.List;

public class PratyTabAdapter extends RecyclerView.Adapter<PratyTabAdapter.ItemHolder>{

    private List<RoomlabelBean> mList = new ArrayList<>();
    private Context mContext;
    private CheckItemListener mCheckListener;

    public PratyTabAdapter(Context mContext,CheckItemListener mCheckListener) {
        this.mCheckListener=mCheckListener;
        this.mContext = mContext;
    }

    public void setData(List<RoomlabelBean> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_party_tab,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {


        if (mList.get(position).isSelected()){
            holder.tabtitleTv.setTextSize(15);
            holder.tabtitleTv.setTextColor(Color.parseColor("#FFFFFF"));
            TextPaint tp = holder.tabtitleTv.getPaint();
            tp.setFakeBoldText(true);
        }else{
            holder.tabtitleTv.setTextSize(13);
            holder.tabtitleTv.setTextColor(Color.parseColor("#CDB2E0"));
            TextPaint tp = holder.tabtitleTv.getPaint();
            tp.setFakeBoldText(false);
        }
        holder.tabtitleTv.setText(mList.get(position).getName());
        if (!TextUtils.isEmpty(mList.get(position).getImg_in())||position>0){
            ImgUtil.INSTANCE.loadFaceIcon(mContext,mList.get(position).getImg_in(), holder.tabiconIv);
        }else{
            ImgUtil.INSTANCE.loadCircleImg(mContext,"",holder.tabiconIv,R.drawable.icon_party_tab_hot);
        }

        holder.itemView.setOnClickListener(v ->{
            if (null != mCheckListener) {
                for (RoomlabelBean bean : mList){
                    bean.setSelected(false);
                }
                mList.get(position).setSelected(true);
                mCheckListener.itemChecked(""+mList.get(position).getId());

                notifyDataSetChanged();

            }
        });
//        holder.itemView.setOnClickListener(v ->
//                ChatRoomManager.INSTANCE.joinChat(mContext, String.valueOf(mList.get(position).getRoom_id()), new JoinChatCallBack() {
//            @Override
//            public void onSuc() {
//
//            }
//
//            @Override
//            public void onFail(@NotNull String msg) {
//
//            }
//        }));
    }
    public interface CheckItemListener {
        void itemChecked(String id);
    }
    @Override
    public int getItemCount() {
        return mList.size();

    }

    static class ItemHolder extends RecyclerView.ViewHolder{
        private ImageView tabiconIv;
        private TextView tabtitleTv;
        ItemHolder(View itemView) {
            super(itemView);
            tabiconIv = itemView.findViewById(R.id.iv_tab_icon);
            tabtitleTv=itemView.findViewById(R.id.tv_tab_title);
        }
    }
}
