package ir.malv.swensonhe.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
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
    Box(
        modifier = Modifier
            .size(170.dp, 240.dp)
            .background(Color.Red)
            .constrainAs(weather) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            }
    )

    // City and date
    Box(
        modifier = Modifier
            .size(200.dp, 60.dp)
            .background(Color.Green)
            .constrainAs(city) {
                top.linkTo(time.bottom)
                end.linkTo(parent.end)
                bottom.linkTo(weather.top)
                start.linkTo(parent.start)
            }
    )

    // Future days
    Box(
        modifier = Modifier
            .size(290.dp, 60.dp)
            .background(Color.Blue)
            .constrainAs(nextWeathers) {
                top.linkTo(weather.bottom)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            }
    )
}


@Preview(showBackground = true)
@Composable
fun MainContentPreview() {
    WeatherSwensonHeTheme {
        MainContent()
    }
}
