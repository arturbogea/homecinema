package com.arturbogea.homecinema.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arturbogea.homecinema.R
import com.arturbogea.homecinema.adapter.AdapterCategoria
import com.arturbogea.homecinema.databinding.ActivityTelaPrincipalBinding
import com.arturbogea.homecinema.model.Categoria
import com.arturbogea.homecinema.model.Filme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class TelaPrincipal : AppCompatActivity() {

    private val binding by lazy {
        ActivityTelaPrincipalBinding.inflate(layoutInflater)
    }

    private lateinit var recyclerViewFilmes: RecyclerView
    private lateinit var adapterCategoria: AdapterCategoria
    private val listaCategorias: MutableList<Categoria> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurando o adapter e o recyclerview
        recyclerViewFilmes = binding.rvFilmes
        recyclerViewFilmes.layoutManager= LinearLayoutManager(this)
        recyclerViewFilmes.setHasFixedSize(true)
        adapterCategoria = AdapterCategoria(this, listaCategorias)
        recyclerViewFilmes.adapter = adapterCategoria
        getCategorias()



        binding.txtSair.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, FormLogin::class.java))
            finish()
            Toast.makeText(this,"Usuário deslogado", Toast.LENGTH_SHORT).show()
        }

    }

    private fun getCategorias(){
        val categoria1 = Categoria("Categoria 1")
        listaCategorias.add(categoria1)

        val categoria2 = Categoria("Categoria 2")
        listaCategorias.add(categoria2)

        val categoria3 = Categoria("Categoria 3")
        listaCategorias.add(categoria3)

        val categoria4 = Categoria("Categoria 4")
        listaCategorias.add(categoria4)
    }


}