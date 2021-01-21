/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.lib.listener

import android.util.TypedValue
import com.lib.listener.DisplayMetricsHolder.screenDisplayMetrics

/**
 * Android dp to pixel manipulation
 */
object PixelUtil {
    /**
     * Convert from DIP to PX
     */
    fun toPixelFromDIP(value: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value,
            screenDisplayMetrics
        )
    }

    /**
     * Convert from DIP to PX
     */
    fun toPixelFromDIP(value: Double): Float {
        return toPixelFromDIP(value.toFloat())
    }

    /**
     * Convert from SP to PX
     */
    fun toPixelFromSP(value: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            value,
            screenDisplayMetrics
        )
    }

    /**
     * Convert from SP to PX
     */
    fun toPixelFromSP(value: Double): Float {
        return toPixelFromSP(value.toFloat())
    }

    /**
     * Convert from PX to DP
     */
    fun toDIPFromPixel(value: Float): Float {
        return value / screenDisplayMetrics!!.density
    }
}