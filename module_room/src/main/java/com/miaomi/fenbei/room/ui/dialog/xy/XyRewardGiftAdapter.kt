package com.miaomi.fenbei.room.ui.dialog.xy

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import com.miaomi.fenbei.base.bean.XYRewardGiftInfo
import com.miaomi.fenbei.base.util.DensityUtil
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.base.util.ScreenUtil
import com.miaomi.fenbei.room.R
import kotlinx.android.synthetic.main.room_item_xy_gift.view.*
import kotlin.math.ceil

/**
 * Created by 
 * on 2019-06-03.
 */
class XyRewardGiftAdapter: RecyclerView.Adapter<XyRewardGiftAdapter.ViewHolder>() {

    private var data: ArrayList<XYRewardGiftInfo> = ArrayList()

    fun setData(list: ArrayList<XYRewardGiftInfo>) {
        data = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = View.inflate(parent.context, R.layout.room_item_xy_gift, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(bean: XYRewardGiftInfo, position: Int) {
//            if (itemView.bg_sv.controller != null && itemView.bg_sv.controller?.animatable != null) {
//                itemView.bg_sv.controller?.animatable?.start()
//            } else {
//                ImgUtil.loadWebpDrawable(itemView.context.applicationContext, R.drawable.chatting_egg_award_bg, itemView.bg_sv, Int.MAX_VALUE)
//            }
            var anim = ScaleAnimation(0f, 1.0f, 0f, 1.0f, Animation.ABSOLUTE,
                    ScreenUtil.getScreenWidth() / 2f - ScreenUtil.getScreenWidth() / 3 * (position % 3),
                    Animation.ABSOLUTE, DensityUtil.dp2px(itemView.context, 400f) / 2f - DensityUtil.dp2px(itemView.context, 400f) / 3 * (ceil((position + 1) / 3f) - 1))
            anim.duration = 300
            itemView.content_layout.startAnimation(anim)
            ImgUtil.loadImg(itemView.context, bean.icon, itemView.gift_iv)
            itemView.gift_name_tv.text = "${bean.name}"
            itemView.gift_price_tv.text = "${bean.price}钻石"
            itemView.gift_num_tv.text = "x"+bean.number
//            var contentStr = StringBuilder("")
//            contentStr.append("<img src='file:///android_asset/chatting_icon_specific_x.png'>")
//            contentStr.append("<img src='file:///android_asset/chatting_icon_specific_${bean.number.toString()[0]}.png'>")
//            if (bean.number > 9) contentStr.append("<img src='file:///android_asset/chatting_icon_specific_${bean.number.toString()[1]}.png'>")
//            var glideImageGeter = GlideImageGeter(itemView.context, itemView.gift_num_tv, 18)
//            var spanned = Html.fromHtml(contentStr.toString(), glideImageGeter, null)
//            var spannableString = SpannableStringBuilder(spanned)
//            itemView.gift_num_tv.text = spannableString
        }
    }
}