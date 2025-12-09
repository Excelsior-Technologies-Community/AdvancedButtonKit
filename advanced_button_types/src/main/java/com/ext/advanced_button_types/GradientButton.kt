package com.ext.advanced_button_types

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat

/**
 * GradientButton - A button with customizable gradient background
 * Features:
 * - Gradient with start and end colors
 * - Multiple gradient orientations (horizontal, vertical, diagonal)
 * - Customizable corner radius
 */
class GradientButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.buttonStyle
) : AppCompatButton(context, attrs, defStyleAttr) {

    private var gradientStartColor: Int = 0
    private var gradientEndColor: Int = 0
    private var gradientOrientation: Int = 0
    private var cornerRadius: Float = 0f

    init {
        // Read custom attributes
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.GradientButton,
            0, 0
        ).apply {
            try {
                gradientStartColor = getColor(
                    R.styleable.GradientButton_gradient_startColor,
                    ContextCompat.getColor(context, android.R.color.holo_blue_dark)
                )
                gradientEndColor = getColor(
                    R.styleable.GradientButton_gradient_endColor,
                    ContextCompat.getColor(context, android.R.color.holo_purple)
                )
                gradientOrientation = getInt(
                    R.styleable.GradientButton_gradient_orientation,
                    0 // Default: LEFT_RIGHT
                )
                cornerRadius = getDimension(
                    R.styleable.GradientButton_button_cornerRadius,
                    16f
                )
            } finally {
                recycle()
            }
        }

        // Apply gradient
        applyGradient()
    }

    /**
     * Apply gradient background to button
     */
    private fun applyGradient() {
        val orientation = when (gradientOrientation) {
            0 -> GradientDrawable.Orientation.LEFT_RIGHT
            1 -> GradientDrawable.Orientation.TOP_BOTTOM
            2 -> GradientDrawable.Orientation.TL_BR // Top-Left to Bottom-Right
            3 -> GradientDrawable.Orientation.BL_TR // Bottom-Left to Top-Right
            else -> GradientDrawable.Orientation.LEFT_RIGHT
        }

        val gradientDrawable = GradientDrawable(
            orientation,
            intArrayOf(gradientStartColor, gradientEndColor)
        )
        gradientDrawable.cornerRadius = cornerRadius
        background = gradientDrawable
    }

    /**
     * Set gradient colors programmatically
     */
    fun setGradientColors(startColor: Int, endColor: Int) {
        gradientStartColor = startColor
        gradientEndColor = endColor
        applyGradient()
    }

    /**
     * Set corner radius programmatically
     */
    fun setCornerRadius(radius: Float) {
        cornerRadius = radius
        applyGradient()
    }

    /**
     * Set gradient orientation programmatically
     * @param orientation: 0=LEFT_RIGHT, 1=TOP_BOTTOM, 2=TL_BR, 3=BL_TR
     */
    fun setGradientOrientation(orientation: Int) {
        gradientOrientation = orientation
        applyGradient()
    }
}