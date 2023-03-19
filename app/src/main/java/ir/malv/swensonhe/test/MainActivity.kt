package ir.malv.swensonhe.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import ir.malv.swensonhe.test.ui.theme.WeatherSwensonHeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherSwensonHeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainContent()
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
fun MainContent() = Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
) {
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
    ForegroundContent()

    // TODO(mahdi): Optional search bar as an overlay controlled by a State
}

@Composable
private fun ForegroundContent(
    modifier: Modifier = Modifier,
) = ConstraintLayout(
    modifier = modifier.fillMaxSize()
) {
    val (time, search, weather, city, nextWeathers) = createRefs()

    // Time
    Text(
        text = "09:17 AM",
        color = Color.White,
        modifier = Modifier.constrainAs(time) {
            top.linkTo(parent.top, margin = 24.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    )

    // Search
    IconButton(
        onClick = {
            // TODO(mahdi): Handle click
        },
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
        temperature = 82.4f,
        iconUrl = "https://cdn.weatherapi.com/weather/64x64/day/116.png",
        message = "Clear fucking day here",
        wind = 3,
        humidity = 67,
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
        city = "San Francisco",
        date = "Tuesday, 12 Apr 2022",
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
            iconUrl = "https://cdn.weatherapi.com/weather/64x64/day/116.png",
            minWeather = 84f,
            maxWeather = 86.4f,
            day = "Today"
        )
        NextWeather(
            iconUrl = "https://cdn.weatherapi.com/weather/64x64/day/116.png",
            minWeather = 84f,
            maxWeather = 86.4f,
            day = "Tomorrow"
        )
        NextWeather(
            iconUrl = "https://cdn.weatherapi.com/weather/64x64/day/116.png",
            minWeather = 84f,
            maxWeather = 86.4f,
            day = "Friday"
        )
    }
}

@Composable
private fun CurrentWeather(
    temperature: Float,
    iconUrl: String,
    message: String,
    wind: Int,
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
    date: String,
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
    Text(
        text = date,
        color = Color.White,
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

@Preview(showBackground = true)
@Composable
fun MainContentPreview() {
    WeatherSwensonHeTheme {
        MainContent()
    }
}
