package com.miaomi.fenbei.base.bean;

import java.util.ArrayList;
import java.util.List;

public class GiftListInfo {

    private List<GiftBean.DataBean> giftList = new ArrayList<>();

    public List<GiftBean.DataBean> getList() {
        return giftList;
    }

    public void saveGiftList(List<GiftBean.DataBean> list){
        if (list.size()>0){
            list.get(0).setSelected(true);
        }
        giftList.clear();
        giftList.addAll(list);
    }


    public void resetGiftList(){
        if (giftList == null || giftList.size() == 0){
            return;
        }
        for (int i = 0;i<giftList.size();i++){
            if (i == 0){
                giftList.get(0).setSelected(true);
            }else{
                giftList.get(i).setSelected(false);
            }
        }
    }

    public void setSelectedGift(GiftBean.DataBean bean){
        for (GiftBean.DataBean item : giftList){
            if (item.getId() == bean.getId()){
                item.setSelected(true);
            }else{
                item.setSelected(false);
            }
        }
    }

    public GiftBean.DataBean getSelectedGift(){
        for (GiftBean.DataBean item : giftList){
            if (item.isSelected()){
                return item;
            }
        }
        return null;
    }
}
