package com.poussiere.kotlinnetworkboundresource.ui.utils

import android.view.View
import androidx.databinding.BindingConversion

/**
 * Boolean to visibility converter
 */
@BindingConversion
fun convertBooleanToVisibility(visible: Boolean): Int {
    return if (visible) View.VISIBLE else View.GONE
}