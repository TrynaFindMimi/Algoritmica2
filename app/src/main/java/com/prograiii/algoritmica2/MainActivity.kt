package com.prograiii.algoritmica2

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.prograiii.algoritmica2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnMCM.setOnClickListener {
            val intent = Intent(this, GameScreen::class.java)
            intent.putExtra("OPERACION", "MCM")
            startActivity(intent)
        }

        binding.btnPrimos.setOnClickListener {
            val intent = Intent(this, NumsPrimosGame::class.java)
            startActivity(intent)
        }

        binding.btnMCD.setOnClickListener {
            val intent = Intent(this, GameScreen::class.java)
            intent.putExtra("OPERACION", "MCD")
            startActivity(intent)
        }
    }
}
