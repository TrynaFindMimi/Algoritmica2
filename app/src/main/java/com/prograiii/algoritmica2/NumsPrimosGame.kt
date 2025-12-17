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
    private var numeroSeleccionado: Int? = null

    private var score = 0
    private var gameEnded = false
    private val handler = Handler(Looper.getMainLooper())
    private var vidas = 3
    val criba = MutableList(101) { true }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        runCriba(100)
        binding = ActivityNumsPrimosGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lanzarMeteoritos(binding.root as ViewGroup)

        binding.btnTrue.setOnClickListener {
            verificarRespuesta(true)
        }
        binding.btnFalse.setOnClickListener {
            verificarRespuesta(false)
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        gameEnded = true
        handler.removeCallbacksAndMessages(null)
    }

    private fun lanzarMeteoritos(contenedor: ViewGroup) {
        val runnable = object : Runnable {
            override fun run() {
                if (gameEnded) return
                val meteoritoView = layoutInflater.inflate(R.layout.item_meteorito, contenedor, false)

                meteoritoView.x = (0..contenedor.width).random().toFloat()
                meteoritoView.y = 0f

                val txtOperacion = meteoritoView.findViewById<TextView>(R.id.txtOperacion)
                val numero = generarNumeroPrimo()
                txtOperacion.text = numero.toString()

                meteoritoView.setOnClickListener {
                    numeroSeleccionado = numero
                    binding.inputDisplay.text = "Número seleccionado: $numero"
                }

                contenedor.addView(meteoritoView)
                moverMeteorito(meteoritoView)

                handler.postDelayed(this, 5000)
            }
        }
        handler.post(runnable)
    }

    private fun moverMeteorito(meteoritoView: View) {
        val anim = ObjectAnimator.ofFloat(meteoritoView, "translationY", 0f, 1500f)
        anim.duration = 15000
        anim.start()

        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                meteoritoView.visibility = View.GONE
            }
        })
    }

    private fun generarNumeroPrimo(): Int {
        return (2..100).random()
    }

    private fun esPrimo(n: Int): Boolean {
        if (n < 2) return false
        for (i in 2..Math.sqrt(n.toDouble()).toInt()) {
            if (n % i == 0) return false
        }
        return true
    }

    private fun verificarRespuesta(respuestaJugador: Boolean) {
        val numero = numeroSeleccionado
        if (numero == null) {
            binding.inputDisplay.text = "Selecciona un meteorito primero"
            return
        }

        val correcto = criba[numero]
        if (respuestaJugador == correcto) {
            score++
            binding.inputDisplay.text = "¡Correcto! Puntos: $score | Vidas: $vidas"
        } else {
            vidas--
            binding.inputDisplay.text = "Incorrecto. Puntos: $score | Vidas: $vidas"
        }

        numeroSeleccionado = null

        if (vidas <= 0) {
            terminarJuego("¡Perdiste! Se acabaron tus vidas.")
        } else if (score >= 30) {
            terminarJuego("¡Ganaste! Llegaste a 30 puntos.")
        }
    }

    private fun terminarJuego(mensaje: String) {
        gameEnded = true
        handler.removeCallbacksAndMessages(null)
        binding.inputDisplay.text = mensaje
        binding.btnTrue.isEnabled = false
        binding.btnFalse.isEnabled = false
    }

    fun runCriba(n: Int) {
        criba[0] = false
        criba[1] = false

        for (i in 2..n) {
            if (i * i > n) break
            if (criba[i]) {
                for (j in i * i..n step i) {
                    criba[j] = false
                }
            }
        }
    }


}
