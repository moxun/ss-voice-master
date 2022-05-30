//package com.miaomi.fenbei.common.view.xrecyclerview
//
//import android.animation.Animator
//import android.graphics.Canvas
//import android.graphics.Paint
//import android.view.View
//
///**
// * Created by Jack on 2015/10/15.
// */
//abstract class BaseIndicatorController {
//
//
//    var target: View? = null
//
//    private var mAnimators: MutableList<Animator>? = null
//
//
//    val width: Int
//        get() = if (target == null) {
//            0
//        } else target!!.width
//
//    val height: Int
//        get() = if (target == null) {
//            0
//        } else target!!.height
//
//    fun destroy() {
//        target = null
//        releaseAnimations()
//        mAnimators = null
//    }
//
//    fun postInvalidate() {
//        if (target != null) {
//            target!!.postInvalidate()
//        }
//    }
//
//    /**
//     * draw indicator
//     * @param canvas
//     * @param paint
//     */
//    abstract fun draw(canvas: Canvas, paint: Paint)
//
//    /**
//     * create animation or animations
//     */
//    abstract fun createAnimation(): MutableList<Animator>
//
//    fun initAnimation() {
//        mAnimators = createAnimation()
//    }
//
//    // add by lgh
//    private fun releaseAnimations() {
//        if (mAnimators == null) {
//            return
//        }
//        val count = mAnimators!!.size
//        for (i in 0 until count) {
//            val animator = mAnimators!![i]
//            animator.cancel()
//            animator.removeAllListeners()
//        }
//        mAnimators!!.clear()
//        mAnimators = null
//    }
//
//    /**
//     * make animation to start or end when target
//     * view was be Visible or Gone or Invisible.
//     * make animation to cancel when target view
//     * be onDetachedFromWindow.
//     * @param animStatus
//     */
//    fun setAnimationStatus(animStatus: AnimStatus) {
//        if (mAnimators == null) {
//            return
//        }
//        val count = mAnimators!!.size
//        for (i in 0 until count) {
//            val animator = mAnimators!![i]
//            val isRunning = animator.isRunning
//            when (animStatus) {
//                BaseIndicatorController.AnimStatus.START -> if (!isRunning) {
//                    animator.start()
//                }
//                BaseIndicatorController.AnimStatus.END -> if (isRunning) {
//                    animator.end()
//                }
//                BaseIndicatorController.AnimStatus.CANCEL -> if (isRunning) {
//                    animator.cancel()
//                }
//            }
//        }
//    }
//
//
//    enum class AnimStatus {
//        START, END, CANCEL
//    }
//
//
//}
