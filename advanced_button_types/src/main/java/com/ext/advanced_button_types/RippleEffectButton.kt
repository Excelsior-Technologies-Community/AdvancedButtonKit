package com.ext.advanced_button_types

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat

/**
 * RippleEffectButton - A button with custom ripple animation effect
 * Features:
 * - Custom ripple color
 * - Animated ripple from touch point
 * - Configurable ripple duration
 * - Works on all API levels
 */
class RippleEffectButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.buttonStyle
) : AppCompatButton(context, attrs, defStyleAttr) {

    private var rippleColor: Int = 0
    private var rippleDuration: Long = 600
    private var rippleAlpha: Int = 100

    private var rippleX = 0f
    private var rippleY = 0f
    private var rippleRadius = 0f
    private var maxRippleRadius = 0f

    private val ripplePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var rippleAnimator: ObjectAnimator? = null

    init {
        // Read custom attributes
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.RippleEffectButton,
            0, 0
        ).apply {
            try {
                rippleColor = getColor(
                    R.styleable.RippleEffectButton_ripple_color,
                    ContextCompat.getColor(context, android.R.color.white)
                )
                rippleDuration = getInt(
                    R.styleable.RippleEffectButton_ripple_duration,
                    600
                ).toLong()
                rippleAlpha = getInt(
                    R.styleable.RippleEffectButton_ripple_alpha,
                    100
                )
            } finally {
                recycle()
            }
        }

        // Setup ripple paint
        ripplePaint.color = rippleColor
        ripplePaint.alpha = rippleAlpha
        ripplePaint.style = Paint.Style.FILL

        // Enable drawing
        setWillNotDraw(false)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // Calculate maximum ripple radius (diagonal of the button)
        maxRippleRadius = Math.sqrt((w * w + h * h).toDouble()).toFloat()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            rippleX = event.x
            rippleY = event.y
            startRippleAnimation()
        }
        return super.onTouchEvent(event)
    }

    private fun startRippleAnimation() {
        // Cancel any existing animation
        rippleAnimator?.cancel()

        // Reset ripple
        rippleRadius = 0f

        // Create and start animation
        rippleAnimator = ObjectAnimator.ofFloat(this, "rippleRadius", 0f, maxRippleRadius).apply {
            duration = rippleDuration
            interpolator = DecelerateInterpolator()
            addUpdateListener {
                invalidate() // Redraw on each frame
            }
            start()
        }
    }

    // Property for animator
    fun setRippleRadius(radius: Float) {
        rippleRadius = radius
    }

    fun getRippleRadius(): Float = rippleRadius

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw ripple effect
        if (rippleRadius > 0 && rippleRadius <= maxRippleRadius) {
            // Calculate alpha based on radius (fade out as it expands)
            val currentAlpha = (rippleAlpha * (1 - rippleRadius / maxRippleRadius)).toInt()
            ripplePaint.alpha = currentAlpha.coerceAtLeast(0)

            canvas.drawCircle(rippleX, rippleY, rippleRadius, ripplePaint)
        }
    }

    /**
     * Set ripple color programmatically
     */
    fun setRippleColor(color: Int) {
        rippleColor = color
        ripplePaint.color = color
        invalidate()
    }

    /**
     * Set ripple duration programmatically
     */
    fun setRippleDuration(duration: Long) {
        rippleDuration = duration
    }

    /**
     * Set ripple alpha programmatically (0-255)
     */
    fun setRippleAlpha(alpha: Int) {
        rippleAlpha = alpha.coerceIn(0, 255)
    }
}