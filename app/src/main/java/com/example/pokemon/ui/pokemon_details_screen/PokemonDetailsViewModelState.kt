package com.example.pokemon.ui.pokemon_details_screen

import com.example.pokemon.data.model.response.PokemonInfoModel

data class PokemonDetailsViewModelState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,
    val pokemon: PokemonInfoModel? = null,
)