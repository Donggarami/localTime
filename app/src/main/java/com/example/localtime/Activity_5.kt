package com.example.localtime

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localtime.ui.theme.LocalTimeTheme

class Activity_5 : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocalTimeTheme {
                val genres = listOf("근태관리", "출석체크", "시설예약", "골드스푼달력")
                Scaffold {
                    Column(
                        modifier = Modifier
                            .background(color = Color.White)
                            .padding(horizontal = 20.dp)
                            .padding(it)
                    ) {
                        Text(
                            fontSize = 25.sp,
                            modifier = Modifier.padding(top = 30.dp, bottom = 5.dp),
                            //데이터명 입력
                            text = "김동수님의 월별"
                        )

                        Text(
                            fontSize = 25.sp,
                            text = "근무 태도 관리",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black

                        )
                        Spacer(modifier = Modifier.size(25.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.padding(bottom = 25.dp)
                        ) {
                            items(genres) { genre ->
                                Genre(genre)
                            }
                        }

                        Box(
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .clip(RoundedCornerShape(10))
                                .background(color = colorResource(id = R.color.purple_2))
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Column(
                                    modifier = Modifier
                                        .padding(vertical = 30.dp)
                                        .padding(start = 20.dp)
                                ) {
                                    Text(
                                        text = "공지사항",
                                        color = Color.White,
                                        fontSize = 32.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Spacer(modifier = Modifier.size(10.dp))

                                    Text(
                                        text = "8월에 공지사항",
                                        color = Color.White,
                                        fontSize = 20.sp
                                    )

                                    Spacer(modifier = Modifier.size(10.dp))

                                    Button(
                                        modifier = Modifier
                                            .size(height = 60.dp, width = 120.dp)
                                            .clip(RoundedCornerShape(20)),
                                        onClick = {},
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.White
                                        )
                                    ) {
                                        Text(text = "Check", color = Color.Black, fontSize = 18.sp)
                                        Spacer(modifier = Modifier.size(10.dp))
                                        Box(
                                            modifier = Modifier
                                                .size(30.dp)
                                                .clip(CircleShape)
                                                .background(Color.Black),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                modifier = Modifier
                                                    .padding(5.dp)
                                                    .size(5.dp),
                                                imageVector = Icons.Filled.Check,
                                                contentDescription = "",
                                                tint = Color.White
                                            )
                                        }

                                    }

                                }

                                Image(
                                    painterResource(id = R.drawable.logo),
                                    contentDescription = "",
                                    modifier = Modifier.weight(1f, fill = false)
                                )
                            }
                        }
                        Text(
                            text = "Recommended",
                            color = Color.Black,
                            fontSize = 23.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 30.dp)
                        )

                        LazyColumn(
                            modifier = Modifier.padding(end = 20.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(1) { genre ->
                                Recommended()
                            }
                        }

                    }
                }
            }
        }
    }
}

//이미지 제작 예시
@Composable
fun Genre(text: String) {
    var isSelected by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(30))
            .border(1.dp, (if (isSelected) Color.White else colorResource(id = R.color.purple_3)))
            .background(if (isSelected) colorResource(id = R.color.purple_1) else colorResource(id = R.color.purple_3))
            .clickable {
                isSelected = !isSelected
            },

        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(10.dp),
            color = if (isSelected) Color.White else Color.Black,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun Recommended() {
    var isSelected by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30))
            .border(1.dp, colorResource(id = R.color.pink_02))
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20))
                    .background(colorResource(id = R.color.purple_4)),
                contentAlignment = Alignment.Center
            )
            {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "icon",
                    tint = Color.White,
                    modifier = Modifier
                        .size(32.dp)
                        .padding(10.dp)
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = "월별 근무 현황",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$8월 근무 기록 $5 Day",
                    color = Color.DarkGray,
                    fontSize = 15.sp,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "icon",
                    tint = if (isSelected) colorResource(id = R.color.purple_4) else colorResource(
                        id = R.color.purple_2
                    ),
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30))
            .border(1.dp, colorResource(id = R.color.pink_02))
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20))
                    .background(colorResource(id = R.color.purple_4)),
                contentAlignment = Alignment.Center
            )
            {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "icon",
                    tint = Color.White,
                    modifier = Modifier
                        .size(32.dp)
                        .padding(10.dp)
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = "일별 근무 현황",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "현재 근무 시간 :$6h",
                    color = Color.DarkGray,
                    fontSize = 15.sp,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "icon",
                    tint = if (isSelected) colorResource(id = R.color.purple_4) else colorResource(
                        id = R.color.purple_2
                    ),
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }

}