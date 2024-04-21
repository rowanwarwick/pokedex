package com.example.pokemon.data.service

import com.example.pokemon.data.model.response.PokemonInfoModel
import com.example.pokemon.data.model.response.PokemonListModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): PokemonListModel

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String,
    ): PokemonInfoModel

}