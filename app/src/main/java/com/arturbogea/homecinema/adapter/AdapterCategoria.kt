package com.arturbogea.homecinema.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arturbogea.homecinema.databinding.CategoriaItemBinding
import com.arturbogea.homecinema.model.Categoria
import com.arturbogea.homecinema.model.Filme

class AdapterCategoria(private val context: Context, private val listaCategorias: MutableList<Categoria>):
    RecyclerView.Adapter<AdapterCategoria.CategoriaViewHolder>() {


    inner class CategoriaViewHolder(binding: CategoriaItemBinding): RecyclerView.ViewHolder(binding.root) {
        val titulo = binding.txtTitulo
        val recyclerViewFilmes = binding.rvfilmes
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val itemLista = CategoriaItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return CategoriaViewHolder(itemLista)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        holder.titulo.text = listaCategorias[position].titulo

        val categoria = listaCategorias[position]
        holder.recyclerViewFilmes.adapter = AdapterFilme(context, categoria.filmes)
        holder.recyclerViewFilmes.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun getItemCount(): Int {
        return listaCategorias.size
    }

}