package com.arturbogea.homecinema.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.arturbogea.homecinema.R
import com.arturbogea.homecinema.databinding.ActivityFormLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class FormLogin : AppCompatActivity() {

    private val binding by lazy{
        ActivityFormLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.statusBarColor = Color.parseColor("#000000")

        binding.editEmail.requestFocus()


        binding.btEntrar.setOnClickListener {

            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            when{
                email.isEmpty() ->{
                    Toast.makeText(this,"Digite o seu e-mail", Toast.LENGTH_SHORT).show()
                }
                senha.isEmpty() ->{
                    Toast.makeText(this,"Digite a sua senha", Toast.LENGTH_SHORT).show()
                }
                else -> {autenticacao(email, senha)}
            }

        }

        binding.txtCadastro.setOnClickListener {
            startActivity(Intent(this, FormCadastro::class.java))
        }

    }

    private fun autenticacao(email: String, senha: String){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener { autenticacao ->
            if (autenticacao.isSuccessful){
                Toast.makeText(this,"Login efetuado com sucesso!", Toast.LENGTH_SHORT).show()
                navegarTelaPrincipal()
            }
        }.addOnFailureListener {
            Toast.makeText(this,"Erro ao logar!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navegarTelaPrincipal(){
        startActivity(Intent(this, TelaPrincipal::class.java))
        finish()
    }

    override fun onStart() {
        super.onStart()

        val usuarioAtual = FirebaseAuth.getInstance().currentUser

        if (usuarioAtual != null){
            navegarTelaPrincipal()
        }

    }

}