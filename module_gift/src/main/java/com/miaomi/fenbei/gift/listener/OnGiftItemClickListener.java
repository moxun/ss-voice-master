package com.miaomi.fenbei.gift.listener;

import com.miaomi.fenbei.base.bean.GiftBean;

public interface OnGiftItemClickListener {
    void onItemClick(GiftBean.DataBean bean);
    void onRefresh();
    void onItemSend(GiftBean.DataBean bean);
    void onItemLongClick();
}
