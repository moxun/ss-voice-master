package com.miaomi.fenbei.room.ui.adapter

//import kotlinx.android.synthetic.main.room_word_item_normal_list.view.liang_iv
//import kotlinx.android.synthetic.main.room_word_item_normal_list.view.user_icon_iv
//import kotlinx.android.synthetic.main.room_word_item_normal_list.view.user_seat_iv
import android.animation.Animator
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.animation.BaseAnimation
import com.chad.library.adapter.base.animation.ScaleInAnimation
import com.miaomi.fenbei.base.bean.MsgBean
import com.miaomi.fenbei.base.bean.MsgType
import com.miaomi.fenbei.base.bean.UserInfo
import com.miaomi.fenbei.base.config.BaseConfig
import com.miaomi.fenbei.base.config.EmojiConfig
import com.miaomi.fenbei.base.core.msg.MsgManager
import com.miaomi.fenbei.base.core.msg.SendMsgListener
import com.miaomi.fenbei.base.util.DataHelper
import com.miaomi.fenbei.base.util.DataHelper.getUserInfo
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.base.util.ToastUtil.i
import com.miaomi.fenbei.base.widget.WordListSelectedView
import com.miaomi.fenbei.gift.GiftManager
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.room.ui.callback.WordClickListener
import com.miaomi.fenbei.room.util.DiceUtil
import com.miaomi.fenbei.room.util.MxjUtil
import com.tencent.imsdk.TIMMessage
import kotlinx.android.synthetic.main.room_item_first_charge.view.*
import kotlinx.android.synthetic.main.room_item_gift_list.view.*
import kotlinx.android.synthetic.main.room_public_screen_item_bx.view.*
import kotlinx.android.synthetic.main.room_public_screen_item_xy.view.*
import kotlinx.android.synthetic.main.room_public_screen_item_xy.view.name
import kotlinx.android.synthetic.main.room_public_screen_item_zs.view.*
import kotlinx.android.synthetic.main.room_word_item_full_msg.view.*
import kotlinx.android.synthetic.main.room_word_item_join_list.view.*
import kotlinx.android.synthetic.main.room_word_item_normal_emoji.view.*
import kotlinx.android.synthetic.main.room_word_item_normal_game_xx_result.view.*
import kotlinx.android.synthetic.main.room_word_item_normal_list.view.*
import kotlinx.android.synthetic.main.room_word_item_normal_list.view.iv_medal
import kotlinx.android.synthetic.main.room_word_item_normal_list.view.label_rv
import kotlinx.android.synthetic.main.room_word_item_normal_list.view.role_iv
import kotlinx.android.synthetic.main.room_word_item_normal_list.view.user_name_tv
import kotlinx.android.synthetic.main.room_word_item_normal_list.view.wealth_level_iv


class WordListAdapter(private val context: Context, listener: WordClickListener) : RecyclerView.Adapter<WordListAdapter.ViewHolder>() {


    private val mInterpolator: Interpolator = LinearInterpolator()
    private val mSelectAnimation: BaseAnimation = ScaleInAnimation()
    var list: ArrayList<MsgBean> = ArrayList()
    private var mListener: WordClickListener = listener
    private val mDuration = 300
    private var mLastPosition = -1

//    private val URLKEY_LEVEL = "TEM_LEVEL"

    private var UNSUPPORT_MSG: Int = -1
    private var TEXT_MSG: Int = 0
    private var JOIN_CHAT: Int = 1
    private var BAN_USER_WORD: Int = 2
    private var GIFT: Int = 3
    private var NOTICE: Int = 4
    private var OFFICE_MSG: Int = 5
    private var EMOJI: Int = 6
    private var ROOM_COLLECT: Int = 7
    private var WINNING_MSG: Int = 8

    //    private var EGGS_UPGRADE: Int = 9
    private var CLEAR_CHAT: Int = 10
    private var OPEN_CHAT: Int = 11
    private var CLOSE_CHAT: Int = 12

    //    private var GIFT_LUCK_REWAED: Int = 13
//    private var RADIO_ATTENTION = 14
//    private var RADIO_JOIN_GUARD = 15
    private var GIFT_LUCK_CHEST: Int = 16
    private var RED_PACKET_MSG: Int = 17
    private var MSG_GAME_XX: Int = 18
    private var GAME_XX_RESULT: Int = 19
    private var KICK_OUT: Int = 20
    private var ZS_WINNING_MSG: Int = 21
    private var FULL_SERVICE_TREE_LUCKTIME: Int = 22
    private var NEW_USER_PAY_GIFT_PACK: Int = 23
    private var FULL_SERVICE_MSG: Int = 24
//    private var FULL_SERVICE_GAME_CCM_LOTTERY = 17

//    fun setData(msgBean: ArrayList<MsgBean>) {
//        list.clear()
//        list.addAll(msgBean)
//        this.notifyDataSetChanged()
//    }

    fun clearData() {
        list.clear()
        this.notifyDataSetChanged()
    }

    fun addItem(msgBean: MsgBean) {
        list.add(msgBean)
        notifyItemInserted(list.size)
    }

    fun addItem(msgBean: MsgBean, type: Int) {
        if (type == WordListSelectedView.WSV_SELECTED_ALL) {
            if (msgBean.opt != MsgType.FULL_SERVICE_MSG) {
                list.add(msgBean)
            }
        }
        if (type == WordListSelectedView.WSV_SELECTED_GIFT) {
            if (msgBean.opt == MsgType.GIFT) {
                list.add(msgBean)
            }
        }
        if (type == WordListSelectedView.WSV_SELECTED_MSG) {
            if (msgBean.opt == MsgType.TEXT_MSG || msgBean.opt == MsgType.EMOJI || msgBean.opt == MsgType.JOIN_CHAT) {
                list.add(msgBean)
            }
        }
        if (type == WordListSelectedView.WSV_SELECTED_GC) {
            if (msgBean.opt == MsgType.FULL_SERVICE_MSG) {
                list.add(msgBean)
            }
        }
        notifyItemInserted(list.size)
    }


//    fun addData(msgBean: ArrayList<MsgBean>) {
//        list.addAll(msgBean)
//        this.notifyDataSetChanged()
//    }

    fun getData(): ArrayList<MsgBean> {
        return list
    }

