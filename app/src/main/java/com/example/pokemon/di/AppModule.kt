package com.example.pokemon.di

import com.example.pokemon.business.IPokemonInteractor
import com.example.pokemon.business.PokemonInteractor
import com.example.pokemon.data.repository.PokemonRepository
import com.example.pokemon.data.service.PokemonService
import com.example.pokemon.ui.pokemon_list_screen.IPokemonListConverter
import com.example.pokemon.ui.pokemon_list_screen.PokemonListConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    @Singleton
    @Provides
    fun providePokemonRepository(service: PokemonService): PokemonRepository =
        PokemonRepository(service)

    @Singleton
    @Provides
    fun providePokemonService(): PokemonService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(PokemonService::class.java)

    @Singleton
    @Provides
    fun providePokemonInteractor(repository: PokemonRepository): IPokemonInteractor = PokemonInteractor(repository)

    @Singleton
    @Provides
    fun providePokemonConverter(): IPokemonListConverter = PokemonListConverter()

}