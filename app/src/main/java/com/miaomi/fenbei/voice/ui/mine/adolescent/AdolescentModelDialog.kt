package com.miaomi.fenbei.voice.ui.mine.adolescent

import android.app.Dialog
import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.miaomi.fenbei.voice.R
import kotlinx.android.synthetic.main.user_adolescent_model_dialog.*

/**
 * Created by 
 * on 2019-09-26.
 */
class AdolescentModelDialog(context: Context): Dialog(context, R.style.common_dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setWindowAnimations(com.miaomi.fenbei.room.R.style.dialogAnimStyle1)//添加动画

        setContentView(R.layout.user_adolescent_model_dialog)
        adolescent_model_tv.paint.flags = Paint.UNDERLINE_TEXT_FLAG
        adolescent_model_tv.paint.isAntiAlias = true
        sure_tv.setOnClickListener { dismiss() }
        adolescent_model_tv.setOnClickListener {
            ARouter.getInstance().build("/mine/adolescentModel").navigation()
            dismiss()
        }
    }

}