package edu.vt.cs5254.multiquiz

import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.Button

private const val DEFAULT_BUTTON_COLOR = "#00AAFF"
private const val SELECTED_BUTTON_COLOR = "#CC3377"

fun Button.updateColor() {
    val color = if (isSelected) SELECTED_BUTTON_COLOR else DEFAULT_BUTTON_COLOR
    backgroundTintList = ColorStateList.valueOf(Color.parseColor(color))
    alpha = if (isEnabled) 1.0f else 0.5f
}
