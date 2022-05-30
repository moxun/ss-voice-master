package com.miaomi.fenbei.room.util

import com.miaomi.fenbei.room.R

object DiceUtil {

    val diceResult = intArrayOf(
            R.drawable.room_icon_dice_point_1,
            R.drawable.room_icon_dice_point_2,
            R.drawable.room_icon_dice_point_3,
            R.drawable.room_icon_dice_point_4,
            R.drawable.room_icon_dice_point_5,
            R.drawable.room_icon_dice_point_6)

//    fun showDice(view:ImageView,result:Int){
//        view.setImageResource(R.drawable.room_dice)
//        (view.drawable as AnimationDrawable).start()
//        Handler().postDelayed({
//            (view.drawable as AnimationDrawable).stop()
//
//            view.setImageResource(diceResult[result])
//            Handler().postDelayed({
//                if (view != null) {
//                    view.visibility = View.GONE
//                }
//            }, 1000)
//        }, 2000L)
//    }
}