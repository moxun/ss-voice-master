package com.miaomi.fenbei.base.net

import android.content.Context
import android.util.Log
import androidx.collection.ArrayMap
import com.miaomi.fenbei.base.bean.UserDividedBean
import com.miaomi.fenbei.base.bean.*
import com.miaomi.fenbei.base.bean.db.MusicBean
import com.miaomi.fenbei.base.bean.event.ConversationBean
import com.miaomi.fenbei.base.util.DataHelper
import com.miaomi.fenbei.base.util.DataHelper.getUID
import com.miaomi.fenbei.base.util.LogUtil
import com.miaomi.fenbei.base.util.SignUtil

class NetService private constructor(context: Context) {

    companion object {
        @Volatile
        private var instance: NetService? = null

        fun getInstance(context: Context): NetService {
            if (instance == null) {
                synchronized(NetService::class) {
                    if (instance == null) {
                        instance = NetService(context)
                    }
                }
            }
            return instance!!
        }
    }


    private var mService: IService = BaseService.getInstance(context).mRetrofit.create(IService::class.java)

    private val callback = object : Callback<BaseBean>() {
        override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {

        }

        override fun onError(msg: String, throwable: Throwable, code: Int) {
        }

        override fun isAlive(): Boolean {
            return true
        }

    }

