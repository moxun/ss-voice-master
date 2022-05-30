package com.miaomi.fenbei.voice.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.text.Editable
import android.text.InputFilter
import android.text.TextUtils
import android.text.TextWatcher
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.miaomi.fenbei.base.bean.CreateChatBean
import com.miaomi.fenbei.base.bean.RoomlabelBean
import com.miaomi.fenbei.base.core.BaseActivity
import com.miaomi.fenbei.base.core.JoinChatCallBack
import com.miaomi.fenbei.base.core.dialog.CommonDialog
import com.miaomi.fenbei.base.core.dialog.LoadingDialog
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.*
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.voice.R
import com.miaomi.fenbei.voice.ui.main.adapter.ChatTypeAdapter
import com.miaomi.fenbei.voice.ui.main.adapter.WelcomeAdapter
import kotlinx.android.synthetic.main.chatting_activity_create_chat.*


@Route(path = RouterUrl.createChat)
class CreateChatActivity : BaseActivity() ,WelcomeAdapter.CheckItemListener{

    @JvmField
    @Autowired
    var type = ChatRoomManager.ROOM_TYPE_PERSONAL
    private lateinit var loadingDialog: LoadingDialog
    private var mLableId: Int = 1
    private var mIcon: String = ""
    private var mBackDrop: String = ""
    private var selectImg = 1
    private var mCreateChatBean : CreateChatBean? = null
    private lateinit var welcomeAdapter : WelcomeAdapter
    val msgList: MutableList<String> = ArrayList()
    companion object {
        const val CHAT_ID = "chat_id"

        fun getIntent(context: Context): Intent {
            return Intent(context, CreateChatActivity::class.java)
        }

        fun getIntent(context: Context, chatId: String): Intent {
            val intent = Intent(context, CreateChatActivity::class.java)
            intent.putExtra(CHAT_ID, chatId)
            return intent
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.chatting_activity_create_chat
    }

    @SuppressLint("NewApi")
    override fun initView() {
        ARouter.getInstance().inject(this)
//        setBaseStatusBar(false, false)
        StatusBarUtil.setColorNoTranslucent(this, Color.parseColor("#642192"));
        loadingDialog = LoadingDialog(this)
        getRoomLabel()
        welcomeAdapter= WelcomeAdapter(this,this)
        val welcomLayoutManager = LinearLayoutManager(this)
        rv_welcom.layoutManager = welcomLayoutManager
        rv_welcom.adapter=welcomeAdapter;
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        rv_chat_type.layoutManager = linearLayoutManager
        chat_name_et.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(15))
        chat_topic_et.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(15))
        chat_notice_tip.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(200))
        chat_name_et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                chat_name_limit.text = chat_name_et.text.length.toString() + "/" + "15"
            }

        })
        chat_topic_et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                chat_topic_limit.text = chat_topic_et.text.length.toString() + "/" + "15"
            }

        })
        tv_add_msg.setOnClickListener {

            //       添加自带默认动画
            addRoomMsg();


        }
        chat_notice_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                chat_notice_limit.text = chat_notice_et.text.length.toString() + "/" + "200"
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })

        chat_icon.setOnClickListener {
            selectImg = 1
            PhotoUtils.getInstance().chooseAvter(this) { url -> uploadImg(url) }
        }

        chat_bg.setOnClickListener {
            selectImg = 2
        }


        create_chat.setOnClickListener {
            createChatRoom()
//            val dialog  = GetGoodNumberDialog("360053",object :GetGoodNumberDialog.OnGetGoodNumberListener{
//                override fun onSuccess() {
////                    ChatRoomManager.createRoom(this@CreateChatActivity,roomType,bean.room_id,bean.agora_token,joinCallBack)
//                }
//
//            })
//            dialog.show(supportFragmentManager)
//            ARouter.getInstance().build(RouterUrl.getRoomGoodNumber)
//                    .withString("roomId", "360053").navigation(this@CreateChatActivity,110)
        }
    }

    private fun getRoomLabel() {
        NetService.getInstance(this).getRoomLabel(type, object : Callback<List<RoomlabelBean>>() {
            override fun onSuccess(nextPage: Int, list: List<RoomlabelBean>, code: Int) {
                for (bean in list) {
                    if (bean.id == mLableId) {
                        bean.isSelected = true
                    }
                }
                var adapter = ChatTypeAdapter(this@CreateChatActivity, list)
                rv_chat_type.adapter = adapter
                adapter.setOnSelectedListener {
                    mLableId = it
                }
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
            }

            override fun isAlive(): Boolean {
                return isLive
            }

        })
    }


    private fun uploadImg(imagePath: String) {
        val fileName = DataHelper.getUID().toString() + System.currentTimeMillis().toString() + ".jpg"
        val putFileUtil = OSSPutFileUtil(fileName, imagePath, if (selectImg == 1) {
            OSSPutFileUtil.FILE_TYPE_AVATAR
        } else {
            OSSPutFileUtil.FILE_TYPE_IMG
        })
        putFileUtil.uploadRoomIconByBitmap(this, object : OSSPutFileUtil.OSSCallBack {
            override fun onSuc() {

                if (selectImg == 1) {
                    mIcon = putFileUtil.url
                } else {
                    mBackDrop = putFileUtil.url
                }
                ImgUtil.loadRoundImg(this@CreateChatActivity, putFileUtil.url, if (selectImg == 1) {
                    chat_icon
                } else {
                    chat_bg
                }, 8F, R.drawable.common_default)
            }

            override fun onFail(msg: String) {
                ToastUtil.error(this@CreateChatActivity, msg)
            }
        })

    }

    private fun addRoomMsg() {

        if (TextUtils.isEmpty(et_welcome_msg.text.toString())) {
            ToastUtil.suc(this, "请填写房间语")
            return
        }else{
            if(msgList.size<=50){

                msgList.add(et_welcome_msg.text.toString());
                welcomeAdapter.addData(et_welcome_msg.text.toString())
                et_welcome_msg.text=null

            }else{
                ToastUtil.suc(this, "房间语已超过50条")
                return
            }

        }

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChatRoom() {

        if (TextUtils.isEmpty(chat_name_et.text.toString())) {
            ToastUtil.suc(this, "请填写房间名称")
            return
        }
        if (TextUtils.isEmpty(mIcon)) {
            ToastUtil.suc(this, "请选择房间封面")
            return
        }

        val chatName: String = chat_name_et.text.toString()
        val chatTopic: String = chat_topic_et.text.toString()
        val micType = 1
        val micWay: Int = if (mic_power_radio.checkedRadioButtonId == R.id.all_allow) 0 else 1
        val chatPassword: String = chat_password_et.text.toString()
        var chatNotice: String = chat_notice_et.text.toString()
        if(chatPassword.length in 1..3){
            ToastUtil.error(this@CreateChatActivity, "请输入4位数密码")
            return
        }
        if (TextUtils.isEmpty(chatNotice)) {
            chatNotice = ""
        }
        val roomMsg:String
        if(msgList.size>0){
            roomMsg = java.lang.String.join(",", msgList)
        }else{
            roomMsg= ""
        }
        loadingDialog.show()
        creatRoom(roomMsg,type, chatTopic, chatName, mIcon, mBackDrop, micType, micWay, chatPassword, chatNotice, mLableId.toString(), object : JoinChatCallBack {
            override fun onSuc() {
                loadingDialog.dismiss()
                finish()
            }

            override fun onFail(msg: String) {
                ToastUtil.error(this@CreateChatActivity, msg)
                loadingDialog.dismiss()
            }
        })
    }


    private fun creatRoom(welcome_message:String,roomType: Int, topic: String, chatName: String, chatRoomIcon: String, chatRoomBg: String, micType: Int, micWay: Int, chatPassword: String, chatNotice: String, labelId: String, joinCallBack: JoinChatCallBack){
        NetService.getInstance(this@CreateChatActivity).createChatRoom(welcome_message,roomType, topic, chatName, chatRoomIcon, chatRoomBg, micType, micWay, chatPassword, chatNotice, labelId, object : Callback<CreateChatBean>() {
            override fun onSuccess(nextPage: Int, bean: CreateChatBean, code: Int) {
                mCreateChatBean = bean
                if (roomType == ChatRoomManager.ROOM_TYPE_LABOR_UNION) {
//                    val dialog  = GetGoodNumberDialog(bean.room_id,object :GetGoodNumberDialog.OnGetGoodNumberListener{
//                        override fun onSuccess() {
//                            ChatRoomManager.createRoom(this@CreateChatActivity,roomType,bean.room_id,bean.agora_token,joinCallBack)
//                        }
//
//                    })
//                    dialog.show(supportFragmentManager)
                    ARouter.getInstance().build(RouterUrl.getRoomGoodNumber)
                            .withString("roomId", bean.room_id).navigation(this@CreateChatActivity,110)
                } else {
                    ChatRoomManager.createRoom(this@CreateChatActivity, roomType, bean.room_id, bean.agora_token, joinCallBack)
                }
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                when (code) {
                    1003 -> {
                        val upDialog = CommonDialog(this@CreateChatActivity)
                        upDialog.setTitle("友情提示")
                        upDialog.setContent("实名认证后才能开房间哦")
                        upDialog.setLeftBt("取消") {
                            upDialog.dismiss()
                        }
                        upDialog.setRightBt("去认证") {
                            ARouter.getInstance().build(RouterUrl.identityAuthentication).navigation()
                            upDialog.dismiss()
                        }
                        upDialog.show()
                    }
                    else -> ToastUtil.error(this@CreateChatActivity, msg)
                }
                joinCallBack.onFail(msg)
            }

            override fun isAlive(): Boolean {
                return isLive

            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 110){
            ChatRoomManager.createRoom(this@CreateChatActivity,type, mCreateChatBean!!.room_id, mCreateChatBean!!.agora_token, object : JoinChatCallBack {
                override fun onSuc() {
                    loadingDialog.dismiss()
                    finish()
                }

                override fun onFail(msg: String) {
                    ToastUtil.error(this@CreateChatActivity, msg)
                    loadingDialog.dismiss()
                }
            })
        }
    }

    override fun itemChecked(msg: String?) {
        //删除
        if (msgList.contains(msg)) {
            msgList.remove(msg)
        }
    }


}
