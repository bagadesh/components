@file:Suppress("NOTHING_TO_INLINE")

package com.bagadesh.data

import android.os.Bundle

/**
 * Created by bagadesh on 06/06/23.
 */

inline fun Bundle?.getStringOrFallback(key: String, fallback: () -> String): String {
    if (this == null) return fallback()
    return getString(key) ?: fallback()
}

fun Bundle?.getBooleanOrFallback(key: String, fallback: Boolean): Boolean {
    if (this == null) return fallback
    return getBoolean(key) ?: fallback
}