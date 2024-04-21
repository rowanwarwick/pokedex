package com.example.pokemon.ui.pokemon_details_screen

import androidx.lifecycle.ViewModel
import com.example.pokemon.business.IPokemonInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val interactor: IPokemonInteractor
) : ViewModel() {

    private val _viewModel: MutableStateFlow<PokemonDetailsViewModelState> =
        MutableStateFlow(PokemonDetailsViewModelState())
    val viewModel: StateFlow<PokemonDetailsViewModelState> = _viewModel.asStateFlow()

    suspend fun getPokemonInfo(name: String) {
        runCatching { interactor.getPokemonDetails(name.lowercase()) }
            .onSuccess { model -> _viewModel.update { it.copy(pokemon = model, isLoading = false) } }
            .onFailure { error -> _viewModel.update { it.copy(isLoading = false, error = error) } }
    }

}