package ir.malv.swensonhe.test.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ir.malv.swensonhe.test.R

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

/**
 * ### Used to represent the current weather
 */
@Composable
fun CurrentWeather(
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
        contentDescription = message,
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
