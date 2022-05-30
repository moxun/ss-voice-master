package com.miaomi.fenbei.room.ui.dialog.xb

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.miaomi.fenbei.base.core.BaseFragment
import com.miaomi.fenbei.room.R

class GameXBRuleFragment : BaseFragment() {
    public lateinit var contentTv:TextView
    public lateinit var backIv:ImageView
    override fun getLayoutId(): Int {
        return R.layout.room_fragment_game_xb_rule
    }

    override fun initView(view: View) {
        contentTv = view.findViewById(R.id.tv_content)
        backIv = view.findViewById(R.id.iv_back)
        backIv.setOnClickListener {
            (parentFragment as GameXBDialog).goHome()
        }
    }

    public fun setContent(str:String){
        contentTv.setText(str)
    }
}