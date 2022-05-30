package com.miaomi.fenbei.gift.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.gift.GiftManager;
import com.miaomi.fenbei.gift.R;
import com.miaomi.fenbei.base.bean.GiftBean;
import com.miaomi.fenbei.gift.listener.OnGiftItemClickListener;
import com.miaomi.fenbei.gift.widget.GiftImageView;

import java.util.ArrayList;
import java.util.List;

public class GiftAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<GiftBean.DataBean> datas = new ArrayList<>();

    public List<GiftBean.DataBean> getList() {
        return datas;
    }

    private OnGiftItemClickListener giftItemClickListener;
    private int tag;
//    private ValueAnimator animator;

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public GiftAdapter(Context mContext, List<GiftBean.DataBean> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    public void setGiftItemClickListener(OnGiftItemClickListener giftItemClickListener) {
        this.giftItemClickListener = giftItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return getTag();
    }


    //    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
//        if (payloads.isEmpty()) {
//            super.onBindViewHolder(holder, position, payloads);
//        } else {
//            if (holder instanceof ItemHolder) {
//                ItemHolder itemHolder = (ItemHolder) holder;
//                if (datas.get(position).isSelected()) {
//                    itemHolder.iconIv.start();
//                } else {
//                    itemHolder.iconIv.cancel();
//                }
//            }
//            if (holder instanceof NobleItemHolder) {
//                NobleItemHolder itemHolder = (NobleItemHolder) holder;
//                if (datas.get(position).isSelected()) {
//                    itemHolder.iconIv.start();
//                } else {
//                    itemHolder.iconIv.cancel();
//                }
//            }
//
//            if (holder instanceof ExpressItemHolder) {
//                ExpressItemHolder itemHolder = (ExpressItemHolder) holder;
//                if (datas.get(position).isSelected()) {
//                    itemHolder.iconIv.start();
//                } else {
//                    itemHolder.iconIv.cancel();
//                }
//            }
//        }
//    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == GiftManager.GIFT_TYPE_NOBLE) {
            return new GiftAdapter.NobleItemHolder(LayoutInflater.from(mContext).inflate(R.layout.gift_item_noble_gift, parent, false));
        }
        if (viewType == GiftManager.GIFT_TYPE_EXPRESS) {
            return new GiftAdapter.ExpressItemHolder(LayoutInflater.from(mContext).inflate(R.layout.gift_item_gift, parent, false));
        }
        if (viewType == GiftManager.GIFT_TYPE_COMMON_PRIVATE) {
            return new GiftAdapter.ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.gift_item_gift_private, parent, false));
        }
        return new GiftAdapter.ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.gift_item_gift, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemHolder) {
            bindItem((ItemHolder) holder, position);
        }
        if (holder instanceof NobleItemHolder) {
            bindNobleItem((NobleItemHolder) holder, position);
        }

        if (holder instanceof ExpressItemHolder) {
            bindExpressItem((ExpressItemHolder) holder, position);
        }

    }


    private void bindNobleItem(final NobleItemHolder holder, final int position) {
        if (datas.get(position).isLock()) {
            holder.lockFl.setVisibility(View.VISIBLE);
        } else {
            holder.lockFl.setVisibility(View.GONE);
        }
        if (datas.get(position).isSelected()) {
            holder.sendTv.setVisibility(View.VISIBLE);
            holder.contentTv.setVisibility(View.GONE);
        } else {
            holder.sendTv.setVisibility(View.GONE);
            holder.contentTv.setVisibility(View.VISIBLE);
        }
        holder.sendTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!datas.get(position).isLock()) {
                    if (giftItemClickListener != null) {
                        giftItemClickListener.onItemSend(datas.get(position));
                    }
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (giftItemClickListener != null) {
                    giftItemClickListener.onItemLongClick();
                }
                return false;
            }
        });
        holder.selectedFl.setSelected(datas.get(position).isSelected());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (GiftBean.DataBean bean : datas) {
                    bean.setSelected(false);
                }
                datas.get(position).setSelected(true);
                notifyDataSetChanged();
                if (giftItemClickListener != null) {
                    giftItemClickListener.onItemClick(datas.get(position));
                    giftItemClickListener.onRefresh();
                }
            }
        });
        if (datas.get(position).getLabel() != null) {
            holder.lableRv.setAdapter(new LabelAdapter(mContext, datas.get(position).getLabel()));
        }
        holder.contentTv.setText(datas.get(position).getName());
        ImgUtil.INSTANCE.loadGiftImg(mContext, datas.get(position).getIcon(), holder.iconIv);
        holder.pirceTv.setText(String.format("%s钻石", datas.get(position).getPrice()));
        holder.numberTv.setVisibility(View.GONE);
