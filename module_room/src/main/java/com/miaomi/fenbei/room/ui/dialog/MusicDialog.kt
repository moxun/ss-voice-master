package com.miaomi.fenbei.room.ui.dialog

import android.content.DialogInterface
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.room.RoomMusicManager
import com.miaomi.fenbei.room.callback.ChatRoomOnMusicCallBack
import com.miaomi.fenbei.base.bean.CloseAllDialogBean
import com.miaomi.fenbei.base.bean.HotMusicSearchBean
import com.miaomi.fenbei.base.bean.db.MusicBean
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.room.ui.adapter.MusicAdapter
import com.miaomi.fenbei.room.util.MusicUtil
import com.miaomi.fenbei.base.bean.BaseBean
import com.miaomi.fenbei.base.core.BaseActivity
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.util.SPUtil
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView
import com.xiaweizi.marquee.MarqueeTextView
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlin.math.roundToInt

class MusicDialog : BaseBottomDialog(), ChatRoomOnMusicCallBack {
    override fun getLayoutRes(): Int {

        return R.layout.room_dialog_music
    }
    val TYPE_REFRESH = 0
    val TYPE_LOADMROE = 1


    private lateinit var musicTitle: MarqueeTextView
    private lateinit var playLayout: View
    private lateinit var playBtn: ImageView
    private lateinit var repeatModeBtn: ImageView
    private lateinit var rootFl: LinearLayout

//    private var loadHelper: LoadHelper = LoadHelper()
    private lateinit var musicList: XRecyclerView
    private var mMusicList:ArrayList<MusicBean> = ArrayList()

    private lateinit var adpter: MusicAdapter
    private var oldMusicList: ArrayList<MusicBean> = ArrayList()

