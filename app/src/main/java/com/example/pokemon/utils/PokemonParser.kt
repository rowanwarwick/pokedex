package com.example.pokemon.utils

import androidx.compose.ui.graphics.Color
import com.example.pokemon.data.model.response.Stat
import com.example.pokemon.data.model.response.Type
import com.example.pokemon.ui.theme.AtkColor
import com.example.pokemon.ui.theme.DefColor
import com.example.pokemon.ui.theme.HPColor
import com.example.pokemon.ui.theme.SpAtkColor
import com.example.pokemon.ui.theme.SpDefColor
import com.example.pokemon.ui.theme.SpdColor
import com.example.pokemon.ui.theme.TypeBug
import com.example.pokemon.ui.theme.TypeDark
import com.example.pokemon.ui.theme.TypeDragon
import com.example.pokemon.ui.theme.TypeElectric
import com.example.pokemon.ui.theme.TypeFairy
import com.example.pokemon.ui.theme.TypeFighting
import com.example.pokemon.ui.theme.TypeFire
import com.example.pokemon.ui.theme.TypeFlying
import com.example.pokemon.ui.theme.TypeGhost
import com.example.pokemon.ui.theme.TypeGrass
import com.example.pokemon.ui.theme.TypeGround
import com.example.pokemon.ui.theme.TypeIce
import com.example.pokemon.ui.theme.TypeNormal
import com.example.pokemon.ui.theme.TypePoison
import com.example.pokemon.ui.theme.TypePsychic
import com.example.pokemon.ui.theme.TypeRock
import com.example.pokemon.ui.theme.TypeSteel
import com.example.pokemon.ui.theme.TypeWater

object PokemonParser {

    fun parseTypeToColor(type: Type): Color {
        return when (type.type.name.lowercase()) {
            "normal" -> TypeNormal
            "fire" -> TypeFire
            "water" -> TypeWater
            "electric" -> TypeElectric
            "grass" -> TypeGrass
            "ice" -> TypeIce
            "fighting" -> TypeFighting
            "poison" -> TypePoison
            "ground" -> TypeGround
            "flying" -> TypeFlying
            "psychic" -> TypePsychic
            "bug" -> TypeBug
            "rock" -> TypeRock
            "ghost" -> TypeGhost
            "dragon" -> TypeDragon
            "dark" -> TypeDark
            "steel" -> TypeSteel
            "fairy" -> TypeFairy
            else -> Color.Black
        }
    }

    fun parseStatToColor(stat: Stat): Color {
        return when (stat.stat.name.lowercase()) {
            "hp" -> HPColor
            "attack" -> AtkColor
            "defense" -> DefColor
            "special-attack" -> SpAtkColor
            "special-defense" -> SpDefColor
            "speed" -> SpdColor
            else -> Color.White
        }
    }

    fun parseStatToAbbr(stat: Stat): String {
        return when (stat.stat.name.lowercase()) {
            "hp" -> "HP"
            "attack" -> "Atk"
            "defense" -> "Def"
            "special-attack" -> "SpAtk"
            "special-defense" -> "SpDef"
            "speed" -> "Spd"
            else -> ""
        }
    }

}