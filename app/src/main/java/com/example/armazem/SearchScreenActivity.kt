package com.example.armazem

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.delay
import java.lang.Thread.sleep

class SearchScreenActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_search_screen)

    println("#### INÍCIO SEARCH ####")

    val paramsSeparation = intent.extras
    var botao: Button = findViewById(R.id.continuar)

    botao.setOnClickListener {
//      var statusProcura: TextView = findViewById(R.id.status_procura)
//      statusProcura.text = "Você está na Rua Errada!!!\nEscaneie novamente..."
//      statusProcura.setTextColor(Color.parseColor("#C72020"))
//      botao.visibility = View.INVISIBLE


      var params = Bundle()
      params.putAll(paramsSeparation)

      val proximaTela = Intent(this, InventoryScreenActivity::class.java)
      proximaTela.putExtras(params)
      startActivity(proximaTela)
    }
  }
}