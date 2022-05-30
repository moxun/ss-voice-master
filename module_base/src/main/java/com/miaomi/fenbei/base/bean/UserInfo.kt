package com.miaomi.fenbei.base.bean


open class UserInfo(
        var user_role: Int = 0,//0为普通用户，1为管理员，2为房主
        var super_manager: Int = 0,
        var nickname: String = "",
        var gender: Int = 0,
        var age: Int = 0,
        var recharge_residue: Int = 0,//钻石余额
        var face: String = "",
        var type: Int = 0,   //麦序
        var state: Int = 0,   //0不在线 1在线
        var status: Int = 0,    //0正常 1禁麦 2为房主禁麦
        var speak: Int = 0,  //0正常 1禁言
        var city: String = "",
        var user_id: Int = 0,
        var is_follow: Int = 0,//0未关注，1关注
        var user_room_id: Int = 0,//用户房间号
        var user_local_room_id: Int = 0,//用户当前所在房间
        var signature: String = "",
        var mic_speaking: Boolean = false,
        var good_number: Int = 0,
        var good_number_state: Int = 0,
        var charm_level: LevelBean = LevelBean(), // 魅力等级
        var wealth_level: LevelBean = LevelBean(), // 财富等级
        var seat_frame: String = "",
        var effects: Int = 0,
        var head_img: String = "",
        var body_img: String = "",
        var auth: Int = 0,
        var fans_number: Int = 0,
        var love: Int = 0,
        var mike: Int = 0,
        var first_sign: Int = 0,
        var seat:String = "",
        var is_guard: Int = -1,
        var mystery: Int = 0,
        var activity_pic: List<String> = listOf(),
        var medal:String = "",
        var medal_img:String = "",
        var medal_name:String = "",
        var rank_id:Int = 0,//贵族等级
        var lecturer:String = "",
        var replaceArr: List<ReplaceArrBean> = ArrayList()
//        var lock:Int = 0//是否锁麦
//        var charm:Int = 0
//        var effect_svga:String = ""
)
//    : Parcelable {
//    constructor(source: Parcel) : this(
//            source.readInt(),
//            source.readInt(),
//            source.readString(),
//            source.readInt(),
//            source.readInt(),
//            source.readInt(),
//            source.readString(),
//            source.readInt(),
//            source.readInt(),
//            source.readInt(),
//            source.readInt(),
//            source.readString(),
//            source.readInt(),
//            source.readInt(),
//            source.readInt(),
//            source.readInt(),
//            source.readString(),
//            1 == source.readInt(),
//            source.readInt(),
//            source.readInt(),
//            source.readParcelable<LevelBean>(LevelBean::class.java.classLoader),
//            source.readParcelable<LevelBean>(LevelBean::class.java.classLoader),
//            source.readString(),
//            source.readInt(),
//            source.readString(),
//            source.readString(),
//            source.readInt(),
//            source.readInt(),
//            source.readInt(),
//            source.readInt(),
//            source.readInt(),
//            source.readString(),
//            source.readInt(),
//            source.readInt(),
//            source.createStringArrayList(),
//            source.readString(),
//            source.readString()
//    )
//
//    override fun describeContents() = 0
//
//    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
//        writeInt(user_role)
//        writeInt(super_manager)
//        writeString(nickname)
//        writeInt(gender)
//        writeInt(age)
//        writeInt(recharge_residue)
//        writeString(face)
//        writeInt(type)
//        writeInt(state)
//        writeInt(status)
//        writeInt(speak)
//        writeString(city)
//        writeInt(user_id)
//        writeInt(is_follow)
//        writeInt(user_room_id)
//        writeInt(user_local_room_id)
//        writeString(signature)
//        writeInt((if (mic_speaking) 1 else 0))
//        writeInt(good_number)
//        writeInt(good_number_state)
//        writeParcelable(charm_level, 0)
//        writeParcelable(wealth_level, 0)
//        writeString(seat_frame)
//        writeInt(effects)
//        writeString(head_img)
//        writeString(body_img)
//        writeInt(auth)
//        writeInt(fans_number)
//        writeInt(love)
//        writeInt(mike)
//        writeInt(first_sign)
//        writeString(seat)
//        writeInt(is_guard)
//        writeInt(mystery)
//        writeStringList(activity_pic)
//        writeString(medal)
//        writeString(effect_svga)
//    }
//
//    companion object {
//        @JvmField
//        val CREATOR: Parcelable.Creator<UserInfo> = object : Parcelable.Creator<UserInfo> {
//            override fun createFromParcel(source: Parcel): UserInfo = UserInfo(source)
//            override fun newArray(size: Int): Array<UserInfo?> = arrayOfNulls(size)
//        }
//    }
//}