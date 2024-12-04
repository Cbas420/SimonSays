package com.example.symonsayssebastianpavia

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lblNombreJugador = findViewById(R.id.NombreJugador) as EditText

        val btnIniciarJuego = findViewById<Button>(R.id.BotonJugar)

        val btnVerRanking = findViewById<Button>(R.id.BotonRanking)

        // Al hacer clic en "Iniciar Juego"
        btnIniciarJuego.setOnClickListener {
            val nombreJugador = lblNombreJugador.text.toString().trim()
            if (nombreJugador.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa tu nombre", Toast.LENGTH_SHORT).show()
            } else {
                // Iniciar el juego y pasar el nombre del jugador a GameActivity
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtra("nombreJugador", nombreJugador)  // Pasamos el nombre
                startActivity(intent)
            }
        }

        btnVerRanking.setOnClickListener {
            // Iniciar RankingActivity
            val intent = Intent(this, RankingActivity::class.java)
            startActivity(intent)
        }

    }
}


