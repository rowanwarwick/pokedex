package com.example.pokemon.ui.pokemon_details_screen

import com.example.pokemon.data.model.response.PokemonInfoModel
import com.example.pokemon.ui.pokemon_details_screen.model.PokemonDetailsUiModel

interface IPokemonDetailsConverter {

    fun mapToUiModel(model: PokemonInfoModel): PokemonDetailsUiModel

}