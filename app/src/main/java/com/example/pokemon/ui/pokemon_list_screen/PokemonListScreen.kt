package com.example.pokemon.ui.pokemon_list_screen

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.pokemon.R
import com.example.pokemon.ui.pokemon_list_screen.model.PokemonCardUiModel
import com.example.pokemon.ui.theme.RobotoCondensed
import com.example.pokemon.utils.POKEMON_DETAIL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {

    var searchText: String by rememberSaveable {
        mutableStateOf("")
    }
    val state by viewModel.viewModel.collectAsState()

    Box {
        state.error?.let {
            AlertDialog(
                onDismissRequest = { },
                confirmButton = {
                    Button(onClick = { viewModel.loadPokemonNextPage() }) {
                        Text("Загрузить данные")
                    }
                },
                title = { Text("Ошибка загрузки") },
                text = { Text(it.message.orEmpty()) }
            )
        }
        Column(modifier = Modifier.padding(bottom = 16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_international_pok_mon_logo),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .align(Alignment.CenterHorizontally)
            )
            SearchBar(
                query = searchText,
                onQueryChange = {
                    searchText = it
                },
                onSearch = { viewModel.searchPokemon(searchText) },
                active = false,
                onActiveChange = {},
                shadowElevation = 5.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search...") }
            ) {}
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
            ) {
                items(state.list) {
                    PokemonCard(
                        navController = navController,
                        pokemon = it,
                        calcDominantColor = viewModel::calcDominantColor
                    )
                    if (state.endReached.not() && state.isLoading.not() && it.number == state.list.size) {
                        viewModel.loadPokemonNextPage()
                    }
                }
            }
        }
    }

}

@Composable
fun PokemonCard(
    navController: NavController,
    pokemon: PokemonCardUiModel,
    calcDominantColor: (Drawable, (Color) -> Unit) -> Unit
) {
    val defaultDominantColor = MaterialTheme.colorScheme.surface
    var dominantColor: Color by remember {
        mutableStateOf(defaultDominantColor)
    }

    Card(
        onClick = {
            val color = dominantColor.toArgb()
            navController.navigate("$POKEMON_DETAIL/$color/${pokemon.pokemonName}")
        },
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
    ) {
        Column(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            dominantColor,
                            defaultDominantColor,
                        )
                    ),
                )
                .padding(5.dp),
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.imageUrl)
                    .build(),
                contentDescription = "",
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                loading = {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.scale(0.5f)
                    )
                },
                onSuccess = { success ->
                    calcDominantColor(success.result.drawable) { color ->
                        dominantColor = color
                    }
                },
                contentScale = ContentScale.Crop
            )
            Text(
                text = pokemon.pokemonName,
                fontFamily = RobotoCondensed,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }

}