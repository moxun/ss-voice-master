package com.miaomi.fenbei.voice.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog
import com.miaomi.fenbei.voice.R

class CreateRoomDialog(private val onCreateRoomLisener: OnCreateRoomLisener): BaseBottomDialog(){
    override fun getLayoutRes(): Int {
        return R.layout.dialog_create_room
    }

    override fun bindView(v: View) {
        v.findViewById<ImageView>(R.id.iv_close_dialog).setOnClickListener {
            dismiss()
        }
        v.findViewById<TextView>(R.id.tv_create_person).setOnClickListener {
            onCreateRoomLisener.onCreatePerson()
            dismiss()
        }
        v.findViewById<TextView>(R.id.tv_create_party).setOnClickListener {
            onCreateRoomLisener.onCreateParty()
            dismiss()
        }
    }
    interface OnCreateRoomLisener{
        fun onCreatePerson()
        fun onCreateParty()
    }

}