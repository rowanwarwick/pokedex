package com.example.pokemon.ui.pokemon_list_screen

import com.example.pokemon.data.model.response.PokemonListModel
import com.example.pokemon.ui.pokemon_list_screen.model.PokemonCardUiModel
import javax.inject.Inject

class PokemonListConverter @Inject constructor() : IPokemonListConverter {

    override fun mapToUiModel(model: PokemonListModel): List<PokemonCardUiModel> =
        model.results.map {
            PokemonCardUiModel(
                pokemonName = it.name,
                imageUrl = it.url,
                number = it.number?.toIntOrNull() ?: 0,
            )
        }

}