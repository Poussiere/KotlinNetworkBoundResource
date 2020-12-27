package com.poussiere.kotlinnetworkboundresource.ui.utils

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion

/**
 * Convert a boolean into an Android view visibility
 */
@BindingConversion
fun convertBooleanToVisibility(visible: Boolean): Int {
    return if (visible) View.VISIBLE else View.GONE
}

