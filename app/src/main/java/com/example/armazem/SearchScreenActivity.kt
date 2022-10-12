package com.example.armazem

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SearchScreenActivity : AppCompatActivity() {

  private lateinit var statusProcura: TextView
  private lateinit var produtoProcura: TextView
  private lateinit var nomeProduto: TextView
  private lateinit var proximaLocalizacao: String
  private lateinit var chaveAtual: String

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_search_screen)

    println("#### INÍCIO SEARCH ####")

    val paramsSeparation = intent.extras
    val separationHash =
      paramsSeparation!!.getSerializable("Separation") as HashMap<String, HashMap<String, String>>
    var botao: Button = findViewById(R.id.continuar)


//    chaveAtual =
    produtoProcura = findViewById(R.id.produto_procura)
    nomeProduto = findViewById(R.id.nome_produto)
    statusProcura = findViewById(R.id.status_procura)
    proximaLocalizacao = "BUSCARUA"

//    statusProcura.visibility = TextView.INVISIBLE
//    botao.visibility = Button.INVISIBLE

    for (x in separationHash.keys) {
      println("CHAVE: " + x)

      val chaves = separationHash[x] as HashMap<String, String>
      if (proximaLocalizacao == "BUSCARUA") {
        produtoProcura.text = "Vá até a rua ${chaves["RUA"]}\nPara chegar até o produto:\n"
        nomeProduto.text = "${chaves["PRODUTO"]}"

        proximaLocalizacao = "NUMERO"
      }
    }

    botao.setOnClickListener {
      var flag = 1
      for (x in separationHash.keys) {
        val chaves = separationHash[x] as HashMap<String, String>
        if (proximaLocalizacao == "NUMERO" && flag == 1) {
          produtoProcura.text =
            "Vá até a coluna número ${chaves["NUMERO"]}\nPara chegar até o produto:\n"
          nomeProduto.text = "${chaves["PRODUTO"]}"

          statusProcura.text = "Objetivo atualizado!\nVerifique o próximo passo"

          flag++
          proximaLocalizacao = "ANDAR"
        } else if (proximaLocalizacao == "ANDAR" && flag == 1) {
          produtoProcura.text = "Vá até o andar ${chaves["ANDAR"]}\nPara chegar até o produto:\n"
          nomeProduto.text = "${chaves["PRODUTO"]}"

          flag++
          proximaLocalizacao = "ESCANEAR"
        } else if (proximaLocalizacao == "ESCANEAR" && flag == 1) {
          produtoProcura.text =
            "Você está em frente do produto\n Escaneie ${chaves["QUANTIDADE"]} vezes"
          nomeProduto.text = "${chaves["PRODUTO"]}"

//          botao.visibility = Button.INVISIBLE

          proximaLocalizacao = "SEPARADOS"
          flag++
        } else if (proximaLocalizacao == "SEPARADOS" && flag == 1) {
          var params = Bundle()
          params.putString("Status", proximaLocalizacao)
          params.putAll(paramsSeparation)

          val proximaTela = Intent(this, InventoryScreenActivity::class.java)
          proximaTela.putExtras(params)
          startActivity(proximaTela)

          finish()
        }
      }
//      var statusProcura: TextView = findViewById(R.id.status_procura)
//      statusProcura.text = "Você está na Rua Errada!!!\nEscaneie novamente..."
//      statusProcura.setTextColor(Color.parseColor("#C72020"))

//      var params = Bundle()
//      params.putString("Status", proximaLocalizacao)
//      params.putAll(paramsSeparation)
//
//      val proximaTela = Intent(this, SearchScreenActivity::class.java)
//      proximaTela.putExtras(params)
//      startActivity(proximaTela)
//
//      finish()
    }
  }
}