package com.example.armazem

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding

class InventoryScreenActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_inventory_screen)

    println("#### INÍCIO INVENTORY ####")

    val paramsSearch = intent.extras
    val productsHash =
      paramsSearch!!.getSerializable("Products") as HashMap<String, String>
    var telaInicial: Button = findViewById(R.id.tela_inicial)
    var novaSeparacao: Button = findViewById(R.id.nova_separacao)
    val novoHash = exibirProdutos(productsHash)

    novaSeparacao.setOnClickListener {
      var params = Bundle()
      params.putSerializable("Products", novoHash)

      val proximaTela = Intent(this, SeparationScreenActivity::class.java)
      proximaTela.putExtras(params)
      startActivity(proximaTela)
    }

    telaInicial.setOnClickListener {
      var params = Bundle()
      params.putSerializable("Products", novoHash)

      val proximaTela = Intent(this, HomeScreenActivity::class.java)
      proximaTela.putExtras(params)
      startActivity(proximaTela)
    }
  }

  fun exibirProdutos(produtosSeparacao: HashMap<String, String>): HashMap<String, String> {
    var tabela: TableLayout = findViewById(R.id.tabela_produtos)
    var products = ProductsList()

    var novoHash = HashMap<String, String>()

    for (x in produtosSeparacao.keys) {
      var dados = produtosSeparacao[x]?.split(":")

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

      var listaEAN: TextView = TextView(this)
      listaEAN.width = 90
      listaEAN.text = "$x"
      listaEAN.textAlignment = View.TEXT_ALIGNMENT_CENTER
      listaEAN.setTypeface(typeFace)
      listaEAN.setTextColor(Color.BLACK)
      linha.addView(listaEAN)

      var listaSeparado: TextView = TextView(this)
      listaSeparado.width = 150
      listaSeparado.text = "10"
      listaSeparado.textAlignment = View.TEXT_ALIGNMENT_CENTER
      listaSeparado.setTypeface(typeFace)
      listaSeparado.setTextColor(Color.BLACK)
      linha.addView(listaSeparado)

      var listaEstoque: TextView = TextView(this)
      listaEstoque.width = 150
      listaEstoque.text = (Integer.parseInt(dados?.get(4)) - 10).toString()
      listaEstoque.textAlignment = View.TEXT_ALIGNMENT_CENTER
      listaEstoque.setTypeface(typeFace)
      listaEstoque.setTextColor(Color.BLACK)
      linha.addView(listaEstoque)

      tabela.addView(separador)
      tabela.addView(linha)

      var junta = "${dados?.get(0)}:${dados?.get(1)}:${dados?.get(2)}:${dados?.get(3)}:${
        (Integer.parseInt(dados?.get(4)) - 10)
      }"
      novoHash.put(x, junta)
    }

    return novoHash
  }
}