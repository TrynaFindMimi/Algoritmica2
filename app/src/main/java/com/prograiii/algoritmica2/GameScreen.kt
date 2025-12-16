package com.prograiii.algoritmica2

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.prograiii.algoritmica2.databinding.ActivityGameScreenBinding

class GameScreen : AppCompatActivity() {

    private lateinit var binding: ActivityGameScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityGameScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lanzarMeteoritos(binding.meteoritoContainer)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun lanzarMeteoritos(contenedor: ViewGroup) {
        val handler = Handler(Looper.getMainLooper())

        val runnable = object : Runnable {
            override fun run() {
                val size = 150
                val meteorito = ImageView(contenedor.context)

                meteorito.setImageResource(R.drawable.meteorito)
                meteorito.layoutParams = FrameLayout.LayoutParams(size, size)

                val maxX = (contenedor.width - size).coerceAtLeast(0)
                meteorito.x = (0..maxX).random().toFloat()
                meteorito.y = 0f

                meteorito.setOnClickListener {
                    destruirMeteorito(meteorito)
                }

                contenedor.addView(meteorito)
                moverMeteorito(meteorito)

                handler.postDelayed(this, 10000)
            }
        }

        handler.post(runnable)
    }

    private fun moverMeteorito(meteorito: ImageView) {
        val anim = ObjectAnimator.ofFloat(meteorito, "translationY", 0f, 1500f)
        anim.duration = 20000
        anim.start()

        meteorito.tag = anim

        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if (meteorito.parent is ViewGroup) {
                    (meteorito.parent as ViewGroup).removeView(meteorito)
                }
            }
        })
    }

    private fun destruirMeteorito(meteorito: ImageView) {
        val anim = meteorito.tag
        if (anim is ObjectAnimator) {
            anim.cancel()
        }

        val parent = meteorito.parent
        if (parent is ViewGroup) {
            parent.removeView(meteorito)
        }
    }
}
