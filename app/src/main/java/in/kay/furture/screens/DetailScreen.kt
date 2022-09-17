package `in`.kay.furture.screens

import `in`.kay.furture.SharedViewModel
import `in`.kay.furture.ui.theme.Typography
import `in`.kay.furture.ui.theme.colorBlack
import `in`.kay.furture.ui.theme.colorPurple
import `in`.kay.furture.ui.theme.colorWhite
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import androidx.palette.graphics.Palette
import okhttp3.internal.toHexString

@Composable
fun DetailScreen(viewModel: SharedViewModel) {
    val furnitureModel = viewModel.data
    val context = LocalContext.current
    var btnColor by remember {
        mutableStateOf(colorPurple)
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = Color(0XFFF3F6F8)),
        constraintSet = constraintsDetail()
    ) {
        Text(
            text = furnitureModel.type.toString(),
            style = Typography.body1,
            fontSize = 18.sp,
            color = Color(0XFF171717).copy(alpha = 0.4f),
            modifier = Modifier.layoutId("tvType")
        )
        Text(
            text = furnitureModel.name.toString().lowercase(),
            style = Typography.h1,
            fontSize = 32.sp,
            color = colorBlack,
            modifier = Modifier.layoutId("tvName")
        )
        Text(
            text = "from",
            style = Typography.body1,
            fontSize = 18.sp,
            color = Color(0XFF171717).copy(alpha = 0.4f),
            modifier = Modifier.layoutId("tvFrom")
        )
        Text(
            text = "$ ${furnitureModel.price.toString()}",
            style = Typography.h1,
            fontSize = 24.sp,
            color = colorBlack,
            modifier = Modifier.layoutId("tvPrice")
        )
        Image(
            painter = painterResource(id = furnitureModel.drawable),
            contentDescription = "",
            modifier = Modifier
                .height(240.dp)
                .zIndex(1.1f)
                .layoutId("ivImg")
        )
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                .background(
                    colorWhite
                )
                .layoutId("clDetail")
                .padding(24.dp, 160.dp, 24.dp, 24.dp)
        ) {
            Text(
                text = furnitureModel.name.toString(),
                style = Typography.h1,
                fontSize = 18.sp,
                color = Color(0XFF171717).copy(alpha = 1f)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = furnitureModel.description.toString().lowercase(),
                style = Typography.body2,
                fontSize = 16.sp,
                color = Color(0XFF171717).copy(alpha = 0.2f),
            )
            Spacer(modifier = Modifier.height(64.dp))
            val bitmap = BitmapFactory.decodeResource(context.resources, furnitureModel.drawable)
            Palette.from(bitmap).generate { palette ->
                kotlin.runCatching {
                    val hexColor = palette?.vibrantSwatch?.rgb?.toHexString()
                    hexColor?.let {
                        btnColor = getColor(hexColor)
                    }
                }.getOrElse {
                    val hexColor = palette?.darkMutedSwatch?.rgb?.toHexString()
                    hexColor?.let {
                        btnColor = getColor(hexColor)
                    }
                }

            }
            Button(
                onClick = {
                    val sceneViewerIntent = Intent(Intent.ACTION_VIEW)
                    val intentUri =
                        Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                            .appendQueryParameter("file", "" + furnitureModel.link)
                            .appendQueryParameter("mode", "ar_only")
                            .appendQueryParameter("title", furnitureModel.name)
                            .build()
                    sceneViewerIntent.data = intentUri
                    sceneViewerIntent.setPackage("com.google.ar.core")
                    sceneViewerIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(sceneViewerIntent)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = btnColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "view into your house.",
                    style = Typography.body1,
                    fontSize = 18.sp,
                    color = Color(0XFFFFFFFF).copy(alpha = 1f)
                )
            }
        }
    }
}

fun constraintsDetail(): ConstraintSet {
    return ConstraintSet {
        val tvType = createRefFor("tvType")
        val tvName = createRefFor("tvName")
        val tvFrom = createRefFor("tvFrom")
        val tvPrice = createRefFor("tvPrice")
        val ivImg = createRefFor("ivImg")
        val clDetail = createRefFor("clDetail")

        constrain(tvType) {
            top.linkTo(parent.top, 64.dp)
            start.linkTo(parent.start, 24.dp)
        }
        constrain(tvName) {
            top.linkTo(tvType.bottom, 4.dp)
            start.linkTo(parent.start, 24.dp)
        }
        constrain(tvFrom) {
            top.linkTo(tvName.bottom, 24.dp)
            start.linkTo(parent.start, 24.dp)
        }
        constrain(tvPrice) {
            top.linkTo(tvFrom.bottom, 4.dp)
            start.linkTo(parent.start, 24.dp)
        }
        constrain(ivImg) {
            top.linkTo(clDetail.top, 32.dp)
            bottom.linkTo(clDetail.top, 32.dp)
            start.linkTo(parent.start, 24.dp)
            end.linkTo(parent.end, 24.dp)
            width = Dimension.fillToConstraints
        }
        constrain(clDetail) {
            top.linkTo(tvPrice.bottom, 80.dp)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }


    }
}

fun getColor(colorString: String): Color {
    return Color(android.graphics.Color.parseColor("#" + colorString))
}