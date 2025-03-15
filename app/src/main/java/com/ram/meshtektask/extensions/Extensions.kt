package com.ram.meshtektask.extensions

import android.app.Activity
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding

/**
 * @author Kenil
 * @date 15-04-2024
 */

inline fun <T : ViewBinding> Activity.viewBinding(crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }