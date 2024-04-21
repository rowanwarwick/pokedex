package com.example.pokemon.business

import com.example.pokemon.data.model.response.PokemonInfoModel
import com.example.pokemon.data.model.response.PokemonListModel
import com.example.pokemon.data.model.response.Result
import com.example.pokemon.data.repository.PokemonRepository
import java.util.Locale
import javax.inject.Inject

class PokemonInteractor @Inject constructor(
    private val repository: PokemonRepository
) : IPokemonInteractor {

    private companion object {
        const val PAGE_SIZE = 20
        const val SAMPLE_URL =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/%s.png"
    }

    override suspend fun getPokemonList(currentPage: Int): PokemonListModel {
        val model = repository.getPokemonList(PAGE_SIZE, currentPage * PAGE_SIZE)
        val results = model.results.map { result ->
            val number = result.url.dropLastWhile { it == '/' }.takeLastWhile { it.isDigit() }
            Result(
                name = result.name.capitalize(Locale.ROOT),
                url = SAMPLE_URL.format(number),
                number = number,
            )
        }
        return PokemonListModel(
            count = model.count,
            next = model.next,
            previous = model.previous,
            results = results
        )
    }

    override suspend fun getPokemonDetails(name: String): PokemonInfoModel =
        repository.getPokemonInfo(name)

}