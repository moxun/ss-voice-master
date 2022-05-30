package com.miaomi.fenbei.imkit.ui

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.miaomi.fenbei.base.bean.EmojiItemBean
import com.miaomi.fenbei.base.core.BaseFragment
import com.miaomi.fenbei.imkit.listener.OnSeletedEmojiListener
import com.miaomi.fenbei.imkit.R
import com.miaomi.fenbei.imkit.ui.adapter.C2CEmojiAdapter
import kotlinx.android.synthetic.main.chatting_fragment_c2c_emoji.*

class C2CEmojiFragment : BaseFragment(){

    companion object {
        @JvmStatic
        fun newInstance(bean: List<EmojiItemBean>): C2CEmojiFragment {
            val emojiFragment = C2CEmojiFragment()
            val bundle = Bundle()
            bundle.putString("bean", Gson().toJson(bean))
            emojiFragment.arguments = bundle
            return emojiFragment
        }
    }


    private lateinit var bean: List<EmojiItemBean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            bean = Gson().fromJson(arguments!!.getString("bean"), object : TypeToken<List<EmojiItemBean>>(){}.type)
        }
    }
    private var statusListen: OnSeletedEmojiListener? = null

    public fun setOnSeletedEmoji(statusListen: OnSeletedEmojiListener) {
        this.statusListen = statusListen
    }

    override fun getLayoutId(): Int {
        return R.layout.chatting_fragment_c2c_emoji
    }

    override fun initView(view: View) {
        emoji_rv.layoutManager = GridLayoutManager(mContext, 4)
        val adapter = C2CEmojiAdapter(bean)
        adapter.setOnSeletedEmoji(OnSeletedEmojiListener {
            statusListen?.onSeletedEmoji(it)
        })
        emoji_rv.adapter = adapter
    }
}