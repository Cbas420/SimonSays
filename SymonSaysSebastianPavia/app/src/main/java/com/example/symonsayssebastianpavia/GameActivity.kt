package com.example.symonsayssebastianpavia

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.GridView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    lateinit var gridJuego: GridView
    lateinit var nombreJugador: String
    lateinit var textViewPuntuacion: TextView
    lateinit var layoutBotones: LinearLayout
    lateinit var buttonReintentar: Button
    lateinit var buttonSalir: Button

    val imageAdapter = ImageAdapter(this)
    private var secuenciaSimon: MutableList<Int> = mutableListOf()
    private var secuenciaUsuario: MutableList<Int> = mutableListOf()
    private var clicksContados = 0
    private val archivoRanking = "ranking.json"
    private var puntuacionActual = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        gridJuego = findViewById(R.id.GridJuego)
        textViewPuntuacion = findViewById(R.id.TextViewPuntuacion)
        layoutBotones = findViewById(R.id.LayoutBotones)
        buttonReintentar = findViewById(R.id.BotonReintentar)
        buttonSalir = findViewById(R.id.BotonSalir)

        gridJuego.adapter = imageAdapter

        nombreJugador = intent.getStringExtra("nombreJugador") ?: "Jugador Desconocido"

        iniciarJuego()

        // Configurar los botones para volver a jugar o salir
        buttonReintentar.setOnClickListener {
            layoutBotones.visibility = LinearLayout.GONE
            iniciarJuego()
        }

        buttonSalir.setOnClickListener {
            finish()  // Cierra la actividad y sale del juego
        }
    }

    private fun iniciarJuego() {
        secuenciaSimon.clear()
        secuenciaUsuario.clear()
        clicksContados = 0
        puntuacionActual = 0
        textViewPuntuacion.text = "Puntuación: $puntuacionActual"
        layoutBotones.visibility = LinearLayout.GONE
        gridJuego.isEnabled = true
        insertarNuevoSucesoPatron()
    }

    private fun insertarNuevoSucesoPatron() {
        val handler = Handler(Looper.getMainLooper())
        val indiceRandom: Int = Random.nextInt(0, 4)

        secuenciaSimon.add(indiceRandom)

        gridJuego.isEnabled = false // Esto es para que no se puedan tocar los botones mientras se muestra la secuencia

        // Mostrar la secuencia de Simón
        handler.postDelayed({
            for (i in secuenciaSimon.indices) {
                handler.postDelayed({
                    imageAdapter.cambiarImagenPatron(secuenciaSimon[i])
                }, i * 900L)
            }
        }, 2000)

        gridJuego.isEnabled = true

        handler.postDelayed({
            guardarSecuenciaUsuario()
        }, (secuenciaSimon.size * 900L) + 2000)
    }

    private fun guardarSecuenciaUsuario() {
        secuenciaUsuario.clear()
        clicksContados = 0
        val clicksMaximos: Int = secuenciaSimon.size

        gridJuego.setOnItemClickListener { _, _, position, _ ->
            if (clicksContados < clicksMaximos) {
                clicksContados++
                secuenciaUsuario.add(position)
                imageAdapter.cambiarImagenClick(position)
                comprobarSecuenciaParcial()
            }

            if (clicksContados == clicksMaximos) {
                comprobarResultado()
            }
        }
    }

    private fun comprobarSecuenciaParcial() {
        val indiceActual = secuenciaUsuario.size - 1
        if (secuenciaUsuario[indiceActual] != secuenciaSimon[indiceActual]) {
            Toast.makeText(this, "Error! Inténtalo de nuevo.", Toast.LENGTH_SHORT).show()
            guardarRanking(puntuacionActual)  // Guardar puntuación antes de reiniciar
            mostrarOpciones()
        }
    }

    private fun comprobarResultado() {
        if (secuenciaUsuario == secuenciaSimon) {
            puntuacionActual++
            textViewPuntuacion.text = "Puntuación: $puntuacionActual"
            insertarNuevoSucesoPatron()
        } else {
            Toast.makeText(this, "Error! Inténtalo de nuevo.", Toast.LENGTH_SHORT).show()
            guardarRanking(puntuacionActual)
            mostrarOpciones()
        }
    }

    private fun mostrarOpciones() {
        gridJuego.isEnabled = false
        layoutBotones.visibility = LinearLayout.VISIBLE
    }

    private fun guardarRanking(puntuacion: Int) {
        val ranking = leerRanking()

        val nuevoJugador = JSONObject()
        nuevoJugador.put("nombre", nombreJugador)
        nuevoJugador.put("puntuacion", puntuacion)

        ranking.put(nuevoJugador)

        val file = File(filesDir, archivoRanking)
        val fos = FileOutputStream(file)
        fos.write(ranking.toString().toByteArray())
        fos.close()
    }

    private fun leerRanking(): JSONArray {
        val file = File(filesDir, archivoRanking)

        if (!file.exists()) {
            return JSONArray()  // Si el archivo no existe, devolver una lista vacía
        }

        val fis = FileInputStream(file)
        val contenido = fis.readBytes().toString(Charsets.UTF_8)
        fis.close()

        return JSONArray(contenido)
    }
}
