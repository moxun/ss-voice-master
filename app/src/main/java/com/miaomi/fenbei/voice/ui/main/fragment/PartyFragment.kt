//package com.miaomi.fenbei.voice.ui.main.fragment
//
//
//import android.view.Gravity
//import android.view.LayoutInflater
//import android.view.View
//import android.widget.LinearLayout
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.indicatorlib.base.BaseFragmentAdapter
//import com.miaomi.fenbei.voice.ChatRoomManager.createChat
//import com.miaomi.fenbei.base.bean.BannerBean
//import com.miaomi.fenbei.base.bean.HttpPageDataBean
//import com.miaomi.fenbei.base.bean.PersonRoomItemBean
//import com.miaomi.fenbei.voice.BaseFragment
//import com.miaomi.fenbei.voice.JoinChatCallBack
//import com.miaomi.fenbei.voice.Callback
//import com.miaomi.fenbei.voice.NetService
//import com.miaomi.fenbei.voice.DataHelper.getUserInfo
//import com.miaomi.fenbei.voice.DensityUtil
//import com.miaomi.fenbei.voice.DisplayUtil
//import com.miaomi.fenbei.voice.ToastUtil.error
//import com.miaomi.fenbei.base.web.WebActivity
//import com.miaomi.fenbei.voice.R
//import com.miaomi.fenbei.voice.ui.AllRoomActivity
//import com.miaomi.fenbei.voice.ui.RankActivity
//import com.miaomi.fenbei.voice.ui.main.adapter.PersonRoomAdapter
//import com.miaomi.fenbei.voice.ui.main.fragment.home.RecommandOtherTypeRoomFragment
//import com.miaomi.fenbei.voice.ui.main.fragment.home.RecommandHotRoomFragment
//import com.miaomi.fenbei.voice.ui.search.SearchActivity
//import kotlinx.android.synthetic.main.head_fragment_party.view.*
//import kotlinx.android.synthetic.main.fragment_party.*
//import java.util.*
//import kotlin.math.abs
//
//
//class PartyFragment : BaseFragment() {
//    val partyBannerList = ArrayList<BannerBean>()
//    private lateinit var adapter: PersonRoomAdapter
//    private var mFragmentList: ArrayList<Fragment> = ArrayList()
//    private lateinit var headView: View
//    private var mPage = 1
//    private lateinit var hotFragment: RecommandHotRoomFragment
//    private lateinit var bigTypeRoomFragment1: RecommandOtherTypeRoomFragment
//    private lateinit var bigTypeRoomFragment2: RecommandOtherTypeRoomFragment
//    private lateinit var bigTypeRoomFragment3: RecommandOtherTypeRoomFragment
//    private lateinit var bigTypeRoomFragment4: RecommandOtherTypeRoomFragment
//    private var isInTop: Boolean = true
//
//    companion object {
//        fun newInstance(): PartyFragment {
//            return PartyFragment()
//        }
//    }
//
//    override fun getLayoutId(): Int {
//        return R.layout.fragment_party
//    }
//
//    override fun initView(view: View) {
//        hotFragment = RecommandHotRoomFragment.newInstance()
//        bigTypeRoomFragment1 = RecommandOtherTypeRoomFragment.newInstance(RecommandOtherTypeRoomFragment.ROOM_TYPE_XNNY_FEMALE)
//        bigTypeRoomFragment2 = RecommandOtherTypeRoomFragment.newInstance(RecommandOtherTypeRoomFragment.ROOM_TYPE_XNNY_MAN)
//        bigTypeRoomFragment3 = RecommandOtherTypeRoomFragment.newInstance(RecommandOtherTypeRoomFragment.ROOM_TYPE_QG)
//        bigTypeRoomFragment4 = RecommandOtherTypeRoomFragment.newInstance(RecommandOtherTypeRoomFragment.ROOM_TYPE_JY)
//        headView = LayoutInflater.from(context!!).inflate(R.layout.head_fragment_party, null)
//        headView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
//        val layoutParams = LinearLayout.LayoutParams(DisplayUtil.getScreenWidth(context!!) - DensityUtil.dp2px(context!!, 30f)
//                , DisplayUtil.getScreenWidth(context!!) / 3)
//        layoutParams.gravity = Gravity.CENTER_HORIZONTAL
//        headView.banner.layoutParams = layoutParams
//        headView.banner.setOnBannerListener {
//            WebActivity.start(context!!, it.url, it.title)
//        }
//
//        adapter = PersonRoomAdapter()
//        adapter.setHeaderView(headView)
//        recycler_view.layoutManager = LinearLayoutManager(mContext)
//        recycler_view.adapter = adapter
//        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                isInTop = !recyclerView.canScrollVertically(-1)
//            }
//        })
//        mFragmentList.clear()
//        mFragmentList.add(hotFragment)
//        mFragmentList.add(bigTypeRoomFragment1)
//        mFragmentList.add(bigTypeRoomFragment2)
//        mFragmentList.add(bigTypeRoomFragment3)
//        mFragmentList.add(bigTypeRoomFragment4)
////        initCommonNavigator()
//        headView.view_pager.offscreenPageLimit = mFragmentList.size
//        headView.view_pager.adapter = BaseFragmentAdapter(fragmentManager, mFragmentList)
//        headView.tv_more_room.setOnClickListener {
//            AllRoomActivity.start(context!!)
//        }
//        headView.banner.setOnBannerChangeLisetner {
//            itemMove(0, adapter.data.size - 1)
//        }
//        headView.tab_layout.setViewPager(headView.view_pager)
//        refresh_layout.isEnableAutoLoadMore = false
//        refresh_layout.setEnableNestedScroll(false)
//        refresh_layout.setOnRefreshListener {
//            getPartyBanner()
//            getPersonRoomList(TYPE_REFRESH)
//            refreshHotRoom()
//        }
//
//        getPersonRoomList(TYPE_REFRESH)
//
//        create_chat_btn.setOnClickListener {
//            RankActivity.start(context)
//            if (getUserInfo() != null) {
//                if (!TextUtils.isEmpty(getUserInfo()!!.room_id) && getUserInfo()!!.room_id != "0") {
//                    joinChat(context!!, getUserInfo()!!.room_id, object : JoinChatCallBack {
//                        override fun onSuc() {}
//                        override fun onFail(msg: String) {
//                            error(context!!, msg)
//                        }
//                    })
//                } else {
//                    createChatRoom()
//                }
//            }
//        }
//
//        fl_search.setOnClickListener {
//            SearchActivity.start(context)
//        }
//        getPartyBanner()
//    }
//
//    private fun refreshHotRoom() {
//        if (headView.view_pager.currentItem == 0) {
//            hotFragment.loadData(TYPE_REFRESH)
//        }
//        if (headView.view_pager.currentItem == 1) {
//            bigTypeRoomFragment1.getData()
//        }
//        if (headView.view_pager.currentItem == 2) {
//            bigTypeRoomFragment2.loadData()
//        }
//        if (headView.view_pager.currentItem == 3) {
//            bigTypeRoomFragment3.loadData()
//        }
//        if (headView.view_pager.currentItem == 4) {
//            bigTypeRoomFragment4.loadData()
//        }
//    }
//
//
//    private fun itemMove(fromPosition: Int, toPosition: Int) {
//        if (!isInTop) {
//            return
//        }
//        if (isHidden) {
//            return
//        }
//        if (refresh_layout.isRefreshing) {
//            return
//        }
//        if (adapter.data.size <= 2) {
//            return
//        }
//        val rvFromPosition = fromPosition + 1
//        val rvToPosition = toPosition + 1
//        adapter.data.add(toPosition, adapter.getData().removeAt(fromPosition))//数据更换
//        adapter.notifyItemMoved(rvFromPosition, rvToPosition)//执行动画
//        adapter.notifyItemRangeChanged(rvFromPosition.coerceAtMost(rvToPosition), abs(rvFromPosition - rvToPosition) + 1)//受影响的itemd都刷新下
//
//    }
//
//    private fun getPartyBanner() {
//        NetService.getInstance(mContext!!).getBanner(getString(R.string.banner_type), object : Callback<List<BannerBean>>() {
//            override fun onSuccess(nextPage: Int, bean: List<BannerBean>, code: Int) {
//                if (bean.isNotEmpty()) {
//                    partyBannerList.clear()
//                    partyBannerList.addAll(bean)
//                    headView.banner.setImages(partyBannerList)
//                            .start()
//                }
//            }
//
//            override fun onError(msg: String, throwable: Throwable, code: Int) {
//                error(mContext!!, msg)
//            }
//
//            override fun isAlive(): Boolean {
//                return isLive
//            }
//        })
//    }
//
//
//    private fun createChatRoom() {
//        val chatName = getUserInfo()!!.nickname + "的房间"
//        val micType = 0
//        val micWay = 0
//        val mLableId = 0
//        val chatPassword = ""
//        val chatNotice = ""
//        createChat(activity!!, "", chatName, "", "", micType, micWay, chatPassword, chatNotice, mLableId.toString(), object : JoinChatCallBack {
//            override fun onSuc() {}
//            override fun onFail(msg: String) {
//                error(context!!, msg)
//            }
//        })
//    }
//
//    private fun getPersonRoomList(type: Int) {
//        if (type == TYPE_REFRESH) {
//            mPage = 1
//        }
//        NetService.getInstance(context!!).getPersonRoomList(mPage, object : Callback<HttpPageDataBean<PersonRoomItemBean>>() {
//            override fun onSuccess(nextPage: Int, bean: HttpPageDataBean<PersonRoomItemBean>, code: Int) {
//                if (type == TYPE_REFRESH) {
//                    adapter.setNewData(bean.list)
//                    refresh_layout.finishRefresh(true) //传入false表示刷新失败
//                } else {
//                    refresh_layout.finishLoadMore(true)
//                    adapter.addData(bean.list)
//                }
//                mPage++
//
//            }
//
//            override fun onError(msg: String, throwable: Throwable, code: Int) {
//                if (type == TYPE_REFRESH) {
//                    refresh_layout.finishRefresh(false) //传入false表示刷新失败
//                } else {
//                    refresh_layout.finishLoadMore(false)
//                }
//            }
//
//            override fun isAlive(): Boolean {
//                return isLive
//            }
//
//        })
//    }
//
//}
