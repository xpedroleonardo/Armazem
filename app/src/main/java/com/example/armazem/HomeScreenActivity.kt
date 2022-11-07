package com.example.armazem

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeScreenActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home_screen)

    println("#### INÍCIO HOME ####")

    val paramsLogin = intent.extras
    val comecar: Button = findViewById(R.id.comecar)
    val estoque: Button = findViewById(R.id.estoque)

    val params = Bundle()
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

  override fun onBackPressed() {
    finishAffinity()
  }
}