package com.miaomi.fenbei.room.ui.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.text.InputFilter
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.miaomi.fenbei.base.bean.AppearBean
import com.miaomi.fenbei.base.bean.CloseAllDialogBean
import com.miaomi.fenbei.base.bean.EmojiGroupBean
import com.miaomi.fenbei.base.bean.EmojiIndex
import com.miaomi.fenbei.base.core.CommonLib
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog
import com.miaomi.fenbei.base.core.emoji.EmojiManager
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.CKeyboardUtil
import com.miaomi.fenbei.base.util.DensityUtil.hideSoftKeyboard
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.room.ui.adapter.InputDialogAdapter
import com.miaomi.fenbei.room.ui.fragment.EmojiFragment
import com.miaomi.fenbei.room.widget.InputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*


class InputDialog : BaseBottomDialog() {


    override fun getLayoutRes(): Int {

        return R.layout.room_dialog_input_layout
    }

    private lateinit var adpter: InputDialogAdapter
    private lateinit var wordRv: RecyclerView
    private lateinit var wordEdit: InputEditText
    private lateinit var sendBtn: TextView

    //    private lateinit var sendEmojiTv:ImageView
    private lateinit var emojiVp: ViewPager
    private var isShowEmoji = false;

    private var mPrefix: String = ""

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    override fun bindView(v: View) {
        adpter = InputDialogAdapter(object : InputDialogAdapter.OnItemClickListener {
            override fun onItemClick(item: String) {
                sendWordMsg(item)
            }

        })
        emojiVp = v.findViewById(R.id.vp_emoji)
        loadDataForUI()
//        sendEmojiTv = v.findViewById(R.id.iv_emoji)
        wordEdit = v.findViewById(R.id.word_chat_btn)
        wordRv = v.findViewById(R.id.rv_common_use_word)
        wordRv.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
        wordRv.adapter = adpter
        wordEdit.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(100))
        val wordList = listOf("halo！", "心动ing", "怎么玩", "来波爆音~", "恋爱dd 你是唯一")
        adpter.setNewData(wordList)
        sendBtn = v.findViewById(R.id.send_btn)
        wordEdit.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendWordMsg(wordEdit.text.toString())

            }
            false
        }
        sendBtn.setOnClickListener {
            if (!TextUtils.isEmpty(wordEdit.text.toString())) {
                sendWordMsg(wordEdit.text.toString())
            }
        }
//        sendEmojiTv.setOnClickListener {
//            ToastUtil.i(context!!,"表情")
//        }
        v.findViewById<ImageView>(R.id.iv_emoji).setOnClickListener {
            CKeyboardUtil.hideKeyboard(wordEdit)
            if (isShowEmoji) {
                isShowEmoji = false
                emojiVp.visibility = View.GONE
            } else {
                isShowEmoji = true
                emojiVp.visibility = View.VISIBLE
            }
        }
        wordEdit.addBackListener {
            dismiss()
        }

    }

    private fun loadDataForUI() {
        if (EmojiManager.emojiList == null || EmojiManager.emojiList.isEmpty()) {
            GlobalScope.launch(Dispatchers.IO) {
                EmojiManager.initEmojiData(context!!)
                withContext(Dispatchers.Main) {
                    updateView(EmojiManager.emojiList)
                }
            }
        } else {
            updateView(EmojiManager.emojiList)
        }
    }

    private fun updateView(emojiList: ArrayList<EmojiGroupBean>) {
        if (!isAdded) {
            return
        }

        var sumPage = 0
        val indexMap = HashMap<Int, EmojiIndex>()
        val groupMap = HashMap<Int, Int>()
        emojiList.forEachIndexed { groupIndex, emojiGroupBean ->
            emojiGroupBean.emoji_item.forEachIndexed { index, list ->
                indexMap[sumPage + index] = EmojiIndex(groupIndex, index)
            }
            groupMap[groupIndex] = sumPage
            sumPage += emojiGroupBean.emoji_item.size
        }

        emojiVp.adapter = object : FragmentPagerAdapter(childFragmentManager) {
            override fun getItem(position: Int): Fragment {
                val page: Int = indexMap[position]!!.groupIndex
                val index: Int = indexMap[position]!!.itemIndex
                return EmojiFragment.newInstance(emojiList[page].emoji_item[index])
            }

            override fun getCount(): Int {
                return sumPage
            }
        }


        emojiVp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
            }

        })
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        wordEdit.setText(mPrefix)
    }

    private fun sendWordMsg(content: String) {
        if (TextUtils.isEmpty(content)) {
            return
        }
        ChatRoomManager.sendText(content)
        wordEdit.setText("")
        upLoadTxt(content, ChatRoomManager.getRoomId())
        dismiss()
    }

    override fun show(fragmentManager: FragmentManager?) {
        super.show(fragmentManager)
        CKeyboardUtil.showKeyboard(wordEdit)
        mPrefix = ""
    }

    fun show(prefix: String, fragmentManager: FragmentManager) {
        super.show(fragmentManager)
        CKeyboardUtil.showKeyboard(wordEdit)
        mPrefix = prefix
    }

    override fun onDismiss(dialog: DialogInterface?) {
        CKeyboardUtil.hideKeyboard(wordEdit)
        super.onDismiss(dialog)
    }

    private fun upLoadTxt(content: String, roomId: String) {
        NetService.getInstance(context!!).upLoadTxt(roomId, content, object : Callback<AppearBean>() {
            override fun onSuccess(nextPage: Int, bean: AppearBean, code: Int) {
                if (bean.isShow_toast) {
                    ToastUtil.suc(CommonLib.mContext, bean.text)
                }
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
            }

            override fun isAlive(): Boolean {
                return true
            }
        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun dismissDialog(bean: CloseAllDialogBean) {
        dialog?.let { if (it.isShowing) dismiss() }
    }
}