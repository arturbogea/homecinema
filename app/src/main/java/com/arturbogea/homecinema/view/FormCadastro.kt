package com.arturbogea.homecinema.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.arturbogea.homecinema.R
import com.arturbogea.homecinema.databinding.ActivityFormCadastroBinding
import com.google.firebase.Firebase
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FormCadastro : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormCadastroBinding.inflate(layoutInflater)
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

        window.statusBarColor = Color.parseColor("#FFFFFF")
        binding.editEmailCadastro.requestFocus()



        binding.btVamosLa.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                vamosLa()
            }
        }

        binding.btContinuar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                continuar()
            }
        }

    }


    private suspend fun vamosLa() {
        val email = binding.editEmailCadastro.text.toString()

        fun showToast(message: String) {
            runOnUiThread {
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            }
        }



        if (email.isNotEmpty()){
            withContext(Dispatchers.Main){
                binding.editSenhaCadastro.visibility = View.VISIBLE
                binding.btVamosLa.visibility = View.GONE
                binding.btContinuar.visibility = View.VISIBLE
                binding.containerHeader.visibility = View.VISIBLE
                binding.txtTitulo.setText("Um mundo de séries e filmes \n ilimitados espera por você.")
                binding.txtDescricao.setText("Crie uma conta para saber mais sobre \n o nosso App de Filmes")
            }
        }else{
            showToast("O email é obrigatório!")
        }
    }

    private suspend fun continuar() {
        val email = binding.editEmailCadastro.text.toString()
        val senha = binding.editSenhaCadastro.text.toString()

        fun showToast(message: String) {
            runOnUiThread {
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            }
        }

        withContext(Dispatchers.Main){

            if (email.isNotEmpty() && senha.isNotEmpty()) {
                cadastro(email, senha)
            } else if (senha.isEmpty()) {
                showToast("A senha é obrigatória!")
            } else if (email.isEmpty()) {
                showToast("O email é obrigatório!")
            } else {
                showToast("Erro")
            }
        }

    }

    private fun cadastro(email: String, senha: String){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener { cadastro ->
            if (cadastro.isSuccessful)
                Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()

                val email = binding.editEmailCadastro
                email.setText("")
                val senha = binding.editSenhaCadastro
                senha.setText("")
        }.addOnFailureListener {
            val erro = it

            when{
                erro is FirebaseAuthWeakPasswordException -> {Toast.makeText(this, "Digite uma senha com no minimo 6 caracteres", Toast.LENGTH_SHORT).show()}
                erro is FirebaseAuthUserCollisionException -> {Toast.makeText(this, "Essa conta já está cadastrada", Toast.LENGTH_SHORT).show()}
                erro is FirebaseNetworkException -> {Toast.makeText(this, "Sem conexão com a internet", Toast.LENGTH_SHORT).show()}
                else -> {Toast.makeText(this, "Erro ao cadastrar o usuário", Toast.LENGTH_SHORT).show()}
            }

        }
    }
}