package com.miaomi.fenbei.base.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by
 * on 2019/12/30.
 */
data class RedPacketBaseBean(
        var maximum: Int = 0,
        var minimum: Int = 0,
        var max_count: Int = 0,
        var min_count: Int = 0,
        var lucky_maximum: Int = 0,
        var lucky_minimum: Int = 0,
        var realm_notice_price: Int = 0,
        var recharge_residue: Int = 0,
        var secret_order_status: Int = 0,
        var collections: ArrayList<RedPacketBean>
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.createTypedArrayList(RedPacketBean.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(maximum)
        writeInt(minimum)
        writeInt(max_count)
        writeInt(min_count)
        writeInt(lucky_maximum)
        writeInt(lucky_minimum)
        writeInt(realm_notice_price)
        writeInt(recharge_residue)
        writeInt(secret_order_status)
        writeTypedList(collections)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RedPacketBaseBean> = object : Parcelable.Creator<RedPacketBaseBean> {
            override fun createFromParcel(source: Parcel): RedPacketBaseBean = RedPacketBaseBean(source)
            override fun newArray(size: Int): Array<RedPacketBaseBean?> = arrayOfNulls(size)
        }
    }
}

data class RedPacketBean(
        var id: Int = 0,
        var user_id: String = "",
        var diamonds: Int = 0,
        var split_count: Int = 0,
        var split_type: Int = 0,
        var say_text: String = "",
        var grab_count: Int = 0,
        var room_id: String = "",
        var room_type: Int = 0,
        var status: Int = 0,
        var need_follow: Int = 0,
        var create_time: String = "",
        var update_time: String = "",
        var expired_at: Long = 0,
        var realm_notice: Int = 0,
        var nickname: String = "",
        var face: String = "",
        var secret_status: String = "",
        var secret_order: String = ""
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readString(),
            source.readInt(),
            source.readString(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readLong(),
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(user_id)
        writeInt(diamonds)
        writeInt(split_count)
        writeInt(split_type)
        writeString(say_text)
        writeInt(grab_count)
        writeString(room_id)
        writeInt(room_type)
        writeInt(status)
        writeInt(need_follow)
        writeString(create_time)
        writeString(update_time)
        writeLong(expired_at)
        writeInt(realm_notice)
        writeString(nickname)
        writeString(face)
        writeString(secret_status)
        writeString(secret_order)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RedPacketBean> = object : Parcelable.Creator<RedPacketBean> {
            override fun createFromParcel(source: Parcel): RedPacketBean = RedPacketBean(source)
            override fun newArray(size: Int): Array<RedPacketBean?> = arrayOfNulls(size)
        }
    }
}
