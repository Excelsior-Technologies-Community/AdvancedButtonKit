package com.ext.advancebuttons

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ext.advanced_button_types.GradientButton
import com.ext.advanced_button_types.LoadingButton
import com.ext.advanced_button_types.RippleEffectButton
import com.ext.advanced_button_types.ToggleButton

class MainActivity : AppCompatActivity() {

    private lateinit var toggleButton: ToggleButton
    private lateinit var gradientButton1: GradientButton
    private lateinit var gradientButton2: GradientButton
    private lateinit var gradientButton3: GradientButton
    private lateinit var loadingButton: LoadingButton
    private lateinit var rippleButton: RippleEffectButton
    private lateinit var rippleClickCount: TextView
    private var clickCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        initializeViews()

        // Setup listeners
        setupToggleButton()
        setupGradientButtons()
        setupLoadingButton()
        setupRippleButton()
    }

    /**
     * Initialize all button views
     */
    private fun initializeViews() {
        // ToggleButton
        toggleButton = findViewById(R.id.toggleButton)

        // GradientButtons
        gradientButton1 = findViewById(R.id.gradientButton1)
        gradientButton2 = findViewById(R.id.gradientButton2)
        gradientButton3 = findViewById(R.id.gradientButton3)

        // LoadingButton
        loadingButton = findViewById(R.id.loadingButton)

        // Set initial text for loading button
        loadingButton.setText("Start Loading")

        // RippleEffectButton
        rippleButton = findViewById(R.id.rippleButton)
        rippleClickCount = findViewById(R.id.rippleClickCount)
    }

    /**
     * Setup ToggleButton with state change listener
     */
    private fun setupToggleButton() {
        toggleButton.setOnToggleChangeListener { isToggled ->
            // Default ON/OFF text is already set, but you can customize it
//            toggleButton.setText("ON", "OFF")
        }
    }

    /**
     * Setup GradientButtons with click listeners
     */
    private fun setupGradientButtons() {
        // Gradient Button 1 - Horizontal
        gradientButton1.setOnClickListener {
            // Demo: Change gradient colors programmatically
            gradientButton1.setGradientColors(
                android.graphics.Color.parseColor("#FF6B6B"),
                android.graphics.Color.parseColor("#4ECDC4")
            )
        }

        // Gradient Button 2 - Vertical
        gradientButton2.setOnClickListener {
            // Demo: Change corner radius
            gradientButton2.setCornerRadius(48f)
        }

        // Gradient Button 3 - Diagonal
        gradientButton3.setOnClickListener {
            // Demo: Change orientation
            gradientButton3.setGradientOrientation(3) // BL_TR
        }
    }

    /**
     * Setup LoadingButton with ProgressBar (3-second simulation)
     */
    private fun setupLoadingButton() {
        loadingButton.setOnClickListener {
            // Prevent multiple clicks while loading
            if (loadingButton.isLoading()) {
                return@setOnClickListener
            }

            // Start loading - shows progress bar, hides text
            loadingButton.startLoading()

            // Simulate async operation (3 seconds)
            loadingButton.postDelayed({
                // Stop loading - hides progress bar, shows text
                loadingButton.stopLoading()
            }, 3000) // 3 second delay
        }
    }

    /**
     * Setup RippleEffectButton with click counter
     */
    private fun setupRippleButton() {
        rippleButton.setOnClickListener {
            // Increment click counter
            clickCount++
            rippleClickCount.text = "Clicks: $clickCount"

            // Change color based on clicks
            when {
                clickCount % 10 == 0 -> {
                    rippleButton.setRippleColor(android.graphics.Color.YELLOW)
                }

                clickCount % 5 == 0 -> {
                    rippleButton.setRippleColor(android.graphics.Color.WHITE)
                }
            }
        }

        // Long click to reset counter
        rippleButton.setOnLongClickListener {
            clickCount = 0
            rippleClickCount.text = "Clicks: 0"
            rippleButton.setRippleColor(android.graphics.Color.WHITE)
            true
        }
    }
}