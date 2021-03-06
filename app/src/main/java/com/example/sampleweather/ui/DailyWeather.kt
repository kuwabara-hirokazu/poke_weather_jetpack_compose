package com.example.sampleweather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.sampleweather.R
import com.example.sampleweather.extension.toDegreesString
import com.example.sampleweather.extension.toHourString
import com.example.sampleweather.extension.toPercentString
import com.example.sampleweather.model.DailyForecast
import com.example.sampleweather.model.HourlyForecast
import kotlinx.coroutines.launch

@Composable
fun DailyItem(
    modifier: Modifier = Modifier,
    dailyForecast: DailyForecast,
    pokemonData: List<String>
) {
    Card(
        modifier = modifier,
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {
        Row {
            dailyForecast.forecasts.forEach { hourlyForecast ->
                HourlyItem(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    hourlyForecast = hourlyForecast,
                    pokemonData = pokemonData.subList(
                        3 * hourlyForecast.hour,
                        3 * hourlyForecast.hour + 3
                    )
                )
            }
        }
    }
}

@Composable
fun HourlyItem(
    modifier: Modifier = Modifier,
    hourlyForecast: HourlyForecast,
    pokemonData: List<String>
) {
    Card(
        modifier = modifier,
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = hourlyForecast.hour.toHourString(),
                color = Color.White,
                fontSize = 24.sp
            )
            Image(
                painter = painterResource(id = hourlyForecast.weatherIcon),
                contentDescription = "Weather icon",
                modifier = Modifier.size(300.dp)
            )
            Row {
                Image(
                    painter = painterResource(id = R.drawable.temperature),
                    contentDescription = "temperature icon",
                    modifier = Modifier.size(28.dp)
                )
                Text(
                    text = hourlyForecast.temperature.toDegreesString(),
                    color = Color.White,
                    fontSize = 28.sp
                )
                Spacer(Modifier.width(20.dp))
                Image(
                    painter = painterResource(id = R.drawable.umbrella),
                    contentDescription = "umbrella icon",
                    modifier = Modifier.size(26.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = hourlyForecast.chanceOfRain.toPercentString(),
                    color = Color.White,
                    fontSize = 28.sp
                )
            }
            Spacer(Modifier.height(36.dp))
            Text(
                text = stringResource(id = R.string.pokemon_alert),
                color = Color.White,
                fontSize = 16.sp
            )
            Row {
                repeat(3) {
                    Image(
                        painter = rememberImagePainter(data = pokemonData[it]),
                        contentDescription = "pokemon icon",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun DailyWeather(
    forecasts: List<DailyForecast>,
    pokemonData: List<String>,
    dayCount: Int,
    onDayChange: (isPressedNextDay: Boolean) -> Unit
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(
                text = stringResource(id = R.string.left_arrow),
                modifier = Modifier.clickable {
                    onDayChange(false)
                    coroutineScope.launch {
                        listState.scrollToItem(0)
                    }
                },
                fontSize = 28.sp,
                color = Color.White
            )
            Text(
                text = forecasts[dayCount].date,
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 40.dp)
            )
            Text(
                text = stringResource(id = R.string.right_arrow),
                modifier = Modifier.clickable {
                    onDayChange(true)
                    coroutineScope.launch {
                        listState.scrollToItem(0)
                    }
                },
                fontSize = 28.sp,
                color = Color.White
            )
        }
        LazyRow(state = listState, contentPadding = PaddingValues(20.dp)) {
            items(1) {
                DailyItem(
                    dailyForecast = forecasts[dayCount],
                    pokemonData = pokemonData.subList(72 * dayCount, 72 * dayCount + 72)
                )
            }
        }
    }
}
