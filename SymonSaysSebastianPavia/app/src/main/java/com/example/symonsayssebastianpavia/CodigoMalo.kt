package com.example.symonsayssebastianpavia

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class CodigoMalo {


    class MainActivity : AppCompatActivity() {

        lateinit var gridJuego: GridView
        val imageAdapter = ImageAdapter(this)


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            gridJuego = findViewById<GridView>(R.id.GridJuego)

            //esto tampoco se que es
            gridJuego.adapter = imageAdapter

            iniciar()

        }

        private fun iniciar() {
            var secuenciaSimon: MutableList<Int> = mutableListOf()

            insertarNuevoSucesoPatron(secuenciaSimon)
        }

        private fun insertarNuevoSucesoPatron(secuenciaSimon: MutableList<Int>) {

            val handler = Handler(Looper.getMainLooper())

            val indiceRandom: Int = Random.nextInt(0, 4)

            secuenciaSimon.add(indiceRandom)

            handler.postDelayed({
                for (i in secuenciaSimon.indices) {
                    handler.postDelayed({
                        imageAdapter.cambiarImagenPatron(secuenciaSimon[i])
                    }, i * 900L)
                }
            }, 2000)


            guardarSecuenciaUsuario(secuenciaSimon)

        }

        private fun guardarSecuenciaUsuario(secuenciaSimon: MutableList<Int>) {
            var secuenciaUsuario: MutableList<Int> = mutableListOf()

            val clicksMaximos: Int = secuenciaSimon.size

            var clicksContados = 0

            gridJuego.setOnItemClickListener { _, _, position, _ ->
                if (clicksMaximos > clicksContados) {
                    clicksContados++
                    secuenciaUsuario.add(position)
                    imageAdapter.cambiarImagenClick(position)
                }
            }

            comprobacionErrorUsuario(secuenciaSimon, secuenciaUsuario)

        }

        private fun comprobacionErrorUsuario(
            secuenciaSimon: MutableList<Int>,
            secuenciaUsuario: MutableList<Int>
        ) {

            //val minSize = minOf(secuenciaSimon.size, secuenciaUsuario.size)

            //SI NO FUNCIONA COMPARARLOS A LOS GOLPES CON ==, USAR LAST INDEX

            if (secuenciaSimon == secuenciaUsuario) {
                insertarNuevoSucesoPatron(secuenciaSimon)


            }


            //Para encender las imagenes le paso la secuencia. El bucle se hace en base al tamaño del array

            //Metodo que genera un numero random del 0 al 3 para elegir uno de los 4 cuadrados. Al crearlo, se añade a la secuencia de del simon dice.

            //Por cada nuevo indice que se genera, se añade un valor mas a la lista mutable

            //La lista mutable es recorrida y se van mostrando las imagenes. Se ejecuta el metodo "cambiar imagen" por cada elemento de la lista mutable

            //Como coño guardo y añado datos a la lista.

            //El tamaño de la secuencia del usuario debe ser igual al de la secuencia.

            //secuencia del simon dice

            //secuencia del usuario

            //Comprobacion del input del usuario correcto


        }


    }

}