package com.example.pokemon.ui.pokemon_list_screen

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.pokemon.business.IPokemonInteractor
import com.example.pokemon.ui.pokemon_list_screen.model.PokemonCardUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val interactor: IPokemonInteractor,
    private val converter: IPokemonListConverter,
) : ViewModel() {

    private val _viewModel: MutableStateFlow<PokemonListViewModelState> = MutableStateFlow(
        PokemonListViewModelState()
    )
    val viewModel: StateFlow<PokemonListViewModelState> = _viewModel.asStateFlow()

    private var cashedListPokemon: List<PokemonCardUiModel> = emptyList()

    private val exceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, error ->
            _viewModel.update { it.copy(error = error) }
        }

    init {
        loadPokemonNextPage()
    }

    fun loadPokemonNextPage() {
        viewModelScope.launch(exceptionHandler) {
            _viewModel.update { it.copy(isLoading = true, error = null) }
            val model = interactor.getPokemonList(_viewModel.value.currentPage)
            _viewModel.update { it.copy(endReached = model.next.isNullOrBlank()) }
            val list = converter.mapToUiModel(model)
            _viewModel.update {
                it.copy(
                    list = it.list + list,
                    isLoading = false,
                    currentPage = it.currentPage + 1
                )
            }
        }
    }

    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bitmap = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
        Palette.from(bitmap).generate { palette ->
            palette?.dominantSwatch?.rgb?.let {
                onFinish(Color(it))
            }
        }
    }

    fun searchPokemon(pokemonName: String) {


        if (pokemonName.isEmpty() && cashedListPokemon.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.Default) {
                _viewModel.update { it.copy(list = cashedListPokemon) }
            }
        } else {
            if (cashedListPokemon.size < viewModel.value.list.size) {
                cashedListPokemon = viewModel.value.list.toList()
            }
            val name = pokemonName.trim()
            viewModelScope.launch(Dispatchers.Default) {
                val searchList = cashedListPokemon.filter { it.pokemonName.contains(name, ignoreCase = true) }
                _viewModel.update { it.copy(list = searchList) }
            }
        }

    }

}