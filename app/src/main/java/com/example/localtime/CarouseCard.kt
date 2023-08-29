package com.example.localtime

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import kotlinx.coroutines.delay


@Composable
fun CarouseCard() {
    val images = listOf(
        R.drawable.source_01,
        R.drawable.source_02,
        R.drawable.source_03
    )
    ImageSliderWithIndication(images = images)
}

@Composable
fun ImageSliderItem(imageRes: Int) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .height(200.dp)
            .width(200.dp)
    )
}

@Composable
fun Indicator(active: Boolean) {
    val color = if (active) Color.Red else Color.Black
    val size = if (active) 10.dp else 10.dp
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(color)
            .size(size)
    )
}

@Composable
fun ImageSliderWithIndication(images: List<Int>) {
    val currentIndex = remember {
        mutableStateOf(0)
    }
    val pageCount =  images.size
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            currentIndex.value = (currentIndex.value + 1) % images.size
        }
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .padding(top = 10.dp)
                .weight(1f)
        ) {
            ImageSliderItem(imageRes = images[currentIndex.value])
        }
        Row(
            modifier = Modifier
                .padding(start = 170.dp, bottom = 50.dp)
        ) {
           images.forEachIndexed { index, i -> 
               Indicator(active = index == currentIndex.value)
               if (index <=  images.size -1){
                   Spacer(modifier = Modifier.width((5.dp)))
               }
           }
        }

    }


}