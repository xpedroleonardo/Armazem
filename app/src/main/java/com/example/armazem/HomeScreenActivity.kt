package com.example.armazem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeScreenActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home_screen)

    println("#### INÍCIO HOME ####")

    val paramsLogin = intent.extras
    var comecar: Button = findViewById(R.id.comecar)
    var estoque: Button = findViewById(R.id.estoque)

    var params = Bundle()
    params.putAll(paramsLogin)

    comecar.setOnClickListener {
      val proximaTela = Intent(this, SeparationScreenActivity::class.java)
      proximaTela.putExtras(params)
      startActivity(proximaTela)
    }

    estoque.setOnClickListener {
      val proximaTela = Intent(this, StorageScreenActivity::class.java)
      proximaTela.putExtras(params)
      startActivity(proximaTela)
    }


  }
}