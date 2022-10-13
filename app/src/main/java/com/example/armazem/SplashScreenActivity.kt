package com.example.armazem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreenActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash_screen)

    println("#### INÍCIO SPLASH ####")
    println("#### CARRENGANDO PRODUTOS E USUÁRIOS.... ####")

    val produtos = ProductsList()
    val produtosHash = produtos.getProduts()

    val usuarios = UsersList()
    val usuariosHash = usuarios.getUsers()

    Handler().postDelayed({
      val params = Bundle()
      params.putSerializable("Products", produtosHash)
      params.putSerializable("Users", usuariosHash)

      val novaTela = Intent(this, LoginScreenActivity::class.java)
      novaTela.putExtras(params)
      startActivity(novaTela)

      finish()
    }, 1000)
  }
}