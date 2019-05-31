package com.adrosonic.adrocafe.adrocafe.utils

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

import java.util.HashMap

class CardDrawerLayout : DrawerLayout {
    private val settings = HashMap<Int, Setting>()
    private var defaultScrimColor = -0x67000000
    private var defaultDrawerElevation: Float = 0.toFloat()
    private var frameLayout: FrameLayout? = null
    var drawerView: View? = null

    constructor(context: Context) : super(context) {
        init(context, null, 0)

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, 0)

    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs, defStyle)

    }


    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        defaultDrawerElevation = drawerElevation
        addDrawerListener(object : DrawerLayout.DrawerListener {

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                this@CardDrawerLayout.drawerView = drawerView
                updateSlideOffset(drawerView, slideOffset)
            }

            override fun onDrawerOpened(drawerView: View) {

            }

            override fun onDrawerClosed(drawerView: View) {

            }

            override fun onDrawerStateChanged(newState: Int) {

            }
        })

        frameLayout = FrameLayout(context)

        super.addView(frameLayout)

    }


    override fun addView(child: View, params: ViewGroup.LayoutParams) {
        child.layoutParams = params
        addView(child)
    }

    override fun addView(child: View) {
        if (child is NavigationView) {
            super.addView(child)
        } else {
            val cardView = CardView(context)
            cardView.radius = 0f
            cardView.addView(child)
            cardView.cardElevation = 0f
            frameLayout!!.addView(cardView)
        }
    }

    fun setViewScale(gravity: Int, percentage: Float) {
        val absGravity = getDrawerViewAbsoluteGravity(gravity)
        val setting: Setting?
        if (!settings.containsKey(absGravity)) {
            setting = Setting()
            settings[absGravity] = setting
        } else
            setting = settings[absGravity]

        setting!!.percentage = percentage

        setting.scrimColor = Color.TRANSPARENT
        setting.drawerElevation = 0f


    }

    fun setViewElevation(gravity: Int, elevation: Float) {
        val absGravity = getDrawerViewAbsoluteGravity(gravity)
        val setting: Setting?
        if (!settings.containsKey(absGravity)) {
            setting = Setting()
            settings[absGravity] = setting
        } else
            setting = settings[absGravity]

        setting!!.scrimColor = Color.TRANSPARENT
        setting.drawerElevation = 0f
        setting.elevation = elevation
    }

    fun setViewScrimColor(gravity: Int, scrimColor: Int) {
        val absGravity = getDrawerViewAbsoluteGravity(gravity)
        val setting: Setting?
        if (!settings.containsKey(absGravity)) {
            setting = Setting()
            settings[absGravity] = setting
        } else
            setting = settings[absGravity]

        setting!!.scrimColor = scrimColor
    }

    fun setDrawerElevation(gravity: Int, elevation: Float) {
        val absGravity = getDrawerViewAbsoluteGravity(gravity)
        val setting: Setting?
        if (!settings.containsKey(absGravity)) {
            setting = Setting()
            settings[absGravity] = setting
        } else
            setting = settings[absGravity]

        setting!!.elevation = 0f
        setting.drawerElevation = elevation
    }

    fun setRadius(gravity: Int, radius: Float) {
        val absGravity = getDrawerViewAbsoluteGravity(gravity)
        val setting: Setting?
        if (!settings.containsKey(absGravity)) {
            setting = Setting()
            settings[absGravity] = setting
        } else
            setting = settings[absGravity]

        setting!!.radius = radius
    }


    private fun getSetting(gravity: Int): Setting? {
        val absGravity = getDrawerViewAbsoluteGravity(gravity)
        return settings[absGravity]

    }

    override fun setDrawerElevation(elevation: Float) {
        defaultDrawerElevation = elevation
        super.setDrawerElevation(elevation)
    }

    override fun setScrimColor(@ColorInt color: Int) {
        defaultScrimColor = color
        super.setScrimColor(color)
    }

    fun useCustomBehavior(gravity: Int) {
        val absGravity = getDrawerViewAbsoluteGravity(gravity)

        if (!settings.containsKey(absGravity)) {
            val setting = Setting()
            settings[absGravity] = setting
        }
    }

    fun removeCustomBehavior(gravity: Int) {
        val absGravity = getDrawerViewAbsoluteGravity(gravity)

        if (settings.containsKey(absGravity)) {
            settings.remove(absGravity)
        }
    }


    override fun openDrawer(drawerView: View, animate: Boolean) {
        super.openDrawer(drawerView, animate)

        post { updateSlideOffset(drawerView, if (isDrawerOpen(drawerView)) 1f else 0f) }
    }

    private fun updateSlideOffset(drawerView: View, slideOffset: Float) {
        val absHorizGravity = getDrawerViewAbsoluteGravity(Gravity.START)
        val childAbsGravity = getDrawerViewAbsoluteGravity(drawerView)


        for (i in 0 until frameLayout!!.childCount) {
            val child = frameLayout!!.getChildAt(i) as CardView
            val setting = settings[childAbsGravity]
            var adjust = 0f

            if (setting != null) {

                child.radius = (setting.radius * slideOffset).toInt().toFloat()
                super.setScrimColor(setting.scrimColor)
                super.setDrawerElevation(setting.drawerElevation)
                val percentage = 1f - setting.percentage
                val reduceHeight = height.toFloat() * percentage * slideOffset
                val params = child.layoutParams as FrameLayout.LayoutParams
                params.topMargin = (reduceHeight / 2).toInt()
                params.bottomMargin = (reduceHeight / 2).toInt()
                child.layoutParams = params
                child.cardElevation = setting.elevation * slideOffset
                adjust = setting.elevation
                val width = if (childAbsGravity == absHorizGravity)
                    drawerView.width + adjust
                else
                    -drawerView.width - adjust
                child.x = width * slideOffset
                //                ViewCompat.setX(child, width * slideOffset);


            } else {
                super.setScrimColor(defaultScrimColor)
                super.setDrawerElevation(defaultDrawerElevation)
            }


        }

    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerView?.let {
            updateSlideOffset(it, if (isDrawerOpen(drawerView!!)) 1f else 0f)
        }
    }


    internal fun getDrawerViewAbsoluteGravity(gravity: Int): Int {
        return GravityCompat.getAbsoluteGravity(
            gravity,
            ViewCompat.getLayoutDirection(this)
        ) and Gravity.HORIZONTAL_GRAVITY_MASK

    }

    internal fun getDrawerViewAbsoluteGravity(drawerView: View): Int {
        val gravity = (drawerView.layoutParams as DrawerLayout.LayoutParams).gravity
        return getDrawerViewAbsoluteGravity(gravity)
    }

    private inner class Setting {
        var percentage = 1f
            internal set
        var scrimColor = defaultScrimColor
            internal set
        var elevation = 0f
            internal set
        var drawerElevation = defaultDrawerElevation
            internal set
        var radius: Float = 0.toFloat()
            internal set
    }

    companion object {
        private val TAG = CardDrawerLayout::class.java.simpleName
    }
}
