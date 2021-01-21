package com.lib.listener

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener

/**
 * 此文件代码来自com.facebook.react.ReactRootView#CustomGlobalLayoutListener
 */
class GlobalLayoutListener(
    /**
     * Activity的根布局(Activity#setContentView方法传入的View) 或 DecorView
     */
    private val mView: View?, l: OnKeyboardChangedListener?) : OnGlobalLayoutListener {
    private val mVisibleViewArea: Rect
    private val mMinKeyboardHeightDetected: Int
    private var mKeyboardHeight = 0
    private val mListener: OnKeyboardChangedListener?
    override fun onGlobalLayout() {
        if (mView == null) {
            return
        }
        checkForKeyboardEvents()
    }

    private fun checkForKeyboardEvents() {
        mView!!.rootView.getWindowVisibleDisplayFrame(mVisibleViewArea)
        val heightDiff =
            DisplayMetricsHolder.screenDisplayMetrics!!.heightPixels - mVisibleViewArea.bottom
        if (mKeyboardHeight != heightDiff && heightDiff > mMinKeyboardHeightDetected) {
            // keyboard is now showing, or the keyboard height has changed
            mKeyboardHeight = heightDiff
            mListener?.onKeyboardChanged(
                true,
                mKeyboardHeight,
                mVisibleViewArea.width(),
                mVisibleViewArea.bottom
            )
        } else if (mKeyboardHeight != 0 && heightDiff <= mMinKeyboardHeightDetected) {
            // keyboard is now hidden
            mKeyboardHeight = 0
            mListener?.onKeyboardChanged(
                false,
                mKeyboardHeight,
                mVisibleViewArea.width(),
                mVisibleViewArea.bottom
            )
        }
    }
    init {
        DisplayMetricsHolder.initDisplayMetricsIfNotInitialized(mView!!.context.applicationContext)
        mVisibleViewArea = Rect()
        mMinKeyboardHeightDetected = PixelUtil.toPixelFromDIP(60f).toInt()
        mListener = l
    }
}