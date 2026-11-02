package com.kelvin.minhagaleria

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.view.Gravity
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.graphics.Color
import android.view.ViewGroup

class MainActivity : Activity() {

    private lateinit var galeria: GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fundo = Color.rgb(9, 7, 15)
        val roxo = Color.rgb(168, 85, 247)

        val tela = LinearLayout(this)
        tela.orientation = LinearLayout.VERTICAL
        tela.setPadding(20, 35, 20, 20)
        tela.setBackgroundColor(fundo)

        val titulo = TextView(this)
        titulo.text = "MINHA GALERIA BLACK"
        titulo.textSize = 25f
        titulo.setTextColor(roxo)
        titulo.gravity = Gravity.CENTER
        tela.addView(titulo)

        val subtitulo = TextView(this)
        subtitulo.text = "Suas memórias. Seu estilo."
        subtitulo.textSize = 15f
        subtitulo.setTextColor(Color.WHITE)
        subtitulo.gravity = Gravity.CENTER
        tela.addView(subtitulo)

        val botao = Button(this)
        botao.text = "＋ ADICIONAR FOTOS"
        tela.addView(botao)

        galeria = GridLayout(this)
        galeria.columnCount = 3

        tela.addView(
            galeria,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        )

        botao.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(intent, 100)
        }

        setContentView(tela)
    }

    @Deprecated("Usado para receber as fotos selecionadas")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            val imagens = data?.clipData

            if (imagens != null) {
                for (i in 0 until imagens.itemCount) {
                    adicionarFoto(imagens.getItemAt(i).uri)
                }
            } else {
                data?.data?.let { adicionarFoto(it) }
            }
        }
    }

    private fun adicionarFoto(uri: android.net.Uri) {
        val imagem = ImageView(this)
        imagem.setImageURI(uri)
        imagem.scaleType = ImageView.ScaleType.CENTER_CROP

        val parametros = GridLayout.LayoutParams()
        parametros.width = 0
        parametros.height = 125
        parametros.columnSpec = GridLayout.spec(
            GridLayout.UNDEFINED, 1f
        )
        parametros.setMargins(4, 4, 4, 4)

        galeria.addView(imagem, parametros)
    }
}
