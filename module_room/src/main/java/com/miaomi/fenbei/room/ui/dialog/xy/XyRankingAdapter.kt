package com.miaomi.fenbei.room.ui.dialog.xy

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.miaomi.fenbei.base.bean.EggRankBean
import com.miaomi.fenbei.base.util.DensityUtil
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.room.R
import com.noober.background.drawable.DrawableCreator
import kotlinx.android.synthetic.main.room_item_xy_ranking.view.*

/**
 * Created by 
 * on 2019-06-03.
 */
class XyRankingAdapter: RecyclerView.Adapter<XyRankingAdapter.ViewHolder>() {

    private var data: ArrayList<EggRankBean> = ArrayList()

    fun setData(list: ArrayList<EggRankBean>) {
        data = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = View.inflate(parent.context, R.layout.room_item_xy_ranking, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(bean: EggRankBean, position: Int) {
//            if (position <= 2) {
//                itemView.rank_iv.visibility = View.VISIBLE
//                itemView.rank_tv.visibility = View.GONE
//                itemView.rank_iv.setImageLevel(position)
//            } else {
//                itemView.rank_iv.visibility = View.GONE
//                itemView.rank_tv.visibility = View.VISIBLE
//                itemView.rank_tv.text = "${position + 1}."
//            }
            when (position) {
                0 -> {
                    itemView.rank_tv.setTextColor(Color.parseColor("#FFD200"))
                    itemView.header_iv.setBackgroundDrawable(DrawableCreator.Builder()
                            .setStrokeColor(Color.parseColor("#FFD200"))
                            .setStrokeWidth(1f).setCornersRadius(DensityUtil.dp2px(itemView.context, 40f).toFloat()).build())
                }
                1 -> {
                    itemView.rank_tv.setTextColor(Color.parseColor("#9FBECE"))
                    itemView.header_iv.setBackgroundDrawable(DrawableCreator.Builder()
                            .setStrokeColor(Color.parseColor("#9FBECE"))
                            .setStrokeWidth(1f).setCornersRadius(DensityUtil.dp2px(itemView.context, 40f).toFloat()).build())
                }
                2 -> {
                    itemView.header_iv.setBackgroundDrawable(DrawableCreator.Builder()
                            .setStrokeColor(Color.parseColor("#F3945B"))
                            .setStrokeWidth(1f).setCornersRadius(DensityUtil.dp2px(itemView.context, 40f).toFloat()).build())
                    itemView.rank_tv.setTextColor(Color.parseColor("#F3945B"))
                }
                else -> {
                    itemView.header_iv.setBackgroundDrawable(null)
                    itemView.rank_tv.setTextColor(Color.parseColor("#FFFFFF"))
                }
            }
            itemView.rank_iv.visibility = View.GONE
            itemView.rank_tv.visibility = View.VISIBLE
            itemView.rank_tv.text = "${position + 1}"
            ImgUtil.loadFaceIcon(itemView.context, bean.face, itemView.header_iv)
            itemView.name_tv.text = bean.nickname
            itemView.price_tv.text = "${bean.price}钻石"
        }
    }

}