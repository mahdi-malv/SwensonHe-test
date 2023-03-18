package ir.malv.swensonhe.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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
 */
@Composable
fun MainContent() = Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
) {
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
}

@Preview(showBackground = true)
@Composable
fun MainContentPreview() {
    WeatherSwensonHeTheme {
        MainContent()
    }
}
