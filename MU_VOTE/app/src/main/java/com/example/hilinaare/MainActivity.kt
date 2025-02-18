package com.example.hilinaare

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        // Handle window insets for edge-to-edge UI
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find the button by its ID
        val buttonNext = findViewById<Button>(R.id.next) // Replace with your button ID

        // Set OnClickListener for the button
        buttonNext.setOnClickListener {
            // Create an Intent to navigate to the next activity
            val intent = Intent(this, Login::class.java) // Replace LoginActivity with your target activity
            startActivity(intent)
        }

        // Find the image by its ID
        val imageView = findViewById<ImageView>(R.id.p1)

        // Add animation to the image
        animateImage(imageView)
    }

    private fun animateImage(imageView: ImageView) {
        // Create an ObjectAnimator for scaling the image
        val scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 0.5f, 1f)
        val scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 0.5f, 1f)

        // Create an ObjectAnimator for alpha (fade-in effect)
        val alpha = ObjectAnimator.ofFloat(imageView, "alpha", 0f, 1f)

        // Set animation duration
        scaleX.duration = 1000 // 1 second
        scaleY.duration = 1000 // 1 second
        alpha.duration = 1000 // 1 second

        // Set interpolator for smooth animation
        scaleX.interpolator = AccelerateDecelerateInterpolator()
        scaleY.interpolator = AccelerateDecelerateInterpolator()
        alpha.interpolator = AccelerateDecelerateInterpolator()

        // Start the animations together
        scaleX.start()
        scaleY.start()
        alpha.start()
    }
}