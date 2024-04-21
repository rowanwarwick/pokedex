package com.example.pokemon.business

import com.example.pokemon.data.model.response.PokemonInfoModel
import com.example.pokemon.data.model.response.PokemonListModel

interface IPokemonInteractor {

    suspend fun getPokemonList(currentPage: Int): PokemonListModel
    suspend fun getPokemonDetails(name: String): PokemonInfoModel

}