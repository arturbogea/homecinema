package com.arturbogea.homecinema.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arturbogea.homecinema.R
import com.arturbogea.homecinema.adapter.AdapterCategoria
import com.arturbogea.homecinema.api.Api
import com.arturbogea.homecinema.databinding.ActivityTelaPrincipalBinding
import com.arturbogea.homecinema.model.Categoria
import com.arturbogea.homecinema.model.Categorias
import com.arturbogea.homecinema.model.Filme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

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




        binding.txtSair.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, FormLogin::class.java))
            finish()
            Toast.makeText(this,"Usuário deslogado", Toast.LENGTH_SHORT).show()
        }

        //Configurar Retrofit

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://stackmobile.com.br")
            .build()
            .create(Api::class.java)

        retrofit.listaCategorias().enqueue(object : Callback<Categorias>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(p0: Call<Categorias>, response: Response<Categorias>) {
                if (response.code() == 200){
                    response.body()?.let {
                        adapterCategoria.listaCategorias.addAll(it.categorias)
                        adapterCategoria.notifyDataSetChanged()
                        binding.containerProgressBar.visibility = View.GONE
                        binding.progressbar.visibility = View.GONE
                        binding.txtCarregando.visibility = View.GONE
                    }
                }
            }

            override fun onFailure(p0: Call<Categorias>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }



}