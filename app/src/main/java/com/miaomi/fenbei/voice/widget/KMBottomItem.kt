package com.miaomi.fenbei.voice.widget

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.miaomi.fenbei.voice.R
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem


class KMBottomItem : BaseTabItem {


    constructor(mContext: Context) : this(mContext, null)

    constructor(mContext: Context, mAttributeSet: AttributeSet?) : this(mContext, mAttributeSet, 0)

    private var icon: ImageView

    private var name: TextView

    private var mDefaultDrawable: Drawable? = null
    private var mCheckedDrawable: Drawable? = null

    private var mDefaultTextColor = Color.parseColor("#999999")
    private var mCheckedTextColor = Color.parseColor("#FF4FA5")
    private var mChecked: Boolean = false
    val scaleY :ObjectAnimator
    val scaleX :ObjectAnimator
    val setAnima :AnimatorSet

    constructor(mContext: Context, mAttributeSet: AttributeSet?, defStyleAttr: Int) : super(mContext, mAttributeSet, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.main_item_main_bottom, this, true)

        icon = findViewById(R.id.tab_icon)
        name = findViewById(R.id.name)
        scaleY = ObjectAnimator.ofFloat(icon, "scaleY", 0.8f, 1f)
        scaleX = ObjectAnimator.ofFloat(icon, "scaleX", 0.8f, 1f)
        setAnima = AnimatorSet()
        setAnima.play(scaleX).with(scaleY)
        setAnima.duration = 500


    }

    fun init(@DrawableRes drawableRes: Int, @DrawableRes checkedDrawableRes: Int, title: String) {
        mDefaultDrawable = ContextCompat.getDrawable(context, drawableRes)
        mCheckedDrawable = ContextCompat.getDrawable(context, checkedDrawableRes)
        name.text = title
    }

    override fun setChecked(checked: Boolean) {
        if (checked) {
            setAnima.start()
            icon.setImageDrawable(mCheckedDrawable)
            name.setTextColor(mCheckedTextColor)
        } else {
            icon.setImageDrawable(mDefaultDrawable)
            name.setTextColor(mDefaultTextColor)
        }
        mChecked = checked
    }

    override fun setSelectedDrawable(drawable: Drawable?) {
        mCheckedDrawable = drawable
        if (mChecked) {
            icon.setImageDrawable(drawable)
        }
    }

    override fun getTitle(): String {
       return name.text.toString()
    }

    override fun setMessageNumber(number: Int) {

    }

    override fun setDefaultDrawable(drawable: Drawable?) {
        mDefaultDrawable = drawable
        if (!mChecked) {
            icon.setImageDrawable(drawable)
        }
    }

    override fun setTitle(title: String?) {
        name.text = title
    }

    override fun setHasMessage(hasMessage: Boolean) {

    }
}