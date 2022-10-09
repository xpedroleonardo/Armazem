package com.example.armazem

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat

class StorageScreenActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_storage_screen)

    println("#### INÍCIO STORAGE ####")

    val paramsInventory = intent.extras
    val productsHash =
      paramsInventory!!.getSerializable("Products") as HashMap<String, String>
    var voltar: Button = findViewById(R.id.voltar)

    exibirProdutos(productsHash)

    voltar.setOnClickListener {
      var params = Bundle()
      params.putAll(paramsInventory)

      val proximaTela = Intent(this, HomeScreenActivity::class.java)
      proximaTela.putExtras(params)
      startActivity(proximaTela)

      finish()
    }
  }

  fun exibirProdutos(armazem: HashMap<String, String>) {
    var tabela: TableLayout = findViewById(R.id.tabela_produtos)

    for (x in armazem.keys) {
      var dados = armazem[x]?.split(":")

      var linha = TableRow(this)
      var separador = View(this)
      var typeFace: Typeface? = ResourcesCompat.getFont(this, R.font.inter)

      val separadorLayout =
        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 3)
      separador.setLayoutParams(separadorLayout)
      separador.setBackgroundColor(Color.parseColor("#CECECE"));

      val linhaLayout =
        TableRow.LayoutParams(
          TableRow.LayoutParams.MATCH_PARENT,
          TableRow.LayoutParams.MATCH_PARENT
        )
      linha.setLayoutParams(linhaLayout)
      linha.setPadding(27, 27, 0, 27)

      var listaProduto: TextView = TextView(this)
      listaProduto.width = 150
      listaProduto.text = "${dados?.get(3)}"
      listaProduto.setTypeface(typeFace)
      listaProduto.setTextColor(Color.BLACK)
      linha.addView(listaProduto)

      var listaQuantidade: TextView = TextView(this)
      listaQuantidade.width = 150
      listaQuantidade.text = "${dados?.get(4)}"
      listaQuantidade.textAlignment = View.TEXT_ALIGNMENT_CENTER
      listaQuantidade.setTypeface(typeFace)
      listaQuantidade.setTextColor(Color.BLACK)
      linha.addView(listaQuantidade)

      tabela.addView(separador)
      tabela.addView(linha)
    }
  }
}