package com.ext.advanced_button_types

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat

/**
 * LoadingButton - A button that shows loading state with progress bar
 * Features:
 * - Shows/hides circular progress bar in center
 * - Hides text during loading
 * - Disables interaction during loading
 * - Smooth transition between states
 * - Fixed: No double background issue
 */
class LoadingButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.buttonStyle
) : FrameLayout(context, attrs, defStyleAttr) {

    private var isLoading = false
    private var originalText: CharSequence = "Button"
    private var progressBarColor: Int = 0
    private var progressBarSize: Int = 0

    private val button: AppCompatButton
    private val progressBar: ProgressBar

    init {
        // Read custom attributes
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.LoadingButton,
            0, 0
        ).apply {
            try {
                progressBarColor = getColor(
                    R.styleable.LoadingButton_loading_progressBarColor,
                    ContextCompat.getColor(context, android.R.color.white)
                )
                progressBarSize = getDimensionPixelSize(
                    R.styleable.LoadingButton_loading_progressBarSize,
                    (32 * resources.displayMetrics.density).toInt() // Default 32dp
                )
            } finally {
                recycle()
            }
        }

        // Create the button with transparent background to avoid double background
        button = AppCompatButton(context, attrs, defStyleAttr).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            // Remove button's own background - FrameLayout will handle the background
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        }
        addView(button)

        // Create progress bar (initially hidden)
        progressBar = ProgressBar(context).apply {
            val params = LayoutParams(progressBarSize, progressBarSize)
            params.gravity = Gravity.CENTER
            layoutParams = params
            visibility = GONE
            indeterminateDrawable?.setColorFilter(progressBarColor, PorterDuff.Mode.SRC_IN)
        }
        addView(progressBar)

        // Store original text from XML
        originalText = button.text
    }

    /**
     * Start loading state - shows progress bar, hides text
     */
    fun startLoading() {
        if (isLoading) return

        isLoading = true

        // Save current text and clear it
        originalText = button.text
        button.text = ""

        // Disable button
        button.isEnabled = false
        isEnabled = false

        // Show progress bar
        progressBar.visibility = VISIBLE
    }

    /**
     * Stop loading state - hides progress bar, shows text
     */
    fun stopLoading() {
        if (!isLoading) return

        isLoading = false

        // Restore text
        button.text = originalText

        // Enable button
        button.isEnabled = true
        isEnabled = true

        // Hide progress bar
        progressBar.visibility = GONE
    }

    /**
     * Check if button is in loading state
     */
    fun isLoading(): Boolean = isLoading

    /**
     * Set button text
     */
    fun setText(text: CharSequence) {
        originalText = text
        if (!isLoading) {
            button.text = text
        }
    }

    /**
     * Get button text
     */
    fun getText(): CharSequence = originalText

    /**
     * Set click listener
     */
    override fun setOnClickListener(listener: OnClickListener?) {
        button.setOnClickListener(listener)
    }

    /**
     * Set progress bar color programmatically
     */
    fun setProgressBarColor(color: Int) {
        progressBarColor = color
        progressBar.indeterminateDrawable?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
    }

    /**
     * Set progress bar size programmatically
     */
    fun setProgressBarSize(sizePx: Int) {
        progressBarSize = sizePx
        val params = progressBar.layoutParams as LayoutParams
        params.width = sizePx
        params.height = sizePx
        progressBar.layoutParams = params
    }
}