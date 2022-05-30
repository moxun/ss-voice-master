package com.miaomi.fenbei.room.ui.dialog.zs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.google.android.flexbox.*
import com.miaomi.fenbei.base.bean.ZSRewardBean
import com.miaomi.fenbei.room.R
import kotlinx.android.synthetic.main.room_dialog_zs_award.*

class ZSAwardDialog(context: Context,var watertype:Int,var bean: List<ZSRewardBean>, val callback: WaterAgainCallback): Dialog(context, R.style.common_dialog) {

    private var adapter = ZSRewardGiftAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.room_dialog_zs_award)
        initView()
    }


    private fun initView() {
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.flexDirection = FlexDirection .ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        layoutManager.alignItems = AlignItems.CENTER
        gv_gift.layoutManager = layoutManager
        gv_gift.adapter = adapter
        sure_bt.setOnClickListener {
            dismiss()
            callback.smashAgain()
        }
        close_iv.setOnClickListener {
            dismiss()
        }

        setOnDismissListener { callback.dismiss() }
        cl_content.visibility = View.VISIBLE
        showRewardContent()
    }

    override fun show() {
        super.show()
        val layoutParams = window!!.attributes
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        window!!.decorView.setPadding(0, 0, 0, 0)
        window!!.attributes = layoutParams
    }


    private fun showRewardContent(){
        cl_content.visibility = View.VISIBLE
        if(watertype==2){
            iv_reward_light.setBackgroundResource(R.drawable.room_bg_xy_reward_light)
            sure_bt.setBackgroundResource(R.drawable.room_icon_zs_xy_agin)
        }else{
            iv_reward_light.setBackgroundResource(R.drawable.room_bg_reward_light)
            sure_bt.setBackgroundResource(R.drawable.room_icon_zs_agin)
        }
//        val anim = AnimationUtils.loadAnimation(context, R.anim.km_dialog_enter)
//        cl_content.startAnimation(anim)
        adapter.setData(bean,watertype)
    }


}