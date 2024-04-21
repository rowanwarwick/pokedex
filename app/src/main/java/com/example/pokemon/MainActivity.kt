package com.example.pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokemon.ui.pokemon_details_screen.PokemonDetailsScreen
import com.example.pokemon.ui.pokemon_list_screen.PokemonListScreen
import com.example.pokemon.ui.theme.PokemonTheme
import com.example.pokemon.utils.DOMINANT_COLOR
import com.example.pokemon.utils.POKEMON_DETAIL
import com.example.pokemon.utils.POKEMON_LIST
import com.example.pokemon.utils.POKEMON_NAME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonTheme {
                val navController = rememberNavController()
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    NavHost(navController = navController, startDestination = POKEMON_LIST) {
                        composable(POKEMON_LIST) {
                            PokemonListScreen(navController = navController)
                        }
                        composable(
                            route = "$POKEMON_DETAIL/{$DOMINANT_COLOR}/{$POKEMON_NAME}",
                            arguments = listOf(
                                navArgument(DOMINANT_COLOR) { type = NavType.IntType },
                                navArgument(POKEMON_NAME) { NavType.StringType },
                            )
                        ) {
                            val pokemonName = it.arguments?.getString(POKEMON_NAME)
                            val dominantColor = it.arguments?.getInt(DOMINANT_COLOR)?.let { color ->
                                Color(color)
                            } ?: Color.White
                            PokemonDetailsScreen(
                                navController = navController,
                                pokemonName = pokemonName.orEmpty(),
                                dominantColor = dominantColor,
                            )
                        }
                    }
                }
            }
        }
    }

}