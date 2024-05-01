package com.arturbogea.homecinema.model

data class Categoria(val titulo: String? = null, val filmes: MutableList<Filme> = mutableListOf())

data class Filme(val capa: Int? = null)