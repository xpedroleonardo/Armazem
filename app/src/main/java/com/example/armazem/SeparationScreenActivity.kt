package com.example.armazem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SeparationScreenActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_separation_screen)
    println("#### INÍCIO SEPARATION ####")
    val paramsHome = intent.extras

    var botao: Button = findViewById(R.id.buscar_produtos)

    botao.setOnClickListener {

      var params = Bundle()
      params.putAll(paramsHome)

      val proximaTela = Intent(this, SearchScreenActivity::class.java)
      proximaTela.putExtras(params)
      startActivity(proximaTela)
    }
  }
}