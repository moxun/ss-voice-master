package com.miaomi.fenbei.voice.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miaomi.fenbei.base.bean.ChatListBean;
import com.miaomi.fenbei.base.core.JoinChatCallBack;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.room.ChatRoomManager;
import com.miaomi.fenbei.voice.R;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGADynamicEntity;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomePartyAdapter extends RecyclerView.Adapter<HomePartyAdapter.ItemHolder>{

    private List<ChatListBean> mList = new ArrayList<>();
    private Context mContext;

    public HomePartyAdapter(Context mContext) {
        this.mContext = mContext;

    }

    public void setData(List<ChatListBean> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home_party,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        ImgUtil.INSTANCE.loadRoundImg(mContext,mList.get(position).getChat_room_icon(), holder.tabiconIv,8f,-1);
        ImgUtil.INSTANCE.loadGif(holder.itemView.getContext(), R.drawable.base_icon_room_online, holder.roomGifIv);
        holder.onlineNumTv.setText(String.valueOf(mList.get(position).getHot_value()));
        showSvgaGiftAnim(holder.itemView.getContext(),holder.svgaImageView,"svga_home_top_room.svga");
        holder.itemView.setOnClickListener(v ->
                ChatRoomManager.INSTANCE.joinChat(holder.itemView.getContext(), mList.get(position).getId(), new JoinChatCallBack() {

            @Override
            public void onSuc() {

            }

            @Override
            public void onFail(@NotNull String msg) {
                ToastUtil.INSTANCE.error(holder.itemView.getContext(),msg);
            }
        }));
    }

    @Override
    public int getItemCount() {
//        if(mList.size()>=3){
//            return 3;
//        }else{
//            return mList.size();
//        }
        return mList.size();

    }

    static class ItemHolder extends RecyclerView.ViewHolder{
        private ImageView tabiconIv;
        private TextView onlineNumTv;
        private ImageView roomGifIv;
        private SVGAImageView svgaImageView;
        ItemHolder(View itemView) {
            super(itemView);
            tabiconIv = itemView.findViewById(R.id.iv_voicecircle_tab_icon);
            onlineNumTv = itemView.findViewById(R.id.online_num);
            roomGifIv = itemView.findViewById(R.id.iv_room_gif);
            svgaImageView = itemView.findViewById(R.id.bg_svga);
        }
    }

    private void showSvgaGiftAnim(Context context, SVGAImageView svgaImageView, String url){
        SVGAParser parser = new SVGAParser(context);
        svgaImageView.setCallback(new SVGACallback() {
            @Override
            public void onPause() {

            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onRepeat() {
            }

            @Override
            public void onStep(int i, double v) {

            }
        });
        parser.decodeFromAssets(url, new SVGAParser.ParseCompletion() {
            @Override
            public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                SVGADynamicEntity dynamicItem = new SVGADynamicEntity();
                SVGADrawable drawable = new SVGADrawable(videoItem, dynamicItem);
                svgaImageView.setImageDrawable(drawable);
                svgaImageView.startAnimation();
            }
            @Override
            public void onError() {
            }
        });
    }
}
