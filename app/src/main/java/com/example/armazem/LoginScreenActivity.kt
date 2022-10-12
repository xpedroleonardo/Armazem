package com.example.armazem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class LoginScreenActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login_screen)
    println("#### INÍCIO LOGIN ####")

    val paramsSplash = intent.extras
    val usersHash = paramsSplash!!.getSerializable("Users") as HashMap<*, *>

    val botaoEntrar: Button = findViewById(R.id.botao_entrar)

    botaoEntrar.setOnClickListener {
      val username: TextView = findViewById(R.id.username_input)
      val password: TextView = findViewById(R.id.password_input)

      if (username.text.isEmpty() || password.text.isEmpty()) {
        Toast.makeText(this, "Digite Usuário e Senha", Toast.LENGTH_SHORT).show()
      } else {

        val usernameValid = username.text.toString().replace("\\s".toRegex(), "")
        val passwordValid = password.text.toString().replace("\\s".toRegex(), "")

        if (usersHash[usernameValid].toString() == passwordValid) {
          val params = Bundle()
          params.putAll(paramsSplash)
          val proximaTela = Intent(this, HomeScreenActivity::class.java)
          proximaTela.putExtras(params)
          startActivity(proximaTela)

          finish()
        } else {
          Toast.makeText(this, "Usuário/Senha incorretos!!!", Toast.LENGTH_SHORT).show()
        }
      }
    }

  }
}