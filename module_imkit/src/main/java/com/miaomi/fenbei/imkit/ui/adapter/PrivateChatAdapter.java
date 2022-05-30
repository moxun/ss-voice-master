package com.miaomi.fenbei.imkit.ui.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.miaomi.fenbei.base.bean.event.C2CMsgBean;
import com.miaomi.fenbei.imkit.R;
import com.miaomi.fenbei.imkit.ui.viewholder.message.MsgBaseHolder;
import com.miaomi.fenbei.imkit.ui.viewholder.message.MsgEmojiHolder;
import com.miaomi.fenbei.imkit.ui.viewholder.message.MsgGiftHolder;
import com.miaomi.fenbei.imkit.ui.viewholder.message.MsgHelloTextHolder;
import com.miaomi.fenbei.imkit.ui.viewholder.message.MsgImageHolder;
import com.miaomi.fenbei.imkit.ui.viewholder.message.MsgRedEnvelopeHolder;
import com.miaomi.fenbei.imkit.ui.viewholder.message.MsgSoundHolder;
import com.miaomi.fenbei.imkit.ui.viewholder.message.MsgTextHolder;
import com.miaomi.fenbei.imkit.ui.viewholder.message.MsgUnSupportHolder;

import java.util.List;

public class PrivateChatAdapter extends RecyclerView.Adapter<MsgBaseHolder>{
    private Context context;
    private List<C2CMsgBean> list;


    public PrivateChatAdapter(Context context, List<C2CMsgBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MsgBaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == C2CMsgBean.Text){
            return new MsgTextHolder(LayoutInflater.from(context).inflate(R.layout.chatting_item_chat_c2c_text,parent,false));
        }
        if (viewType == C2CMsgBean.Image){
            return new MsgImageHolder(LayoutInflater.from(context).inflate(R.layout.chatting_item_chat_c2c_image,parent,false));
        }
        if (viewType == C2CMsgBean.Sound){
            return new MsgSoundHolder(LayoutInflater.from(context).inflate(R.layout.chatting_item_chat_c2c_sound,parent,false));
        }
        if (viewType == C2CMsgBean.Emoji){
            return new MsgEmojiHolder(LayoutInflater.from(context).inflate(R.layout.chatting_item_chat_c2c_emoji,parent,false));
        }
        if (viewType == C2CMsgBean.CustomGift){
            return new MsgGiftHolder(LayoutInflater.from(context).inflate(R.layout.chatting_item_chat_c2c_gift,parent,false));
        }
        if (viewType == C2CMsgBean.CustomHello){
            return new MsgHelloTextHolder(LayoutInflater.from(context).inflate(R.layout.chatting_item_chat_c2c_text,parent,false));
        }
        if (viewType == C2CMsgBean.CustomRedEnvelope){
            return new MsgRedEnvelopeHolder(LayoutInflater.from(context).inflate(R.layout.chatting_item_chat_c2c_red_envelope,parent,false));
        }
        return new MsgUnSupportHolder(LayoutInflater.from(context).inflate(R.layout.chatting_item_chat_c2c_text,parent,false));
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getMsgType() == C2CMsgBean.Custom){
            if (list.get(position).getCustomBean() == null){
                return C2CMsgBean.Custom;
            }else{
                return list.get(position).getCustomBean().getRealMsgType();
            }
        }else{
            return list.get(position).getMsgType();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MsgBaseHolder holder, int position) {
        holder.setOnItemOprateListener(onItemOprateListener);
        holder.bindData(getListItem(position-1),getListItem(position));
    }

    public MsgBaseHolder.OnItemOprateListener onItemOprateListener;
    public void setOnItemOprateListener(MsgBaseHolder.OnItemOprateListener onItemOprateListener){
        this.onItemOprateListener = onItemOprateListener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private C2CMsgBean getListItem(int position){
        if (position == -1){
            return null;
        }else{
            return list.get(position);
        }
    }

}
