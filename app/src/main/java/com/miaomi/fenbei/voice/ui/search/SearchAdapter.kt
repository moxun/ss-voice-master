package com.miaomi.fenbei.voice.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.miaomi.fenbei.room.ChatRoomManager

import com.miaomi.fenbei.base.bean.SearchItemBean
import com.miaomi.fenbei.base.core.BaseActivity
import com.miaomi.fenbei.base.core.JoinChatCallBack
import com.miaomi.fenbei.base.core.dialog.LoadingDialog
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.base.util.StringUtil
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.base.widget.LevelView
import com.miaomi.fenbei.base.widget.SexView
import com.miaomi.fenbei.voice.R

class SearchAdapter(private val mList: List<SearchItemBean>, private val context: BaseActivity) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private var keyWord: String? = null
    private var type: Int? = 0
    private lateinit var loadingDialog: LoadingDialog

    fun setKeyWord(keyWord: String,type:Int) {
        this.keyWord = keyWord
        this.type = type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        loadingDialog = LoadingDialog(parent.context)
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.main_item_search, parent, false))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (type == SearchFragment.TYPE_SEARCH_ROOM || type == SearchFragment.TYPE_SEARCH_ROOM_RADIO){
            ImgUtil.loadFaceIcon(context, mList[position].icon, holder.avterIv)
            holder.ageTv.visibility = View.GONE
            holder.gongxianIv.visibility = View.GONE
            holder.itemView.setOnClickListener {
                //加入房间
                ChatRoomManager.joinChat(context, mList[position].id.toString(), object : JoinChatCallBack {
                    override fun onSuc() {
                    }

                    override fun onFail(msg: String) {
                        ToastUtil.suc(context, msg)
                    }
                })
            }
            if (mList[position].good_number == 0){
                holder.idTv.text = StringUtil.strToSpannableStr("ID: ${mList[position].id}", keyWord)
            }else{
                holder.idTv.text = StringUtil.strToSpannableStr("ID: ${mList[position].good_number}", keyWord)
            }
        }else{
            if (mList[position].good_number == 0){
                holder.idTv.text = StringUtil.strToSpannableStr("ID: ${mList[position].user_id}", keyWord)
            }else{
                holder.idTv.text = StringUtil.strToSpannableStr("ID: ${mList[position].good_number}", keyWord)
            }
            holder.gongxianIv.visibility = View.VISIBLE
            holder.ageTv.visibility = View.VISIBLE
            holder.ageTv.setSeleted(mList.get(position).gender)
            ImgUtil.loadFaceIcon(context, mList[position].icon, holder.avterIv)
            if (mList[position].wealth_level != null){
                holder.gongxianIv.setWealthLevel(mList.get(position).wealth_level.grade)
            }
            holder.itemView.setOnClickListener {
                ARouter.getInstance().build("/app/userhomepage")
                        .withString("user_id", mList[position].user_id.toString())
                        .navigation()
            }

        }
        holder.nickNameTv.text = StringUtil.strToSpannableStr(mList[position].name, keyWord)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var avterIv: ImageView = itemView.findViewById(R.id.iv_avter)
        var nickNameTv: TextView = itemView.findViewById(R.id.tv_nickname)
        var ageTv: SexView = itemView.findViewById(R.id.tv_sex)
        var idTv: TextView = itemView.findViewById(R.id.tv_id)
        var gongxianIv: LevelView = itemView.findViewById(R.id.iv_gongxian)

    }
}
