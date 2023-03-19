package ir.malv.swensonhe.test.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import dagger.hilt.android.AndroidEntryPoint
import ir.malv.swensonhe.test.R
import ir.malv.swensonhe.test.repository.WeatherData
import ir.malv.swensonhe.test.ui.components.DisplayDate
import ir.malv.swensonhe.test.ui.components.DisplayTime
import ir.malv.swensonhe.test.ui.components.SearchBox
import ir.malv.swensonhe.test.ui.components.twoDayAfter
import ir.malv.swensonhe.test.ui.theme.WeatherSwensonHeTheme
import ir.malv.swensonhe.test.ui.viewmodel.MainViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherSwensonHeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainContent(
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

/**
 * Provides main Ui of the app
 * FIXME(mahdi): At the moment all Ui is just one function. Must be extracted later on
 */
@Composable
fun MainContent(
    viewModel: MainViewModel
) = Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
) {
    var showSearch by remember { mutableStateOf(false) }
    val citySuggestionState by viewModel.citySuggestion.collectAsState()
    val weatherData by viewModel.weatherData.collectAsState()

    // Background
    Image(
        modifier = Modifier.matchParentSize(),
        painter = painterResource(R.drawable.bg),
        contentDescription = "Background image",
        contentScale = ContentScale.Crop,
        colorFilter = ColorFilter.tint(
            Color(0xFF002762).copy(alpha = 0.83f),
            BlendMode.Darken
        )
    )

    // Main content
    ForegroundContent(
        weatherData = weatherData,
        onSearchClicked = { showSearch = true }
    )

    AnimatedVisibility(
        showSearch,
        modifier = Modifier.align(Alignment.TopStart),
        enter = slideInVertically(
            initialOffsetY = { -it },
            animationSpec = tween(
                durationMillis = 500,
            )
        ),
        exit = slideOutVertically(
            targetOffsetY = { -it },
            animationSpec = tween(
                durationMillis = 500,
            )
        )
    ) {
        SearchBox(
            onBackClicked = { showSearch = false },
            onCloseSuggestions = viewModel::clearSuggestions,
            onSearchContentChanged = viewModel::onTextQueryChanged,
            onCityClick = {
                showSearch = false
                println("City $it was clicked")
                viewModel.onCitySelected(it)
            },
            suggestionList = citySuggestionState
        )
    }
}

@Composable
private fun ForegroundContent(
    weatherData: WeatherData,
    onSearchClicked: () -> Unit,
    modifier: Modifier = Modifier,
) = ConstraintLayout(
    modifier = modifier.fillMaxSize()
) {
    val (time, search, weather, city, nextWeathers) = createRefs()

    // Time
    DisplayTime(
        modifier = Modifier.constrainAs(time) {
            top.linkTo(parent.top, margin = 24.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    )

    // Search
    IconButton(
        onClick = onSearchClicked,
        modifier = Modifier.constrainAs(search) {
            end.linkTo(parent.end, margin = 12.dp)
            top.linkTo(time.top)
            bottom.linkTo(time.bottom)
        }
    ) {
        Icon(
            tint = Color.White,
            painter = painterResource(R.drawable.ic_search),
            contentDescription = "Search City"
        )
    }

    // Weather
    CurrentWeather(
        temperature = weatherData.currentTemperature,
        iconUrl = weatherData.day1IconUrl,
        message = weatherData.currentText,
        wind = weatherData.currentWindSpeed,
        humidity = weatherData.currentHumidity,
        modifier = Modifier
            .constrainAs(weather) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            }
    )

    // City and date
    CityAndDate(
        modifier = Modifier
            .constrainAs(city) {
                top.linkTo(time.bottom)
                end.linkTo(parent.end)
                bottom.linkTo(weather.top)
                start.linkTo(parent.start)
            },
        city = weatherData.city
    )

    // Future days
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .constrainAs(nextWeathers) {
                top.linkTo(weather.bottom)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        NextWeather(
            iconUrl = weatherData.day1IconUrl,
            minWeather = weatherData.day1Min,
            maxWeather = weatherData.day1Max,
            day = "Today"
        )
        NextWeather(
            iconUrl = weatherData.day2IconUrl,
            minWeather = weatherData.day2Min,
            maxWeather = weatherData.day2Max,
            day = "Tomorrow"
        )
        NextWeather(
            iconUrl = weatherData.day3IconUrl,
            minWeather = weatherData.day3Min,
            maxWeather = weatherData.day3Max,
            day = twoDayAfter()
        )
    }
}

@Composable
private fun CurrentWeather(
    temperature: Float,
    iconUrl: String,
    message: String,
    wind: Float,
    humidity: Int,
    modifier: Modifier = Modifier,
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    AsyncImage(
        modifier = Modifier.size(90.dp, 90.dp),
        model = iconUrl,
        contentDescription = "Current weather", // TODO(mahdi): Use the text to make it clear
    )
    Text(
        text = buildAnnotatedString {
            pushStyle(SpanStyle(fontSize = 56.sp, fontWeight = FontWeight.Bold))
            append(temperature.toString())
            pop()
            pushStyle(SpanStyle(fontSize = 56.sp, fontWeight = FontWeight.Light))
            append("°F")
        },
        color = Color.White,
    )
    Spacer(Modifier.height(8.dp))
    Text(
        text = message,
        color = Color.White,
        style = TextStyle(fontSize = 16.sp)
    )
    Spacer(Modifier.height(8.dp))
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_wind),
            contentDescription = "Wind $wind MPH",
            tint = Color.White
        )
        Text(
            text = " $wind mph",
            color = Color.White
        )
        Spacer(Modifier.width(48.dp))

        Icon(
            painter = painterResource(R.drawable.ic_droplet),
            contentDescription = "Humidity $humidity percent",
            tint = Color.White
        )
        Text(
            text = " $humidity%",
            color = Color.White
        )
    }
}

@Composable
private fun CityAndDate(
    city: String,
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Text(
        text = city,
        color = Color.White,
        style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold)
    )
    DisplayDate(
        style = TextStyle(fontSize = 16.sp)
    )
}

/**
 * ### Ui for displaying weather info of next days
 */
@Composable
fun NextWeather(
    iconUrl: String,
    minWeather: Float,
    maxWeather: Float,
    day: String,
    modifier: Modifier = Modifier,
) = Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
) {
    AsyncImage(
        modifier = Modifier.size(32.dp, 32.dp),
        model = iconUrl,
        contentDescription = "Weather status"
    )
    Text(
        text = "$minWeather°/$maxWeather°F",
        color = Color.White,
        style = TextStyle(fontSize = 12.sp)
    )
    Text(
        text = day,
        color = Color.White,
        style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)
    )
}