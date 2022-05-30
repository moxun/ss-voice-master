package com.miaomi.fenbei.room.ui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miaomi.fenbei.base.bean.JoinTheFanGroupBean;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.room.R;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGADynamicEntity;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JoinTheFanGroupAdapter extends RecyclerView.Adapter<JoinTheFanGroupAdapter.ItemHolder>{

    private List<JoinTheFanGroupBean> mList = new ArrayList<>();
    private Context mContext;


    public JoinTheFanGroupAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<JoinTheFanGroupBean> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JoinTheFanGroupAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new JoinTheFanGroupAdapter.ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_join_fans,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull JoinTheFanGroupAdapter.ItemHolder holder, final int position) {
        if (mList.get(position).getSrc() != null){
            ImgUtil.INSTANCE.loadRoundImg(mContext,mList.get(position).getSrc(), holder.faceIv,8f,-1);
            if (mList.get(position).getSrc().endsWith(".svga")){
                showSvgaGiftAnim(holder.itemView.getContext(),holder.svgaImageView,mList.get(position).getSrc());
            }
        }

        holder.nameTv.setText(mList.get(position).getName());

    }
    @Override
    public int getItemCount() {
        return mList.size();

    }

    static class ItemHolder extends RecyclerView.ViewHolder{
        private ImageView faceIv;
        private TextView nameTv;
        private SVGAImageView svgaImageView;
        ItemHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.tv_name);
            faceIv=itemView.findViewById(R.id.iv_face);
            svgaImageView = itemView.findViewById(R.id.iv_svga);
        }
    }

    private void showSvgaGiftAnim(Context context, final SVGAImageView svgaImageView, String url){
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
        try {
            parser.decodeFromURL(new URL(url), new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                    SVGADynamicEntity dynamicItem = new SVGADynamicEntity();
                    SVGADrawable drawable = new SVGADrawable(videoItem, dynamicItem);
                    svgaImageView.setImageDrawable(drawable);
                    svgaImageView.stepToFrame(0,true);
                }
                @Override
                public void onError() {
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
