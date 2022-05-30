package com.miaomi.fenbei.room.ui.dialog.xy

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.google.android.flexbox.*
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.bean.XYRewardGiftInfo
import com.miaomi.fenbei.base.widget.GridSpacingItemDecoration
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.room_dialog_xy_award.*


/**
 * Created by 
 * on 2019-06-03.
 */
class XYAwardDialog(context: Context, var bean: ArrayList<XYRewardGiftInfo>, val callback: XYHomeFragment.SmashAgainCallback): Dialog(context, R.style.common_dialog) {

    private var XYRewardGiftList: ArrayList<XYRewardGiftInfo> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.room_dialog_xy_award)
        initView()
    }


    private fun initView() {
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.flexDirection = FlexDirection .ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        layoutManager.alignItems = AlignItems.CENTER
//        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//            override fun getSpanSize(position: Int): Int {
//                if (bean.size % 3 > 0) {
//                    when (bean.size % 3) {
//                        1 -> {
//                            when (bean.size) {
//                                1 -> return 4
//                                4 -> return if (position == 3) 4 else 2
//                                7 -> return if (position == 6) 4 else 2
//                            }
//                        }
//                        2 -> {
//                            when (bean.size) {
//                                2 -> return if (position == 0) 3 else 2
//                                5 -> return if (position == 3) 3 else 2
//                                7 -> return if (position == 8) 3 else 2
//                            }
//                        }
//                    }
//                }
//                return 2
//            }
//        }
        gv_gift.layoutManager = layoutManager
        gv_gift.addItemDecoration(GridSpacingItemDecoration(3,DensityUtil.dp2px(10f),false))
        var adapter = XyRewardGiftAdapter()
        adapter.setData(XYRewardGiftList)
        gv_gift.adapter = adapter
        adapter.setData(bean)
//        GlobalScope.launch {
////            withContext(Dispatchers.Main) {
////                var anim = AnimationUtils.loadAnimation(context, R.anim.anim_egg_award_anim)
////                title_layout.startAnimation(anim)
////                title_layout.visibility = View.VISIBLE
////            }
//            delay(480)
//            bean.forEachIndexed { index, giftInfo ->
//                withContext(Dispatchers.Main) {
//                    XYRewardGiftList.add(giftInfo)
//                    adapter.notifyItemChanged(index)
//                }
//                delay(300)
//            }
//            withContext(Dispatchers.Main) {
//                sure_bt.visibility = View.VISIBLE
//            }
//        }
        sure_bt.setOnClickListener {
            dismiss()
            callback.smashAgain()
        }
        close_iv.setOnClickListener {
            dismiss()
        }

        setOnDismissListener { callback.dismiss() }
    }

    override fun show() {
        super.show()
        val layoutParams = window!!.attributes
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        window!!.decorView.setPadding(0, 0, 0, 0)
        window!!.attributes = layoutParams
    }

}