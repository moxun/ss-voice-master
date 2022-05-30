package com.miaomi.fenbei.voice.ui.main

import android.Manifest
import android.os.Vibrator
import android.text.TextUtils
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.hjq.permissions.OnPermission
import com.hjq.permissions.XXPermissions
import com.imuxuan.floatingview.FloatingMagnetView
import com.imuxuan.floatingview.FloatingView
import com.imuxuan.floatingview.MagnetViewListener
import com.miaomi.fenbei.base.bean.event.LoginEvent
import com.miaomi.fenbei.base.bean.event.UnReadNumBean
import com.miaomi.fenbei.base.core.BaseActivity
import com.miaomi.fenbei.base.core.JoinChatCallBack
import com.miaomi.fenbei.base.core.dialog.CommonDialog
import com.miaomi.fenbei.base.util.*
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.room.RoomStatusListener
import com.miaomi.fenbei.room.callback.ChatRoomLeaveRoomCallBack
import com.miaomi.fenbei.voice.R
import com.miaomi.fenbei.voice.ui.PerfectPersonInfoActivity
import com.miaomi.fenbei.voice.ui.RankFragment
import com.miaomi.fenbei.voice.ui.main.fragment.MineFragment
import com.miaomi.fenbei.voice.ui.main.fragment.NewsFragment
import com.miaomi.fenbei.voice.ui.main.fragment.RoomFragment
import com.miaomi.fenbei.voice.ui.pyq.PYQFragment
import kotlinx.android.synthetic.main.main_activity_main.*
import kotlinx.android.synthetic.main.main_chatting_float_view.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

@Route(path = RouterUrl.main)
class MainActivity : BaseActivity(), RoomStatusListener {

    //    private lateinit var msgItem: BaseTabItem
    private var mExitTime: Long = 0
    private var isShowMinView = false
    private lateinit var vibrator: Vibrator
//    private lateinit var animation: AnimationDrawable

    /**
     * 获取权限保存图片
     */
    private fun requestPermissionAudio() {
        XXPermissions.with(this)
            .permission(
                arrayOf(
                    Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
            .request(object : OnPermission {
                override fun noPermission(denied: MutableList<String>, quick: Boolean) {

                }

                override fun hasPermission(granted: MutableList<String>, isAll: Boolean) {
                }

            })
    }


    override fun getLayoutId(): Int {
        return R.layout.main_activity_main
    }

    fun setCurrentItem(page: Int) {
        main_vp.currentItem = page
    }

    override fun initView() {
        ChatRoomManager.addMonitor(this)
        StatusBarUtil.setStatusBarTextColor(this, true)
        EventBus.getDefault().register(this)
        StatisticHelper.postLoginLog(this)
        val fragmentList: ArrayList<Fragment> = ArrayList()
        fragmentList.add(RoomFragment())
//        fragmentList.add(PlayingUserFragment.newInstance())
        fragmentList.add(RankFragment.newInstance())
//        fragmentList.add(HomeFragment())
        fragmentList.add(NewsFragment.newInstance())
        fragmentList.add(MineFragment.newInstance())
        main_vp.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getCount(): Int {
                return fragmentList.size
            }

            override fun getItem(position: Int): Fragment {
                return fragmentList[position]
            }
        }

        main_vp.currentItem = 0
//        msgItem = newItem(R.drawable.main_new_msg_uncheck, R.drawable.main_new_msg_checked, "")
//        val navigationController = main_bottom.custom()
//                .addItem(newItem(R.drawable.main_new_chat_uncheck, R.drawable.main_new_chat_checked, ""))
//                .addItem(newItem(R.drawable.main_dynamic_uncheck, R.drawable.main_dynamic_checked, ""))
//                .addItem(msgItem)
//                .addItem(newItem(R.drawable.main_new_mine_uncheck, R.drawable.main_new_mine_checked, ""))
//                .build()
//        navigationController.setupWithViewPager(main_vp)
        main_vp.offscreenPageLimit = fragmentList.size
//
//        navigationController.addSimpleTabItemSelectedListener { index, old ->
//            main_vp.currentItem = index
//        }
        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        main_vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                resetImageView()
                when (position) {
                    0 -> index_bottom_bar_home_image.isSelected = true
                    1 -> index_bottom_bar_find_image.isSelected = true
//                    2 -> index_bottom_bar_party.isSelected = true
                    2 -> index_bottom_bar_message_image.isSelected = true
                    3 -> index_bottom_bar_me_image.isSelected = true
                }
            }
        })
        index_bottom_bar_home_image.isSelected = true
        ll_bar_home.setOnClickListener {
            resetVibrator()
            main_vp.setCurrentItem(0, false)
        }
        ll_bar_find.setOnClickListener {
            resetVibrator()
            main_vp.setCurrentItem(1, false)
        }