    fun setData(msgBean: ArrayList<MsgBean>, type: Int) {
        list.clear()
        if (type != WordListSelectedView.WSV_SELECTED_ALL) {
            if (type == WordListSelectedView.WSV_SELECTED_GIFT) {
                for (item in msgBean) {
                    if (item.opt == MsgType.GIFT) {
                        list.add(item)
                    }
                }
            }
            if (type == WordListSelectedView.WSV_SELECTED_MSG) {
                for (item in msgBean) {
                    if (item.opt == MsgType.TEXT_MSG || item.opt == MsgType.EMOJI || item.opt == MsgType.GAME_LSP_MSG || item.opt == MsgType.GAME_LSP_RESULT) {
                        list.add(item)
                    }
                }
            }
            if (type == WordListSelectedView.WSV_SELECTED_GC) {
                for (item in msgBean) {
                    if (item.opt == MsgType.FULL_SERVICE_MSG) {
                        list.add(item)
                    }
                }
            }
        } else {
            for (item in msgBean) {
                if (item.opt != MsgType.FULL_SERVICE_MSG) {
                    list.add(item)
                }
            }
        }
        this.notifyDataSetChanged()
    }

//    fun notifyItemInserted(size:Int,type:Int){
//        if (type != WordListSelectedView.WSV_SELECTED_ALL){
//            if (type == WordListSelectedView.WSV_SELECTED_GIFT){
//                for (item in list){
//                    if (item.opt == MsgType.GIFT){
//                        newList.add(item)
//                    }
//                }
//            }
//            if (type == WordListSelectedView.WSV_SELECTED_MSG){
//                for (item in list){
//                    if (item.opt == MsgType.TEXT_MSG || item.opt == MsgType.EMOJI){
//                        newList.add(item)
//                    }
//                }
//            }
//            list.clear()
//            list.addAll(newList)
//        }
//        notifyItemInserted(size)
//    }

    override fun getItemViewType(position: Int): Int {
        val type = getData()[position].opt
        when (type) {
            MsgType.JOIN_CHAT -> {//进入房间
                return JOIN_CHAT
            }
            MsgType.BAN_USER_WORD, MsgType.CB_FULL_SERVICE_PUT, MsgType.CB_FULL_SERVICE_REWARD -> {//禁言
                return BAN_USER_WORD
            }
            MsgType.GIFT -> {//送礼
                if (getData()[position].giftBean!!.giftType == GiftManager.GIFT_TYPE_CHEST) {
                    return GIFT_LUCK_CHEST
                }
                return GIFT
            }
            MsgType.NOTICE -> {//公告
                return NOTICE
            }
            MsgType.OFFICE_MSG -> {//系统消息
                return OFFICE_MSG
            }
            MsgType.EMOJI -> { //表情
                return EMOJI
            }
            MsgType.ROOM_COLLECT -> {
                return ROOM_COLLECT
            }
            MsgType.WINNING_MSG -> {
                return WINNING_MSG
            }
            MsgType.ZS_WINNING_MSG -> {
                return ZS_WINNING_MSG
            }

            MsgType.GAME_LSP_MSG -> {
                return MSG_GAME_XX
            }
            MsgType.FULL_SERVICE_TREE_LUCKTIME -> {
                return FULL_SERVICE_TREE_LUCKTIME
            }

            MsgType.NEW_USER_PAY_GIFT_PACK -> {
                return NEW_USER_PAY_GIFT_PACK
            }
            MsgType.GAME_LSP_RESULT -> {
                return GAME_XX_RESULT
            }
//            MsgType.FULL_SERVICE_GAME_CCM_LOTTERY -> {
//                return FULL_SERVICE_GAME_CCM_LOTTERY
//            }
            MsgType.FULL_SERVICE_MSG -> {
                return FULL_SERVICE_MSG
            }
            MsgType.CLOSE_CHAT -> {
                return CLOSE_CHAT
            }
            MsgType.OPEN_CHAT -> {
                return OPEN_CHAT
            }
            MsgType.CLEAR_CHAT -> {
                return CLEAR_CHAT
            }
            MsgType.TEXT_MSG -> {
                return TEXT_MSG
            }
            MsgType.RED_PACKET_MSG -> {
                return RED_PACKET_MSG
            }
            MsgType.KICK_OUT -> {
                return KICK_OUT
            }
//            MsgType.RADIO_ATTENTION -> {
//                return RADIO_ATTENTION
//            }
//            MsgType.RADIO_JOIN_GUARD -> {
//                return RADIO_JOIN_GUARD
//            }
            else -> {
                return UNSUPPORT_MSG
            }
        }
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        addAnimation(holder)
    }

    private fun addAnimation(holder: RecyclerView.ViewHolder) {
        if (holder.getLayoutPosition() > mLastPosition) {
            val animation: BaseAnimation = mSelectAnimation
            for (anim in animation.getAnimators(holder.itemView)) {
                startAnim(anim, holder.layoutPosition)
            }
        }
        mLastPosition = holder.layoutPosition
    }