    fun postLoginLog() {
        val keyMap = ArrayMap<String, String>()
        mService.postLoginLog(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun addBlack(userId: String, roomId: String, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["user_id"] = userId
        keyMap["room_id"] = roomId
        mService.addBlack(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getRankRoomList(type: Int, callback: Callback<RankRoomBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["type"] = type.toString()
        mService.getRankRoomList(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getEmojiList(callback: Callback<List<EmojiGroupBean>>) {
        val keyMap = ArrayMap<String, String>()
        mService.getEmojiList(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getPersonEmojiList(callback: Callback<List<EmojiGroupBean>>) {
        val keyMap = ArrayMap<String, String>()
        mService.getPersonEmojiList(SignUtil.sign(keyMap)).enqueue(callback)
    }


//    fun imDevelop(callback: Callback<IMDevelopBean>){
//        val keyMap = ArrayMap<String, String>()
//        mService.imDevelop(SignUtil.sign(keyMap)).enqueue(callback)
//    }
//
//    fun getConversation(userIds: String, callBack: Callback<List<ConversationBean>>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["user_ids"] = userIds
//        val call = mService.getConversation(SignUtil.sign(keyMap))
//        call.enqueue(callBack)
//    }
    /**
     * 获取聊天人信息
     */
    fun getConversationUserInfo(id: String, callBack: Callback<List<ConversationBean>>) {
        val users = ArrayList<String>()
        users.add(id)
        val keyMap = ArrayMap<String, String>()
        keyMap["user_ids"] = users.toString()
        val call = mService.getConversation(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }


    /**
     * 获取聊天人信息
     */
    fun getConversationUserInfos(id: String, callBack: Callback<List<ConversationBean>>) {
//        val users = ArrayList<String>()
//        users.add(id)
        val keyMap = ArrayMap<String, String>()
        keyMap["user_ids"] = id
        val call = mService.getConversation(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    /**
     * 关注
     */
    fun addConcern(token: String, followerId: String, callBack: Callback<FollowBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["token"] = token
        keyMap["follower_id"] = followerId
        val call = mService.addConcern(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

//    /**
//     * 关注
//     */
//    fun addConcern(roomId:String,token: String, followerId: String, callBack: Callback<FollowBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["room_id"] = roomId
//        keyMap["token"] = token
//        keyMap["follower_id"] = followerId
//        val call = mService.addConcern(SignUtil.sign(keyMap))
//        call.enqueue(callBack)
//    }

//    /**
//     * 分享任务上报
//     */
//    fun upLoadShare(room_id:String,callback: Callback<AppearBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["room_id"] = room_id
//        mService.upLoadShare(SignUtil.sign(keyMap)).enqueue(callback)
//    }


    fun isBlack(userId: String, callBack: Callback<IsBlackBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["user_id"] = userId
        val call = mService.isBlack(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }


    fun login(face: String, pwd: String, phone: String, vCode: String, type: Int, openId: String, unionId: String, sex: String, nickname: String, callback: Callback<MineBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["face"] = face
        keyMap["username"] = phone
        keyMap["verify_code"] = vCode
        keyMap["type"] = type.toString()
        keyMap["openid"] = openId
        keyMap["unionid"] = unionId
        keyMap["gender"] = sex
        keyMap["nickname"] = nickname
        keyMap["pwd"] = pwd
        mService.login(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun loginOneKey(dhChannerl: String, dhPlatform: String, dhToken: String, phoneNum: String, callback: Callback<MineBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["dh_channel"] = dhChannerl
        keyMap["dh_platform"] = dhPlatform
        keyMap["dh_token"] = dhToken
        keyMap["type"] = "5"
        keyMap["phone_num"] = phoneNum
        mService.login(SignUtil.sign(keyMap)).enqueue(callback)
    }

//    fun loginOnly(face:String,pwd:String,phone:String,vCode:String,type:Int,openId:String,unionId:String,sex:String,nickname:String, only: String = "", callback: Callback<LoginBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["face"] = face
//        keyMap["username"] = phone
//        keyMap["verify_code"] = vCode
//        keyMap["type"] = type.toString()
//        keyMap["openid"] = openId
//        keyMap["unionid"] = unionId
//        keyMap["gender"] = sex
//        keyMap["nickname"] = nickname
//        keyMap["pwd"] = pwd
//        keyMap["only"] = only
//        mService.login(SignUtil.sign(keyMap)).enqueue(callback)
//    }


    fun sendCode(type: Int, phone: String, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["mobile"] = phone
        keyMap["type"] = type.toString()
        mService.sendCode(SignUtil.sign(keyMap)).enqueue(callback)

    }

    fun getHotMusicList(page: Int, callback: Callback<List<MusicBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["page"] = page.toString()
        mService.getHotMusicList(SignUtil.sign(keyMap)).enqueue(callback)

    }


    fun searcheHotMusic(keyword: String, callback: Callback<HotMusicSearchBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["keyword"] = keyword
        mService.searcheHotMusic(SignUtil.sign(keyMap)).enqueue(callback)

    }


    fun getUserUploadMusic(callback: Callback<List<MusicBean>>) {
        val keyMap = ArrayMap<String, String>()
        mService.getUserUploadMusic(SignUtil.sign(keyMap)).enqueue(callback)

    }


    fun addMusic(
        url: String, name: String, size: String, singer: String, uploader: String,
        callback: Callback<BaseBean>
    ) {
        val keyMap = ArrayMap<String, String>()
        keyMap["url"] = url
        keyMap["name"] = name
        keyMap["size"] = size
        keyMap["singer"] = singer
        keyMap["uploader"] = uploader
        mService.addMusic(SignUtil.sign(keyMap)).enqueue(callback)

    }

    fun getMineInfo(pkg: String, callBack: Callback<MineBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap.put("pkg", pkg)
        val call = mService.getMineInfo(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun changePwd(pwdOld: String, pwd: String, rePwd: String, callBack: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["pwd"] = pwd
        keyMap["old_pwd"] = pwdOld
        keyMap["new_pwd"] = pwd
        keyMap["repeat_pwd"] = rePwd
        val call = mService.changePwd(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun findPwd(userId: String, mobile: String, code: String, pwd: String, rePwd: String, callBack: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["user_id"] = userId
        keyMap["mobile"] = mobile
        keyMap["pwd"] = pwd
        keyMap["verify_code"] = code
        keyMap["new_pwd"] = pwd
        keyMap["repeat_pwd"] = rePwd
        val call = mService.findPwd(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

//    fun verilyCode(phone:String,vCode:String,callBack: Callback<ValidaBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["mobile"] = phone
//        keyMap["verify_code"] = vCode
//        val call = mService.verilyCode(SignUtil.SignUtil.sign(keyMap))
//        call.enqueue(callBack)
//    }

    fun getRankList(type: Int, callback: Callback<List<RankBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["type"] = type.toString()
        mService.getRankList(SignUtil.sign(keyMap)).enqueue(callback)
    }

//    fun getRadioList(type: Int, callback: Callback<RoomRadioCharmRankBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["type"] = type.toString()
//        mService.getRadioList(SignUtil.sign(keyMap)).enqueue(callback)
//    }


    //搜索
    fun search(page: Int, keyword: String, type: Int, callback: Callback<List<SearchItemBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["page"] = page.toString()
        keyMap["type"] = type.toString()
        keyMap["keyword"] = keyword
        mService.search(SignUtil.sign(keyMap)).enqueue(callback)
    }

    //搜索房间
    fun searchFamily(page: Int, keyword: String, callback: Callback<List<FamilyBean.ListBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["page"] = page.toString()
        keyMap["keyword"] = keyword
        mService.searchFamily(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun aliPay(price: String, payMoney: String, tradeName: String, callback: Callback<AliPayBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["price"] = price
        keyMap["pay_money"] = payMoney
        keyMap["pay_type"] = "1"
        keyMap["trade_name"] = tradeName
        mService.aliPay(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getDiamonds(callback: Callback<DiamondsBean>) {
        val keyMap = ArrayMap<String, String>()
        mService.getDiamonds(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getGoodsList(callback: Callback<List<GoodsBean>>) {
        val keyMap = ArrayMap<String, String>()
        mService.getGoodsList(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun wechatPay(appid: String, price: String, payMoney: String, tradeName: String, callback: Callback<WechatPayBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["price"] = price
        keyMap["pay_money"] = payMoney
        keyMap["pay_type"] = "2"
        keyMap["trade_name"] = tradeName
        keyMap["app_id"] = appid
        mService.wechatPay(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun wxMiniPay(payMoney: String, tradeName: String, callback: Callback<WxMiniPayBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["pay_money"] = payMoney
        keyMap["pay_type"] = "7"
        keyMap["trade_name"] = tradeName
        mService.wxMiniPay(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun wxNewMiniPay(payMoney: String, callback: Callback<WxMiniPayBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["pay_money"] = payMoney
//        keyMap["pay_type"] = "7"
//        keyMap["trade_name"] = tradeName
        mService.wxNewMiniPay(SignUtil.sign(keyMap)).enqueue(callback)
    }


    fun getPayWay(callback: Callback<WxPayWayBean>) {
        val keyMap = ArrayMap<String, String>()
        mService.getPayWay(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun payHistory(page: Int, callback: Callback<List<PayHistroyBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["page"] = page.toString()
        mService.payHistory(SignUtil.sign(keyMap)).enqueue(callback)
    }

//    fun othetIncome(page:Int,callback: Callback<List<PayHistroyBean>>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["page"] = page.toString()
//        mService.othetIncome(SignUtil.sign(keyMap)).enqueue(callback)
//    }


    fun getBanner(type: String, callback: Callback<List<BannerBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["type"] = type
        mService.getBanner(SignUtil.sign(keyMap)).enqueue(callback)
    }

    //获取家族列表
    fun getFamilyList(type: Int, callback: Callback<FamilyBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["type"] = type.toString()
        mService.getFamilyList(SignUtil.sign(keyMap)).enqueue(callback)
    }

//    //创建家族
//    fun createFamily(fmName: String,icon: String,wechat: String,before_platform: String,num: String, callback: Callback<Any>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["family_name"] = fmName
//        keyMap["icon"] = icon
//        keyMap["wechat"] = wechat
//        keyMap["num"] = num
//        keyMap["before_platform"] = before_platform
//        mService.createFamily(SignUtil.sign(keyMap)).enqueue(callback)
//    }

    //家族信息
    fun getFamilyInfo(fmId: String, callback: Callback<FamilyCenterInfoBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["family_id"] = fmId
        mService.getFamilyInfo(SignUtil.sign(keyMap)).enqueue(callback)
    }

    //家族查询比例信息查询
    fun getFamilyDivided_Type(fmId: String, callback: Callback<FamilyProportionBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["family_id"] = fmId
        mService.getFamilyDivided_Type(SignUtil.sign(keyMap)).enqueue(callback)
    }

    //家族设置比例
    fun getFamily_Checkout_Divided_Type(fmId: String, divided_type: Int, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["family_id"] = fmId
        keyMap["divided_type"] = divided_type.toString()
        mService.getFamily_Checkout_Divided_Type(SignUtil.sign(keyMap)).enqueue(callback)
    }

    //家族全部用户设置比例
    fun getFamily_Split_Into_All(fmId: String, divided_type: Int, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["family_id"] = fmId
        keyMap["divided"] = divided_type.toString()
        mService.getFamily_Split_Into_All(SignUtil.sign(keyMap)).enqueue(callback)
    }

    //家族查询全部比例信息查询
    fun getFamilyDivided(fmId: String, callback: Callback<FamilyProportionBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["family_id"] = fmId
        mService.getFamilyDivided(SignUtil.sign(keyMap)).enqueue(callback)
    }
    //家族查询部分比例信息查询

    fun getFamily_User_Divided(fmId: String, callback: Callback<UserDividedBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["family_id"] = fmId
        mService.getFamily_User_Divided(SignUtil.sign(keyMap)).enqueue(callback)
    }

    //家族查询部分比例信息列表
    fun getFamilyUserDividedDetail(fmId: String, divided: Int, keyword: String, callback: Callback<DividedUserBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["family_id"] = fmId
        keyMap["divided"] = divided.toString()
        keyMap["keyword"] = keyword
        mService.getFamilyUserDividedDetail(SignUtil.sign(keyMap)).enqueue(callback)
    }

    //成员比例单独分配（不包含当前比例）添加成员
    fun getFamilyAddDividedUser(fmId: String, divided: Int, keyword: String, callback: Callback<DividedUserBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["family_id"] = fmId
        keyMap["divided"] = divided.toString()
        keyMap["keyword"] = keyword
        mService.getFamilyAddDividedUser(SignUtil.sign(keyMap)).enqueue(callback)
    }

    //家族成员
    fun getFamilyMemberList(fmId: String, keyword: String, callback: Callback<List<FamilyMemberBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["family_id"] = fmId
        keyMap["keyword"] = keyword
        mService.getFamilyMemberList(SignUtil.sign(keyMap)).enqueue(callback)
    }

    //我的家族
    fun meFamily(callback: Callback<MeFamilyBean>) {
        val keyMap = ArrayMap<String, String>()
        mService.meFamily(SignUtil.sign(keyMap)).enqueue(callback)
    }

    //推荐家族
    fun RecommedFamily(callback: Callback<FamilyBean>) {
        val keyMap = ArrayMap<String, String>()
        mService.recommedFamily(SignUtil.sign(keyMap)).enqueue(callback)
    }

    //推荐好友
    fun RoomRandList(callback: Callback<List<RoomListBean.DataBean>>) {
        val keyMap = ArrayMap<String, String>()
        mService.RoomRandList(SignUtil.sign(keyMap)).enqueue(callback)
    }

    //家族富豪魅力排行
    fun getFamilyRankList(type: Int, fmId: String, callback: Callback<List<FamilyRankBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["family_id"] = fmId
        keyMap["type"] = type.toString()
        mService.getFamilyRankList(SignUtil.sign(keyMap)).enqueue(callback)
    }

    //
//家族房间
    fun getFamilyRoomList(fmId: String, keyword: String, callback: Callback<List<FamilyCenterInfoBean.RoomInfoBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["family_id"] = fmId
        keyMap["keyword"] = keyword
        mService.getFamilyRoomList(SignUtil.sign(keyMap)).enqueue(callback)
    }

    //退出家族
    fun outFamily(uid: String, callback: Callback<Any>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["user_id"] = uid
        mService.outFamily(SignUtil.sign(keyMap)).enqueue(callback)
    }

    //申请退出家族
    fun outFamilyApply(callback: Callback<Any>) {
        val keyMap = ArrayMap<String, String>()
        mService.outFamilyApply(SignUtil.sign(keyMap)).enqueue(callback)
    }

    //申请解散家族
    fun dissolutionFamilyApply(fmId: String, callback: Callback<Any>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["family_id"] = fmId
        mService.dissolutionFamilyApply(SignUtil.sign(keyMap)).enqueue(callback)
    }
//    //编辑家族
//    fun editFamily(fmId: String,fmName: String,icon: String,note: String, callback: Callback<Any>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["family_id"] = fmId
//        keyMap["family_name"] = fmName
//        keyMap["icon"] = icon
//        keyMap["note"] = note
//        mService.editFamily(SignUtil.sign(keyMap)).enqueue(callback)
//    }

    //加入家族
    fun joinFamily(familyId: String, callback: Callback<Any>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["family_id"] = familyId
        mService.joinFamily(SignUtil.sign(keyMap)).enqueue(callback)
    }

//    //获取申请列表
//    fun getApplyList(familyId:String, callback: Callback<List<ApplyListBean>>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["family_id"] = familyId
//        mService.getApplyList(SignUtil.SignUtil.sign(keyMap)).enqueue(callback)
//    }

//    //家族操作 0 添加删除管理员-1踢出-2同意加入-3拒绝加入
//    fun operateFamily(fmId: String,usrId: String,type: String, callback: Callback<Any>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["family_id"] = fmId
//        keyMap["user_id"] = usrId
//        keyMap["type"] = type
//        mService.operateFamily(SignUtil.sign(keyMap)).enqueue(callback)
//    }


    fun getBalanceHistory(callBack: Callback<List<BalanceHistoryBean>>) {
        val keyMap = ArrayMap<String, String>()
        val call = mService.getBalanceHistory(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun submitFeedback(token: String, note: String, mobile: String, callBack: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["token"] = token
        keyMap["note"] = note
        keyMap["mobile"] = mobile
        val call = mService.submitFeedback(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun bindPhone(token: String, code: String, mobile: String, callBack: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["token"] = token
        keyMap["verify_code"] = code
        keyMap["mobile"] = mobile
        val call = mService.bindPhone(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun getCode(mobile: String, type: Int, callBack: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["mobile"] = mobile
        keyMap["type"] = type.toString() + ""
        val call = mService.sendCode(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    //             name|真实姓名
    //             idcard|身份证号码
    //             type|提现方式 1支付宝 2银行卡
    //             bank_account|银行卡号
    //             bank_name|开户行
    //             bank_address|开户行支行
    //             alipay_account|支付宝账号
    fun submitAuthentication(card_in_hand: String, idcard_front: String, idcard_reverse: String, name: String, idcard: String, cellphone: String, code: String, callBack: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["name"] = name
        keyMap["idcard"] = idcard
        keyMap["cellphone"] = cellphone
        keyMap["code"] = code
        keyMap["idcard_front"] = idcard_front
        keyMap["idcard_reverse"] = idcard_reverse
        keyMap["card_in_hand"] = card_in_hand
        val call = mService.submitAuthentication(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun bindAliAccount(name: String, alipay_account: String, callBack: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["name"] = name
        keyMap["alipay_account"] = alipay_account
        val call = mService.bindAliAccount(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }


//    fun getMineInfo(pkg: String, token: String, callBack: Callback<MineBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["token"] = token
//        keyMap["pkg"] = pkg
//        val call = mService.getMineInfo(SignUtil.SignUtil.sign(keyMap))
//        call.enqueue(callBack)
//    }

    fun editInfo(bean: MineBean, callBack: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["nickname"] = bean.nickname
        keyMap["gender"] = bean.gender.toString()
        keyMap["birth"] = bean.birth
        keyMap["city"] = bean.city
        keyMap["signature"] = bean.signature
        keyMap["face"] = bean.face
        val call = mService.editInfo(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun pecfectInfo(nickname: String, gender: Int, birth: String, user_code: String, face: String, callBack: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["nickname"] = nickname
        keyMap["gender"] = gender.toString()
        keyMap["birth"] = birth
        keyMap["user_code"] = user_code
        keyMap["face"] = face
        val call = mService.pecfectInfo(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }


    fun getCashOutInfo(token: String, callBack: Callback<CashOutBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["token"] = token
        val call = mService.getCashOutInfo(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun CashWithdrawal(token: String, money: String, callBack: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["token"] = token
        keyMap["money"] = money
        val call = mService.CashWithdrawal(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun getCashOutRecord(token: String, page: Int, callBack: Callback<CashOutRecordBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["token"] = token
        keyMap["page"] = page.toString() + ""
        val call = mService.getCashOutRecord(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun getBalanceInfo(token: String, callBack: Callback<BalanceInfoBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["token"] = token
        val call = mService.getBalanceInfo(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun getGiftHistory(type: Int, page: Int, callBack: Callback<GiftHistoryBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["type"] = type.toString()
        keyMap["page"] = page.toString()
        val call = mService.getGiftHistory(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun getIncomeHistory(callBack: Callback<IncomeHistoryBean>) {
        val keyMap = ArrayMap<String, String>()
        val call = mService.getIncomeHistory(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }


    fun getMessageInfo(token: String, page: Int, callBack: Callback<List<MessageBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["token"] = token
        keyMap["page"] = page.toString() + ""
        val call = mService.getMessageInfo(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

//    fun clearMessage(token: String, callBack: Callback<BaseBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["token"] = token
//        val call = mService.clearMessage(SignUtil.SignUtil.sign(keyMap))
//        call.enqueue(callBack)
//    }

    fun getIdentifyInfo(token: String, callBack: Callback<IdentifyInfoBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["token"] = token
        val call = mService.getIdentifyInfo(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun checkVersion(pkg: String, callBack: Callback<VersionBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["type"] = "1"
        keyMap["pkg"] = pkg
        val call = mService.checkVersion(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun getFollowList(page: Int, callBack: Callback<List<FriendsBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["page"] = page.toString()
        val call = mService.getFollowList(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun getFansList(pkg: Int, callBack: Callback<List<FriendsBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["page"] = pkg.toString()
        val call = mService.getFansList(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun getFriends(pkg: Int, keyword: String, callBack: Callback<List<FriendsBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["page"] = pkg.toString()
        keyMap["keyword"] = keyword
        val call = mService.getFriends(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun searchFriend(type: Int, keyword: String, callBack: Callback<List<FriendsBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["keyword"] = keyword
        keyMap["type"] = type.toString()
        val call = mService.searchFriend(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

//    fun getConversation(userIds: String, callBack: Callback<List<ConversationBean>>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["user_ids"] = userIds
//        val call = mService.getConversation(SignUtil.SignUtil.sign(keyMap))
//        call.enqueue(callBack)
//    }

//    fun getUserInfo(token: String, userId: String, callBack: Callback<UserInfoBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["token"] = token
//        keyMap["user_id"] = userId
//        val call = mService.getUserInfo(SignUtil.SignUtil.sign(keyMap))
//        call.enqueue(callBack)
//    }

//    fun lightLove(userId: String, callBack: Callback<LoveBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["user_id"] = userId
//        val call = mService.lightLove(SignUtil.sign(keyMap))
//        call.enqueue(callBack)
//    }


    //    type | 上传类型,1:背景图片上传,2:个人照片上传
    fun uploadImage(token: String, type: Int, imaUrl: String, callBack: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["token"] = token
        keyMap["img_url"] = imaUrl
        keyMap["type"] = type.toString() + ""
        val call = mService.uploadImage(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun uploaVideo(type: Int, imaUrl: String, duration: Int, callBack: ApitCallback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["img_url"] = imaUrl
        keyMap["type"] = type.toString() + ""
        keyMap["long"] = duration.toString()
        val call = mService.uploaVideo(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun deleteVideo(type: Int, callBack: ApitCallback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["type"] = type.toString() + ""
        val call = mService.deleteVideo(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }


//    fun getOwnAdress(type: Int, callBack: Callback<OwnAdressBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["type"] = type.toString() + ""
//        val call = mService.getOwnAdress(SignUtil.sign(keyMap))
//        call.enqueue(callBack)
//    }

    fun getShoppingMall(type: Int, callBack: Callback<List<ShopMallItemBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["type"] = type.toString()
        val call = mService.getShoppingMall(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun getMyDress(type: Int, callBack: Callback<List<DressItemBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["type"] = type.toString()
        val call = mService.getMyDress(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun buyDress(id: Int, type: Int, callBack: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["dress_id"] = id.toString()
        keyMap["type"] = type.toString()
        val call = mService.buyDress(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }


    fun useDress(id: Int, type: Int, callBack: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["id"] = id.toString()
        keyMap["type"] = type.toString()
        val call = mService.useDress(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun deleteImage(url: String, callBack: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["img_url"] = url
        val call = mService.deleteImage(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }


    fun getIncomeInfo(token: String, callBack: Callback<ExchangeDiamondsBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["token"] = token
        val call = mService.getIncomeInfo(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun exchangeDiamonds(pwd: String, number: String, callBack: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["pwd"] = pwd
        keyMap["number"] = number
        val call = mService.exchangeDiamonds(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    //获取家族Id
    fun getFamilyId(callBack: Callback<FamilyIdBean>) {
        val keyMap = ArrayMap<String, String>()
        val call = mService.getFamilyId(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun sendRedUser(diamonds: Int, userId: String, callBack: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["diamonds"] = diamonds.toString()
        keyMap["get_user"] = userId
        val call = mService.sendRedUser(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }
//    fun setPwd(pwd: String, callBack: Callback<BaseBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["pwd"] = pwd
//        val call = mService.setPwd(SignUtil.SignUtil.sign(keyMap))
//        call.enqueue(callBack)
//    }

//    fun getMyLevel(callBack: Callback<List<LevelBean>>) {
//        val keyMap = ArrayMap<String, String>()
//        val call = mService.getMyLevel(SignUtil.SignUtil.sign(keyMap))
//        call.enqueue(callBack)
//    }

    fun getBlackList(callback: Callback<ArrayList<UserInfo>>) {
        val keyMap = ArrayMap<String, String>()
        mService.getBlackList(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun blackRemove(userId: String, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["user_id"] = userId
        mService.blackRemove(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun searchRoomUser(chatRoomId: String, keyWord: String, callback: Callback<List<UserInfo>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        keyMap["keyword"] = keyWord
        mService.searchRoomUser(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getManagerInfo(roomId: String, callback: Callback<ManagerInfoBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = roomId
        mService.getManagerInfo(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun setUserRole(roomId: String, userId: String, callback: Callback<RoleSetBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = roomId
        keyMap["user_id"] = userId
        mService.setUserRole(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getRoomLabel(roomType: Int, callback: Callback<List<RoomlabelBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["type"] = roomType.toString()
        mService.getRoomLabel(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getFollowChats(page: Int, callback: Callback<ArrayList<ChatListBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["page"] = page.toString()
        mService.getFollowChats(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getHotChats(page: Int, callback: Callback<ArrayList<ChatListBean>>) {
        val keyMap = ArrayMap<String, String>()
//        keyMap["page"] = page.toString()
        mService.getHotChats(SignUtil.sign(keyMap)).enqueue(callback)
    }

//    fun getNewRoomList(callback: Callback<java.util.ArrayList<ChatListBean>>) {
//        val keyMap = ArrayMap<String, String>()
//        mService.getNewRoomList(SignUtil.sign(keyMap)).enqueue(callback)
//    }


    fun updateChatInfo(topic: String, chatRoomId: String, chatName: String, chatIcon: String, chatBg: String, micType: Int, micWay: Int, chatPassword: String, note: String, labelId: String, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        keyMap["name"] = chatName
        keyMap["icon"] = chatIcon
        keyMap["backdrop"] = chatBg
        keyMap["type"] = micType.toString()
        keyMap["way"] = micWay.toString()
        keyMap["password"] = chatPassword
        keyMap["note"] = note
        keyMap["label"] = labelId
        keyMap["topic"] = topic
        mService.updateChatInfo(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun updateRadioChatInfo(topic: String, chatRoomId: String, chatName: String, chatIcon: String, chatBg: String, micType: Int, micWay: Int, chatPassword: String, note: String, labelId: String, radioName: String, dataTime: String, radioIntroduction: String, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        keyMap["name"] = chatName
        keyMap["icon"] = chatIcon
        keyMap["backdrop"] = chatBg
        keyMap["type"] = micType.toString()
        keyMap["way"] = micWay.toString()
        keyMap["password"] = chatPassword
        keyMap["note"] = note
        keyMap["label"] = labelId
        keyMap["topic"] = topic
        keyMap["introduction"] = radioIntroduction
        keyMap["radio_station_name"] = radioName
        keyMap["open_time"] = dataTime
        mService.updateRadioChatInfo(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun radioRommColumnEdit(id: Int, room_id: Int, icon: String, radio_name: String, startTime: String, endTime: String, today_topic: String, introduction: String, userid: String, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["id"] = id.toString()
        keyMap["room_id"] = room_id.toString()
        keyMap["radio_name"] = radio_name
        keyMap["icon"] = icon
        keyMap["start"] = startTime
        keyMap["end"] = endTime
        keyMap["today_topic"] = today_topic
        keyMap["introduction"] = introduction
        keyMap["user_id"] = userid
        mService.radioRommColumnEdit(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun radioRommColumnAdd(room_id: String, icon: String, radio_name: String, startTime: String, endTime: String, today_topic: String, introduction: String, userid: String, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = room_id
        keyMap["radio_name"] = radio_name
        keyMap["icon"] = icon
        keyMap["start"] = startTime
        keyMap["end"] = endTime
        keyMap["today_topic"] = today_topic
        keyMap["introduction"] = introduction
        keyMap["user_id"] = userid
        mService.radioRommColumnAdd(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getRoomListByLabel(lable: String, callback: Callback<List<ChatListBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["label"] = lable
        mService.getRoomListByLabel(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getPersionalRoomListByLabel(lable: String, callback: Callback<List<ChatListBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["label"] = lable
        mService.getPersonalRoomListByLabel(SignUtil.sign(keyMap)).enqueue(callback)
    }
//    fun bindInviteCode(code:String,callback: Callback<Any>){
//        val keyMap = ArrayMap<String, String>()
//        keyMap["invite_code"] = code
//        mService.bindInviteCode(SignUtil.sign(keyMap)).enqueue(callback)
//    }

//    fun getPersonRoomList(page:Int,callback: Callback<List<PersonRoomItemBean>>){
//        val keyMap = ArrayMap<String, String>()
//        keyMap["page"] = page.toString()
//        mService.getPersonRoomList(SignUtil.sign(keyMap)).enqueue(callback)
//    }

    fun getPartyGoodNumber(callback: Callback<Any>) {
        val keyMap = ArrayMap<String, String>()
        mService.getPartyGoodNumber(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun bindPartyGoodNumber(room_id: String, room_good_number: String, callback: Callback<Any>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_good_number"] = room_good_number
        keyMap["room_id"] = room_id
        mService.bindPartyGoodNumber(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun buyPartyGoodNumber(room_id: String, room_good_number: String, callback: Callback<Any>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_good_number"] = room_good_number
        keyMap["room_id"] = room_id
        mService.buyPartyGoodNumber(SignUtil.sign(keyMap)).enqueue(callback)
    }


    fun getLeopardPartyGoodNumber(callback: Callback<List<LeopardGoodNumberBean>>) {
        val keyMap = ArrayMap<String, String>()
        mService.getLeopardPartyGoodNumber(SignUtil.sign(keyMap)).enqueue(callback)
    }


//    fun getChatLevel(callback: Callback<ChatLevelBean>){
//        val keyMap = ArrayMap<String, String>()
//        mService.getChatLevel(SignUtil.sign(keyMap)).enqueue(callback)
//    }

    fun exchangePwdSet(pwd: String, callback: Callback<Any>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["pwd"] = pwd
        mService.exchangePwdSet(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun exchangePwdUpdate(pwd: String, new_pwd: String, callback: Callback<Any>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["pwd"] = pwd
        keyMap["new_pwd"] = new_pwd
        mService.exchangePwdUpdate(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun exchangePwdClose(pwd: String, callback: Callback<Any>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["pwd"] = pwd
        mService.exchangePwdClose(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun mobileVerify(code: String, type: Int, callback: Callback<String>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["verify_code"] = code
        keyMap["type"] = type.toString() + ""
        mService.mobileVerify(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun mobileChange(mobile: String, code: String, sign: String, callback: Callback<Any>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["mobile_sign"] = sign
        keyMap["mobile"] = mobile
        keyMap["verify_code"] = code
        mService.mobileChange(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun youngPwdSet(young_pwd: String, callBack: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["young_pwd"] = young_pwd
        mService.youngPwdSet(SignUtil.sign(keyMap)).enqueue(callBack)
    }

    fun youngPwdClose(young_pwd: String, callBack: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["young_pwd"] = young_pwd
        mService.youngPwdClose(SignUtil.sign(keyMap)).enqueue(callBack)
    }

    fun youngPwdVerify(young_pwd: String, callBack: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["young_pwd"] = young_pwd
        mService.youngPwdVerify(SignUtil.sign(keyMap)).enqueue(callBack)
    }

    fun removeWarrant(type: String, mobile: String, code: String, callBack: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["type"] = type
        keyMap["verify_code"] = code
        keyMap["mobile"] = mobile
        mService.removeWarrant(SignUtil.sign(keyMap)).enqueue(callBack)
    }

    fun removeAccountThird(openId: String, unionId: String, callBack: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["open_id"] = openId
        keyMap["unionid"] = unionId
        mService.removeAccountThird(SignUtil.sign(keyMap)).enqueue(callBack)
    }

    fun removeAccountMobile(mobile: String, vCode: String, callBack: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["mobile"] = mobile
        keyMap["verify_code"] = vCode
        mService.removeAccountMobile(SignUtil.sign(keyMap)).enqueue(callBack)
    }

    fun personalHomepage(userId: String, callBack: Callback<UserHomePageInfoBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["user_id"] = userId
        mService.personalHomepage(SignUtil.sign(keyMap)).enqueue(callBack)
    }

    fun getGiftWall(page: Int, userId: String, callBack: Callback<GiftWallBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["page"] = page.toString()
        keyMap["user_id"] = userId
        mService.getGiftWall(SignUtil.sign(keyMap)).enqueue(callBack)
    }

    fun getSendGiftWall(page: Int, userId: String, callBack: Callback<List<GiftWallBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["page"] = page.toString()
        keyMap["user_id"] = userId
        mService.getSendGiftWall(SignUtil.sign(keyMap)).enqueue(callBack)
    }


    fun userVisit(userId: String, callBack: Callback<String>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["user_id"] = userId
        mService.userVisit(SignUtil.sign(keyMap)).enqueue(callBack)
    }

//    fun getPersonRoomList(page:Int, callBack: Callback<HttpPageDataBean<PersonRoomItemBean>>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["page"] = page.toString()
//        mService.getPersonRoomList(SignUtil.sign(keyMap)).enqueue(callBack)
//    }

    fun getVisitRecord(page: Int, callBack: Callback<List<CallOnBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["page"] = page.toString()
        mService.getVisitRecord(SignUtil.sign(keyMap)).enqueue(callBack)
    }

    fun getRoomGiftHistory(page: Int, roomId: String, callBack: Callback<List<RoomGiftHistoryBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["page"] = page.toString()
        keyMap["room_id"] = roomId
        mService.getRoomGiftHistory(SignUtil.sign(keyMap)).enqueue(callBack)
    }

    fun getRecommandUsers(offset: Int, callBack: Callback<RecommandUserBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["offset"] = offset.toString()
        mService.getRecommandUsers(SignUtil.sign(keyMap)).enqueue(callBack)
    }

    fun getPhotoList(callBack: Callback<List<PreviewBean>>) {
        val keyMap = ArrayMap<String, String>()
        mService.getphotoList(SignUtil.sign(keyMap)).enqueue(callBack)
    }

    fun getNobleInfo(callBack: Callback<List<NobleBean>>) {
        val keyMap = ArrayMap<String, String>()
        mService.getNobleInfo(SignUtil.sign(keyMap)).enqueue(callBack)
    }

    fun bugNoble(id: Int, roomId: String, invite_uid: String, callBack: Callback<BuyNobleBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap.put("rank", id.toString())
        keyMap.put("room_id", roomId)
        keyMap.put("invite_uid", invite_uid)
        mService.bugNoble(SignUtil.sign(keyMap)).enqueue(callBack)
    }


//    fun getGreatGodCategories(callBack: Callback<List<GreateGodCateGories>>) {
//        val keyMap = ArrayMap<String, String>()
//        mService.getGreatGodCategories(SignUtil.sign(keyMap)).enqueue(callBack)
//    }
//
//    fun getCategoryDetail(id:Int,callBack: Callback<GreatGodDetailBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["id"] = id.toString()
//        mService.getCategoryDetail(SignUtil.sign(keyMap)).enqueue(callBack)
//    }
//
//    fun applyGreatGod(bean:GreatGodApplyBean,callBack: Callback<BaseBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["category_id"] = bean.category_id.toString()
//        keyMap["spec_id1"] = bean.spec_id1.toString()
//        keyMap["spec_id2"] = bean.spec_id2.toString()
//        keyMap["ability_img"] = bean.ability_img
//        keyMap["audio_url"] = bean.audio_url
//        keyMap["introduction"] = bean.introduction
//        keyMap["duration"] = bean.duration.toString()
//        mService.applyGreatGod(SignUtil.sign(keyMap)).enqueue(callBack)
//    }
//
//    fun getUserSignData(callBack: Callback<SignatureBean>) {
//        val keyMap = ArrayMap<String, String>()
//        mService.getUserSignData(SignUtil.sign(keyMap)).enqueue(callBack)
//    }

//    fun signIn(callBack: Callback<BaseBean>) {
//        val keyMap = ArrayMap<String, String>()
//        mService.signIn(SignUtil.sign(keyMap)).enqueue(callBack)
//    }

//    fun buyGuard(type:Int,room_id:String,callBack: Callback<BaseBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["room_id"] = room_id
//        keyMap["type"] = type.toString()
//        mService.buyGuard(SignUtil.sign(keyMap)).enqueue(callBack)
//    }
//
//    fun updateRadioChatInfo(chatRoomId: String, chatName: String, chatIcon: String, note: String,labelId: String, type: String, callback: Callback<BaseBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["room_id"] = chatRoomId
//        keyMap["name"] = chatName
//        keyMap["icon"] = chatIcon
//        keyMap["note"] = note
//        keyMap["label"] = labelId
//        keyMap["type"] = type
//        mService.updateRadioChatInfo(SignUtil.sign(keyMap)).enqueue(callback)
//    }
//
//    fun getGuardInfo(chatRoomId: String, callback: Callback<GuardBean>){
//        val keyMap = ArrayMap<String, String>()
//        keyMap["room_id"] = chatRoomId
//        mService.getGuardInfo(SignUtil.sign(keyMap)).enqueue(callback)
//    }
//    fun getGuardRankList(chatRoomId: String, callback: Callback<List<GuardWeekRankBean>>){
//        val keyMap = ArrayMap<String, String>()
//        keyMap["room_id"] = chatRoomId
//        mService.getGuardRankList(SignUtil.sign(keyMap)).enqueue(callback)
//    }

//    fun getRadioRoomLabel(callback: Callback<List<RoomlabelBean>>) {
//        val keyMap = ArrayMap<String, String>()
//        mService.getRadioRoomLabel(SignUtil.sign(keyMap)).enqueue(callback)
//    }
//
//    fun getRadioRoomList(callBack: Callback<ArrayList<RadioRoomBean>>){
//        val keyMap = ArrayMap<String, String>()
//        mService.getRadioRoomList(SignUtil.sign(keyMap)).enqueue(callBack)
//    }
//
//    fun getAdvert(type:String,callBack: Callback<List<AdvertBean>>){
//        val keyMap = ArrayMap<String, String>()
//        keyMap["type"] = type
//        mService.getAdvert(SignUtil.sign(keyMap)).enqueue(callBack)
//    }

//    fun mysterySwitch(type:String,callBack: Callback<BaseBean>){
//        val keyMap = ArrayMap<String, String>()
//        keyMap["type"] = type
//        mService.mysterySwitch(SignUtil.sign(keyMap)).enqueue(callBack)
//    }
//
//    fun getNobleInfo(callBack: Callback<List<NobleBean>>){
//        val keyMap = ArrayMap<String, String>()
//        mService.getNobleInfo(SignUtil.sign(keyMap)).enqueue(callBack)
//    }
//
//    fun bugNoble(id:Int,roomId:String,callBack: ApitCallback<BuyNobleBean>){
//        val keyMap = ArrayMap<String, String>()
//        keyMap.put("rank",id.toString())
//        keyMap.put("room_id",roomId)
//        mService.bugNoble(SignUtil.sign(keyMap)).enqueue(callBack)
//    }

    fun getHomepage(callBack: Callback<List<HomepageBean>>) {
        val keyMap = ArrayMap<String, String>()
        mService.getHomepage(SignUtil.sign(keyMap)).enqueue(callBack)
    }


//    fun sendMsg(token: String,type:Int,callBack: ApitCallback<BaseBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap.put("token",token)
//        keyMap.put("type",type.toString())
//        val call = mService.sendMsg(SignUtil.sign(keyMap))
//        call.enqueue(callBack)
//    }

    //    public void getPackGiftList(String page,final Callback<GiftBean> callBack) {
//        ArrayMap<String, String> keyMap = new ArrayMap<>();
//        keyMap.put("page",page);
//        final Call<Data<GiftBean>> call = mService.getPackGiftList(SignUtil.INSTANCE.SignUtil.sign(keyMap));
//        call.enqueue(callBack);
//    }
//    public void getGiftList(String page,final Callback<GiftBean> callBack) {
//        ArrayMap<String, String> keyMap = new ArrayMap<>();
//        keyMap.put("page",page);
//        final Call<Data<GiftBean>> call = mService.getGiftList(SignUtil.INSTANCE.SignUtil.sign(keyMap));
//        call.enqueue(callBack);
//    }
    fun getAllGiftList(callBack: Callback<List<GiftBean.DataBean>>) {
        val keyMap = ArrayMap<String, String>()
        val call = mService.getAllGift(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

//    fun getBackpackGiftList(callBack: Callback<List<GiftBean.DataBean>>) {
//        val keyMap = ArrayMap<String, String>()
//        val call = mService.getBackpackGiftList(SignUtil.sign(keyMap))
//        call.enqueue(callBack)
//    }

    fun getAllPack(callBack: Callback<MyPackBean>) {
        val keyMap = ArrayMap<String, String>()
        val call = mService.getAllPack(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

//    fun getAllGuardGift(roomId: String, callBack: Callback<List<GiftBean.DataBean>>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["room_id"] = "" + roomId
//        val call = mService.getAllGuardGift(SignUtil.sign(keyMap))
//        call.enqueue(callBack)
//    }

    fun getAllNobleGift(roomId: String, callBack: Callback<List<GiftBean.DataBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = "" + roomId
        val call = mService.getAllNobleGift(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

//    fun getAllLuckGift(callBack: Callback<List<GiftBean.DataBean>>) {
//        val keyMap = ArrayMap<String, String>()
//        val call = mService.getAllLuckGift(SignUtil.sign(keyMap))
//        call.enqueue(callBack)
//    }


//    fun getLuckRecord(page: Int, callBack: Callback<LuckRecordBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["page"] = "" + page
//        val call = mService.getLuckRecord(SignUtil.sign(keyMap))
//        call.enqueue(callBack)
//    }


//    public void getGiftInfo(String roomId,String userId,final Callback<GiftInfoBean> callBack) {
//        ArrayMap<String, String> keyMap = new ArrayMap<>();
//        keyMap.put("room_id",roomId);
//        keyMap.put("user_id",userId);
//        final Call<Data<GiftInfoBean>> call = mService.getGiftInfo(SignUtil.INSTANCE.SignUtil.sign(keyMap));
//        call.enqueue(callBack);
//    }

    //    public void getGiftInfo(String roomId,String userId,final Callback<GiftInfoBean> callBack) {
//        ArrayMap<String, String> keyMap = new ArrayMap<>();
//        keyMap.put("room_id",roomId);
//        keyMap.put("user_id",userId);
//        final Call<Data<GiftInfoBean>> call = mService.getGiftInfo(SignUtil.INSTANCE.SignUtil.sign(keyMap));
//        call.enqueue(callBack);
//    }
    fun givePackGift(roomId: String, giftNum: Int, sendId: String, getId: String, giftId: String, callBack: Callback<DiamondsBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["gift_num"] = "" + giftNum
        keyMap["send_id"] = sendId
        keyMap["get_id"] = getId
        keyMap["gift_id"] = giftId
        keyMap["room_id"] = roomId
        val call = mService.givePackGift(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

//    fun givePackGiftDT(roomId: String, giftNum: Int, sendId: String, getId: String, giftId: String, callBack: Callback<DiamondsBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["gift_num"] = "" + giftNum
//        keyMap["send_id"] = sendId
//        keyMap["get_id"] = getId
//        keyMap["gift_id"] = giftId
//        keyMap["room_id"] = roomId
//        val call = mService.givePackGiftDT(SignUtil.sign(keyMap))
//        call.enqueue(callBack)
//    }
//
//    fun giveLuckGiftDT(giftNum: Int, getMic: String, roomId: String, sendId: String, getId: String, giftId: String, callBack: Callback<GiftLuckBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["send_id"] = sendId
//        keyMap["get_mike"] = getMic
//        keyMap["get_id"] = getId
//        keyMap["gift_id"] = giftId
//        keyMap["room_id"] = roomId
//        keyMap["gift_num"] = "" + giftNum
//        val call = mService.giveLuckGiftDT(SignUtil.sign(keyMap))
//        call.enqueue(callBack)
//    }
//
//    fun giveLuckGift(giftNum: Int, getMic: String, roomId: String, sendId: String, getId: String, giftId: String, callBack: Callback<GiftLuckBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["send_id"] = sendId
//        keyMap["get_mike"] = getMic
//        keyMap["get_id"] = getId
//        keyMap["gift_id"] = giftId
//        keyMap["room_id"] = roomId
//        keyMap["gift_num"] = "" + giftNum
//        val call = mService.giveLuckGift(SignUtil.sign(keyMap))
//        call.enqueue(callBack)
//    }

//    fun giveGuardGiftDT(roomId: String, giftNum: Int, sendId: String, getId: String, giftId: String, callBack: Callback<DiamondsBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["gift_num"] = "" + giftNum
//        keyMap["send_id"] = sendId
//        keyMap["get_id"] = getId
//        keyMap["gift_id"] = giftId
//        keyMap["room_id"] = roomId
//        val call = mService.giveGuardGiftDT(SignUtil.sign(keyMap))
//        call.enqueue(callBack)
//    }

//    fun giveCommonGiftDT(roomId: String, giftNum: Int, sendId: String, getId: String, giftId: String, callBack: Callback<DiamondsBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["gift_num"] = "" + giftNum
//        keyMap["send_id"] = sendId
//        keyMap["get_id"] = getId
//        keyMap["gift_id"] = giftId
//        keyMap["room_id"] = roomId
//        val call = mService.giveCommonGiftDT(SignUtil.sign(keyMap))
//        call.enqueue(callBack)
//    }

    fun giveCommonGift(roomId: String, giftNum: Int, sendId: String, getId: String, giftId: String, callBack: Callback<DiamondsBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["gift_num"] = "" + giftNum
        keyMap["send_id"] = sendId
        keyMap["get_id"] = getId
        keyMap["gift_id"] = giftId
        keyMap["room_id"] = roomId
        val call = mService.giveCommonGift(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun giveNobleGift(roomId: String, giftNum: Int, sendId: String, getId: String, giftId: String, callBack: Callback<DiamondsBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["gift_num"] = "" + giftNum
        keyMap["send_id"] = sendId
        keyMap["get_id"] = getId
        keyMap["gift_id"] = giftId
        keyMap["room_id"] = roomId
        val call = mService.giveNobleGift(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun giveExpressGift(roomId: String, giftNum: Int, sendId: String, getId: String, giftId: String, note: String, callBack: Callback<DiamondsBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["gift_num"] = "" + giftNum
        keyMap["send_id"] = sendId
        keyMap["get_id"] = getId
        keyMap["gift_id"] = giftId
        keyMap["room_id"] = roomId
        keyMap["note"] = note
        val call = mService.giveExpressGift(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }


//    fun giveNobleGiftDT(roomId: String, giftNum: Int, sendId: String, getId: String, giftId: String, callBack: Callback<DiamondsBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["gift_num"] = "" + giftNum
//        keyMap["send_id"] = sendId
//        keyMap["get_id"] = getId
//        keyMap["gift_id"] = giftId
//        keyMap["room_id"] = roomId
//        val call = mService.giveNobleGiftDT(SignUtil.sign(keyMap))
//        call.enqueue(callBack)
//    }
//
//    fun giveChestGiftDT(roomId: String, giftNum: Int, sendId: String, getId: String, giftId: String, callBack: Callback<ChestGiftBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["gift_num"] = "" + giftNum
//        keyMap["send_id"] = sendId
//        keyMap["get_id"] = getId
//        keyMap["gift_id"] = giftId
//        keyMap["room_id"] = roomId
//        val call = mService.giveChestGiftDT(SignUtil.sign(keyMap))
//        call.enqueue(callBack)
//    }

    fun giveChestGift(roomId: String, giftNum: Int, sendId: String, getId: String, giftId: String, callBack: Callback<ChestGiftBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["gift_num"] = "" + giftNum
        keyMap["send_id"] = sendId
        keyMap["get_id"] = getId
        keyMap["gift_id"] = giftId
        keyMap["room_id"] = roomId
        val call = mService.giveChestGift(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

//    public void getSVGAInfo(final Callback<List<SVGUrlBean>> callBack) {
//        ArrayMap<String, String> keyMap = new ArrayMap<>();
//        final Call<Data<List<SVGUrlBean>>> call = mService.getSVGAInfo(SignUtil.INSTANCE.SignUtil.sign(keyMap));
//        call.enqueue(callBack);
//    }

    //    public void getSVGAInfo(final Callback<List<SVGUrlBean>> callBack) {
//        ArrayMap<String, String> keyMap = new ArrayMap<>();
//        final Call<Data<List<SVGUrlBean>>> call = mService.getSVGAInfo(SignUtil.INSTANCE.SignUtil.sign(keyMap));
//        call.enqueue(callBack);
//    }
    fun sendPrivateGift(get_id: String, gift_id: String, gift_num: String, callBack: Callback<DiamondsBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["send_id"] = getUID().toString()
        keyMap["get_id"] = get_id
        keyMap["gift_id"] = gift_id
        keyMap["gift_num"] = gift_num
        keyMap["room_id"] = ""
        val call = mService.sendPrivateGift(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun getRoomRankList(roomId: String, type: Int, callback: Callback<RoomRankBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = roomId
        keyMap["type"] = type.toString()
        mService.getRoomRankList(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getRoomTopThree(roomId: String, callback: Callback<List<RankBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = roomId
        mService.getRoomTopThree(SignUtil.sign(keyMap)).enqueue(callback)
    }

//    fun getRoomRadioRankList(roomId: String, type: Int, callback: Callback<RoomRankBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["room_id"] = roomId
//        keyMap["type"] = type.toString()
//        mService.getRoomRadioRankList(SignUtil.sign(keyMap)).enqueue(callback)
//    }

//    fun getRoomRadioCharmRankList(anchorId:String,type: Int, callback: Callback<RoomRadioCharmRankBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["type"] = type.toString()
//        keyMap["anchor_id"] = anchorId
//        mService.getRoomRadioCharmRankList(SignUtil.sign(keyMap)).enqueue(callback)
//    }


    fun createChatRoomCallBack(roomId: String, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = roomId
        mService.createChatRoomCallback(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun createChatRoom(welcome_message: String, roomType: Int, topic: String, chatName: String, chatIcon: String, chatBg: String, micType: Int, micWay: Int, chatPassword: String, note: String, labelId: String, callback: Callback<CreateChatBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["name"] = chatName
        keyMap["icon"] = chatIcon
        keyMap["backdrop"] = chatBg
        keyMap["type"] = micType.toString()
        keyMap["way"] = micWay.toString()
        keyMap["password"] = chatPassword
        keyMap["note"] = note
        keyMap["topic"] = topic
        keyMap["room_type"] = roomType.toString()
        keyMap["label"] = labelId.toString()
        keyMap["welcome_message"] = welcome_message

        mService.createChatRoom(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun joinChatRoom(chatRoomId: String, password: String, callback: Callback<JoinChatBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        keyMap["password"] = password
        mService.joinChatRoom(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun micCtrl(chatRoomId: String, position: Int, callback: Callback<MicStatusBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        keyMap["way"] = position.toString()
        mService.micCtrl(SignUtil.sign(keyMap)).enqueue(callback)
    }

//    fun radioMicCtrl(chatRoomId:String,position: Int,callback: Callback<MicStatusBean>){
//        val keyMap = ArrayMap<String, String>()
//        keyMap["room_id"] = chatRoomId
//        keyMap["way"] = position.toString()
//        mService.radioMicCtrl(SignUtil.sign(keyMap)).enqueue(callback)
//    }


    fun chatDwon(chatRoomId: String, toUserId: Int, callback: Callback<MicStatusBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        keyMap["user_id"] = toUserId.toString()
        mService.chatDown(SignUtil.sign(keyMap)).enqueue(callback)
    }

//    fun radioChatDwon(chatRoomId:String,toUserId: Int,callback: Callback<MicStatusBean>){
//        val keyMap = ArrayMap<String, String>()
//        keyMap["room_id"] = chatRoomId
//        keyMap["user_id"] = toUserId.toString()
//        mService.radioChatDown(SignUtil.sign(keyMap)).enqueue(callback)
//    }

    fun applyMic(chatRoomId: String, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        mService.applyMic(SignUtil.sign(keyMap)).enqueue(callback)
    }

//    fun stationApplyMic(chatRoomId:String,callback: Callback<BaseBean>){
//        val keyMap = ArrayMap<String, String>()
//        keyMap["room_id"] = chatRoomId
//        mService.stationApplyMic(SignUtil.sign(keyMap)).enqueue(callback)
//    }

    fun applyMicOpt(chatRoomId: String, toUserId: Int, type: Int, callback: Callback<MicStatusBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        keyMap["type"] = type.toString()
        keyMap["user_id"] = toUserId.toString()
        mService.applyMicOpt(SignUtil.sign(keyMap)).enqueue(callback)
    }

//    fun radioApplyMicOpt(chatRoomId:String,toUserId:Int,type:Int,callback: Callback<MicStatusBean>){
//        val keyMap = ArrayMap<String, String>()
//        keyMap["room_id"] = chatRoomId
//        keyMap["type"] = type.toString()
//        keyMap["user_id"] = toUserId.toString()
//        mService.radioApplyMicOpt(SignUtil.sign(keyMap)).enqueue(callback)
//    }

    fun micCtrl4Host(chatRoomId: String, uid: Int, position: Int, callback: Callback<MicStatusBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        keyMap["user_id"] = uid.toString()
        keyMap["way"] = position.toString()
        mService.micCtrl4Host(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun micCtrl4XqHost(chatRoomId: String, uid: Int, position: Int, callback: Callback<MicStatusBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        keyMap["user_id"] = uid.toString()
        keyMap["way"] = position.toString()
        mService.micCtrl4XqHost(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun lockMic(chatRoomId: String, type: Int, position: Int, callback: Callback<MicStatusBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        keyMap["type"] = type.toString()
        keyMap["way"] = position.toString()
        mService.lockMic(SignUtil.sign(keyMap)).enqueue(callback)
    }

//    fun radioLockMic(chatRoomId:String,type: Int,position: Int,callback: Callback<MicStatusBean>){
//        val keyMap = ArrayMap<String, String>()
//        keyMap["room_id"] = chatRoomId
//        keyMap["type"] = type.toString()
//        keyMap["way"] = position.toString()
//        mService.radioLockMic(SignUtil.sign(keyMap)).enqueue(callback)
//    }

    fun banUser4Host(chatRoomId: String, uid: Int, opt: Int, callback: Callback<BanUserStatusBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        keyMap["user_id"] = uid.toString()
        keyMap["type"] = opt.toString()
        mService.banUser4Host(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getChatRoomInfo(chatRoomId: String, callback: Callback<ChatRoomInfo>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        mService.getChatRoomInfo(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun deleteColumn(column_id: Int, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["column_id"] = column_id.toString()
        mService.deleteColumn(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun openMic(chatRoomId: String, type: Int, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        keyMap["type"] = type.toString()
        mService.openMic(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun sendText(chatRoomId: String, content: String, callback: Callback<TextStatusBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        keyMap["content"] = content
        mService.sendText(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun kickOut(chatRoomId: String, uid: Int, callback: Callback<UniqueIdBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        keyMap["user_id"] = uid.toString()
        mService.kickOut(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun levelChat(chatRoomId: String, uid: Int, type: Int, callback: Callback<UniqueIdBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        keyMap["user_id"] = uid.toString()
        keyMap["type"] = type.toString()
        mService.levelChat(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getUserInfo(chatRoomId: String, uid: Int, callback: Callback<UserCardBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        keyMap["user_id"] = uid.toString()
        mService.getUserInfo(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getUserList(chatRoomId: String, callback: Callback<ArrayList<UserInfo>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        mService.getUserList(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun followChat(chatRoomId: String, callback: Callback<FollowResultBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        mService.followChat(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun report(id: String, reportObject: Int, type: Int, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["id"] = id
        keyMap["report_object"] = reportObject.toString()
        keyMap["type"] = type.toString()
        mService.report(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getApplyList(chatRoomId: String, callback: Callback<ApplyInfoList>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        mService.getApplyList(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getXqQueueList(chatRoomId: String, callback: Callback<RowBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        mService.getXqQueueList(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getXqMaixuStatus(chatRoomId: String, callback: Callback<MaixuStatusBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        mService.getXqMaixuStatus(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getXqJoin_Queue(chatRoomId: String, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        mService.getXqJoin_Queue(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getXqOutMaixuStatus(chatRoomId: String, callback: Callback<MaixuStatusBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        mService.getXqOutMaixuStatus(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getXqkOutStatus(chatRoomId: String, userid: String, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        keyMap["user_id"] = userid
        mService.getkOutStatus(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getXqKtopStatus(chatRoomId: String, userid: String, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        keyMap["user_id"] = userid
        mService.getktopStatus(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getFinal_choice(chatRoomId: String, type: Int, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        keyMap["type"] = type.toString()
        mService.getFinal_choice(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getReversal(chatRoomId: String, type: Int, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        keyMap["type"] = type.toString()
        mService.getReversal(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getNextStep(chatRoomId: String, type: Int, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = chatRoomId
        keyMap["type"] = type.toString()

        mService.getNextStep(SignUtil.sign(keyMap)).enqueue(callback)
    }

    //获取家族申请列表
    fun getFamilyApplyList(familyId: String, callback: Callback<List<ApplyListBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["family_id"] = familyId
        mService.getFamilyApplyList(SignUtil.sign(keyMap)).enqueue(callback)
    }


    //编辑家族
    fun editFamily(fmId: String, fmName: String, icon: String, note: String, callback: Callback<Any>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["family_id"] = fmId
        keyMap["family_name"] = fmName
        keyMap["icon"] = icon
        keyMap["note"] = note
        mService.editFamily(SignUtil.sign(keyMap)).enqueue(callback)
    }

    //获取用户认证手机号
    fun getUserCellPhone(dhChannerl: String, dhPlatrom: String, dhToken: String, callback: Callback<String>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["dh_channel"] = dhChannerl
        keyMap["dh_platform"] = dhPlatrom
        keyMap["dh_token"] = dhToken
        mService.getUserCellPhone(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun updataSendMsgInfo(chatId: String, roomId: String, callback: Callback<String>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["chat_uid"] = chatId
        keyMap["room_id"] = roomId
        mService.updataSendMsgInfo(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun integralExchange(type: String, giftId: String, callback: Callback<Any>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["type"] = type
        keyMap["click_id"] = giftId
        mService.integralExchange(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getGiftIntegralList(callback: Callback<List<CJntegralExchangeBean>>) {
        val keyMap = ArrayMap<String, String>()
        mService.getGiftIntegralList(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getActivityIntegral(callback: Callback<CJintegralActivityIntegralBean>) {
        val keyMap = ArrayMap<String, String>()
        mService.getActivityIntegral(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getEggExchangeRecord(page: Int, callback: Callback<List<CJntegralExchangeRecordBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["page"] = page.toString()
        mService.getEggExchangeRecord(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getCJFontanaRecord(page: Int, callback: Callback<List<CJFontanaRecordBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["page"] = page.toString()
        mService.getCJFontanaRecord(SignUtil.sign(keyMap)).enqueue(callback)
    }


    fun openCJBox(callback: Callback<CJntegralOpenBoxBean>) {
        val keyMap = ArrayMap<String, String>()
        mService.openCJBox(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun gameInfo(type: String, position: String, chatRoomId: String, callback: Callback<List<RoomGameListBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["type"] = type
        keyMap["position"] = position
        keyMap["room_id"] = chatRoomId
        mService.gameInfo(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun useWater(number: Int, type: Int, callback: Callback<ZSUseWaterBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["water"] = number.toString()
        keyMap["type"] = type.toString()
        mService.useWater(SignUtil.sign(keyMap)).enqueue(callback)
    }


    fun buywater(number: Int, type: Int, callback: Callback<ZSBuyWaterBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["num"] = number.toString()
        keyMap["type"] = type.toString()
        mService.buywater(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getZSIndex(callback: Callback<ZSIndexBean>) {
        val keyMap = ArrayMap<String, String>()
        mService.getZSIndex(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getZSRecordList(page: Int, callback: Callback<java.util.ArrayList<ZSPrizeRecordBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["page"] = page.toString()
        mService.getZSRecordList(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getZSGiftPrizePool(type: Int, callback: Callback<List<ZSGiftPrizePoolBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["type"] = type.toString()
        mService.getZSGiftPrizePool(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getZSRankList(type: Int, callback: Callback<List<ZSRankItemBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["type"] = type.toString()
        mService.getZSRankList(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun showZsMessage(display: Int, callback: Callback<Any>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["display"] = display.toString()
        mService.showZsMessage(SignUtil.sign(keyMap)).enqueue(callback)
    }


//    fun getRadioApplyList(chatRoomId:String,callback: Callback<ApplyInfoList>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["room_id"] = chatRoomId
//        mService.getRadioApplyList(SignUtil.sign(keyMap)).enqueue(callback)
//    }


//    fun getPopularity(roomId: String, callback: Callback<PopularityBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["room_id"] = roomId
//        mService.getPopularity(SignUtil.sign(keyMap)).enqueue(callback)
//    }

    fun getChatInfo(roomId: String, callback: Callback<HostInfoBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = roomId
        mService.getChatInfo(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun followSomeBodyAction(roomId: String, uid: Int, callback: Callback<FollowUserBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = roomId
        keyMap["follower_id"] = uid.toString()
        mService.followSomeBodyAction(SignUtil.sign(keyMap)).enqueue(callback)
    }


//    fun isBlack(userId:String,callback: Callback<com.miaomi.fenbei.imkit.bean.IsBlackBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["user_id"] = userId
//        mService.isBlack(SignUtil.sign(keyMap)).enqueue(callback)
//    }

    fun reconnection(roomId: String, callback: Callback<ReconnectionBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = roomId
        mService.reconnection(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun eggIndex(roomId: String, callback: Callback<EggIndexBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = roomId
        mService.eggIndex(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun eggJackpot(callback: Callback<List<JackpotBean>>) {
        val keyMap = ArrayMap<String, String>()
        mService.eggJackpot(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun eggRank(type: String, callback: Callback<List<EggRankBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["type"] = type
        mService.eggRank(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun eggRule(callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        mService.eggRule(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun buyHammer(number: Int, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["number"] = number.toString()
        keyMap["token"] = DataHelper.getLoginToken()
        mService.buyHammer(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun smashEgg(roomId: String, type: Int, show: Int, callback: Callback<ArrayList<XYRewardGiftInfo>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["token"] = DataHelper.getLoginToken()
        keyMap["room_id"] = roomId
        keyMap["type"] = type.toString()
        keyMap["show"] = show.toString()
        mService.smashEgg(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun eggRecord(page: Int, callback: Callback<List<RecordBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["page"] = page.toString()
        mService.eggRecord(SignUtil.sign(keyMap)).enqueue(callback)
    }

//    fun eggExchangeList(callback: Callback<ExchangeListBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["token"] = DataHelper.getLoginToken()
//        mService.eggExchangeList(SignUtil.sign(keyMap)).enqueue(callback)
//    }

//    fun eggExchange(id: String, callback: Callback<BaseBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["id"] = id
//        mService.eggExchange(SignUtil.sign(keyMap)).enqueue(callback)
//    }

//    fun roomUidList(roomId: String, callback: Callback<List<UserInfo>>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["room_id"] = roomId
//        mService.roomUidList(SignUtil.sign(keyMap)).enqueue(callback)
//    }

//    fun roomActivityImg(callback: Callback<List<RoomActivityImgBean>>) {
//        val keyMap = ArrayMap<String, String>()
//        mService.roomActivityImg(SignUtil.sign(keyMap)).enqueue(callback)
//    }

//    fun chatSquare(type: String, content: String, callback: Callback<BaseBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["type"] = type
//        keyMap["content"] = content
//        mService.chatSquare(SignUtil.sign(keyMap)).enqueue(callback)
//    }

    fun chatCloseScreen(type: String, roomId: String, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["type"] = type
        keyMap["room_id"] = roomId
        mService.chatCloseScreen(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getActivityCenterList(callback: Callback<List<HdCenterItemBean>>) {
        val keyMap = ArrayMap<String, String>()
        mService.getActivityCenterList(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getPartyRoomHourRank(roomId: String, callback: Callback<PartyRoomHourBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = roomId
        mService.getPartyRoomHourRank(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getRandomRoomBean(callback: Callback<List<RandomRoomBean>>) {
        val keyMap = ArrayMap<String, String>()
        mService.getRandomRoomBean(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getCurrentRoom(userId: Int, callback: Callback<CurrentRoomBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["user_id"] = userId.toString()
        mService.getCurrentRoom(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getFangroupAnchorFansList(page: Int, anchorId: String, type: Int, callback: Callback<AnchorRankBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["anchor_id"] = anchorId
        keyMap["page"] = page.toString()
        keyMap["type"] = type.toString()
        mService.getFangroupAnchorFansList(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getFangroupAnchorList(page: Int, anchorId: String, callback: Callback<AnchorRankBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["anchor_id"] = anchorId
        keyMap["page"] = page.toString()
        mService.getFangroupAnchorList(SignUtil.sign(keyMap)).enqueue(callback)
    }


    fun getFangroupAnchorFanConfig(room_id: String, type: Int, callback: Callback<DtFansBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = room_id
        keyMap["type"] = type.toString()
        mService.getFangroupAnchorFanConfig(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun buyFangroupAnchorFan(room_id: String, type: Int, day_num: Int, callback: Callback<AnchorRankBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = room_id
        keyMap["type"] = type.toString()
        keyMap["day_num"] = day_num.toString()
        mService.buyFangroupAnchorFan(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun editFangroupName(fan_name: String, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["fan_name"] = fan_name
        mService.editFangroupName(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getUserRoomId(callback: Callback<GetUserRoomIdBean>) {
        val keyMap = ArrayMap<String, String>()
        mService.getUserRoomId(SignUtil.sign(keyMap)).enqueue(callback)
    }


    fun getMyMusicList(page: Int, callback: Callback<List<MusicBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["page"] = page.toString()
        mService.getMyMusicList(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getSearchMusicList(keyWord: String, callback: Callback<HotMusicSearchBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["keyword"] = keyWord
        mService.getSearchMusicList(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun deleteMusic(id: Int, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["id"] = id.toString()
        mService.deleteMusic(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun chatBarley(roomId: String, userId: Int, way: Int, callback: Callback<MicStatusBean>) {
        val keymap = ArrayMap<String, String>()
        keymap["user_id"] = if (userId > 0) userId.toString() else ""
        keymap["room_id"] = roomId
        keymap["way"] = way.toString()
        keymap["token"] = DataHelper.getLoginToken()
        mService.chatBarley(SignUtil.sign(keymap)).enqueue(callback)
    }

//    fun radioChatBarley(roomId: String, userId: Int, way: Int,callback: Callback<MicStatusBean>) {
//        val keymap = ArrayMap<String, String>()
//        keymap["user_id"] = if (userId > 0) userId.toString() else ""
//        keymap["room_id"] = roomId
//        keymap["way"] = way.toString()
//        keymap["token"] = DataHelper.getLoginToken()
//        mService.radioChatBarley(SignUtil.sign(keymap)).enqueue(callback)
//    }

    fun superRoomClose(roomId: String, callback: Callback<BaseBean>) {
        val keymap = ArrayMap<String, String>()
        keymap["room_id"] = roomId
        keymap["token"] = DataHelper.getLoginToken()
        mService.superRoomClose(SignUtil.sign(keymap)).enqueue(callback)
    }

//    fun exchageLuckCoin(type: Int,number:String, callback: Callback<BaseBean>) {
//        val keymap = ArrayMap<String, String>()
//        keymap["number"] = number
//        keymap["type"] = type.toString()
//        mService.exchageLuckCoin(SignUtil.sign(keymap)).enqueue(callback)
//    }

    fun clearMike(roomId: String, way: Int, callback: Callback<BaseBean>) {
        val keymap = ArrayMap<String, String>()
        keymap["room_id"] = roomId
        keymap["way"] = way.toString()
        keymap["token"] = DataHelper.getLoginToken()
        mService.clearMike(SignUtil.sign(keymap)).enqueue(callback)
    }

    fun TurnLight(roomId: String, callback: Callback<BaseBean>) {
        val keymap = ArrayMap<String, String>()
        keymap["room_id"] = roomId
        mService.turnOfflight(SignUtil.sign(keymap)).enqueue(callback)
    }
//    fun getHotChats(page:Int,callback: Callback<ArrayList<ChatListBean>>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["page"] = page.toString()
//        mService.getHotChats(SignUtil.sign(keyMap)).enqueue(callback)
//    }

    fun sociatyChatClose(roomId: String, callback: Callback<LiveFinishBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = roomId
        mService.sociatyChatClose(SignUtil.sign(keyMap)).enqueue(callback)
    }

//    fun createRadioChatRoom(chatName:String, chatIcon:String, note:String, labelId:String, callback: Callback<CreateChatBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["name"] = chatName
//        keyMap["icon"] = chatIcon
//        keyMap["note"] = note
//        keyMap["label"] = labelId
//        mService.radioChatCreate(SignUtil.sign(keyMap)).enqueue(callback)
//    }

//    fun radioChatDetail(chatRoomId:String,callback: Callback<ChatRoomInfo>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["room_id"] = chatRoomId
//        mService.radioChatDetail(SignUtil.sign(keyMap)).enqueue(callback)
//    }

//    fun leveRadiolChat(chatRoomId:String, callback: Callback<LiveFinishBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["room_id"] = chatRoomId
//        mService.levelRadioChat(SignUtil.sign(keyMap)).enqueue(callback)
//    }

//    fun getGuardRankList(chatRoomId: String, callback: Callback<List<GuardWeekRankBean>>){
//        val keyMap = ArrayMap<String, String>()
//        keyMap["room_id"] = chatRoomId
//        mService.getGuardRankList(SignUtil.sign(keyMap)).enqueue(callback)
//    }

    fun getRadioBanner(type: String, position: String, room_id: String, callback: Callback<List<BannerBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["type"] = type
        keyMap["position"] = position
        keyMap["room_id"] = room_id
        mService.getRadioBanner(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getBrightStatus(room_id: String, callback: Callback<BrightStatusBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = room_id
        mService.getBrightStatus(SignUtil.sign(keyMap)).enqueue(callback)
    }
//    fun radioHourInfo(roomId: String, callback: Callback<RadioHourInfoBean>) {
//        val keyMap = ArrayMap<String, String>()
//        keyMap["room_id"] = roomId
//        mService.radioHourInfo(SignUtil.sign(keyMap)).enqueue(callback)
//    }

    fun getRedPacketBaseData(roomId: String, callback: Callback<RedPacketBaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = roomId
        mService.getRedPacketBaseData(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun sendRedPacket(
        roomId: String, diamonds: String, split_count: String, split_type: String, say_text: String, need_follow: String, realm_notice: String,
        secret_status: String, secret_order: String, callback: Callback<RedPacketBean>
    ) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = roomId
        keyMap["diamonds"] = diamonds
        keyMap["split_count"] = split_count
        keyMap["split_type"] = split_type
        keyMap["say_text"] = say_text
        keyMap["need_follow"] = need_follow
        keyMap["realm_notice"] = realm_notice
        keyMap["secret_status"] = secret_status
        keyMap["secret_order"] = secret_order
        mService.sendRedPacket(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun grabRedPacket(roomId: String, collection_id: String, secret_order: String, callback: Callback<GotRedPacketBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = roomId
        keyMap["collection_id"] = collection_id
        keyMap["secret_order"] = secret_order
        mService.grabRedPacket(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getGrabList(collection_id: String, callback: Callback<RedPacketRankBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["collection_id"] = collection_id
        mService.getGrabList(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun upLoadTxt(room_id: String, content: String, callback: Callback<AppearBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["content"] = content
        keyMap["room_id"] = room_id
        mService.upLoadTxt(SignUtil.sign(keyMap)).enqueue(callback)
    }

//
//    fun stayReport(room_id:String,callback: Callback<AppearBean>){
//        val keyMap = ArrayMap<String, String>()
//        keyMap["room_id"] = room_id
//        mService.stayReport(SignUtil.sign(keyMap)).enqueue(callback)
//    }


    fun getMsgHisroyList(room_id: String, callback: Callback<List<MsgHistoryBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = room_id
        mService.historyConversation(SignUtil.sign(keyMap)).enqueue(callback)
    }

//    fun getMakeFriengMsgList(callBack: Callback<List<MakeFriendBean>>) {
//        val keyMap = ArrayMap<String, String>()
//        val call = mService.getMakeFriengMsgList(SignUtil.sign(keyMap))
//        call.enqueue(callBack)
//    }

    fun setRoomNote(room_id: String, note: String, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = room_id
        keyMap["note"] = note
        mService.setRoomNote(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun setRoomPwd(room_id: String, pwd: String, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = room_id
        keyMap["password"] = pwd
        mService.setRoomPwd(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getRoomPwd(room_id: String, callback: Callback<String>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = room_id
        mService.getRoomPwd(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun bindBankCardAccount(name: String, bank_account: String, bank_name: String, bank_address: String, callBack: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["name"] = name
        keyMap["bank_account"] = bank_account
        keyMap["bank_name"] = bank_name
        keyMap["bank_address"] = bank_address
        val call = mService.bindBankCard(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun getBankList(callBack: Callback<List<BankListBean>>) {
        val keyMap = ArrayMap<String, String>()
        val call = mService.getBankList(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun issueFriendsCircle(type: Int, duration: Int, desc: String, urls: ArrayList<String>, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["type"] = type.toString()
        var url = ""
        if (urls.size > 0) {
            url = urls.toString().substring(1, urls.toString().length - 1).replace(" ", "")
        }
        keyMap["url"] = url
        keyMap["long"] = duration.toString()
        keyMap["desc"] = desc
        mService.issueFriendsCircle(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun delFriendsCircle(id: String, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["id"] = id
        mService.delFriendsCircle(SignUtil.sign(keyMap)).enqueue(callback)
    }


    fun getFriendsCircleList(type: Int, page: Int, callback: Callback<List<FindBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["type"] = type.toString()
        keyMap["page"] = page.toString()
        mService.getFriendsCircleList(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getFriendsCircle(friendscircleId: String, callback: Callback<FindBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["friends_circle_id"] = friendscircleId
        mService.getFriendsCircle(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun sendFriendsCircleComment(friendscircleId: String, comment: String, parent_id: Int, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["friends_circle_id"] = friendscircleId
        keyMap["comment"] = comment
        keyMap["parent_id"] = parent_id.toString()
        mService.sendFriendsCircleComment(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun delFriendsComment(friendscircleId: String, comment_id: Int, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["friends_circle_id"] = friendscircleId
        keyMap["comment_id"] = comment_id.toString()
        mService.delFriendsComment(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getFriendsCircleListByUid(uid: String, page: Int, callback: Callback<List<FindBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["user_id"] = uid
        keyMap["page"] = page.toString()
        mService.getFriendsCircleListByUid(SignUtil.sign(keyMap)).enqueue(callback)
    }


    fun heartPYQ(id: String, callback: Callback<HeartBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["id"] = id
        mService.heartPYQ(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun heartBBQ(id: String, callback: Callback<List<String>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["id"] = id
        mService.heartBBQ(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getMortalResult(room_id: String, callback: Callback<List<GameXXBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = room_id
        mService.getMortalResult(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun isGetMortalResult(room_id: String, callback: Callback<MortalResultBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = room_id
        mService.isGetMortalResult(SignUtil.sign(keyMap)).enqueue(callback)
    }

    //
    fun showXXReslut(room_id: String, operate_type: Int, callback: Callback<Boolean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = room_id
        keyMap["operate_type"] = operate_type.toString()
        mService.showXXReslut(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun openMortal(room_id: String, pai_count: Int, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = room_id
        keyMap["pai_count"] = pai_count.toString()
        mService.openMortal(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getRecommandUser(gender: Int, callback: Callback<UserInfo>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["gender"] = gender.toString()
        mService.getRecommandUser(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getRecommandRoom(callback: Callback<String>) {
        val keyMap = ArrayMap<String, String>()
        mService.getRecommandRoom(SignUtil.sign(keyMap)).enqueue(callback)
    }

//    fun getRankTopThree(callback: Callback<PartyRankTopThree>){
//        val keyMap = ArrayMap<String, String>()
//        mService.getRankTopThree(SignUtil.sign(keyMap)).enqueue(callback)
//    }

    fun getExpressRecord(page: Int, callback: Callback<List<ExpressItemBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["page"] = page.toString()
        mService.getExpressRecord(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getRedRecord(type: Int, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["type"] = type.toString()
        mService.getRedRecord(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun hideTreasure(type: Int, room_id: String, gift_id: String, callback: Callback<DiamondsBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["type"] = type.toString()
        keyMap["room_id"] = room_id
        keyMap["gift_id"] = gift_id
        mService.hideTreasure(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getHideIndex(room_id: String, callback: Callback<XBIndexBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["room_id"] = room_id
        mService.getHideIndex(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getSingleTreasure(id: String, callback: Callback<XBItemBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["id"] = id
        mService.getSingleTreasure(SignUtil.sign(keyMap)).enqueue(callback)
    }


    fun hideDig(id: String, dig_price: String, callback: Callback<XBRewardBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["id"] = id
        keyMap["dig_price"] = dig_price
        mService.hideDig(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getXBReleaseRecordList(page: Int, callback: Callback<List<XBTFRecordBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["page"] = page.toString()
        mService.getXBReleaseRecordList(SignUtil.sign(keyMap)).enqueue(callback)
    }


    fun getXBHuntingRecordList(page: Int, callback: Callback<List<XBXBRecordBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["page"] = page.toString()
        mService.getXBHuntingRecordList(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getHideGiftList(callback: Callback<List<XBHideGiftBean>>) {
        val keyMap = ArrayMap<String, String>()
        mService.getHideGiftList(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getHideRankList(type: Int, callback: Callback<List<XBRankBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["type"] = type.toString()
        mService.getHideRankList(SignUtil.sign(keyMap)).enqueue(callback)
    }


    fun chatSquare(type: String, content: String, callback: Callback<BaseBean>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["type"] = type
        keyMap["content"] = content
        mService.chatSquare(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getMakeFriengMsgList(callBack: Callback<List<MakeFriendBean>>) {
        val keyMap = ArrayMap<String, String>()
        val call = mService.getMakeFriengMsgList(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    fun getSquareHeadInfo(callBack: Callback<MakeFriendBean>) {
        val keyMap = ArrayMap<String, String>()
        val call = mService.getSquareHeadInfo(SignUtil.sign(keyMap))
        call.enqueue(callBack)
    }

    //家族操作 0 添加删除管理员-1踢出-2同意加入-3拒绝加入4同意退出5拒绝退出
    fun operateFamily(fmId: String, usrId: String, type: Int, callback: Callback<Any>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["family_id"] = fmId
        keyMap["user_id"] = usrId
        keyMap["type"] = type.toString()
        mService.operateFamily(SignUtil.sign(keyMap)).enqueue(callback)
    }

    //家族操作 取消管理员
    fun administrator_del(fmId: String, usrId: String, callback: Callback<Any>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["family_id"] = fmId
        keyMap["user_id"] = usrId
        mService.administrator_del(SignUtil.sign(keyMap)).enqueue(callback)
    }

    //创建家族
    fun createFamily(fmName: String, icon: String, familynotice: String, callback: Callback<Any>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["family_name"] = fmName
        keyMap["icon"] = icon
        keyMap["note"] = familynotice
//        keyMap["num"] = num
//        keyMap["before_platform"] = before_platform
        mService.createFamily(SignUtil.sign(keyMap)).enqueue(callback)
    }

    //家族分成比例 部分 保存
    fun upDividedSection(fmId: String, usrId: String, type: Int, callback: Callback<Any>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["family_id"] = fmId
        keyMap["user_ids"] = usrId
        keyMap["divided"] = type.toString()
        mService.upDividedSection(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getRedListRecord(type: Int, callback: Callback<List<RedPacketRecordBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["type"] = type.toString()
        mService.getRedListRecord(SignUtil.sign(keyMap)).enqueue(callback)
    }

    fun getRedRecordDetail(order_no: String, callback: Callback<List<GetRedPacketUsersBean>>) {
        val keyMap = ArrayMap<String, String>()
        keyMap["order_no"] = order_no
        mService.getRedRecordDetail(SignUtil.sign(keyMap)).enqueue(callback)
    }


}