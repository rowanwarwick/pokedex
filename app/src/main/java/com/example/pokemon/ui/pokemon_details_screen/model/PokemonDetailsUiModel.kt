package com.example.pokemon.ui.pokemon_details_screen.model

data class PokemonDetailsUiModel(
    val name: String,
    val weight: Int,
    val height: Int,

    val baseExperience: Int,
    val id: Int,
    val order: Int,
    val isDefault: Boolean,
    val locationAreaEncounters: String,
)
