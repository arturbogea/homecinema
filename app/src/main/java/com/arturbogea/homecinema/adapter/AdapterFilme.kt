package com.arturbogea.homecinema.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.arturbogea.homecinema.databinding.FilmeItemBinding
import com.arturbogea.homecinema.model.Filme
import com.arturbogea.homecinema.view.DetalhesFilme
import com.bumptech.glide.Glide

class AdapterFilme(private val context: Context, private val listaFilmes: MutableList<Filme>):
    RecyclerView.Adapter<AdapterFilme.FilmeViewHolder>() {

    inner class FilmeViewHolder(binding: FilmeItemBinding): RecyclerView.ViewHolder(binding.root){
        val capa = binding.capaFilme
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmeViewHolder {
        val itemLista = FilmeItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return FilmeViewHolder(itemLista)
    }

    override fun onBindViewHolder(holder: FilmeViewHolder, position: Int) {
        Glide.with(context).load(listaFilmes[position].capa).centerCrop().into(holder.capa)

        holder.capa.setOnClickListener {
            val intent = Intent(context,DetalhesFilme::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listaFilmes.size
    }

}