    override fun bindView(v: View) {

        v.findViewById<View>(R.id.tv_add_music).setOnClickListener {
            ARouter.getInstance().build("/app/music").navigation()
        }



       musicList = v.findViewById(R.id.music_list)
        musicList.layoutManager = LinearLayoutManager(context)
        musicList.setPullRefreshEnabled(true)
        musicList.setLoadingMoreEnabled(true)
        musicList.setLoadingListener(object : XRecyclerView.LoadingListener{
            override fun onRefresh() {
                getMusicList(TYPE_REFRESH)
            }

            override fun onLoadMore() {
                getMusicList(TYPE_LOADMROE)
            }

        })
        adpter = MusicAdapter(mMusicList)
        musicList.adapter = adpter
        musicTitle = v.findViewById(R.id.music_title)
        playLayout = v.findViewById(R.id.play_layout)
        playBtn = v.findViewById(R.id.play_btn)
        repeatModeBtn = v.findViewById(R.id.repeat_mode_btn)
        rootFl = v.findViewById(R.id.root_fl)
//        loadHelper.registerLoad(rootFl)
        val initMode = SPUtil.getInt(SPUtil.CONFIG_MUSIC_REPEAT_MODE, 0)
        when (initMode) {
            0 -> {  //列表
                repeatModeBtn.setImageResource(R.drawable.room_icon_music_repeat_all)
            }
            1 -> {  //随机
                repeatModeBtn.setImageResource(R.drawable.room_icon_music_repeat_random)
            }
            2 -> {  //单曲
                repeatModeBtn.setImageResource(R.drawable.room_icon_music_repeat_once)
            }
        }

        musicTitle.startScroll()
        v.findViewById<View>(R.id.repeat_mode_btn).setOnClickListener {
            val mode = SPUtil.getInt(SPUtil.CONFIG_MUSIC_REPEAT_MODE, 0)
            when (mode) {
                0 -> {  //列表
                    SPUtil.putInt(SPUtil.CONFIG_MUSIC_REPEAT_MODE, 1)
                    repeatModeBtn.setImageResource(R.drawable.room_icon_music_repeat_random)
                }
                1 -> {  //单曲
                    SPUtil.putInt(SPUtil.CONFIG_MUSIC_REPEAT_MODE, 2)
                    repeatModeBtn.setImageResource(R.drawable.room_icon_music_repeat_once)
                }
                2 -> {
                    SPUtil.putInt(SPUtil.CONFIG_MUSIC_REPEAT_MODE, 0)
                    repeatModeBtn.setImageResource(R.drawable.room_icon_music_repeat_all)
                }
            }
        }
//        v.findViewById<SearchView>(R.id.sv_search).setOnSearchListener {
//            getSearchMusicList(it)
//        }
//        v.findViewById<SearchView>(R.id.sv_search).setOnClearListener {
//            refresh()
//        }

//        val musicBeanList = MusicUtil.getAllMusic()
//        if (musicBeanList.isEmpty()) {
//            refresh()
//        } else {
//            bindData(musicBeanList)
//        }

        RoomMusicManager.registerMusic(this)
        val curMusicBean = RoomMusicManager.getCurMusic()
        if (curMusicBean == null) {
            playLayout.tag = 0L
        } else {
            playLayout.tag = curMusicBean.id
            if (RoomMusicManager.isPlaying()) {
                playBtn.setImageResource(R.drawable.room_icon_music_operate_pause)
            } else {
                playBtn.setImageResource(R.drawable.room_icon_music_operate_start)
            }
            musicTitle.text = curMusicBean.name
        }
//
        v.findViewById<View>(R.id.play_btn).setOnClickListener {

            if (RoomMusicManager.getCurMusic() != null) {
                if (RoomMusicManager.isPlaying()) {
                    RoomMusicManager.pausePlayMusic()
                } else {
                    RoomMusicManager.resumePlayMusic()
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        getMusicList(TYPE_REFRESH)
    }

//    private fun showVolumeLayout() {
//        context?.let {
//            VolumeDialog(it).show()
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        musicTitle.stopScroll()
    }

    var page:Int = 1
    private fun getMusicList(type:Int){
        if (type == TYPE_REFRESH){
            musicList.setLoadingMoreEnabled(true)
            page = 1
        }
        NetService.getInstance(context!!).getMyMusicList(page,object : Callback<List<MusicBean>>() {
            override fun onSuccess(nextPage: Int, list: List<MusicBean>, code: Int) {
                musicList.refreshComplete()
                if (type == TYPE_REFRESH){
                    mMusicList.clear()
                    mMusicList.addAll(MusicUtil.getAddMusic())
                    mMusicList.addAll(list)
                    adpter.notifyDataSetChanged()
                }else{
                    mMusicList.addAll(list)
                    adpter.notifyDataSetChanged()
                }
//                loadHelper.bindView(Data.CODE_SUC)
                page ++
                bindData(mMusicList)
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
//                loadHelper.bindView(Data.CODE_SUC)
                musicList.refreshComplete()
            }

            override fun isAlive(): Boolean {
                return isLive
            }

            override fun noMore() {
                super.noMore()
                musicList.setLoadingMoreEnabled(false)
            }
        })
    }

    private fun getSearchMusicList(keyword:String){
        NetService.getInstance(context!!).getSearchMusicList(keyword,object : Callback<HotMusicSearchBean>() {
            override fun onSuccess(nextPage: Int, bean: HotMusicSearchBean, code: Int) {
                mMusicList.clear()
                mMusicList.addAll(bean.list)
                adpter.notifyDataSetChanged()
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                musicList.refreshComplete()
            }

            override fun isAlive(): Boolean {
                return (context as BaseActivity).isLive
            }

            override fun noMore() {
                super.noMore()
                musicList.setLoadingMoreEnabled(false)
            }
        })
    }

    private fun deleteMusic(id:Long){
        NetService.getInstance(context!!).deleteMusic(id.toInt(),object : Callback<BaseBean>() {
            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
                ToastUtil.suc(context!!, "删除成功")
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
            }

            override fun isAlive(): Boolean {
               return  isAlive()
            }

        })
    }

    private fun bindData(musicBeanList: ArrayList<MusicBean>) {
        oldMusicList.clear()
        oldMusicList.addAll(musicBeanList)
        RoomMusicManager.setMusicList(musicBeanList)
    }

    private fun refresh(){
        mMusicList.clear()
        mMusicList.addAll(oldMusicList)
        adpter.notifyDataSetChanged()
    }

    override fun onPlay(musicBean: MusicBean?) {
        if (musicBean != null && dialog !=null && dialog!!.isShowing) {
            musicTitle.text = musicBean.name
            playLayout.tag = musicBean.id
            playBtn.setImageResource(R.drawable.room_icon_music_pause)
            adpter.notifyDataSetChanged()
        }
    }

    override fun onPauseMusic() {
        playBtn.setImageResource(R.drawable.room_icon_music_play)
    }

    override fun onResumeMusic() {
        playBtn.setImageResource(R.drawable.room_icon_music_pause)

    }

    override fun progress(progress: Int) {

//        context!!.runOnUiThread {
//            updateProgress()
//        }
    }

//    private fun updateProgress() {
//        current_progress.text = timeParse(ChatRoomManager.getMusicProgress().toLong())
//        music_progress.progress = (ChatRoomManager.getMusicProgress() * 100/ mMusicBean!!.duration ).toInt()
//    }

    override fun onRemove(musicBean: MusicBean) {
        val musicId = playLayout.tag as Long
        if (musicId == musicBean.id) {
            playLayout.tag = 0L
            SPUtil.putLong(SPUtil.CONFIG_MUSIC_ID, 0L)
        }
        deleteMusic(musicBean.id)
    }


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        RoomMusicManager.unregisterMusic()
    }

    private fun timeParse(duration: Long): String {
        var time = ""
        val minute = duration / 60000
        val seconds = duration % 60000
        val second = (seconds / 1000).toFloat().roundToInt()
        if (minute < 10) {
            time += "0"
        }
        time += "$minute:"
        if (second < 10) {
            time += "0"
        }
        time += second
        return time
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun dismissDialog(bean: CloseAllDialogBean) {
        dialog?.let { if (it.isShowing) dismiss() }
    }
}