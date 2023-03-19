package ir.malv.swensonhe.test.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.malv.swensonhe.test.R
import ir.malv.swensonhe.test.repository.LocationData

/**
 * TODO(mahdi): Provide info for params specially [suggestionList]
 */
@Composable
fun SearchBox(
    onBackClicked: () -> Unit,
    onCloseSuggestions: () -> Unit,
    onCityClick: (LocationData) -> Unit,
    onSearchContentChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    suggestionList: List<LocationData> = emptyList(),
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
        elevation = 5.dp,
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var value by remember { mutableStateOf("") }

                IconButton(
                    onClick = onBackClicked,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = "Close search box",
                        tint = Color(0xFF444E72) // TODO(mahdi): provide a color class
                    )
                }
                Spacer(Modifier.width(12.dp))
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = value,
                    onValueChange = {
                        onSearchContentChanged(it)
                        value = it
                    },
                    shape = RoundedCornerShape(15.dp),
                    singleLine = true,
                    label = { Text("Search city") },
                    trailingIcon = {
                        if (value.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    value = ""
                                    onSearchContentChanged("")
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_close),
                                    tint = Color(0xFF444E72),
                                    contentDescription = "Clear search"
                                )
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    )
                )
            }

            if (suggestionList.isNotEmpty()) {
                suggestionList.forEach {
                    Text(
                        modifier = Modifier
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = { onCityClick(it) }
                            )
                            .padding(horizontal = 24.dp),
                        text = buildAnnotatedString {
                            withStyle(
                                SpanStyle(fontWeight = FontWeight.Bold)
                            ) {
                                append(it.city)
                            }
                            append("- ${it.country}")
                        },
                        style = TextStyle(fontSize = 20.sp)
                    )
                    Spacer(Modifier.height(16.dp))
                }
                Box(
                    modifier = Modifier
                        .background(Color(0xFFF1F4FF))
                        .fillMaxWidth()
                        .height(32.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = onCloseSuggestions
                        )
                ) {
                    Icon(
                        modifier = Modifier
                            .size(12.dp)
                            .align(Alignment.Center),
                        painter = painterResource(R.drawable.ic_up),
                        contentDescription = "Close suggestions",
                        tint = Color(0xFF444E72)
                    )
                }
            }
        }
    }
}
