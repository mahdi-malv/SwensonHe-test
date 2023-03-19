package ir.malv.swensonhe.test.ui.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import java.text.SimpleDateFormat
import java.util.*

/**
 * Displays the time and updates
 */
@Composable
fun DisplayTime(
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle()
) {
    val currentTime by rememberUpdatedState(System.currentTimeMillis())
    val dateFormat = SimpleDateFormat("hh:mm a", Locale.US)
    val timeString = dateFormat.format(currentTime)
    Text(timeString, color = Color.White, modifier = modifier, style = style)
}

/**
 * Displays the date and updates
 */
@Composable
fun DisplayDate(
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle()
) {
    val currentDate by rememberUpdatedState(System.currentTimeMillis())
    val dateFormat = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.US)
    val dateString = dateFormat.format(currentDate)
    Text(dateString, color = Color.White, modifier = modifier, style = style)
}

fun twoDayAfter(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, 2)
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    val weekdays = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
    return weekdays[dayOfWeek - 1]
}
