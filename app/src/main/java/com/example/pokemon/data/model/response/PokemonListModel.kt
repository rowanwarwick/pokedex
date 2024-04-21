package com.example.pokemon.data.model.response

data class PokemonListModel(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Result>
)

data class Result(
    val name: String,
    val url: String,
    val number: String? = null,
)