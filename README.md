# Advanced Button Library for Android

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9-blue?logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
[![API](https://img.shields.io/badge/API-21%2B-orange.svg)](#)

**Advanced Button Library** is a powerful, lightweight, and highly customizable Android library offering modern, animated, and visually stunning button components — perfect for apps that demand beautiful UI with minimal effort.

Say goodbye to boring buttons. Say hello to **Toggle Switches**, **Gradient Buttons**, **Loading States**, and **Custom Ripple Effects** — all with smooth animations and full XML + programmatic control.

---

## Preview

<img src="app/src/main/assets/Video.gif" height="320"/>

---

## Features

- **ToggleButton** – iOS-style animated switch with customizable ON/OFF text, colors & thumb
- **GradientButton** – Stunning gradient backgrounds (horizontal, vertical, diagonal) with rounded corners
- **LoadingButton** – Seamless loading state with centered circular progress bar (auto-hides text)
- **RippleEffectButton** – Beautiful custom ripple animation from touch point (better than default)
- **Fully Customizable** – Change colors, radius, duration, orientation at runtime
- **Smooth Animations** – Powered by `ValueAnimator` and `ObjectAnimator`
- **Material Design Ready** – Built on `AppCompatButton`, works perfectly with themes
- **Lightweight & No Dependencies**

---

## Installation

### Step 1: Add JitPack repository (root `build.gradle`)

```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2: Add dependency (app `build.gradle`)

```gradle
dependencies {
     implementation 'com.github.Excelsior-Technologies-Community:AdvancedButtonKit:1.0.0'
}
```

---

## Usage

### 1. ToggleButton (Animated Switch)

```xml
<com.ext.advanced_button_types.ToggleButton
    android:id="@+id/toggleButton"
    android:layout_width="150dp"
    android:layout_height="50dp"
    app:toggle_isToggled="false"
    app:toggle_thumbColor="@android:color/white"
    app:toggle_toggledBackgroundColor="#4CAF50"
    app:toggle_untoggledBackgroundColor="#F44336" />
```

**Kotlin:**
```kotlin
toggleButton.setOnToggleChangeListener { isOn ->
    Toast.makeText(this, if (isOn) "ON" else "OFF", Toast.LENGTH_SHORT).show()
}

// Show custom text
toggleButton.setText("ENABLED", "DISABLED")
// Or symbols
toggleButton.setText("YES", "NO")
// Or no text (plain switch)
toggleButton.setText("", "")
```

---

### 2. GradientButton

```xml
<com.ext.advanced_button_types.GradientButton
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="Diagonal Gradient"
    android:textColor="@android:color/white"
    app:gradient_startColor="#8E2DE2"
    app:gradient_endColor="#4A00E0"
    app:gradient_orientation="tlBr"
    app:gradient_cornerRadius="32dp" />
```

**Supported orientations:**
- `leftRight` · `topBottom` · `tlBr` · `blTr`

**Programmatically:**
```kotlin
gradientButton.setGradientColors(Color.CYAN, Color.MAGENTA)
gradientButton.setCornerRadius(50f)
gradientButton.setGradientOrientation(3) // blTr
```

---

### 3. LoadingButton

```xml
<com.ext.advanced_button_types.LoadingButton
    android:id="@+id/loadingButton"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="Submit"
    android:textColor="@android:color/white"
    app:loading_progressBarColor="@android:color/white"
    app:loading_progressBarSize="48dp" />
```

**Kotlin:**
```kotlin
loadingButton.setOnClickListener {
    loadingButton.startLoading()

    Handler(Looper.getMainLooper()).postDelayed({
        loadingButton.stopLoading()
        Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
    }, 3000)
}
```

Prevents double taps · Clean state management

---

### 4. RippleEffectButton

```xml
<com.ext.advanced_button_types.RippleEffectButton
    android:id="@+id/rippleButton"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="Tap for Magic"
    android:textColor="@android:color/white"
    app:ripple_color="#FFFFFF"
    app:ripple_alpha="120"
    app:ripple_duration="700" />
```

**Kotlin:**
```kotlin
rippleButton.setRippleColor(Color.YELLOW)
rippleButton.setRippleDuration(1000)
```

Works on all API levels · No XML ripple drawable needed

---

## XML Attributes

| View                  | Attribute                            | Format      | Description                         |
|-----------------------|--------------------------------------|-------------|-------------------------------------|
| `ToggleButton`        | `toggle_isToggled`                   | boolean     | Initial state                       |
|                       | `toggle_toggledBackgroundColor`      | color       | Background when ON                  |
|                       | `toggle_untoggledBackgroundColor`    | color       | Background when OFF                 |
|                       | `toggle_thumbColor`                  | color       | Thumb (circle) color                |
| `GradientButton`      | `gradient_startColor`                | color       | Start gradient color                |
|                       | `gradient_endColor`                  | color       | End gradient color                  |
|                       | `gradient_orientation`               | enum        | leftRight / topBottom / tlBr / blTr |
|                       | `gradient_cornerRadius`              | dimension   | Corner radius                       |
| `LoadingButton`       | `loading_progressBarColor`           | color       | Spinner color                       |
|                       | `loading_progressBarSize`            | dimension   | Spinner size (dp)                   |
| `RippleEffectButton`  | `ripple_color`                       | color       | Ripple color                        |
|                       | `ripple_duration`                    | integer     | Animation duration (ms)             |
|                       | `ripple_alpha`                       | integer     | Ripple transparency (0–255)         |

---

## Programmatic Methods

All views support full runtime customization:

```kotlin
// ToggleButton
toggleButton.setThumbColor(Color.WHITE)
toggleButton.setToggledBackgroundColor(Color.GREEN)

// GradientButton
gradientButton.setGradientColors(startColor, endColor)
gradientButton.setCornerRadius(40f)

// LoadingButton
loadingButton.setProgressBarColor(Color.WHITE)
loadingButton.setProgressBarSize(60)

// RippleEffectButton
rippleButton.setRippleColor(Color.CYAN)
rippleButton.setRippleDuration(800)
```

---

## Requirements

- Android API 21+ (Lollipop)
- Kotlin
- AndroidX

---

## 📄 License
 
```
MIT License
 
Copyright (c) 2025 Excelsior Technologies
 
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
 
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
 
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
 
---
