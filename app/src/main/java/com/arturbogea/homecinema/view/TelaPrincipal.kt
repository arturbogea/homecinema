package com.arturbogea.homecinema.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.arturbogea.homecinema.R
import com.arturbogea.homecinema.databinding.ActivityTelaPrincipalBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class TelaPrincipal : AppCompatActivity() {

    private val binding by lazy {
        ActivityTelaPrincipalBinding.inflate(layoutInflater)
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

        binding.txtSair.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, FormLogin::class.java))
            finish()
            Toast.makeText(this,"Usuário deslogado", Toast.LENGTH_SHORT).show()
        }

    }
}