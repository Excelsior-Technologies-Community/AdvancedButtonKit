package com.ext.advanced_button_types

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat

/**
 * ToggleButton - A switch-style button that switches between ON/OFF states
 * Features:
 * - Switch design with thumb animation
 * - Customizable ON/OFF text display (optional)
 * - Customizable colors for ON and OFF states
 * - Smooth animated transitions
 * - Works as plain toggle without text
 */
class ToggleButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.buttonStyle
) : AppCompatButton(context, attrs, defStyleAttr) {

    // Toggle state
    private var isToggled = false

    // Colors for different states
    private var toggledBackgroundColor: Int = 0
    private var untoggledBackgroundColor: Int = 0
    private var thumbColor: Int = 0

    // Text for toggle states (nullable for plain toggle without text)
    private var toggledText: String? = null
    private var untoggledText: String? = null

    // Animation
    private var thumbPosition = 0f // 0 to 1 (left to right)
    private var thumbAnimator: ValueAnimator? = null

    // Drawing objects
    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val thumbPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    // Dimensions
    private var switchWidth = 0f
    private var switchHeight = 0f
    private var thumbRadius = 0f
    private val padding = 8f

    // Listener for toggle state changes
    private var onToggleChangeListener: ((Boolean) -> Unit)? = null

    init {
        // Read custom attributes
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ToggleButton,
            0, 0
        ).apply {
            try {
                isToggled = getBoolean(R.styleable.ToggleButton_toggle_isToggled, false)
                toggledBackgroundColor = getColor(
                    R.styleable.ToggleButton_toggle_toggledBackgroundColor,
                    ContextCompat.getColor(context, android.R.color.holo_green_dark)
                )
                untoggledBackgroundColor = getColor(
                    R.styleable.ToggleButton_toggle_untoggledBackgroundColor,
                    ContextCompat.getColor(context, android.R.color.holo_red_dark)
                )
                thumbColor = getColor(
                    R.styleable.ToggleButton_toggle_thumbColor,
                    ContextCompat.getColor(context, android.R.color.white)
                )
            } finally {
                recycle()
            }
        }

        // Setup paints
        thumbPaint.color = thumbColor
        thumbPaint.style = Paint.Style.FILL

        textPaint.color = ContextCompat.getColor(context, android.R.color.white)
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.textSize = 50f
        textPaint.isFakeBoldText = true

        // Set initial position
        thumbPosition = if (isToggled) 1f else 0f

        // Enable drawing
        setWillNotDraw(false)

        // Remove default button background
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        text = "" // Clear default text
        toggledText = "ON"
        untoggledText = "OFF"

        // Set click listener for toggling
        setOnClickListener {
            toggle()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        switchWidth = w.toFloat()
        switchHeight = h.toFloat()
        thumbRadius = (switchHeight / 2) - padding
    }

    override fun onDraw(canvas: Canvas) {
        // Draw background
        val bgColor = if (isToggled) toggledBackgroundColor else untoggledBackgroundColor
        backgroundPaint.color = bgColor

        val rect = RectF(0f, 0f, switchWidth, switchHeight)
        canvas.drawRoundRect(rect, switchHeight / 2, switchHeight / 2, backgroundPaint)

        // Draw text only if text is set (not null and not empty)
        if (isToggled) {
            // Draw toggled text (ON) on the left if available
            if (!toggledText.isNullOrEmpty()) {
                val textY = (switchHeight / 2) - ((textPaint.descent() + textPaint.ascent()) / 2)
                val textX = switchWidth * 0.3f
                canvas.drawText(toggledText!!, textX, textY, textPaint)
            }
        } else {
            // Draw untoggled text (OFF) on the right if available
            if (!untoggledText.isNullOrEmpty()) {
                val textY = (switchHeight / 2) - ((textPaint.descent() + textPaint.ascent()) / 2)
                val textX = switchWidth * 0.7f
                canvas.drawText(untoggledText!!, textX, textY, textPaint)
            }
        }

        // Draw thumb (circle)
        val thumbX =
            padding + thumbRadius + (thumbPosition * (switchWidth - 2 * padding - 2 * thumbRadius))
        val thumbY = switchHeight / 2
        canvas.drawCircle(thumbX, thumbY, thumbRadius, thumbPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                parent.requestDisallowInterceptTouchEvent(true)
                return true
            }

            MotionEvent.ACTION_UP -> {
                performClick()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * Toggle the button state with animation
     */
    fun toggle() {
        isToggled = !isToggled
        animateThumb()
        onToggleChangeListener?.invoke(isToggled)
    }

    /**
     * Set toggle state programmatically
     */
    fun setToggled(toggled: Boolean, animate: Boolean = true) {
        if (isToggled != toggled) {
            isToggled = toggled
            if (animate) {
                animateThumb()
            } else {
                thumbPosition = if (isToggled) 1f else 0f
                invalidate()
            }
            onToggleChangeListener?.invoke(isToggled)
        }
    }

    /**
     * Get current toggle state
     */
    fun isToggled(): Boolean = isToggled

    /**
     * Set listener for toggle state changes
     */
    fun setOnToggleChangeListener(listener: (Boolean) -> Unit) {
        onToggleChangeListener = listener
    }

    /**
     * Set custom text for both toggled and untoggled states
     * Pass null or empty strings to show plain toggle without text
     *
     * @param toggledText Text to show when toggle is ON (e.g., "ON", "YES", "ENABLED")
     * @param untoggledText Text to show when toggle is OFF (e.g., "OFF", "NO", "DISABLED")
     *
     * Examples:
     * setText("ON", "OFF") - Shows ON/OFF
     * setText("YES", "NO") - Shows YES/NO
     * setText("✓", "✗") - Shows checkmark/cross
     * setText("", "") - Plain toggle without text
     */
    fun setText(toggledText: String?, untoggledText: String?) {
        this.toggledText = toggledText
        this.untoggledText = untoggledText
        invalidate() // Redraw to reflect text changes
    }

    /**
     * Set custom text for toggled state only
     */
    fun setToggledText(text: String?) {
        this.toggledText = text
        invalidate()
    }

    /**
     * Set custom text for untoggled state only
     */
    fun setUntoggledText(text: String?) {
        this.untoggledText = text
        invalidate()
    }

    /**
     * Get toggled state text
     */
    fun getToggledText(): String? = toggledText

    /**
     * Get untoggled state text
     */
    fun getUntoggledText(): String? = untoggledText

    /**
     * Animate thumb movement
     */
    private fun animateThumb() {
        thumbAnimator?.cancel()

        val startPos = thumbPosition
        val endPos = if (isToggled) 1f else 0f

        thumbAnimator = ValueAnimator.ofFloat(startPos, endPos).apply {
            duration = 300
            addUpdateListener { animation ->
                thumbPosition = animation.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    /**
     * Set thumb color programmatically
     */
    fun setThumbColor(color: Int) {
        thumbColor = color
        thumbPaint.color = color
        invalidate()
    }

    /**
     * Set toggled background color
     */
    fun setToggledBackgroundColor(color: Int) {
        toggledBackgroundColor = color
        invalidate()
    }

    /**
     * Set untoggled background color
     */
    fun setUntoggledBackgroundColor(color: Int) {
        untoggledBackgroundColor = color
        invalidate()
    }
}