package com.miaomi.fenbei.base.bean;

import java.util.List;

public class UserCardBean extends UserInfo{


    /**
     * nickname : 测试
     * gender : 1
     * age : 18
     * face : http://im-file-oss.miaorubao.com/im_test/img/avatar/1002511618841240058.jpg
     * city :
     * user_id : 100251
     * signature : 新人驾到,欢迎来撩
     * good_number : 100251
     * seat_frame :
     * effects : 162
     * love : 0
     * mystery : 0
     * good_number_state : 0
     * is_follow : 1
     * type : 0
     * user_room_id : 123466
     * user_local_room_id : 123466
     * speak : 0
     * status : 0
     * fans_number : 4
     * user_role : 2
     * super_manager : 0
     * charm_level : {"icon":"http://im-file-oss.94miao.com/im_test/img/avatar/1567676109_556.png","grade":14}
     * wealth_level : {"icon":"http://im-file-oss.94miao.com/im_test/img/avatar/1567490934_252.png","grade":12}
     * recharge_residue : 199585782
     * is_guard : -1
     * medal : http://im-file-oss.miaorubao.com/im/img/avatar/1613637714_790.png
     * seat : http://im-file-oss.miaorubao.com/im/img/avatar/1603037207_896.svga
     * noble_name : 幻神
     * rank_id : 14
     * gift_cat_count : 20
     * star_count : 60
     * three_gift : [{"id":91,"name":"我们结婚吧","icon":"http://im-file-oss.miaorubao.com/im_test/img/gift_icon/1619580449_750.png","price":52000,"num":1},{"id":73,"name":"酒心巧克力","icon":"http://im-file-oss.miaorubao.com/im_test/img/gift_icon/1619580312_963.png","price":5200,"num":1},{"id":152,"name":"七彩美人鱼","icon":"http://im-file-oss.miaorubao.com/im_test/img/gift_icon/1619509383_700.png","price":5200,"num":1}]
     * lecturer :
     */

    private int gift_cat_count;
    private int star_count;
    private List<GiftWallBean.ListBean> three_gift;

    public int getGift_cat_count() {
        return gift_cat_count;
    }

    public void setGift_cat_count(int gift_cat_count) {
        this.gift_cat_count = gift_cat_count;
    }

    public int getStar_count() {
        return star_count;
    }

    public void setStar_count(int star_count) {
        this.star_count = star_count;
    }

    public List<GiftWallBean.ListBean> getThree_gift() {
        return three_gift;
    }

    public void setThree_gift(List<GiftWallBean.ListBean> three_gift) {
        this.three_gift = three_gift;
    }
}
