package com.miaomi.fenbei.room.ui.dialog

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.bean.CloseAllDialogBean
import com.miaomi.fenbei.room.ui.adapter.EmojiSelectAdapter
import com.miaomi.fenbei.room.ui.fragment.EmojiFragment
import com.miaomi.fenbei.base.bean.EmojiGroupBean
import com.miaomi.fenbei.base.bean.EmojiIndex
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog
import com.miaomi.fenbei.base.core.emoji.EmojiManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*


class EmojiDialog : BaseBottomDialog() {

    interface EmojiCallback {
        fun onGroupClick(bean: EmojiGroupBean, position: Int)
    }

    override fun getLayoutRes(): Int {

        return R.layout.room_dialog_emoji
    }

    private lateinit var emojiVp: ViewPager
    private lateinit var emojiSelectList: RecyclerView

    override fun bindView(v: View) {
        emojiVp = v.findViewById(R.id.emoji_vp)
        emojiSelectList = v.findViewById(R.id.emoji_select_list)
        emojiSelectList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        loadDataForUI()
    }

    private fun loadDataForUI() {
        if (EmojiManager.emojiList == null || EmojiManager.emojiList.isEmpty()) {
            GlobalScope.launch (Dispatchers.IO) {
                EmojiManager.initEmojiData(context!!)
                withContext(Dispatchers.Main) {
                    updateView(EmojiManager.emojiList)
                }
            }
        } else {
            updateView(EmojiManager.emojiList)
        }
    }

    private fun updateView(emojiList:ArrayList<EmojiGroupBean>) {
        if (!isAdded){
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

        val adapter = EmojiSelectAdapter(emojiList, object : EmojiCallback {
            override fun onGroupClick(bean: EmojiGroupBean, position: Int) {
                emojiVp.currentItem = groupMap[position]!!
            }
        })

        emojiVp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                val page: Int = indexMap[position]!!.groupIndex
                adapter.update(page)
            }

        })
        emojiSelectList.adapter = adapter
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun dismissDialog(bean: CloseAllDialogBean) {
        dialog?.let { if (it.isShowing) dismiss() }
    }
}