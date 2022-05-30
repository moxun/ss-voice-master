package com.miaomi.fenbei.imkit.ui.conversation;

import com.miaomi.fenbei.base.bean.event.ConversationBean;
import com.miaomi.fenbei.base.core.msg.MsgManager;
import com.miaomi.fenbei.imkit.listener.OnConversationDeleteListener;
import com.miaomi.fenbei.imkit.R;
import com.miaomi.fenbei.imkit.ui.viewholder.conversation.CommonConversationViewHolder;
import com.miaomi.fenbei.imkit.ui.viewholder.conversation.StrangerViewHolder;
import com.miaomi.fenbei.imkit.ui.viewholder.conversation.SystemViewHolder;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ConversationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ConversationBean> list;
    private Context context;
    private OnConversationDeleteListener onDeleteListener;


    public ConversationAdapter(List<ConversationBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        //是否是系统消息
        if (list.get(position).getUser_id().equals(MsgManager.INSTANCE.getSystemUid())){
            return 1;
        }else{
            //陌生人消息
            if (list.get(position).getUser_id().equals("-1")){
                return 2;

            }else{
                return 0;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1){
            return new SystemViewHolder(LayoutInflater.from(context).inflate(R.layout.user_item_conversation_system, parent, false));
        }
        if (viewType == 2){
            return new StrangerViewHolder(LayoutInflater.from(context).inflate(R.layout.user_item_conversation_stranger, parent, false));
        }
        if (viewType == 0){
            return new CommonConversationViewHolder(LayoutInflater.from(context).inflate(R.layout.user_item_conversation_common, parent, false));
        }
        return new CommonConversationViewHolder(LayoutInflater.from(context).inflate(R.layout.user_item_conversation_common, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
       if (holder instanceof SystemViewHolder){
           ((SystemViewHolder) holder).bindData(list.get(position));
//           ((SystemViewHolder) holder).setOnDeleteListener(onDeleteListener);
       }else if (holder instanceof CommonConversationViewHolder){
           ((CommonConversationViewHolder) holder).bindData(list.get(position));
           ((CommonConversationViewHolder) holder).setOnDeleteListener(onDeleteListener);
       }else if (holder instanceof StrangerViewHolder){
           ((StrangerViewHolder) holder).bindData(list.get(position));
//           ((StrangerViewHolder) holder).setOnDeleteListener(onDeleteListener);
       }
    }



    @Override
    public int getItemCount() {
        return list.size();
    }



    public void setOnDeleteListener(OnConversationDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }
}
