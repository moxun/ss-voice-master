package com.miaomi.fenbei.base.core

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.imuxuan.floatingview.FloatingView
import com.miaomi.fenbei.base.R
import com.umeng.analytics.MobclickAgent
import kotlinx.android.synthetic.main.common_toolbar.*


abstract class BaseActivity : AppCompatActivity() {
    var isLive: Boolean = false
    var isPause: Boolean = true
    val TYPE_REFRESH = 0
    val TYPE_LOADMROE = 1

    var page = 1
    var needFloatingView = true

    protected fun setBaseStatusBar(useThemestatusBarColor: Boolean, useStatusBarColor: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            val decorView = window.decorView
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            //根据上面设置是否对状态栏单独设置颜色
            if (useThemestatusBarColor) {
                window.statusBarColor = Color.parseColor("#FD7F8F")//设置状态栏背景色
            } else {
                window.statusBarColor = Color.TRANSPARENT//透明
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && useStatusBarColor) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

    }

    final override fun onCreate(savedInstanceState: Bundle?) {
        isLive = true
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        if (savedInstanceState != null){
            ARouter.getInstance().build("/app/splash").withTransition(R.anim.anim_bottom_in, R.anim.anim_bottom_out).withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK).navigation()
        }
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initToolbar()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        initView()
    }





    private fun initToolbar() {
        if (back_btn != null) {
            back_btn.setOnClickListener {
                finish()
            }
        }
        if (main_tv != null) {

            main_tv.text = title.toString()
        }
    }

    override fun getResources(): Resources {

        val resources = super.getResources()
        if (resources != null && resources.configuration.fontScale != 1.0f) {
            val configuration = resources.configuration
            configuration.fontScale = 1.0f
            resources.updateConfiguration(configuration, resources.displayMetrics)
        }
        return resources
    }



    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
        isPause = true
        if (needFloatingView){
            FloatingView.get().attach(this)
        }
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
        isPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        isLive = false
        if (needFloatingView){
            FloatingView.get().detach(this)
        }
    }

    abstract fun getLayoutId(): Int
    abstract fun initView()

    protected fun setTitle(title:String){
        main_tv.text = title
    }

}