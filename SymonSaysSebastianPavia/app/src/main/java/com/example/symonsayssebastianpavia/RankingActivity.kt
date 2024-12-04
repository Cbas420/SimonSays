package com.example.symonsayssebastianpavia

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONArray
import java.io.File
import java.io.FileInputStream

class RankingActivity : AppCompatActivity() {

    private val archivoRanking = "ranking.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        val listViewRanking = findViewById<ListView>(R.id.listViewRanking)

        val ranking = leerRanking()

        val rankingList = mutableListOf<Pair<String, Int>>()

        for (i in 0 until ranking.length()) {
            val jugador = ranking.getJSONObject(i)
            val nombre = jugador.getString("nombre")
            val puntuacion = jugador.getInt("puntuacion")
            rankingList.add(Pair(nombre, puntuacion))
        }

        // mayor a menor
        val rankingOrdenado = rankingList.sortedByDescending { it.second }

        // Crear una lista de cadenas para mostrar en el ListView
        val rankingDisplayList = rankingOrdenado.map { "${it.first} - Puntuación: ${it.second}" }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, rankingDisplayList)
        listViewRanking.adapter = adapter

        val btnVolverAlMenu = findViewById<Button>(R.id.BotonMenu)
        btnVolverAlMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun leerRanking(): JSONArray {
        val file = File(filesDir, archivoRanking)
        if (!file.exists()) {
            return JSONArray()  // devolver una lista vacía si no existe el archivo del ranking
        }
        val fis = FileInputStream(file)
        val contenido = fis.readBytes().toString(Charsets.UTF_8)
        fis.close()
        return JSONArray(contenido)
    }
}