//        ImgUtil.INSTANCE.loadImg(mContext, datas.get(position).getNoble_label(), holder.rankIv);
    }

    private void bindExpressItem(final ExpressItemHolder holder, final int position) {
//        if (datas.get(position).isSelected()) {
//            holder.iconIv.start();
//        } else {
//            holder.iconIv.cancel();
//        }
        if (datas.get(position).isSelected()) {
            holder.sendTv.setVisibility(View.VISIBLE);
            holder.contentTv.setVisibility(View.GONE);
        } else {
            holder.sendTv.setVisibility(View.GONE);
            holder.contentTv.setVisibility(View.VISIBLE);
        }
        holder.sendTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (giftItemClickListener != null) {
                    giftItemClickListener.onItemSend(datas.get(position));
                }
            }
        });
        holder.selectedLl.setSelected(datas.get(position).isSelected());
        if (datas.get(position).getLabel() != null) {
            holder.lableRv.setAdapter(new LabelAdapter(mContext, datas.get(position).getLabel()));
        }
        holder.contentTv.setText(datas.get(position).getName());
        ImgUtil.INSTANCE.loadGiftImg(mContext, datas.get(position).getIcon(), holder.iconIv);
        holder.pirceTv.setText("" + datas.get(position).getPrice());
        if (tag == GiftManager.GIFT_TYPE_PACK) {
            if (datas.get(position).getNumber() == 0) {
                holder.numberTv.setText("送光了");
            } else {
                holder.numberTv.setVisibility(View.VISIBLE);
                holder.numberTv.setText(String.format("×%d", datas.get(position).getNumber()));
            }
        } else {
            holder.numberTv.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (giftItemClickListener != null) {
                    giftItemClickListener.onItemClick(datas.get(position));
                    giftItemClickListener.onRefresh();
                }
                for (GiftBean.DataBean bean : datas) {
                    bean.setSelected(false);
                }
                datas.get(position).setSelected(true);
                notifyDataSetChanged();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (giftItemClickListener != null) {
                    giftItemClickListener.onItemLongClick();
                }
                return false;
            }
        });
    }


    private void bindItem(final ItemHolder holder, final int position) {
//        if (datas.get(position).isSelected()) {
//            holder.iconIv.start();
//        } else {
//            holder.iconIv.cancel();
//        }
        if (datas.get(position).isSelected()) {
//            holder.iconIv.startAnimation();
            holder.sendTv.setVisibility(View.VISIBLE);
            holder.contentTv.setVisibility(View.GONE);
        } else {
            holder.sendTv.setVisibility(View.GONE);
            holder.contentTv.setVisibility(View.VISIBLE);
        }
        holder.sendTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (giftItemClickListener != null) {
                    giftItemClickListener.onItemSend(datas.get(position));
                }
            }
        });
        holder.selectedLl.setSelected(datas.get(position).isSelected());
        if (datas.get(position).getLabel() != null) {
            holder.lableRv.setAdapter(new LabelAdapter(mContext, datas.get(position).getLabel()));
        }
        holder.contentTv.setText(datas.get(position).getName());
        ImgUtil.INSTANCE.loadGiftImg(mContext, datas.get(position).getIcon(), holder.iconIv);
        holder.pirceTv.setText(datas.get(position).getPrice() + "钻石");
        if (tag == GiftManager.GIFT_TYPE_PACK) {
            if (datas.get(position).getNumber() == 0) {
                holder.numberTv.setText("送光了");
            } else {
                holder.numberTv.setVisibility(View.VISIBLE);
                holder.numberTv.setText(String.format("×%d", datas.get(position).getNumber()));
            }
        } else {
            holder.numberTv.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (giftItemClickListener != null) {
                    giftItemClickListener.onItemClick(datas.get(position));
                    giftItemClickListener.onRefresh();
                }
                for (GiftBean.DataBean bean : datas) {
                    bean.setSelected(false);
                }
                datas.get(position).setSelected(true);
                notifyDataSetChanged();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (giftItemClickListener != null) {
                    giftItemClickListener.onItemLongClick();
                }
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    static class ItemHolder extends RecyclerView.ViewHolder {
        TextView contentTv;
        TextView pirceTv;
        GiftImageView iconIv;
        LinearLayout selectedLl;
        TextView numberTv;
        RecyclerView lableRv;
        TextView sendTv;

        ItemHolder(View itemView) {
            super(itemView);
            pirceTv = itemView.findViewById(R.id.tv_price);
            contentTv = itemView.findViewById(R.id.tv_content);
            iconIv = itemView.findViewById(R.id.iv_icon);
            selectedLl = itemView.findViewById(R.id.ll_content);
            numberTv = itemView.findViewById(R.id.tv_number);
            lableRv = itemView.findViewById(R.id.rv_label);
            sendTv = itemView.findViewById(R.id.tv_send);
            lableRv.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }
    }

//    static class LuckItemHolder extends RecyclerView.ViewHolder {
//        TextView contentTv;
//        TextView pirceTv;
//        GiftImageView iconIv;
//        LinearLayout selectedFl;
//        TextView numberTv;
//        RecyclerView lableRv;
//        ImageView dobleClickIv;
//        LuckItemHolder(View itemView) {
//            super(itemView);
//            pirceTv = itemView.findViewById(R.id.tv_price);
//            contentTv = itemView.findViewById(R.id.tv_content);
//            iconIv = itemView.findViewById(R.id.iv_icon);
//            selectedFl = itemView.findViewById(R.id.ll_content);
//            numberTv = itemView.findViewById(R.id.tv_number);
//            lableRv = itemView.findViewById(R.id.rv_label);
//            dobleClickIv = itemView.findViewById(R.id.iv_double_click);
//            lableRv.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
//        }
//    }

    static class NobleItemHolder extends RecyclerView.ViewHolder {
        TextView contentTv;
        TextView pirceTv;
        GiftImageView iconIv;
        LinearLayout selectedFl;
        TextView numberTv;
        RecyclerView lableRv;
        //        ImageView rankIv;
        FrameLayout lockFl;
        TextView sendTv;

        NobleItemHolder(View itemView) {
            super(itemView);
            pirceTv = itemView.findViewById(R.id.tv_price);
            contentTv = itemView.findViewById(R.id.tv_content);
            iconIv = itemView.findViewById(R.id.iv_icon);
            selectedFl = itemView.findViewById(R.id.ll_content);
            numberTv = itemView.findViewById(R.id.tv_number);
            lableRv = itemView.findViewById(R.id.rv_label);
            lockFl = itemView.findViewById(R.id.fl_lock);
//            rankIv = itemView.findViewById(R.id.iv_rank);
            sendTv = itemView.findViewById(R.id.tv_send);
            lableRv.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }
    }

    static class ExpressItemHolder extends RecyclerView.ViewHolder {
        TextView contentTv;
        TextView pirceTv;
        GiftImageView iconIv;
        LinearLayout selectedLl;
        TextView numberTv;
        RecyclerView lableRv;
        TextView sendTv;

        ExpressItemHolder(View itemView) {
            super(itemView);
            pirceTv = itemView.findViewById(R.id.tv_price);
            contentTv = itemView.findViewById(R.id.tv_content);
            iconIv = itemView.findViewById(R.id.iv_icon);
            selectedLl = itemView.findViewById(R.id.ll_content);
            numberTv = itemView.findViewById(R.id.tv_number);
            lableRv = itemView.findViewById(R.id.rv_label);
            sendTv = itemView.findViewById(R.id.tv_send);
            lableRv.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }
    }

}
