package com.miaomi.fenbei.base.core.emoji

import android.content.Context
import com.miaomi.fenbei.base.bean.EmojiGroupBean
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService

/**
 * Created by 
 * on 2019-09-03.
 */
object EmojiManager {
    var emojiList:ArrayList<EmojiGroupBean> = ArrayList<EmojiGroupBean>()
//    var emojiBean: EmojiBean = EmojiBean(emptyList())

    fun initEmojiData(context: Context) {
        NetService.getInstance(context).getEmojiList(object : Callback<List<EmojiGroupBean>>() {
            override fun onSuccess(nextPage: Int, bean: List<EmojiGroupBean>, code: Int) {
//                emojiBean = bean
                emojiList.clear()
                emojiList.addAll(bean)
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
            }

            override fun isAlive(): Boolean {
                return true
            }
        })
    }
}