package com.example.pokemon.ui.pokemon_list_screen

import com.example.pokemon.data.model.response.PokemonListModel
import com.example.pokemon.ui.pokemon_list_screen.model.PokemonCardUiModel

interface IPokemonListConverter {

    fun mapToUiModel(model: PokemonListModel): List<PokemonCardUiModel>

}