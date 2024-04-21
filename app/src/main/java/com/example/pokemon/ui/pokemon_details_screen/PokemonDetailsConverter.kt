package com.example.pokemon.ui.pokemon_details_screen

import com.example.pokemon.data.model.response.PokemonInfoModel
import com.example.pokemon.ui.pokemon_details_screen.model.PokemonDetailsUiModel
import javax.inject.Inject

class PokemonDetailsConverter @Inject constructor(): IPokemonDetailsConverter {

    override fun mapToUiModel(model: PokemonInfoModel): PokemonDetailsUiModel =
        PokemonDetailsUiModel(
            name = model.name,
            weight = model.weight,
            height = model.height,

            baseExperience = model.baseExperience,
            id = model.id,
            order = model.order,
            isDefault = model.isDefault,
            locationAreaEncounters = model.locationAreaEncounters,




//            val abilities: List<Ability>,
//    val forms: List<Form>,
//    val gameIndices: List<GameIndice>,
//    val heldItems: List<Any>,
//    val moves: List<Move>,
//    val pastTypes: List<Any>,
//    val species: Species,
//    val sprites: Sprites,
//    val stats: List<Stat>,
//    val types: List<Type>,
        )

}