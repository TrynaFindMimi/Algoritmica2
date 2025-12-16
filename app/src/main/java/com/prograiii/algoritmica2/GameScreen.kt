package com.prograiii.algoritmica2

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.FrameLayout
import android.widget.ImageView
import android.view.ViewGroup
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

        val operacion = intent.getStringExtra("OPERACION")

        when (operacion) {
            "MCM" -> {
                // TODO mcm
            }
            "MCD" -> {
                // TODO mcd
            }
        }
    }

    private fun lanzarMeteoritos(contenedor: ViewGroup) {
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                val meteorito = ImageView(contenedor.context).apply {
                    setImageResource(R.drawable.meteorito)
                    layoutParams = FrameLayout.LayoutParams(150, 150)
                    x = (0..contenedor.width).random().toFloat()
                    y = 0f
                }
                contenedor.addView(meteorito)
                moverMeteorito(meteorito)
                handler.postDelayed(this, 5000)
            }
        }
        handler.post(runnable)
    }

    private fun moverMeteorito(meteorito: ImageView) {
        val anim = ObjectAnimator.ofFloat(meteorito, "translationY", 0f, 1500f)
        anim.duration = 9000
        anim.start()

        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                (meteorito.parent as ViewGroup).removeView(meteorito)
            }
        })
    }
}
