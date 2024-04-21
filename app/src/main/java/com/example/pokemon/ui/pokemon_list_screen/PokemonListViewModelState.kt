package com.example.pokemon.ui.pokemon_list_screen

import com.example.pokemon.ui.pokemon_list_screen.model.PokemonCardUiModel

data class PokemonListViewModelState(
    val list: List<PokemonCardUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val endReached: Boolean = false,
    val currentPage: Int = 0,
    val error: Throwable? = null,
)
