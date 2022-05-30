package com.miaomi.fenbei.voice.ui

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.miaomi.fenbei.base.bean.BaseBean
import com.miaomi.fenbei.base.bean.HostInfoBean
import com.miaomi.fenbei.base.bean.RoomlabelBean
import com.miaomi.fenbei.base.core.BaseActivity
import com.miaomi.fenbei.base.core.dialog.LoadingDialog
import com.miaomi.fenbei.base.dialog.TimeRangePickerDialog
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.*
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.voice.R
import com.miaomi.fenbei.voice.ui.main.adapter.ChatTypeAdapter
import kotlinx.android.synthetic.main.chatting_activity_radio_room_setting.*


@Route(path = RouterUrl.radioroomSetting)
class RadioRoomSettingActivity : BaseActivity() {

    @JvmField
    @Autowired
    var type = ChatRoomManager.ROOM_TYPE_PERSONAL
    @JvmField
    @Autowired
    var mChatId: String = ""
    private lateinit var loadingDialog: LoadingDialog
    private var mLableId: Int = 4
    private var mIcon: String = ""
    private var mBackDrop: String = ""
    private var selectImg = 1


    override fun getLayoutId(): Int {
        return R.layout.chatting_activity_radio_room_setting
    }

    override fun initView() {
        setBaseStatusBar(false,false)
        ARouter.getInstance().inject(this)
        loadingDialog = LoadingDialog(this)
        getChatInfo(mChatId)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.HORIZONTAL
        rv_chat_type.layoutManager = linearLayoutManager
        chat_name_et.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(15))
        chat_topic_et.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(15))
        radio_name_et.filters= arrayOf<InputFilter>(InputFilter.LengthFilter(8))
        chat_notice_tip.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(200))
        radio_introduction_et.filters=arrayOf<InputFilter>(InputFilter.LengthFilter(100))
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
        radio_name_et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                radio_name_limit.text = radio_name_et.text.length.toString() + "/" + "8"
            }

        })
        radio_introduction_et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                radio_introduction_limit.text = radio_introduction_et.text.length.toString() + "/" + "100"
            }

        })
        chat_notice_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                chat_notice_limit.text = chat_notice_et.text.length.toString() + "/" + "300"
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })
        tv_date_time.setOnClickListener{
            DateDialog()
        }
        tv_column.setOnClickListener {
        ColumnListActivity.start(this,1)
        }
        chat_icon.setOnClickListener {
            selectImg = 1
            PhotoUtils.getInstance().chooseAvter(this) { url -> uploadImg(url) }
        }

        chat_bg.setOnClickListener {
            selectImg = 2
        }

        create_chat.setOnClickListener {
            createChatRoom()
        }
    }

    private fun getRoomLabel() {
        NetService.getInstance(this).getRoomLabel(type,object : Callback<List<RoomlabelBean>>() {
            override fun onSuccess(nextPage: Int, list: List<RoomlabelBean>, code: Int) {
                for (bean in list) {
                    if (bean.id == mLableId) {
                        bean.isSelected = true
                    }
                }
                var adapter = ChatTypeAdapter(this@RadioRoomSettingActivity, list)
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

    private fun getChatInfo(mChatId: String) {
        NetService.getInstance(this).getChatInfo(mChatId, object : Callback<HostInfoBean>() {
            override fun onSuccess(nextPage: Int, bean: HostInfoBean, code: Int) {
                renderView(bean)
                getRoomLabel()
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
            }

            override fun isAlive(): Boolean {
                return isLive
            }
        })
    }

    private fun renderView(bean: HostInfoBean) {
//        mLableId = bean.labelId!!
        chat_name_et.setText(bean.name)
        chat_topic_et.setText(bean.room_topic)
        if (!TextUtils.isEmpty(bean.icon)) {
            ImgUtil.loadRoundImg(this, bean.icon!!, chat_icon, 8F, R.drawable.common_default)
        }
        if (!TextUtils.isEmpty(bean.backdrop)) {
            ImgUtil.loadRoundImg(this, bean.backdrop, chat_bg, 8F, R.drawable.common_default)
        }

        if (bean.type == 0) {
            mic_radio.check(R.id.four_mic)
        } else {
            mic_radio.check(R.id.eight_mic)
        }

        if (bean.way == 0) {
            mic_power_radio.check(R.id.all_allow)
        } else {
            mic_power_radio.check(R.id.allow_invite)
        }
        chat_password_et.setText(bean.password)
        chat_notice_et.setText(bean.note)
        radio_name_et.setText(bean.radio_station_name)
        tv_date_time.setText(bean.open_time)
        radio_introduction_et.setText(bean.introduction)
        mIcon = bean.icon!!
        mBackDrop = bean.backdrop

    }
    private  fun  DateDialog(){
        val dialog = TimeRangePickerDialog(this, "00:00-00:00", object : TimeRangePickerDialog.ConfirmAction {
            override fun onLeftClick() {

            }

            override fun onRightClick(startAndEndTime: String?) {
                tv_date_time.setText(startAndEndTime)
            }
        })
        dialog.show()



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
                ImgUtil.loadRoundImg(this@RadioRoomSettingActivity, putFileUtil.url, if (selectImg == 1) {
                    chat_icon
                } else {
                    chat_bg
                }, 8F, R.drawable.common_default)
            }

            override fun onFail(msg: String) {
                ToastUtil.error(this@RadioRoomSettingActivity, msg)
            }
        })

    }

    private fun createChatRoom() {

        if (TextUtils.isEmpty(chat_name_et.text.toString())) {
            ToastUtil.suc(this, "请填写房间名称")
            return
        }
        if (TextUtils.isEmpty(mIcon)) {
            ToastUtil.suc(this, "请选择房间封面")
            return
        }
        if (TextUtils.isEmpty(radio_name_et.text.toString())) {
            ToastUtil.suc(this, "请填写电台名称")
            return
        }
        if (TextUtils.isEmpty(tv_date_time.text.toString())) {
            ToastUtil.suc(this, "请填写开厅时间")
            return
        }
        if (TextUtils.isEmpty(radio_introduction_et.text.toString())) {
            ToastUtil.suc(this, "请填写电台简介")
            return
        }

        val chatName: String = chat_name_et.text.toString()
        val chatTopic: String = chat_topic_et.text.toString()
        val micType = 4
        val micWay: Int = if (mic_power_radio.checkedRadioButtonId == R.id.all_allow) 0 else 1
        val micSwitchType: String = if (mic_st.isChecked) "0" else "1"
        val chatPassword: String = chat_password_et.text.toString()
        var chatNotice: String = chat_notice_et.text.toString()
        var radioName: String = radio_name_et.text.toString()
        var dataTime: String = tv_date_time.text.toString()
        var radioIntroduction: String = radio_introduction_et.text.toString()
        if(chatPassword.length in 1..3){
            ToastUtil.error(this@RadioRoomSettingActivity,"请输入4位数密码")
            return
        }
        if (TextUtils.isEmpty(chatNotice)) {
            chatNotice = ""
        }
        updateChatInfo(this@RadioRoomSettingActivity, chatTopic, chatName, mIcon, mBackDrop, micType, micWay, chatPassword, chatNotice, mLableId.toString(), micSwitchType,radioName,dataTime,radioIntroduction)
    }

    private fun updateChatInfo(context: Context, topic: String, chatName: String, chatRoomIcon: String, chatRoomBg: String, micType: Int, micWay: Int, chatPassword: String, chatNotice: String, labelId: String, micSwitchType: String,radioName:String,dataTime:String,radioIntroduction:String) {

        NetService.getInstance(context).updateRadioChatInfo(topic, mChatId, chatName, chatRoomIcon, chatRoomBg, micType, micWay, chatPassword, chatNotice, labelId,radioName,dataTime,radioIntroduction, object : Callback<BaseBean>() {
            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
                setResult(Activity.RESULT_OK)
                finish()
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context, msg)
            }

            override fun isAlive(): Boolean {

                return isLive
            }

        })
    }

}