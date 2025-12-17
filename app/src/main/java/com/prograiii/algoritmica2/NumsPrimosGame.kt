package com.prograiii.algoritmica2

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.prograiii.algoritmica2.databinding.ActivityNumsPrimosGameBinding

class NumsPrimosGame : AppCompatActivity() {

    private lateinit var binding: ActivityNumsPrimosGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityNumsPrimosGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lanzarMeteoritos(binding.root as ViewGroup)

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
                val meteoritoView = layoutInflater.inflate(R.layout.item_meteorito, contenedor, false)

                meteoritoView.x = (0..contenedor.width).random().toFloat()
                meteoritoView.y = 0f

                val txtOperacion = meteoritoView.findViewById<TextView>(R.id.txtOperacion)
                txtOperacion.text = generarNumeroPrimo()

                contenedor.addView(meteoritoView)
                moverMeteorito(meteoritoView)

                handler.postDelayed(this, 5000)
            }
        }
        handler.post(runnable)
    }

    private fun moverMeteorito(meteoritoView: View) {
        val anim = ObjectAnimator.ofFloat(meteoritoView, "translationY", 0f, 1500f)
        anim.duration = 6000
        anim.start()

        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                meteoritoView.visibility = View.GONE
            }
        })
    }

    private fun generarNumeroPrimo(): String {
        val n = (2..100).random()
        return n.toString()
    }
}