//        index_bottom_bar_party.setOnClickListener {
//            resetVibrator()
//            main_vp.setCurrentItem(2, false)
//        }
        rl_bar_message.setOnClickListener {
            resetVibrator()
            main_vp.setCurrentItem(2, false)
        }
        rl_bar_me.setOnClickListener {
            resetVibrator()
            main_vp.setCurrentItem(3, false)
        }
        requestPermissionAudio()
        NotificationUtils.checkNotify(this)
        VersionUtil.checkVersion(this, false)
    }

    private fun resetVibrator() {
        vibrator.vibrate(10)
    }

    private fun resetImageView() {
        index_bottom_bar_home_image.isSelected = false
        index_bottom_bar_find_image.isSelected = false
        index_bottom_bar_party.isSelected = false
        index_bottom_bar_message_image.isSelected = false
        index_bottom_bar_me_image.isSelected = false
    }

    /**
     * 房间最小化窗口
     */
    private fun showRoomMiniWindow() {
        if (isShowMinView) {
            return
        }
        isShowMinView = true
        FloatingView.get()
            .customView(R.layout.main_chatting_float_view).add()
        val params =
            FrameLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
        params.gravity = Gravity.BOTTOM or Gravity.END
        params.setMargins(13, params.topMargin, params.rightMargin, 300)
        FloatingView.get().layoutParams(params)
        if (ChatRoomManager.mChatRoomInfo != null) {
            ImgUtil.loadCircleImg(applicationContext, ChatRoomManager.mChatRoomInfo!!.host_info.icon!!, FloatingView.get().view.icon)
            ImgUtil.loadGif(applicationContext, R.drawable.base_icon_room_online, FloatingView.get().view.iv_room_gif)
            FloatingView.get().view.tv_room_name.text = ChatRoomManager.mChatRoomInfo!!.host_info.name
        }

        FloatingView.get().view.close_btn.setOnClickListener {
            FloatingView.get().remove()
            ChatRoomManager.leaveChat()
            isShowMinView = false
        }

        FloatingView.get().listener(object : MagnetViewListener {
            override fun onClick(magnetView: FloatingMagnetView) {
                FloatingView.get().remove()
                isShowMinView = false
                ChatRoomManager.joinChat(this@MainActivity, ChatRoomManager.getRoomId(), object : JoinChatCallBack {
                    override fun onSuc() {
                    }

                    override fun onFail(msg: String) {
                        ToastUtil.suc(this@MainActivity, msg)
                    }
                })
            }

            override fun onRemove(magnetView: FloatingMagnetView) {
            }

        })
    }


//    private fun newItem(uncheckIcon: Int, checkedIcon: Int, text: String): BaseTabItem {
//        val itemView = KMBottomItem(this)
//        itemView.init(uncheckIcon, checkedIcon, text)
//        return itemView
//    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!TextUtils.isEmpty(ChatRoomManager.getRoomId())) {
                val dialog = CommonDialog(this)
                dialog.setTitle("友情提示")
                    .setContent("当前正在房间中，是否退出")
                    .setLeftBt("取消") {
                        dialog.dismiss()
                    }
                    .setRightBt("退出") {
                        isShowMinView = false
                        FloatingView.get().remove()
                        ChatRoomManager.leaveChat()
                        dialog.dismiss()
                    }
                    .show()
            } else {
                if ((System.currentTimeMillis() - mExitTime) > 3000) {
                    ToastUtil.suc(this, "再按一次退出程序")
                    mExitTime = System.currentTimeMillis()
                } else {
                    finish()
                }
            }
            return true
        }

        return super.onKeyDown(keyCode, event)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNewMsg(bean: UnReadNumBean) {
        when (bean.unReadNum) {
            0L -> {
                tv_unread.visibility = View.GONE
            }
            in 1..99 -> {
                tv_unread.visibility = View.VISIBLE
                tv_unread.text = bean.unReadNum.toString()
            }
            else -> {
                tv_unread.visibility = View.VISIBLE
                tv_unread.text = "99+"
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginOut(event: LoginEvent) {
        if (!event.loginStatus) {
            if (ChatRoomManager.isInRoom()) {
                ChatRoomManager.leaveChat(object : ChatRoomLeaveRoomCallBack {
                    override fun leaveSucceed() {
                        LoginHelper.INSTANCE.startLoginActivity(this@MainActivity)
                    }
                })
                isShowMinView = false
                FloatingView.get().remove()
            } else {
                LoginHelper.INSTANCE.startLoginActivity(this@MainActivity)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if (ChatRoomManager.isInRoom() && DataHelper.getUserInfo() != null)
            ChatRoomManager.leaveChat()
        ChatRoomManager.removeMonitor(this)
        EventBus.getDefault().unregister(this)
    }

    override fun onResume() {
        super.onResume()
        //解决在房间内不显示房间悬浮窗问题
        if (!isShowMinView && !TextUtils.isEmpty(ChatRoomManager.getRoomId())) {
            showRoomMiniWindow()
        }
    }


    override fun onStart() {
        super.onStart()
        if (DataHelper.isNewUser()) {
            PerfectPersonInfoActivity.start(this)
            return
        }

    }

    override fun onMin() {
        showRoomMiniWindow()
    }

    override fun inRoom() {
        isShowMinView = false
        FloatingView.get().remove()
    }

}
