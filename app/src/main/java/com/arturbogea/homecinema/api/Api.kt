package com.arturbogea.homecinema.api

import com.arturbogea.homecinema.model.Categorias
import retrofit2.Call
import retrofit2.http.GET

interface Api {

    @GET("/filmes")

    fun listaCategorias(): Call<Categorias>

}