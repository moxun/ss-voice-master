package com.miaomi.fenbei.room.ui.fragment


import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import android.view.View
import com.google.gson.Gson
import com.miaomi.fenbei.base.bean.EmojiItemBean
import com.miaomi.fenbei.base.core.BaseFragment
import kotlinx.android.synthetic.main.room_fragment_emoji.*
import com.google.gson.reflect.TypeToken
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.room.ui.adapter.EmojiAdapter


class EmojiFragment : BaseFragment(){

    companion object {
        @JvmStatic
        fun newInstance(bean: List<EmojiItemBean>): EmojiFragment {
            val emojiFragment = EmojiFragment()
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

    override fun getLayoutId(): Int {
        return R.layout.room_fragment_emoji
    }

    override fun initView(view: View) {
        emoji_rv.layoutManager = GridLayoutManager(mContext, 4)
        emoji_rv.adapter = EmojiAdapter(bean)
    }
}
