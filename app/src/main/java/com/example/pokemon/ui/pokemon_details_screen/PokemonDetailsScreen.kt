package com.example.pokemon.ui.pokemon_details_screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokemon.R
import com.example.pokemon.data.model.response.PokemonInfoModel
import com.example.pokemon.utils.PokemonParser.parseStatToAbbr
import com.example.pokemon.utils.PokemonParser.parseStatToColor
import com.example.pokemon.utils.PokemonParser.parseTypeToColor
import java.util.Locale

@Composable
fun PokemonDetailsScreen(
    navController: NavController,
    pokemonName: String,
    dominantColor: Color,
    viewModel: PokemonDetailsViewModel = hiltViewModel()
) {

    val state by viewModel.viewModel.collectAsState()

    LaunchedEffect(key1 = pokemonName) {
        viewModel.getPokemonInfo(pokemonName)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(dominantColor),
    ) {
        PokemonToolbar(navController)
        if (state.isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else {
            PokemonResultLoaded(state = state)
        }
        state.pokemon?.let {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(it.sprites.frontDefault)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .offset(y = 20.dp)
                    .align(Alignment.TopCenter)
            )
        }
    }

}


@Composable
fun PokemonToolbar(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxHeight(0.2f)
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        Color.Transparent
                    )
                )
            ),
        contentAlignment = Alignment.TopStart,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .offset(16.dp, 16.dp)
                .size(36.dp)
                .clickable { navController.popBackStack() }
        )
    }
}

@Composable
fun PokemonResultLoaded(
    state: PokemonDetailsViewModelState,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 120.dp, bottom = 36.dp, start = 16.dp, end = 16.dp)
            .shadow(10.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.BottomCenter
    ) {
        state.error?.let {
            Text(
                text = it.message.orEmpty(),
                fontSize = 20.sp,
                color = Color.Red,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
        state.pokemon?.let {
            PokemonInfo(it)
        }
    }
}

@Composable
fun PokemonInfo(model: PokemonInfoModel) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 100.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "#${model.id} ${model.name.capitalize(Locale.ROOT)}",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        LazyRow(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(vertical = 16.dp),
        ) {
            items(model.types) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(parseTypeToColor(it))
                        .height(35.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = it.type.name.capitalize(Locale.ROOT),
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(0.8f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PokemonDataItem(
                dataValue = model.weight / 10f,
                dataName = "kg",
                dataIcon = painterResource(id = R.drawable.ic_weight),
                modifier = Modifier.weight(1f)
            )
            Spacer(
                modifier = Modifier
                    .size(1.dp, 80.dp)
                    .background(Color.LightGray)
            )
            PokemonDataItem(
                dataValue = model.height / 10f,
                dataName = "m",
                dataIcon = painterResource(id = R.drawable.ic_height),
                modifier = Modifier.weight(1f)
            )
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxHeight()
        ) {
            Text(
                text = "Base stats:",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 4.dp)
            )
            PokemonBaseStats(pokemon = model)
        }
    }
}

@Composable
fun PokemonDataItem(
    dataValue: Float,
    dataName: String,
    dataIcon: Painter,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(
            painter = dataIcon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "$dataValue $dataName",
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun PokemonBaseStats(
    pokemon: PokemonInfoModel,
) {
    val maxBaseStat = remember {
        pokemon.stats.maxOf { it.baseStat }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        pokemon.stats.forEachIndexed { index, stat ->
            PokemonStat(
                statName = parseStatToAbbr(stat),
                statValue = stat.baseStat,
                maxValue = maxBaseStat,
                statColor = parseStatToColor(stat),
                animDelay = index * 100
            )
        }
    }
}

@Composable
fun PokemonStat(
    statName: String,
    statValue: Int,
    maxValue: Int,
    statColor: Color,
    animDelay: Int,
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curPercent by animateFloatAsState(
        targetValue = if (animationPlayed) statValue / maxValue.toFloat() else 0f,
        animationSpec = tween(durationMillis = 1000, delayMillis = animDelay)
    )
    DisposableEffect(Unit) {
        animationPlayed = true
        onDispose { animationPlayed = false }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(28.dp)
            .clip(CircleShape)
            .background(Color.LightGray)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(CircleShape)
                .fillMaxHeight()
                .fillMaxWidth(curPercent)
                .background(statColor)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = statName,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = (curPercent * maxValue).toInt().toString(),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}