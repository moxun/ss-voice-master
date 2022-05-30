package com.miaomi.fenbei.room.ui.dialog

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miaomi.fenbei.base.bean.*
import com.miaomi.fenbei.room.R
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.room.ChatRoomManager

import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.room.ui.adapter.RowObjectAdapter
import com.miaomi.fenbei.room.ui.adapter.RowWheatAdapter
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class RowWheatDialog : BaseBottomDialog() {


    private lateinit var rv_xq_guest: RecyclerView
    private lateinit var rv_object: RecyclerView
    private lateinit  var ciListBean: RowBean.CiListBean
    private lateinit  var zhuListBean: RowBean.ZhuListBean
    private lateinit  var adapter: RowWheatAdapter
    private lateinit  var user: UserInfo

    private lateinit  var rowObjectAdapter: RowObjectAdapter
    private lateinit  var micgoTv:TextView
    private lateinit  var koutTv:TextView
    private lateinit  var ktopTv:TextView
    private lateinit  var kbaomaiTv:TextView
    private  var zuserId:String="0"
    private  var cuserId:String="0"
    private  var  kout:Int=1
    private  var  ktop:Int=2
    override fun getLayoutRes(): Int {

        return R.layout.room_dialog_row_list
    }


    override fun bindView(v: View) {
        micgoTv= v.findViewById(R.id.tv_mic_go)
        koutTv= v.findViewById(R.id.tv_k_out)
        ktopTv= v.findViewById(R.id.tv_k_top)
        rv_xq_guest = v.findViewById(R.id.rv_xq_guest)
        kbaomaiTv=v.findViewById(R.id.tv_k_baomai)
        rv_xq_guest.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_object = v.findViewById(R.id.rv_object)
        rv_object.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
         adapter = RowWheatAdapter(context,object : RowWheatAdapter.CheckItemListener{
             override fun itemChecked(id: String) {
                 cuserId=id
                 koutTv.isSelected=true
                 ktopTv.isSelected=true
                 kbaomaiTv.isSelected=true
             }

         })
         rowObjectAdapter = RowObjectAdapter(context,object : RowObjectAdapter.CheckItemListener{
            override fun itemChecked(id: String) {
                zuserId=id
                koutTv.isSelected=true
                ktopTv.isSelected=true
                kbaomaiTv.isSelected=true
            }
         })
        rv_xq_guest.adapter = adapter
        rv_object.adapter = rowObjectAdapter
        bindData()
        MaiStatus()

        if (ChatRoomManager.getUserRole() > 1) {
            koutTv.visibility=View.VISIBLE
            ktopTv.visibility=View.VISIBLE
            kbaomaiTv.visibility=View.VISIBLE
            micgoTv.visibility=View.GONE
            rowObjectAdapter.onSelect(1)
            adapter.onSelect(1)
        }else{
            koutTv.visibility=View.GONE
            ktopTv.visibility=View.GONE
            kbaomaiTv.visibility=View.GONE
            micgoTv.visibility=View.VISIBLE
        }
        micgoTv.setOnClickListener{
            if( micgoTv.isSelected){
                oNjoin_Queue()
            }else{
                outMaiStatus()
            }
        }
        koutTv.setOnClickListener {

            if(!zuserId.equals("0")||!cuserId.equals("0")){
                kOutStatus(kout)
            }

        }
        kbaomaiTv.setOnClickListener {
            if(!zuserId.equals("0")||!cuserId.equals("0")){
                if(!zuserId.equals("0")){
                    micCtrl4XqHost(zuserId)
                }
                if(!cuserId.equals("0")){
                    micCtrl4XqHost(cuserId)
                }
            }
        }
        ktopTv.setOnClickListener {
            if(!zuserId.equals("0")||!cuserId.equals("0")){
                kOutStatus(ktop)
            }

        }
    }
      fun  showView() {
          bindData()
          MaiStatus()
    }

    private fun bindData() {
        NetService.getInstance(requireContext()).getXqQueueList(ChatRoomManager.getRoomId(),object : Callback<RowBean>(){
            override fun onSuccess(nextPage: Int, bean: RowBean, code: Int) {
                if(bean.ci_list.size<=0){
                    ciListBean= RowBean.CiListBean()
                    ciListBean.user_id=0
                    ciListBean.face=""
                    ciListBean.nickname="相亲麦位"
                    bean.ci_list.add(ciListBean)
                }
                if(bean.zhu_list.size<=0){
                    zhuListBean= RowBean.ZhuListBean()
                    zhuListBean.user_id=0
                    zhuListBean.face=""
                    zhuListBean.nickname="嘉宾位"
                    bean.zhu_list.add(zhuListBean)
                }
                adapter.setData(bean.ci_list, ChatRoomManager.getUserRole())
                rowObjectAdapter.setData(bean.zhu_list, ChatRoomManager.getUserRole());
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {

            }

            override fun isAlive(): Boolean {
                return true
            }

        })

    }
    private fun  outMaiStatus() {
        NetService.getInstance(context!!).getXqOutMaixuStatus(ChatRoomManager.getRoomId(),object : Callback<MaixuStatusBean>(){
            override fun onSuccess(nextPage: Int, bean: MaixuStatusBean, code: Int) {
                if(bean.maixu_status==0){
                    micgoTv.isSelected=true
                    micgoTv.setText("申请上麦")
                }else if(bean.maixu_status==1){
                    micgoTv.visibility=View.INVISIBLE
                }else{
                    micgoTv.isSelected=false
                    micgoTv.setText("取消排麦")
                }
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {

            }

            override fun isAlive(): Boolean {
                return true
            }

        })
    }
    private fun  oNjoin_Queue() {
        NetService.getInstance(context!!).getXqJoin_Queue(ChatRoomManager.getRoomId(),object : Callback<BaseBean>(){
            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
                micgoTv.isSelected=false
                micgoTv.setText("取消排麦")
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {

            }

            override fun isAlive(): Boolean {
                return true
            }

        })

    }
    private fun  MaiStatus() {
        NetService.getInstance(context!!).getXqMaixuStatus(ChatRoomManager.getRoomId(),object : Callback<MaixuStatusBean>(){
            override fun onSuccess(nextPage: Int, bean: MaixuStatusBean, code: Int) {
                if(bean.maixu_status==0){
                    micgoTv.isSelected=true
                    micgoTv.setText("申请上麦")
                }else if(bean.maixu_status==1&&bean.type==1&&ChatRoomManager.getUserRole()==1){
                    koutTv.visibility=View.VISIBLE
                    ktopTv.visibility=View.VISIBLE
                    kbaomaiTv.visibility=View.VISIBLE
                    micgoTv.visibility=View.GONE
                    rowObjectAdapter.onSelect(1)
                    adapter.onSelect(1)
                }else if(bean.maixu_status==1){
                    micgoTv.visibility=View.INVISIBLE
                }else{
                    micgoTv.isSelected=false
                    micgoTv.setText("取消排麦")
                }
            }
            override fun onError(msg: String, throwable: Throwable, code: Int) {

            }

            override fun isAlive(): Boolean {
                return true
            }

        })
    }
    private fun  kOutStatus(ktype:Int) {
        var userid=""
        if(!zuserId.equals("0")&&!cuserId.equals("0")){
             userid=zuserId+","+cuserId
        }else if(!zuserId.equals("0")){
            userid=zuserId
        }else{
            userid=cuserId
        }
       if(ktype==kout){
        NetService.getInstance(context!!).getXqkOutStatus(ChatRoomManager.getRoomId(),userid,object : Callback<BaseBean>(){
            override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
                koutTv.isSelected=false
                ktopTv.isSelected=false
                kbaomaiTv.isSelected=false
            }
            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context!!, msg)
            }

            override fun isAlive(): Boolean {
                return true
            }

        })
    }else{
           NetService.getInstance(context!!).getXqKtopStatus(ChatRoomManager.getRoomId(),userid,object : Callback<BaseBean>(){
               override fun onSuccess(nextPage: Int, bean: BaseBean, code: Int) {
                   koutTv.isSelected=false
                   ktopTv.isSelected=false
                   kbaomaiTv.isSelected=false
               }

               override fun onError(msg: String, throwable: Throwable, code: Int) {
                   ToastUtil.error(context!!, msg)
               }

               override fun isAlive(): Boolean {
                   return true
               }

           })
    }

    }


    fun micCtrl4XqHost(userid:String){
        NetService.getInstance(context!!).micCtrl4XqHost(ChatRoomManager.getRoomId(), Integer.parseInt(userid), -1, object : Callback<MicStatusBean>() {
            override fun onSuccess(nextPage: Int, bean: MicStatusBean, code: Int) {
                //上麦成功
                zuserId="0"
                cuserId="0"
                koutTv.isSelected=false
                ktopTv.isSelected=false
                kbaomaiTv.isSelected=false
                user= UserInfo()
                user.user_id=Integer.parseInt(userid)
                ChatRoomManager.sendMsg(MsgType.INVITE_TO_MIC, "",ChatRoomManager.getRoomId(), unique_id = bean.unique_id,toUserInfo = user)

            }
            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context!!, msg)
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