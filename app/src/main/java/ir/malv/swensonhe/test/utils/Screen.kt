package ir.malv.swensonhe.test.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

/**
 * A simple check to verify with more than 98% accuracy that the device is tablet or not
 */
@Composable
fun isExpandedScreen(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp > 840
}
