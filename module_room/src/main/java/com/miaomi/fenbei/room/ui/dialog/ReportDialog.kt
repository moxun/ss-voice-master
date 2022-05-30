package com.miaomi.fenbei.room.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle

import com.miaomi.fenbei.room.R
import kotlinx.android.synthetic.main.room_dialog_report.*


class ReportDialog(context: Context, reportObject:Int,callback: CallBack) : Dialog(context, R.style.common_dialog) {

    interface CallBack {
        fun onCancel(dialog: ReportDialog)
        fun onSubmit(dialog: ReportDialog, type: Int)

    }

    private val mCallBack = callback
    private val mReportObject = reportObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.room_dialog_report)

        if (mReportObject == 0){
            tv_title.text = "举报房间"
        }else{
            tv_title.text = "举报用户"
        }

        tv_left.setOnClickListener {
            dismiss()
            mCallBack.onCancel(this)
        }

        tv_right.setOnClickListener {

            var type = 0

            when (report_radio_group.checkedId) {
                R.id.report_1 -> type = 0
                R.id.report_2 -> type = 1
                R.id.report_3 -> type = 2
                R.id.report_4 -> type = 3
            }
            mCallBack.onSubmit(this,type)


        }
    }

}
