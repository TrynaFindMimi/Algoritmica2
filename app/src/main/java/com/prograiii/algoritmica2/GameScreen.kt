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
import com.prograiii.algoritmica2.databinding.ActivityGameScreenBinding

class GameScreen : AppCompatActivity() {

    private lateinit var binding: ActivityGameScreenBinding
    private var tipoOperacion: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityGameScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tipoOperacion = intent.getStringExtra("OPERACION")

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
                val meteoritoView = layoutInflater.inflate(R.layout.item_meteorito, contenedor, false)

                meteoritoView.x = (0..contenedor.width).random().toFloat()
                meteoritoView.y = 0f

                val txtOperacion = meteoritoView.findViewById<TextView>(R.id.txtOperacion)
                txtOperacion.text = generarOperacion()

                contenedor.addView(meteoritoView)
                moverMeteorito(meteoritoView)

                handler.postDelayed(this, 5000)
            }
        }
        handler.post(runnable)
    }

    private fun moverMeteorito(meteoritoView: View) {
        val anim = ObjectAnimator.ofFloat(meteoritoView, "translationY", 0f, 1500f)
        anim.duration = 9000
        anim.start()

        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                meteoritoView.visibility = View.GONE
            }
        })
    }

    private fun generarOperacion(): String {
        return when (tipoOperacion) {
            "MCM" -> {
                val a = (2..9).random()
                val b = (2..9).random()
                "$a ∧ $b"
            }
            "MCD" -> {
                val a = (10..50).random()
                val b = (10..50).random()
                "$a ∨ $b"
            }
            else -> "?"
        }
    }
}
