package com.miaomi.fenbei.voice.ui.pyq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miaomi.fenbei.base.bean.CommentBean;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.TimeUtil;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;


public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ItemHodler> {
    private Context context;
    private List<CommentBean> list = new ArrayList<>();
    private Boolean is_own;
    public CommentListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CommentBean> data,Boolean is_own){
        list.clear();
        this.is_own=is_own;
        list.addAll(data);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHodler(LayoutInflater.from(context).inflate(R.layout.item_comment_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentListAdapter.ItemHodler holder, final int position) {
        holder.usernameTv.setText(""+list.get(position).getUser_name());
        holder.contentTv.setText(list.get(position).getComment());
        if(!list.get(position).getHf_user_name().equals("0")){
            holder.replyTv.setVisibility(View.VISIBLE);
            holder.hfusernameTv.setVisibility(View.VISIBLE);
            holder.hfusernameTv.setText(""+list.get(position).getHf_user_name()+": ");
        }else {
            holder.hfusernameTv.setVisibility(View.GONE);
            holder.replyTv.setVisibility(View.GONE);
        }
        holder.timeTv.setText(""+ TimeUtil.getDayTime(list.get(position).getCreate_time()));
        ImgUtil.INSTANCE.loadFaceIcon(holder.itemView.getContext(),list.get(position).getFace(),holder.avterIv);
        if(is_own){
            holder.delTv.setVisibility(View.VISIBLE);
        }else{
         if(DataHelper.INSTANCE.getUID()==list.get(position).getSend_user_id()){
             holder.delTv.setVisibility(View.VISIBLE);
            }else{
             holder.delTv.setVisibility(View.GONE);
         }
        }
      holder.contentTv.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (onCommentClickListener != null){
                  onCommentClickListener.onReply(list.get(position).getId());
              }
          }
      });
      holder.delTv.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (onCommentClickListener != null){
                  onCommentClickListener.onDel(list.get(position).getId());
              }
          }
      });
    }
    OnCommentClickListener onCommentClickListener;

    public void setOnOnCommentClickListener(OnCommentClickListener onCommentClickListener) {
        this.onCommentClickListener = onCommentClickListener;
    }

    public interface OnCommentClickListener{
        void onReply(int uid);
        void onDel(int uid);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ItemHodler extends RecyclerView.ViewHolder{
          ImageView avterIv;
          TextView  usernameTv;
          TextView  replyTv;
          TextView  hfusernameTv;
          TextView  contentTv;
          TextView  timeTv;
          TextView delTv;
        public ItemHodler(View itemView) {
            super(itemView);
            avterIv = itemView.findViewById(R.id.iv_avter);
            usernameTv = itemView.findViewById(R.id.tv_user_name);
            hfusernameTv = itemView.findViewById(R.id.tv_hf_user_name);
            timeTv = itemView.findViewById(R.id.tv_time);
            contentTv = itemView.findViewById(R.id.tv_content);
            replyTv = itemView.findViewById(R.id.tv_reply);
            delTv=itemView.findViewById(R.id.tv_del);

        }
    }
}
