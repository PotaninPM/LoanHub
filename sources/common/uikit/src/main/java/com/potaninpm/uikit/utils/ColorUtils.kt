package com.potaninpm.uikit.utils

import androidx.compose.ui.graphics.Color

fun parseHexColor(hex: String): Color {
    val normalized = hex.removePrefix("#")
    return Color(android.graphics.Color.parseColor("#$normalized"))
}

fun parseHexColorOrNull(hex: String): Color? {
    return try {
        parseHexColor(hex)
    } catch (_: Exception) {
        null
    }
}

fun Color.toHexString(): String {
    val a = (alpha * 255).toInt()
    val r = (red * 255).toInt()
    val g = (green * 255).toInt()
    val b = (blue * 255).toInt()
    return "#%02X%02X%02X%02X".format(a, r, g, b)
}
