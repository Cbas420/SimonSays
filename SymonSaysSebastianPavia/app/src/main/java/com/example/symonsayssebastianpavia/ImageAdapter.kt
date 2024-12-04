package com.example.symonsayssebastianpavia

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.ImageView
import kotlinx.coroutines.delay
import java.util.Collections
import kotlin.random.Random

class ImageAdapter(val context: Context): BaseAdapter() { //que es eso de context y que es base adapter

    private val imagenesApagadas = intArrayOf(R.drawable.botonazulapagado, R.drawable.botonrojoapagado, R.drawable.botonverdeapagado, R.drawable.botonamarilloapagado)
    private val imagenesEncendidas = intArrayOf(R.drawable.botonazulencendido, R.drawable.botonrojoencendido, R.drawable.botonverdeencendido, R.drawable.botonamarilloencendido)

    private val indicesImagenes = intArrayOf(0, 1, 2, 3)
    private val indicesImagenesMezcladas = indicesImagenes.clone().apply { shuffle() }

    private val imagenesApagadasMezcladas = IntArray(imagenesApagadas.size) { i -> imagenesApagadas[ indicesImagenesMezcladas [i] ] }
    private val imagenesEncendidasMezcladas = IntArray(imagenesEncendidas.size) { i -> imagenesEncendidas[ indicesImagenesMezcladas [i] ] }



    override fun getCount(): Int {
        return imagenesApagadas.size
    }

    override fun getItem(position: Int): Any {
        return imagenesApagadas[position]
    }

    override fun getItemId(position: Int): Long {
        return 0;
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View { //esto que es tambien hermano no entiendo un carajo
        var gridView:View? = convertView //esto que es
        if (gridView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater //Esto que coño es
            gridView = inflater.inflate(R.layout.imagen_botones,parent,null==true) // Y esto
        }

        val imageView = gridView?.findViewById(R.id.imagenColor) as ImageView //Y esto tambien bro hermano que es esto
        imageView.setImageResource(imagenesApagadasMezcladas[position]) //Esto tambien

        return gridView

    }

    fun cambiarImagenClick(position: Int){
        val changedImage = imagenesApagadasMezcladas[position]
        val handler = Handler(Looper.getMainLooper())
        imagenesApagadasMezcladas[position] = imagenesEncendidasMezcladas[position]
        notifyDataSetChanged()

        handler.postDelayed({
            imagenesApagadasMezcladas[position] = changedImage
            notifyDataSetChanged()
        }, 500)
        notifyDataSetChanged()


    }

    fun cambiarImagenPatron(secuencia: Int) {
        val handler = Handler(Looper.getMainLooper())
        val changedImage = imagenesApagadasMezcladas[secuencia]
        imagenesApagadasMezcladas[secuencia] = imagenesEncendidasMezcladas[secuencia]
        notifyDataSetChanged()
        handler.postDelayed({
            imagenesApagadasMezcladas[secuencia] = changedImage
            notifyDataSetChanged()
                            }, 800)
        }


}