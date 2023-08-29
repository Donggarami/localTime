package com.example.localtime

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.localtime.ui.theme.LocalTimeTheme


class Activity_3 : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocalTimeTheme {
                val colors = listOf(Color(0xFFffe53b), Color(0xFFff2525))
                val context = LocalContext.current
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie))

                Column(
                    modifier = Modifier
                        .background(color = Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally


                ) {
                    Text(
                        fontSize = 25.sp,
                        modifier = Modifier.padding(top = 30.dp, bottom = 5.dp),
                        //데이터명 입력
                        text = "'김동수'님 헬스트레이너"
                    )

                    Text(
                        fontSize = 25.sp,
                        text = "GYM MAN 종합 근무",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black

                    )
                    Spacer(modifier = Modifier.size(25.dp))
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
                                    text = "개인정보",
                                    color = Color.White,
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.size(10.dp))

                                Text(
                                    text = "GYMMAN 수정",
                                    color = Color.White,
                                    fontSize = 20.sp
                                )

                                Spacer(modifier = Modifier.size(10.dp))

                                Button(
                                    modifier = Modifier
                                        .size(height = 60.dp, width = 120.dp)
                                        .clip(RoundedCornerShape(20)),
                                    onClick = {
                                        val intent = Intent(context, EmployeeInfoActivity::class.java)
                                        context.startActivity(intent) },
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
//                            LottieAnimation(
//                                composition = composition,
//                                modifier = Modifier
//                                    .weight(0.2f, fill = false)
//                            )
                            Image(
                                painterResource(id = R.drawable.logo),
                                contentDescription = "",
                                modifier = Modifier.weight(1f, fill = false)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(30.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,

                        ) {
                        Button(
                            onClick = {
                                val intent = Intent(context, calendar::class.java)
                                context.startActivity(intent)
                            },
                            modifier = Modifier
                                .width(150.dp)
                                .height(150.dp)
                                .padding(start = 5.dp, end = 10.dp, top = 8.dp, bottom = 8.dp)
                                .border(
                                    1.dp,
                                    Color(android.graphics.Color.parseColor("#ba55d3")),
                                    shape = RoundedCornerShape(16)
                                ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(
                                    android.graphics.Color.parseColor("#ba55d3")
                                )
                            ),
                            shape = RoundedCornerShape(10)
                        )
                        {

                            Text(
                                text = "GymMan Calendar",
                                textAlign = TextAlign.Center,
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Button(
                            onClick = {
                                val intent = Intent(context, Activity_6::class.java)
                                context.startActivity(intent)
                            },
                            modifier = Modifier
                                .width(150.dp)
                                .height(150.dp)
                                .padding(start = 10.dp, end = 5.dp, top = 8.dp, bottom = 8.dp)
                                .border(
                                    1.dp,
                                    Color(android.graphics.Color.parseColor("#ba55d3")),
                                    shape = RoundedCornerShape(16)
                                ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(
                                    android.graphics.Color.parseColor("#ffffff")
                                )
                            ),
                            shape = RoundedCornerShape(10)
                        )
                        {

                            Text(
                                text = "시설예약",
                                textAlign = TextAlign.Center,
                                color = Color(android.graphics.Color.parseColor("#ba55d3")),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Row() {
                        Button(
                            onClick = {
                                val intent = Intent(context, Activity_5::class.java)
                                context.startActivity(intent)
                            },
                            modifier = Modifier
                                .width(150.dp)
                                .height(150.dp)
                                .padding(start = 5.dp, end = 10.dp, top = 8.dp, bottom = 8.dp)
                                .border(
                                    1.dp, Color(android.graphics.Color.parseColor("#ba55d3")),
                                    shape = RoundedCornerShape(16)
                                ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(
                                    android.graphics.Color.parseColor("#ffffff")
                                )
                            ),
                            shape = RoundedCornerShape(10)
                        )
                        {
                            Text(
                                text = "근태관리",
                                textAlign = TextAlign.Center,
                                color = Color(android.graphics.Color.parseColor("#ba55d3")),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Button(
                            onClick = {
                                val intent = Intent(context, Activity_4::class.java)
                                context.startActivity(intent)
                            },
                            modifier = Modifier
                                .width(150.dp)
                                .height(150.dp)
                                .padding(start = 10.dp, end = 5.dp, top = 8.dp, bottom = 8.dp)
                                .border(
                                    1.dp, Color(android.graphics.Color.parseColor("#ba55d3")),
                                    shape = RoundedCornerShape(16)
                                ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(
                                    android.graphics.Color.parseColor("#ba55d3")
                                )
                            ),
                            shape = RoundedCornerShape(10)
                        )
                        {

                            Text(
                                text = "출근,퇴근 체크",
                                textAlign = TextAlign.Center,
                                color = Color(android.graphics.Color.parseColor("#ffffff")),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(20.dp))
                    Image(
                        painter = painterResource(id = R.drawable.bottom_background),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds
                    )
                }

            }
        }
    }
}


