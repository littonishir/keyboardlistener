/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.lib.keyboardlistener

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.Display
import java.lang.reflect.InvocationTargetException

/**
 * Holds an instance of the current DisplayMetrics so we don't have to thread it through all the
 * classes that need it.
 * Note: windowDisplayMetrics are deprecated in favor of ScreenDisplayMetrics: window metrics
 * are supposed to return the drawable area but there's no guarantee that they correspond to the
 * actual size of the [android.app.Activity]'s RootView. Moreover, they are not consistent with what iOS
 * returns. Screen metrics returns the metrics of the entire screen, is consistent with iOS and
 * should be used instead.
 */
object DisplayMetricsHolder {
    var screenDisplayMetrics: DisplayMetrics? = null
    fun initDisplayMetricsIfNotInitialized(context: Context) {
        if (screenDisplayMetrics != null) {
            return
        }
        initDisplayMetrics(context)
    }

    private fun initDisplayMetrics(context: Context) {
        val displayMetrics = context.resources.displayMetrics
        screenDisplayMetrics = displayMetrics
        val screenDisplayMetrics = DisplayMetrics()
        screenDisplayMetrics.setTo(displayMetrics)
        val display = context.display

        // Get the real display metrics if we are using API level 17 or higher.
        // The real metrics include system decor elements (e.g. soft menu bar).
        //
        // See: http://developer.android.com/reference/android/view/Display.html#getRealMetrics(android.util.DisplayMetrics)
        if (Build.VERSION.SDK_INT >= 17) {
            display!!.getRealMetrics(screenDisplayMetrics)
        } else {
            // For 14 <= API level <= 16, we need to invoke getRawHeight and getRawWidth to get the real dimensions.
            // Since react-native only supports API level 16+ we don't have to worry about other cases.
            //
            // Reflection exceptions are rethrown at runtime.
            //
            // See: http://stackoverflow.com/questions/14341041/how-to-get-real-screen-height-and-width/23861333#23861333
            try {
                val mGetRawH = Display::class.java.getMethod("getRawHeight")
                val mGetRawW = Display::class.java.getMethod("getRawWidth")
                screenDisplayMetrics.widthPixels = (mGetRawW.invoke(display) as Int)
                screenDisplayMetrics.heightPixels = (mGetRawH.invoke(display) as Int)
            } catch (e: InvocationTargetException) {
                throw RuntimeException("Error getting real dimensions for API level < 17", e)
            } catch (e: IllegalAccessException) {
                throw RuntimeException("Error getting real dimensions for API level < 17", e)
            } catch (e: NoSuchMethodException) {
                throw RuntimeException("Error getting real dimensions for API level < 17", e)
            }
        }
        DisplayMetricsHolder.screenDisplayMetrics = screenDisplayMetrics
    }
}