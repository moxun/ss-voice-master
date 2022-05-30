//package com.miaomi.fenbei.common.view
//
//import android.content.Context
//import android.graphics.Typeface
//import androidx.viewpager.widget.ViewPager
//import android.util.AttributeSet
//import android.view.Gravity
//import android.view.View
//import android.widget.LinearLayout
//import android.widget.RelativeLayout
//import com.miaomi.fenbei.common.R
//import com.miaomi.fenbei.common.util.DensityUtil
//import kotlinx.android.synthetic.main.common_my_tab_title.view.*
//import java.util.*
//
///**
// * Created by
// * on 2019-08-27.
// */
//class MyTabLayout: LinearLayout, ViewPager.OnPageChangeListener {
//
//    private var childViews = ArrayList<View>()
//    private var mSelectTextColor = 0xff222222.toInt()
//    private var mUnSelectTextColor = 0xff555555.toInt()
//    private var mTextSize = 18f
//
//    constructor(context: Context?) : super(context)
//    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
//    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
//        init(attrs, defStyleAttr)
//    }
//
//    private fun init(attrs: AttributeSet?, defStyleAttr: Int) {
//        childViews.clear()
//        orientation = HORIZONTAL
//        gravity = Gravity.CENTER
//        var typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTabLayoutStyle, defStyleAttr, 0)
//        mSelectTextColor = typedArray.getColor(R.styleable.MyTabLayoutStyle_tl_setSelectTextColor, mSelectTextColor)
//        mUnSelectTextColor = typedArray.getColor(R.styleable.MyTabLayoutStyle_tl_setUnSelectTextColor, mUnSelectTextColor)
//        mTextSize = typedArray.getDimension(R.styleable.MyTabLayoutStyle_tl_setTextSize, 18f)
//        typedArray.recycle()
//    }
//
//    fun bindViewPager(titles: List<String>, viewPager: ViewPager) {
//        titles.forEachIndexed { index, s ->
//            var view = View.inflate(context, R.layout.common_my_tab_title, null)
//            view.setOnClickListener {viewPager.currentItem = index}
//            view.title_tv.text = s
//            view.title_tv.textSize = mTextSize
//            var params = RelativeLayout.LayoutParams(DensityUtil.dp2px(context, mTextSize*s.length), DensityUtil.dp2px(context, 8f))
//            params.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.title_tv)
//            params.bottomMargin = DensityUtil.dp2px(context, 1f)
//            view.line.layoutParams = params
//            if (index == 0) {
//                view.title_tv.setTextColor(mSelectTextColor)
//                view.title_tv.typeface = Typeface.DEFAULT_BOLD
//                view.line.visibility = View.VISIBLE
//            } else {
//                view.title_tv.setTextColor(mUnSelectTextColor)
//                view.title_tv.typeface = Typeface.DEFAULT
//                view.line.visibility = View.GONE
//            }
//            addView(view)
//            childViews.add(view)
//        }
//        viewPager.addOnPageChangeListener(this)
//    }
//
//    override fun onPageScrollStateChanged(state: Int) {
//
//    }
//
//    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//
//    }
//
//    override fun onPageSelected(position: Int) {
//        childViews.forEach {
//            it.title_tv.setTextColor(mUnSelectTextColor)
//            it.title_tv.typeface = Typeface.DEFAULT
//            it.line.visibility = View.GONE
//        }
//        getChildAt(position).title_tv.setTextColor(mSelectTextColor)
//        getChildAt(position).title_tv.typeface = Typeface.DEFAULT_BOLD
//        getChildAt(position).line.visibility = View.VISIBLE
//    }
//}