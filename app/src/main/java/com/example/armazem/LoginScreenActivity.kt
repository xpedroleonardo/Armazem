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
    val usersHash = paramsSplash!!.getSerializable("Users") as HashMap<String, String>

    val botaoEntrar: Button = findViewById(R.id.botao_entrar)

    botaoEntrar.setOnClickListener {
      val username: TextView = findViewById(R.id.username_input)
      val password: TextView = findViewById(R.id.password_input)

      if (username.text.isEmpty() || password.text.isEmpty()) {
        Toast.makeText(this, "Digite Usuário e Senha", Toast.LENGTH_SHORT).show()
      } else {
        if (usersHash[username.text.toString()].toString() == password.text.toString()) {
          Toast.makeText(this, "Login realizado com sucesso!!!", Toast.LENGTH_SHORT).show()

          var params = Bundle()
          params.putAll(paramsSplash)
          val proximaTela = Intent(this, HomeScreenActivity::class.java)
          proximaTela.putExtras(params)
          startActivity(proximaTela)
//
//          finish()
        } else {
          Toast.makeText(this, "Usuário/Senha incorretos!!!", Toast.LENGTH_SHORT).show()
        }
      }
    }

  }
}