    protected fun startAnim(anim: Animator, index: Int) {
        anim.setDuration(mDuration.toLong()).start()
        anim.interpolator = mInterpolator
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View
        when (viewType) {
            JOIN_CHAT -> {//进入房间
                view = LayoutInflater.from(context).inflate(R.layout.room_word_item_join_list, parent, false)
            }
            RED_PACKET_MSG -> {
                view = LayoutInflater.from(context).inflate(R.layout.room_word_item_normal_list, parent, false)
            }
            BAN_USER_WORD -> {//禁言
                view = LayoutInflater.from(context).inflate(R.layout.room_item_word_list, parent, false)
            }
            GIFT -> {//送礼
                view = LayoutInflater.from(context).inflate(R.layout.room_item_gift_list, parent, false)
            }
            OFFICE_MSG, NOTICE, CLEAR_CHAT, CLOSE_CHAT, OPEN_CHAT, KICK_OUT -> {//公告
                view = LayoutInflater.from(context).inflate(R.layout.room_item_word_list, parent, false)
            }
            FULL_SERVICE_MSG -> {//系统消息
                view = LayoutInflater.from(context).inflate(R.layout.room_word_item_full_msg, parent, false)
            }
            TEXT_MSG -> {
                view = LayoutInflater.from(context).inflate(R.layout.room_word_item_normal_list, parent, false)
            }

            EMOJI -> {
                view = LayoutInflater.from(context).inflate(R.layout.room_word_item_normal_emoji, parent, false)
            }
            ROOM_COLLECT -> {
                view = LayoutInflater.from(context).inflate(R.layout.room_item_word_list, parent, false)
            }
            WINNING_MSG -> {
                view = LayoutInflater.from(context).inflate(R.layout.room_public_screen_item_xy, parent, false)
            }

            ZS_WINNING_MSG -> {
                view = LayoutInflater.from(context).inflate(R.layout.room_public_screen_item_zs, parent, false)
            }
            FULL_SERVICE_TREE_LUCKTIME -> {
                view = LayoutInflater.from(context).inflate(R.layout.room_public_item_luck_zs, parent, false)
            }
            NEW_USER_PAY_GIFT_PACK -> {
                view = LayoutInflater.from(context).inflate(R.layout.room_item_first_charge, parent, false)
            }
            MSG_GAME_XX -> {
                view = LayoutInflater.from(context).inflate(R.layout.room_word_item_normal_game_xx, parent, false)
            }

            GAME_XX_RESULT -> {
                view = LayoutInflater.from(context).inflate(R.layout.room_word_item_normal_game_xx_result, parent, false)
            }
//            FULL_SERVICE_GAME_CCM_LOTTERY -> {
//                view = LayoutInflater.from(context).inflate(R.layout.chatting_item_word_list, parent, false)
//            }
//            EGGS_UPGRADE -> {
//                view = LayoutInflater.from(context).inflate(R.layout.chatting_item_word_list, parent, false)
//            }
//            GIFT_LUCK_REWAED -> {
//                view = LayoutInflater.from(context).inflate(R.layout.chatting_item_reward_list, parent, false)
//            }
            GIFT_LUCK_CHEST -> {
                view = LayoutInflater.from(context).inflate(R.layout.room_public_screen_item_bx, parent, false)
            }
//            RADIO_ATTENTION, RADIO_JOIN_GUARD -> {
//                view = LayoutInflater.from(context).inflate(R.layout.chatting_item_attention_or_guard_list, parent, false)
//            }
            else -> {
                view = LayoutInflater.from(context).inflate(R.layout.room_item_word_list, parent, false)
            }
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) = when (itemViewType) {
//                OFFICE_MSG -> {
//                    itemView.name.movementMethod = LinkMovementMethod.getInstance()
//                    itemView.name.text = getChatString(list[position])
//                }
            OFFICE_MSG, BAN_USER_WORD, NOTICE, ROOM_COLLECT, OPEN_CHAT, CLOSE_CHAT, CLEAR_CHAT, KICK_OUT -> {
                itemView.name.movementMethod = LinkMovementMethod.getInstance()
                itemView.name.text = getChatString(list[position])
            }
            JOIN_CHAT -> {//进入房间
                if (list[position].fromUserInfo.activity_pic.isNotEmpty()) {
                    itemView.label_join_rv.visibility = View.VISIBLE
                    itemView.label_join_rv.layoutManager = GridLayoutManager(itemView.context, 1, GridLayoutManager.HORIZONTAL, false)
                    itemView.label_join_rv.adapter = WordListItemLabelAdapter(list[position].fromUserInfo.activity_pic)
                } else {
                    itemView.label_join_rv.visibility = View.GONE
                }
                if (TextUtils.isEmpty(list[position].fromUserInfo.medal)) {
                    itemView.iv_medal.visibility = View.GONE
                } else {
                    itemView.iv_medal.visibility = View.VISIBLE
                    ImgUtil.loadImg(context, list[position].fromUserInfo.medal, itemView.iv_medal)
                }

                itemView.user_name_join_tv.setNobleText(list[position].fromUserInfo.nickname, list[position].fromUserInfo.rank_id)
//
                itemView.wealth_level_join_iv.setWealthLevel(list[position].fromUserInfo.wealth_level.grade)
                if (list[position].fromUserInfo.user_role == 0) {
                    itemView.role_join_iv.visibility = View.GONE
                } else {
                    itemView.role_join_iv.visibility = View.VISIBLE
                    itemView.role_join_iv.setImageResource(if (list[position].fromUserInfo.user_role == 2)
                        R.drawable.room_icon_user_flag_owner else R.drawable.room_icon_user_flag_manager)
                }
                itemView.liang_join_iv.visibility = if (list[position].fromUserInfo.good_number_state == 1 && list[position].fromUserInfo.mystery == 0) View.VISIBLE else View.GONE
//                    itemView.first_charge_join_iv.visibility = if (list[position].fromUserInfo.first_sign == 1 && list[position].fromUserInfo.mystery == 0) View.VISIBLE else View.GONE
                itemView.setOnClickListener { mListener.onUserItemClick(it, list[position].fromUserInfo) }
                if (list[position].fromUserInfo.user_id != DataHelper.getUID()) {
                    itemView.welcome_tv.visibility = View.VISIBLE
                    itemView.welcome_tv.setOnClickListener {
                        ChatRoomManager.sendMsg(MsgType.TEXT_MSG, "欢迎${list[position].fromUserInfo.nickname}",
                            ChatRoomManager.getRoomId())
                    }
                } else {
                    itemView.welcome_tv.visibility = View.GONE
                }
            }
            EMOJI -> {
                if (list[position].fromUserInfo.activity_pic.isNotEmpty() && list[position].fromUserInfo.mystery == 0) {
                    itemView.label_rv.visibility = View.VISIBLE
                    itemView.label_rv.adapter = WordListItemLabelAdapter(list[position].fromUserInfo.activity_pic)
                    itemView.label_rv.layoutManager = GridLayoutManager(itemView.context, 1, GridLayoutManager.HORIZONTAL, false)
                } else {
                    itemView.label_rv.visibility = View.GONE
                }
                if (TextUtils.isEmpty(list[position].fromUserInfo.medal)) {
                    itemView.iv_medal.visibility = View.GONE
                } else {
                    itemView.iv_medal.visibility = View.VISIBLE
                    ImgUtil.loadImg(context!!, list[position].fromUserInfo.medal, itemView.iv_medal)
                }
//                itemView.user_seat_iv.setBackgroundResource(0)
//                ImgUtil.loadGifOrWebp(context, list[position].fromUserInfo.seat_frame, itemView.user_seat_iv, Int.MAX_VALUE)
//                ImgUtil.loadFaceIcon(context, list[position].fromUserInfo.face, itemView.user_icon_iv)
                itemView.user_name_tv.setNobleText(list[position].fromUserInfo.nickname, list[position].fromUserInfo.rank_id)
                itemView.wealth_level_iv.setWealthLevel(list[position].fromUserInfo.wealth_level.grade)
                if (list[position].fromUserInfo.user_role == 0) {
                    itemView.role_iv.visibility = View.GONE
                } else {
                    itemView.role_iv.visibility = View.VISIBLE
                    itemView.role_iv.setImageResource(if (list[position].fromUserInfo.user_role == 2)
                        R.drawable.room_icon_user_flag_owner else R.drawable.room_icon_user_flag_manager)
                }
                if (list[position].fromUserInfo.good_number_state == 1) {
                    itemView.liang_emoji_iv.visibility = View.VISIBLE
                } else {
                    itemView.liang_emoji_iv.visibility = View.GONE
                }
//                itemView.user_icon_iv.setOnClickListener { mListener.onUserItemClick(it, list[position].fromUserInfo) }
                itemView.user_name_tv.setOnClickListener { mListener.onUserItemClick(it, list[position].fromUserInfo) }
//                itemView.liang_tv.visibility = if (list[position].fromUserInfo.good_number_state == 1 && list[position].fromUserInfo.mystery == 0) View.VISIBLE else View.GONE
//                    itemView.first_charge_iv.visibility = if (list[position].fromUserInfo.first_sign == 1 && list[position].fromUserInfo.mystery == 0) View.VISIBLE else View.GONE
//                itemView.msg_content_tv.movementMethod = LinkMovementMethod.getInstance()
//                if(list[position].fromUserInfo.good_number_state == 1){
//                    itemView.liang_tv.show(list[position].fromUserInfo.good_number.toString(),list[position].fromUserInfo.good_number_state == 1)
//                }else{
//                    itemView.liang_tv.show(list[position].fromUserInfo.user_id.toString(),list[position].fromUserInfo.good_number_state == 1)
//                }
//                var content = "(Img)"
//                val string = SpannableString(content)
//                var drawable: Drawable? = null
//                    var drawable1: Drawable? = null
//                    var drawable2: Drawable? = null
                when (list[position].emojiBean!!.emoji_id) {
                    EmojiConfig.EMOJI_SZ -> {
                        ImgUtil.loadImg(context, DiceUtil.diceResult[list[position].emojiBean!!.emoji_result], itemView.msg_content_iv)
                    }
                    EmojiConfig.EMOJI_MXJ -> {
                        ImgUtil.loadImg(context, MxjUtil.getIcon(list[position].emojiBean!!.emoji_result), itemView.msg_content_iv)
                    }
                    else -> {
                        ImgUtil.loadImg(context, list[position].emojiBean!!.emoji_gif, itemView.msg_content_iv)
                    }
//                        35 -> drawable = context!!.resources.getDrawable(SpinUtil.ballPoint[list[position].emojiBean!!.emoji_result])
//                        75 -> drawable = context!!.resources.getDrawable(CoinUtil.coins[list[position].emojiBean!!.emoji_result])
//                        77 -> drawable = context!!.resources.getDrawable(MicNumberUtil.micNumber[list[position].emojiBean!!.emoji_result])
//                        89 -> {
//                            drawable = context!!.resources.getDrawable(GameNumberUtil.orangeNumber[list[position].emojiBean!!.emoji_result.toString()[0].toString().toInt() - 1])
//                            drawable1 = context!!.resources.getDrawable(GameNumberUtil.greenNumber[list[position].emojiBean!!.emoji_result.toString()[1].toString().toInt() - 1])
//                            drawable2 = context!!.resources.getDrawable(GameNumberUtil.redNumber[list[position].emojiBean!!.emoji_result.toString()[2].toString().toInt() - 1])
//                        }
                }
//                    if (list[position].emojiBean!!.emoji_id == 89) {
//                        drawable?.setBounds(0, 0, DensityUtil.dp2px(context, 18f), DensityUtil.dp2px(context, 18f))
//                        drawable1?.setBounds(0, 0, DensityUtil.dp2px(context, 18f), DensityUtil.dp2px(context, 18f))
//                        drawable2?.setBounds(0, 0, DensityUtil.dp2px(context, 18f), DensityUtil.dp2px(context, 18f))
//                        string.setSpan(URLImageSpan(drawable), string.indexOf("(Img)"), string.indexOf("(Img)") + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//                        string.setSpan(URLImageSpan(drawable1), string.indexOf("(Img)") + 3, string.indexOf("(Img)") + 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//                        string.setSpan(URLImageSpan(drawable2), string.indexOf("(Img)") + 4, string.indexOf("(Img)") + 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//
//                    } else {
//                        drawable?.setBounds(0, 0, DensityUtil.dp2px(context, 34f), DensityUtil.dp2px(context, 34f))
//                        string.setSpan(URLImageSpan(drawable), string.indexOf("(Img)"), string.indexOf("(Img)") + 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//                    }
//                drawable?.setBounds(0, 0, DensityUtil.dp2px(context, 34f), DensityUtil.dp2px(context, 34f))
//                string.setSpan(URLImageSpan(drawable), string.indexOf("(Img)"), string.indexOf("(Img)") + 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//                itemView.msg_content_tv.text = string
            }
//                BAN_USER_WORD -> {//禁言
//
//                }
            GIFT -> {//送礼
                itemView.from_username_tv.setNobleText(list[position].fromUserInfo.nickname, list[position].fromUserInfo.rank_id)
                itemView.to_username_tv.setNobleText(list[position].toUserInfo.nickname, list[position].toUserInfo.rank_id)
                ImgUtil.loadImg(context!!, list[position].giftBean!!.giftIcon, itemView.gift_iv)
                itemView.gift_num_tv.text = "x${list[position].giftBean!!.giftNum}"
                itemView.from_username_tv.setOnClickListener { mListener.onUserItemClick(it, list[position].fromUserInfo) }
                itemView.to_username_tv.setOnClickListener { mListener.onUserItemClick(it, list[position].toUserInfo) }

            }
            RED_PACKET_MSG -> {
                if (list[position].fromUserInfo.activity_pic.isNotEmpty() && list[position].fromUserInfo.mystery == 0) {
                    itemView.label_rv.visibility = View.VISIBLE
                    itemView.label_rv.layoutManager = GridLayoutManager(itemView.context, 1, GridLayoutManager.HORIZONTAL, false)
                    itemView.label_rv.adapter = WordListItemLabelAdapter(list[position].fromUserInfo.activity_pic)
                } else {
                    itemView.label_rv.visibility = View.GONE
                }
                if (TextUtils.isEmpty(list[position].fromUserInfo.medal)) {
                    itemView.iv_medal.visibility = View.GONE
                } else {
                    itemView.iv_medal.visibility = View.VISIBLE
                    ImgUtil.loadImg(context!!, list[position].fromUserInfo.medal, itemView.iv_medal)
                }
//                itemView.user_seat_iv.setBackgroundResource(0)
//                ImgUtil.loadGifOrWebp(context!!, list[position].fromUserInfo.seat_frame, itemView.user_seat_iv, Int.MAX_VALUE)
//                ImgUtil.loadFaceIcon(context!!, list[position].fromUserInfo.face, itemView.user_icon_iv)
                itemView.user_name_tv.setNobleText(list[position].fromUserInfo.nickname, list[position].fromUserInfo.rank_id)
                itemView.msg_content_tv.text = getChatString(list[position])
                itemView.wealth_level_iv.setWealthLevel(list[position].fromUserInfo.wealth_level.grade)
                if (list[position].fromUserInfo.user_role == 0) {
                    itemView.role_iv.visibility = View.GONE
                } else {
                    itemView.role_iv.visibility = View.VISIBLE
                    itemView.role_iv.setImageResource(if (list[position].fromUserInfo.user_role == 2)
                        R.drawable.room_icon_user_flag_owner else R.drawable.room_icon_user_flag_manager)
                }
//                if(list[position].fromUserInfo.good_number_state == 1){
//                    itemView.liang_tv.show(list[position].fromUserInfo.good_number.toString(),list[position].fromUserInfo.good_number_state == 1)
//                }else{
//                    itemView.liang_tv.show(list[position].fromUserInfo.user_id.toString(),list[position].fromUserInfo.good_number_state == 1)
//                }
//                itemView.liang_iv.visibility = if (list[position].fromUserInfo.good_number_state == 1 && list[position].fromUserInfo.mystery == 0) View.VISIBLE else View.GONE
//                    itemView.first_charge_iv.visibility = if (list[position].fromUserInfo.first_sign == 1 && list[position].fromUserInfo.mystery == 0) View.VISIBLE else View.GONE
//                itemView.user_icon_iv.setOnClickListener { mListener.onUserItemClick(it, list[position].fromUserInfo) }
                itemView.user_name_tv.setOnClickListener { mListener.onUserItemClick(it, list[position].fromUserInfo) }
                itemView.msg_content_tv.setOnClickListener {
                    mListener.onWordItemClick(MsgType.RED_PACKET_MSG, list[position].chatId)
                }
            }
            WINNING_MSG -> {
                itemView.name.movementMethod = LinkMovementMethod.getInstance()
                val content = if (list[position].chatId != DataHelper.getIMDevelop().imBigGroupID) {
                    itemView.name.setBackgroundResource(R.drawable.room_chat_history_bg)
                    "恭喜 ${list[position].fromUserInfo.nickname} 在许愿活动中获得 ${list[position].giftBean?.giftName}(${list[position].giftBean?.giftPrice}钻石) x${list[position].giftBean?.giftNum}"
                } else {
                    if (list[position].roomId == ChatRoomManager.getRoomId()) {
                        itemView.name.setBackgroundResource(R.drawable.room_chat_history_bg)
                        "恭喜 ${list[position].fromUserInfo.nickname} 在许愿活动中获得 ${list[position].giftBean?.giftName}(${list[position].giftBean?.giftPrice}钻石) x${list[position].giftBean?.giftNum}"
                    } else {
                        itemView.name.setBackgroundResource(R.drawable.room_chat_history_bg)
                        "【全服】 ${list[position].fromUserInfo.nickname} 在许愿活动中获得 " +
                                "${list[position].giftBean?.giftName}(${list[position].giftBean?.giftPrice}钻石) x${list[position].giftBean?.giftNum}"
                    }
                }
                ImgUtil.loadGiftImg(context, list[position].giftBean!!.giftIcon, itemView.iv_icon)
                itemView.name.text = content
                itemView.name.setOnClickListener {
                    if (DataHelper.getUserInfo()!!.wealth_level.grade >= 1) {
                        mListener.onWordItemClick(MsgType.WINNING_MSG, list[position].chatId)
                    }
                }

            }
            ZS_WINNING_MSG -> {
                itemView.zs_name.movementMethod = LinkMovementMethod.getInstance()
                if (list[position].type == 2) {
                    itemView.zs_fl_content.setBackgroundResource(R.drawable.room_bg_item_gongping_zs)
                    val content = "恭喜 ${list[position].fromUserInfo.nickname} 在庆典时刻中,获得了 ${list[position].giftBean?.giftName}(${list[position].giftBean?.giftPrice}钻石) x${list[position].giftBean?.giftNum}"
                    itemView.zs_name.text = content
                } else {
                    itemView.zs_fl_content.setBackgroundResource(R.drawable.room_chat_history_bg)
                    val content = "恭喜 ${list[position].fromUserInfo.nickname} 在参加游园活动时,获得了 ${list[position].giftBean?.giftName}(${list[position].giftBean?.giftPrice}钻石) x${list[position].giftBean?.giftNum}"
                    itemView.zs_name.text = content
                }

                ImgUtil.loadGiftImg(context, list[position].giftBean!!.giftIcon, itemView.zs_iv_icon)

            }
            FULL_SERVICE_TREE_LUCKTIME -> {
                itemView.zs_name.movementMethod = LinkMovementMethod.getInstance()
                val content = "庆典时刻开启~奇妙游乐园爆率限时升级限定礼物拿到手软~"
                itemView.zs_name.text = content
            }
            NEW_USER_PAY_GIFT_PACK -> {
                val string = SpannableString(list[position].fromUserInfo.nickname + " 获得了新户首充大礼包")
                string.setSpan(ForegroundColorSpan(Color.parseColor("#70E6FF")), 0, list[position].fromUserInfo.nickname.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                string.setSpan(ForegroundColorSpan(Color.parseColor("#FFFFFF")), list[position].fromUserInfo.nickname.length, string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                itemView.name.text = string
                itemView.tv_flirt.setOnClickListener {
                    MsgManager.INSTANCE.sendFollowMsg(list[position].fromUserInfo.user_id.toString(), object : SendMsgListener {
                        override fun onSendSuc(timMessage: TIMMessage) {
                            if (getUserInfo()!!.wealth_level.grade == 0 && getUserInfo()!!.charm_level.grade == 0) {
                                i(context, "财富或魅力等级大于0级才能私聊")
                                return
                            }
                            ARouter.getInstance().build("/imkit/privatechat")
                                .withString("CHAT_ID", list[position].fromUserInfo.user_id.toString())
                                .withString("FROM_USER_NICKNAME", list[position].fromUserInfo.nickname)
                                .withString("FROM_USER_AVTER", list[position].fromUserInfo.face)
                                .navigation()
                        }

                        override fun onSendFail(i: Int, s: String) {}
                    })

                }


            }
            GIFT_LUCK_CHEST -> {
//                    itemView.name.text = Html.fromHtml(getFDString(list[position]).toString())
                itemView.chest_from_username_tv.setNobleText(list[position].fromUserInfo.nickname, list[position].fromUserInfo.rank_id)
                itemView.chest_to_username_tv.setNobleText(list[position].toUserInfo.nickname, list[position].toUserInfo.rank_id)
                ImgUtil.loadGiftImg(context!!, list[position].giftBean!!.giftIcon, itemView.chest_gift_iv)
                itemView.chest_tv.text = "【${list[position].giftBean!!.giftChestName}】"
//                ImgUtil.loadGiftImg(context!!, list[position].giftBean!!.giftChestUrl, itemView.chest_iv)
                itemView.tv_price.text = "(" + list[position].giftBean!!.giftPrice + "钻石)"
                itemView.chest_gift_num_tv.text = "x${list[position].giftBean!!.giftNum}"
                itemView.chest_from_username_tv.setOnClickListener { mListener.onUserItemClick(it, list[position].fromUserInfo) }
                itemView.chest_to_username_tv.setOnClickListener { mListener.onUserItemClick(it, list[position].toUserInfo) }
            }
            TEXT_MSG, MSG_GAME_XX -> {
                if (list[position].fromUserInfo.activity_pic.isNotEmpty() && list[position].fromUserInfo.mystery == 0) {
                    itemView.label_rv.visibility = View.VISIBLE
                    itemView.label_rv.layoutManager = GridLayoutManager(itemView.context, 1, GridLayoutManager.HORIZONTAL, false)
                    itemView.label_rv.adapter = WordListItemLabelAdapter(list[position].fromUserInfo.activity_pic)
                } else {
                    itemView.label_rv.visibility = View.GONE
                }
                if (TextUtils.isEmpty(list[position].fromUserInfo.medal_img)) {
                    itemView.iv_medal_img.visibility = View.GONE
                } else {
                    itemView.tv_medal_name.setText(list[position].fromUserInfo.medal_name)
                    itemView.iv_medal_img.visibility = View.VISIBLE
                    ImgUtil.loadImg(context!!, list[position].fromUserInfo.medal_img, itemView.iv_medal_img)
                }
                if (TextUtils.isEmpty(list[position].fromUserInfo.medal)) {
                    itemView.iv_medal.visibility = View.GONE
                } else {
                    itemView.iv_medal.visibility = View.VISIBLE
                    ImgUtil.loadImg(context!!, list[position].fromUserInfo.medal, itemView.iv_medal)
                }
//                itemView.msg_content_tv.setBackgroundResource(getContentBg(list[position].fromUserInfo.rank_id))
//                itemView.user_seat_iv.setBackgroundResource(0)
//                ImgUtil.loadGifOrWebp(context!!, list[position].fromUserInfo.seat_frame, itemView.user_seat_iv, Int.MAX_VALUE)
//                ImgUtil.loadFaceIcon(context!!, list[position].fromUserInfo.face, itemView.user_icon_iv)
                itemView.user_name_tv.setNobleText(list[position].fromUserInfo.nickname, list[position].fromUserInfo.rank_id)
                itemView.msg_content_tv.text = getChatString(list[position])
//                    ImgUtil.loadImg(context!!, list[position].fromUserInfo.wealth_level.icon, itemView.wealth_level_iv, R.drawable.common_charm_icon_placeholder_m)
                itemView.wealth_level_iv.setWealthLevel(list[position].fromUserInfo.wealth_level.grade)
                if (list[position].fromUserInfo.user_role == 0) {
                    itemView.role_iv.visibility = View.GONE
                } else {
                    itemView.role_iv.visibility = View.VISIBLE
                    itemView.role_iv.setImageResource(if (list[position].fromUserInfo.user_role == 2)
                        R.drawable.room_icon_user_flag_owner else R.drawable.room_icon_user_flag_manager)
                }
                if (list[position].fromUserInfo.good_number_state == 1) {
                    itemView.liang_iv.visibility = View.VISIBLE
                } else {
                    itemView.liang_iv.visibility = View.GONE
                }
//                itemView.liang_iv.visibility = if (list[position].fromUserInfo.good_number_state == 1 && list[position].fromUserInfo.mystery == 0) View.VISIBLE else View.GONE
//                    itemView.first_charge_iv.visibility = if (list[position].fromUserInfo.first_sign == 1 && list[position].fromUserInfo.mystery == 0) View.VISIBLE else View.GONE
//                itemView.user_icon_iv.setOnClickListener { mListener.onUserItemClick(it, list[position].fromUserInfo) }
                itemView.user_name_tv.setOnClickListener { mListener.onUserItemClick(it, list[position].fromUserInfo) }
                itemView.msg_content_tv.setOnLongClickListener {
                    val cm = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    var mClipData = ClipData.newPlainText("Label", list[position].content)
                    cm.primaryClip = mClipData
                    ToastUtil.suc(context!!, "已复制到剪切板")
                    false
                }
            }
            FULL_SERVICE_MSG -> {
                itemView.iv_face.isSelected = list[position].fromUserInfo.gender == BaseConfig.USER_INFO_GENDER_MAN
                itemView.isSelected = list[position].fromUserInfo.gender == BaseConfig.USER_INFO_GENDER_MAN
                ImgUtil.loadFaceIcon(context, list[position].fromUserInfo.face, itemView.iv_face)
                itemView.tv_name.text = list[position].fromUserInfo.nickname
                itemView.tv_content.text = list[position].content
                itemView.tv_content.setOnClickListener {
                    mListener.onEnterRoomClick(list[position].fromUserInfo)
                }
            }
            GAME_XX_RESULT -> {
                if (list[position].fromUserInfo.activity_pic.isNotEmpty() && list[position].fromUserInfo.mystery == 0) {
                    itemView.label_rv.visibility = View.VISIBLE
                    itemView.label_rv.layoutManager = GridLayoutManager(itemView.context, 1, GridLayoutManager.HORIZONTAL, false)
                    itemView.label_rv.adapter = WordListItemLabelAdapter(list[position].fromUserInfo.activity_pic)
                } else {
                    itemView.label_rv.visibility = View.GONE
                }
                if (TextUtils.isEmpty(list[position].fromUserInfo.medal)) {
                    itemView.iv_medal.visibility = View.GONE
                } else {
                    itemView.iv_medal.visibility = View.VISIBLE
                    ImgUtil.loadImg(context!!, list[position].fromUserInfo.medal, itemView.iv_medal)
                }
//                itemView.user_seat_iv.setBackgroundResource(0)
//                ImgUtil.loadGifOrWebp(context!!, list[position].fromUserInfo.seat_frame, itemView.user_seat_iv, Int.MAX_VALUE)
//                ImgUtil.loadCircleImg(context!!, list[position].fromUserInfo.face, itemView.user_icon_iv)
                itemView.user_name_tv.setText(list[position].fromUserInfo.nickname)
//                    ImgUtil.loadImg(context!!, list[position].fromUserInfo.wealth_level.icon, itemView.wealth_level_iv, R.drawable.common_charm_icon_placeholder_m)
                itemView.wealth_level_iv.setWealthLevel(list[position].fromUserInfo.wealth_level.grade)
                if (list[position].fromUserInfo.user_role == 0) {
                    itemView.role_iv.visibility = View.GONE
                } else {
                    itemView.role_iv.visibility = View.VISIBLE
                    itemView.role_iv.setImageResource(if (list[position].fromUserInfo.user_role == 2)
                        R.drawable.room_icon_user_flag_owner else R.drawable.room_icon_user_flag_manager)
                }
//                if(list[position].fromUserInfo.good_number_state == 1){
//                    itemView.liang_tv.show(list[position].fromUserInfo.good_number.toString(),list[position].fromUserInfo.good_number_state == 1)
//                }else{
//                    itemView.liang_tv.show(list[position].fromUserInfo.user_id.toString(),list[position].fromUserInfo.good_number_state == 1)
//                }//                itemView.liang_iv.visibility = if (list[position].fromUserInfo.good_number_state == 1 && list[position].fromUserInfo.mystery == 0) View.VISIBLE else View.GONE
//                    itemView.first_charge_iv.visibility = if (list[position].fromUserInfo.first_sign == 1 && list[position].fromUserInfo.mystery == 0) View.VISIBLE else View.GONE
//                itemView.user_icon_iv.setOnClickListener { mListener.onUserItemClick(it, list[position].fromUserInfo) }
                itemView.user_name_tv.setOnClickListener { mListener.onUserItemClick(it, list[position].fromUserInfo) }
                itemView.xx_game_1.visibility = View.GONE
                itemView.xx_game_2.visibility = View.GONE
                itemView.xx_game_3.visibility = View.GONE
                itemView.xx_game_4.visibility = View.GONE
                itemView.xx_game_5.visibility = View.GONE
                when (list[position].dataGameXXBean.size) {
                    1 -> {
                        itemView.xx_game_1.visibility = View.VISIBLE
                        itemView.xx_game_1.small()
                        itemView.xx_game_1.setLevel(list[position].dataGameXXBean[0])
                    }
                    2 -> {
                        itemView.xx_game_1.visibility = View.VISIBLE
                        itemView.xx_game_1.small()
                        itemView.xx_game_1.setLevel(list[position].dataGameXXBean[0])
                        itemView.xx_game_2.visibility = View.VISIBLE
                        itemView.xx_game_2.small()
                        itemView.xx_game_2.setLevel(list[position].dataGameXXBean[1])
                    }
                    3 -> {
                        itemView.xx_game_1.visibility = View.VISIBLE
                        itemView.xx_game_1.small()
                        itemView.xx_game_1.setLevel(list[position].dataGameXXBean[0])
                        itemView.xx_game_2.visibility = View.VISIBLE
                        itemView.xx_game_2.small()
                        itemView.xx_game_2.setLevel(list[position].dataGameXXBean[1])
                        itemView.xx_game_3.visibility = View.VISIBLE
                        itemView.xx_game_3.small()
                        itemView.xx_game_3.setLevel(list[position].dataGameXXBean[2])
                    }
                    5 -> {
                        itemView.xx_game_1.visibility = View.VISIBLE
                        itemView.xx_game_1.small()
                        itemView.xx_game_1.setLevel(list[position].dataGameXXBean[0])
                        itemView.xx_game_2.visibility = View.VISIBLE
                        itemView.xx_game_2.small()
                        itemView.xx_game_2.setLevel(list[position].dataGameXXBean[1])
                        itemView.xx_game_3.visibility = View.VISIBLE
                        itemView.xx_game_3.small()
                        itemView.xx_game_3.setLevel(list[position].dataGameXXBean[2])
                        itemView.xx_game_4.visibility = View.VISIBLE
                        itemView.xx_game_4.small()
                        itemView.xx_game_4.setLevel(list[position].dataGameXXBean[3])
                        itemView.xx_game_5.visibility = View.VISIBLE
                        itemView.xx_game_5.small()
                        itemView.xx_game_5.setLevel(list[position].dataGameXXBean[4])
                    }
                    else -> {

                    }
                }

            }
            else -> {
                itemView.name.movementMethod = LinkMovementMethod.getInstance()
                itemView.name.text = list[position].opt.toString()
            }
        }
    }

//    private fun setHtmlImg(spannableStringBuilder: SpannableStringBuilder, levelImg: URLImageSpan) {
//        var imageSpans = spannableStringBuilder.getSpans(0, spannableStringBuilder.length, ImageSpan::class.java)
//        imageSpans.forEach {
//            var drawable = it.drawable
//            var imageSpan = URLImageSpan(drawable)
//            var start = spannableStringBuilder.getSpanStart(it)
//            var end = spannableStringBuilder.getSpanEnd(it)
//            if (it.source != null && it.source.contains(URLKEY_LEVEL)) {
//                spannableStringBuilder.setSpan(levelImg, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//            } else {
//                spannableStringBuilder.setSpan(imageSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//            }
//            spannableStringBuilder.removeSpan(it)
//        }
//    }

//    private fun getLevelImg(level: Int): URLImageSpan {
//        var bitmap = getCacheBm(level)
//        if (bitmap == null) {
//            var levelView = LevelView(context)
//            levelView.measure(View.MeasureSpec.makeMeasureSpec((1 shl 30) - 1, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec((1 shl 30) - 1, View.MeasureSpec.AT_MOST))
//            levelView.setWealthLevel(level)
//            bitmap = convertViewToBitmap(levelView)
//            putCacheBm(level, bitmap!!)
//        }
//        return URLImageSpan(context, bitmap, URLImageSpan.ALIGN_FONTCENTER)
//    }

//    private fun convertViewToBitmap(view: View): Bitmap? {
//        view.measure(View.MeasureSpec.makeMeasureSpec((1 shl 30) - 1, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec((1 shl 30) - 1, View.MeasureSpec.AT_MOST))
//        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
//        view.buildDrawingCache()
//        return view.drawingCache
//    }

//    private var iconPool: MutableMap<Int, Bitmap> = HashMap()

//    private fun getCacheBm(key: Int): Bitmap? {
//        return iconPool[key]
//    }

//    private val MAX_ICON_CACHE = 10
//    private val MIN_ICON_CACHE = 5

//    private fun putCacheBm(key: Int, bitmap: Bitmap) {
//        if (iconPool.size > MAX_ICON_CACHE) {
//            var index = 0
//            val it: MutableIterator<Map.Entry<Int, Bitmap>> = iconPool.entries.iterator()
//            while (it.hasNext()) {
//                index++
//                if (index > MIN_ICON_CACHE) {
//                    break
//                }
//                val item = it.next()
//                it.remove()
//            }
//        }
//        iconPool[key] = bitmap
//    }

    inner class WordClickSpan(bean: UserInfo) : ClickableSpan() {
        private val mBean = bean
        override fun onClick(widget: View) {
            mListener.onUserItemClick(widget, mBean)
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.isUnderlineText = false
        }
    }

//    private fun getFDString(msg: MsgBean): StringBuilder {
//        var spannableString = StringBuilder("")
//        spannableString.append("恭喜！！！ ")
//        spannableString.append("<font color='#FFDD7E'>${msg.fromUserInfo?.nickname}</font>")
//        spannableString.append(" 送给 ")
//        spannableString.append("<font color='#FFDD7E'>${msg.toUserInfo?.nickname}</font>")
//        spannableString.append("<font color='#FFDD7E'>${msg.giftBean!!.giftNum}</font>")
//        spannableString.append("个")
//        spannableString.append("<font color='#FFDD7E'>${msg.giftBean!!.giftChestName}</font>")
//        spannableString.append(" 里面竟然藏着 ")
//        spannableString.append("<font color='#FFDD7E'>${msg.giftBean!!.giftName}</font>")
//        spannableString.append(" ！！！ ")
//        return spannableString
//    }

    private fun getChatString(msg: MsgBean): SpannableString {
        val from = msg.fromUserInfo?.nickname
        var content = msg.content
        val receive = msg.toUserInfo?.nickname
        val type = msg.opt

        when (type) {
//            MsgType.JOIN_CHAT -> {//进入房间
//                val firstSubStringSize = from.length
//                val string = SpannableString("$from")
//                string.setSpan(ForegroundColorSpan(Color.parseColor("#7BCAC6")), 0, firstSubStringSize, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//                string.setSpan(WordClickSpan(msg.fromUserInfo), 0, firstSubStringSize, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//                return string
//            }
            MsgType.GIFT -> {//送礼
                val firstSubStringSize = from.length
                val secondSubStringSize = firstSubStringSize + 3
                val thirdSubStringSize = secondSubStringSize + receive.length
                val string = SpannableString("$from 送 $receive $content")
                string.setSpan(ForegroundColorSpan(Color.parseColor("#FFDD7E")), 0, firstSubStringSize, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                string.setSpan(ForegroundColorSpan(Color.parseColor("#C2E64E")), firstSubStringSize, secondSubStringSize, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                string.setSpan(ForegroundColorSpan(Color.parseColor("#FFDD7E")), secondSubStringSize, thirdSubStringSize, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                string.setSpan(ForegroundColorSpan(Color.parseColor("#C2E64E")), thirdSubStringSize + 1, thirdSubStringSize + 1 + content.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                string.setSpan(WordClickSpan(msg.fromUserInfo), 0, firstSubStringSize, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                string.setSpan(WordClickSpan(msg.toUserInfo), secondSubStringSize, thirdSubStringSize, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                return string
            }
            MsgType.NOTICE -> {//公告
                val string = SpannableString("$content")
                string.setSpan(ForegroundColorSpan(Color.parseColor("#30FAC9")), 0, string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                return string
            }
            MsgType.CLEAR_CHAT, MsgType.OPEN_CHAT, MsgType.CLOSE_CHAT, MsgType.BAN_USER_WORD, MsgType.KICK_OUT -> {
                val string = SpannableString(content)
                string.setSpan(ForegroundColorSpan(Color.parseColor("#67FFB6")), 0, string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                return string
            }
            MsgType.OFFICE_MSG -> {//系统消息
                val string = SpannableString(content)
                string.setSpan(ForegroundColorSpan(Color.parseColor("#67FFB6")), 0, content.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                return string
            }
            MsgType.ROOM_COLLECT -> { //房间收藏
                val string = SpannableString("$from 收藏了房间")
                string.setSpan(ForegroundColorSpan(Color.parseColor("#ED52F9")), 0, from.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                string.setSpan(ForegroundColorSpan(Color.parseColor("#ED52F9")), from.length, string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                string.setSpan(WordClickSpan(msg.fromUserInfo), 0, from.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                return string
            }

            MsgType.CB_FULL_SERVICE_REWARD -> {
                content = from + " 在房间中挖到一个宝藏" + content
                val string = SpannableString(content)
                string.setSpan(ForegroundColorSpan(Color.parseColor("#F5BB21")), 0, content.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                return string
            }
            MsgType.CB_FULL_SERVICE_PUT -> {
                content = from + " 在房间里投放了一个宝藏" + content
                val string = SpannableString(content)
                string.setSpan(ForegroundColorSpan(Color.parseColor("#F5BB21")), 0, content.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                return string
            }
            MsgType.RED_PACKET_MSG -> {
                content = "【红包】"
                val string = SpannableString(content)
                string.setSpan(ForegroundColorSpan(Color.parseColor("#F5BB21")), 0, content.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                return string
            }
//            MsgType.SPECIAL_MSG -> {
//                val string = SpannableString(content)
//                string.setSpan(ForegroundColorSpan(Color.parseColor("#F5BB21")), 0, content.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//                return string
//            }
            else -> {
//                val firstSubStringSize = from.length + 1
//                val secondSubStringSize = firstSubStringSize + content.length
//                val string = SpannableString("$from：$content")
//                string.setSpan(ForegroundColorSpan(Color.parseColor("#F0E198")), 0, firstSubStringSize, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//                string.setSpan(ForegroundColorSpan(Color.parseColor("#FFFFFF")), firstSubStringSize, +secondSubStringSize, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//                string.setSpan(WordClickSpan(msg.fromUserInfo), 0, firstSubStringSize, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                return SpannableString(content)
            }
        }
    }

    private fun getContentBg(nobleId: Int): Int {

        if (nobleId == BaseConfig.NOBLE_LEVEL_BJ) {
            return R.drawable.bg_noble_chat_bubble_1
        }
        if (nobleId == BaseConfig.NOBLE_LEVEL_HJ) {
            return R.drawable.bg_noble_chat_bubble_2
        }
        if (nobleId == BaseConfig.NOBLE_LEVEL_GJ) {
            return R.drawable.bg_noble_chat_bubble_3
        }
        if (nobleId == BaseConfig.NOBLE_LEVEL_GW) {
            return R.drawable.bg_noble_chat_bubble_4
        }
        if (nobleId == BaseConfig.NOBLE_LEVEL_DH) {
            return R.drawable.bg_noble_chat_bubble_5
        }
        if (nobleId == BaseConfig.NOBLE_LEVEL_HS) {
            return R.drawable.bg_noble_chat_bubble_6
        }
        return R.drawable.room_chat_history_bg
    }


}
