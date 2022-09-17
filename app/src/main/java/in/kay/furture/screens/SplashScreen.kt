package `in`.kay.furture.screens

import `in`.kay.furture.ui.theme.colorBlack
import `in`.kay.furture.ui.theme.colorPurple
import `in`.kay.furture.ui.theme.colorWhite
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun SplashScreen(navController: NavHostController) {
    val context = LocalContext.current
    val slow = 700
    val fast = 300
    var isAnimStart by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = Unit, block = {
        delay(500L)
        isAnimStart = true
        delay(1200L)
        navController.navigate("home")
    })

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(colorPurple),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.ShoppingCart,
            contentDescription = "null",
            tint = colorWhite,
            modifier = Modifier.size(32.dp)
        )
        AnimatedVisibility(
            visible = isAnimStart,
            enter = fadeIn(
                animationSpec = tween(durationMillis = fast)
            ) + expandHorizontally(
                expandFrom = Alignment.End,
                animationSpec = tween(
                    durationMillis = slow,
                    easing = FastOutLinearInEasing,
                )
            ),
            exit = fadeOut(
                animationSpec = tween(
                    durationMillis = slow,
                    easing = FastOutLinearInEasing,
                )
            ) + shrinkHorizontally(
                shrinkTowards = Alignment.End,
                animationSpec = tween(
                    durationMillis = slow,
                    easing = FastOutLinearInEasing,
                )
            )
        ) {
            Row(modifier = Modifier.padding(start = 4.dp)) {
                Text(
                    text = "Fur",
                    color = colorWhite,
                    modifier = Modifier.padding(start = 0.dp),
                    style = `in`.kay.furture.ui.theme.Typography.h1
                )
                Text(
                    text = "ture",
                    color = colorWhite,
                    style = `in`.kay.furture.ui.theme.Typography.h1
                )
            }
        }
    }
}

