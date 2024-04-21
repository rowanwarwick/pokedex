package com.example.pokemon.data.repository

import com.example.pokemon.data.model.response.PokemonInfoModel
import com.example.pokemon.data.model.response.PokemonListModel
import com.example.pokemon.data.service.PokemonService
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val service: PokemonService
) {

    suspend fun getPokemonList(limit: Int, offset: Int): PokemonListModel = service.getPokemonList(limit, offset)

    suspend fun getPokemonInfo(name: String): PokemonInfoModel = service.getPokemonInfo(name)

}