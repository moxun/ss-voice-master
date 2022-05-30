package com.miaomi.fenbei.voice.ui.main.adapter

import android.content.Context
import android.text.TextUtils
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.miaomi.fenbei.base.bean.RecommandUserBean.UsersBean
import com.miaomi.fenbei.base.config.BaseConfig
import com.miaomi.fenbei.base.core.JoinChatCallBack
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.room.ChatRoomManager.joinChat
import com.miaomi.fenbei.voice.R
import com.opensource.svgaplayer.*
import com.opensource.svgaplayer.SVGAParser.ParseCompletion
import kotlinx.android.synthetic.main.item_playing_user.view.*


class PlayingUserAdapter : BaseQuickAdapter<UsersBean, BaseViewHolder>(R.layout.item_playing_user, ArrayList<UsersBean>()) {

    override fun convert(helper: BaseViewHolder, item: UsersBean) {
        ImgUtil.loadPlayUserImg(helper.itemView.context, item.face, helper.itemView.iv_avter)
        ImgUtil.loadCircleImg(helper.itemView.context, item.face, helper.itemView.iv_avter_x)
        helper.itemView.iv_avter_x.isSelected = item.gender == BaseConfig.USER_INFO_GENDER_MAN
        if (item.recommend == 1) {
            helper.itemView.tv_label.visibility = View.VISIBLE
        } else {
            helper.itemView.tv_label.visibility = View.GONE
        }
        helper.itemView.tv_name.text = item.nickname
        helper.itemView.iv_sex.setContent(item.gender == 1, item.age)
        helper.itemView.tv_sign.text = item.signature
        helper.itemView.setOnClickListener { v: View? ->
            if (item.room_id == 0) {
                ARouter.getInstance().build("/app/userhomepage")
                        .withString("user_id", item.user_id.toString())
                        .navigation()
            } else {
                joinChat(mContext, item.room_id.toString(), object : JoinChatCallBack {
                    override fun onSuc() {
                    }

                    override fun onFail(msg: String) {
                        ToastUtil.error(mContext,msg)
                    }
                })
            }
        }
        showSvgaGiftAnim(mContext, helper.itemView.iv_svga, "svga_user_palying.svga")
    }

    private fun showSvgaGiftAnim(context: Context, svgaImageView: SVGAImageView, url: String) {
        val parser = SVGAParser(context)
        svgaImageView.callback = object : SVGACallback {
            override fun onPause() {}
            override fun onFinished() {}
            override fun onRepeat() {}
            override fun onStep(i: Int, v: Double) {}
        }
        parser.decodeFromAssets(url, object : ParseCompletion {
            override fun onComplete(videoItem: SVGAVideoEntity) {
                val dynamicItem = SVGADynamicEntity()
                val drawable = SVGADrawable(videoItem, dynamicItem)
                svgaImageView.setImageDrawable(drawable)
                svgaImageView.startAnimation()
            }

            override fun onError() {}
        })
    }

}