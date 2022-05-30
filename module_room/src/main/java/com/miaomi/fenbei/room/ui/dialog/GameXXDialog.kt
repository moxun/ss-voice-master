package com.miaomi.fenbei.room.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.miaomi.fenbei.base.bean.GameXXBean
import com.miaomi.fenbei.base.bean.MortalResultBean
import com.miaomi.fenbei.base.bean.MsgType
import com.miaomi.fenbei.base.net.Callback
import com.miaomi.fenbei.base.net.NetService
import com.miaomi.fenbei.base.util.ToastUtil
import com.miaomi.fenbei.base.widget.GameXXView
import com.miaomi.fenbei.room.ChatRoomManager
import com.miaomi.fenbei.room.ChatRoomManager.getRoomId
import com.miaomi.fenbei.room.ChatRoomManager.sendMsg
import com.miaomi.fenbei.room.R
import kotlinx.android.synthetic.main.dialog_game_xx.*
import kotlin.collections.ArrayList

class GameXXDialog(context: Context) : Dialog(context, R.style.common_dialog) {

    private var contents: ArrayList<GameXXBean> = ArrayList()

    //展示
    private val XX_RESULT_SHOW = 1

    //放弃
    private val XX_RESULT_CANCEL = 2

    private var isGetReslut = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setWindowAnimations(R.style.dialogAnimStyle)//添加动画
        setContentView(R.layout.dialog_game_xx)
        init()
    }

    private fun init() {
        isGetMortalResult()
        tv_submit.setOnClickListener {
            contents.clear()
            getMortalResult()
        }
        tv_cancel.setOnClickListener {
            showXXReslut(XX_RESULT_CANCEL)
            dismiss()
        }
        tv_show.setOnClickListener {
            showXXReslut(XX_RESULT_SHOW)
            dismiss()
        }
    }


    private fun xx(view: GameXXView, bean: GameXXBean) {
//        var type = Random().nextInt(4)
//        var level = Random().nextInt(13)
//        var bean = GameXXBean()
//        bean.level = level
//        bean.type = type
        view.visibility = View.VISIBLE
        view.setLevel(bean)
        contents.add(bean)
    }

    private fun getMortalResult() {
        NetService.getInstance(context).getMortalResult(getRoomId(), object : Callback<List<GameXXBean>>() {
            override fun onSuccess(nextPage: Int, list: List<GameXXBean>, code: Int) {
                sendMsg(MsgType.GAME_LSP_MSG, "获得灵兽牌", getRoomId())
                if (list.size == 5) {
                    xx(xx_game_1, list[0])
                    xx(xx_game_2, list[1])
                    xx(xx_game_3, list[2])
                    xx(xx_game_4, list[3])
                    xx(xx_game_5, list[4])
                }

                if (list.size == 3) {
                    xx(xx_game_1, list[0])
                    xx(xx_game_2, list[1])
                    xx(xx_game_3, list[2])
                }

                if (list.size == 2) {
                    xx(xx_game_1, list[0])
                    xx(xx_game_2, list[1])
                }

                if (list.size == 1) {
                    xx(xx_game_1, list[0])
                }
                tv_submit.isClickable = false
                isGetReslut = true
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context, "麦下无法参与或参与人数已满")
            }

            override fun isAlive(): Boolean {
                return true
            }

        })
    }


    private fun isGetMortalResult() {
        NetService.getInstance(context).isGetMortalResult(getRoomId(), object : Callback<MortalResultBean>() {
            override fun onSuccess(nextPage: Int, bean: MortalResultBean, code: Int) {
                xx_game_1.visibility = View.GONE
                xx_game_2.visibility = View.GONE
                xx_game_3.visibility = View.GONE
                xx_game_4.visibility = View.GONE
                xx_game_5.visibility = View.GONE
                if (bean.list.size >= 1) {
                    if (bean.list.size == 5) {
                        xx(xx_game_1, bean.list[0])
                        xx(xx_game_2, bean.list[1])
                        xx(xx_game_3, bean.list[2])
                        xx(xx_game_4, bean.list[3])
                        xx(xx_game_5, bean.list[4])
                    }

                    if (bean.list.size == 3) {
                        xx(xx_game_1, bean.list[0])
                        xx(xx_game_2, bean.list[1])
                        xx(xx_game_3, bean.list[2])
                    }

                    if (bean.list.size == 2) {
                        xx(xx_game_1, bean.list[0])
                        xx(xx_game_2, bean.list[1])
                    }

                    if (bean.list.size == 1) {
                        xx(xx_game_1, bean.list[0])
                    }
                    tv_submit.isClickable = false
                    isGetReslut = true
                } else {

                    if (bean.pai_count == 5) {
                        xx_game_1.visibility = View.VISIBLE
                        xx_game_2.visibility = View.VISIBLE
                        xx_game_3.visibility = View.VISIBLE
                        xx_game_4.visibility = View.VISIBLE
                        xx_game_5.visibility = View.VISIBLE
                    }

                    if (bean.pai_count == 3) {
                        xx_game_1.visibility = View.VISIBLE
                        xx_game_2.visibility = View.VISIBLE
                        xx_game_3.visibility = View.VISIBLE
                    }

                    if (bean.pai_count == 2) {
                        xx_game_1.visibility = View.VISIBLE
                        xx_game_2.visibility = View.VISIBLE
                    }
                    if (bean.pai_count == 1) {
                        xx_game_1.visibility = View.VISIBLE
                    }
                    tv_submit.isClickable = true
                    isGetReslut = false
                }
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context, msg)
            }

            override fun isAlive(): Boolean {
                return true
            }

        })
    }

    private fun showXXReslut(type: Int) {
        if (!isGetReslut) {
            return
        }
        NetService.getInstance(context).showXXReslut(getRoomId(), type, object : Callback<Boolean>() {
            override fun onSuccess(nextPage: Int, bean: Boolean, code: Int) {
                if (bean) {
                    if (type == XX_RESULT_CANCEL) {
                        sendMsg(MsgType.GAME_LSP_MSG, "放弃", getRoomId())
                    } else {
                        if (contents.size > 0) {
                            ChatRoomManager.sendGameXXReslutMsg(MsgType.GAME_LSP_RESULT, "", getRoomId(), gameXXBean = contents)
                        }
                    }
                }
            }

            override fun onError(msg: String, throwable: Throwable, code: Int) {
                ToastUtil.error(context, msg)
            }

            override fun isAlive(): Boolean {
                return true
            }

        })
    }

}