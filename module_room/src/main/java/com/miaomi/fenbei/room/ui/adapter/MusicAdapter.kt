package com.miaomi.fenbei.room.ui.adapter


import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.room.RoomMusicManager
import com.miaomi.fenbei.base.bean.db.MusicBean
import com.miaomi.fenbei.room.util.MusicUtil
import com.miaomi.fenbei.base.util.ToastUtil
import kotlinx.android.synthetic.main.room_item_music.view.*


class MusicAdapter(list:ArrayList<MusicBean>) : RecyclerView.Adapter<MusicAdapter.ViewHolder>() {
    private var mList: ArrayList<MusicBean> = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.room_item_music, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position], position)
    }

    override fun getItemCount(): Int = mList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(bean: MusicBean, position: Int) {
            itemView.music_title.text = bean.name
            itemView.tv_tag.text = bean.uploader
            if (RoomMusicManager.getCurMusic()?.id == bean.id){
                itemView.iv_isplaying.visibility = View.VISIBLE
                itemView.tv_position.text = ""
            }else{
                itemView.iv_isplaying.visibility = View.GONE
                itemView.tv_position.text = (position+1).toString()
            }
            itemView.delete_btn.setOnClickListener {
                if (bean.musicId > 0){
                    MusicUtil.deleteMusic(bean.musicId)
                    ToastUtil.suc(itemView.context,"删除成功")
                }else{
                    RoomMusicManager.removeMusic(position)
                }
                mList.removeAt(position)
                notifyDataSetChanged()
            }
            itemView.item_content.setOnClickListener {
//                for (item in mList){
//                    item.isPlaying = false
//                }
//                mList[position].isPlaying = true
                val curMusic = RoomMusicManager.getCurMusic()
                if (curMusic != null && curMusic.id == bean.id) {
                    if (RoomMusicManager.isPlaying()) {
                        RoomMusicManager.pausePlayMusic()
                    } else {
                        RoomMusicManager.resumePlayMusic()
                    }
                } else {
                    RoomMusicManager.playMusic(bean)
                }
                notifyDataSetChanged()
            }
        }
    }
}

