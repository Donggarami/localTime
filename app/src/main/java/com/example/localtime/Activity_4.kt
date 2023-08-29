package com.example.localtime

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.localtime.ui.theme.LocalTimeTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

class Activity_4 : ComponentActivity() {
    //
    val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getLastLocation()
            } else {
                //  finish()
            }
        }
    private fun requestLocationPermission() {
        val permission = android.Manifest.permission.ACCESS_FINE_LOCATION

        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {

            getLastLocation()
        } else {

            requestPermissionLauncher.launch(permission)
        }
    }
    private var arrivalTime by mutableStateOf("")
    private var departureTime by mutableStateOf("")



    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        setContent {
            val user = auth.currentUser
            val uid = user?.uid ?: ""

            LocalTimeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    var isSelected by remember { mutableStateOf(false) }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .background(color = Color(android.graphics.Color.parseColor("#ffffff"))),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    navigationIcon = {
                                        IconButton(onClick = {val intent = Intent(context, Activity_3::class.java)
                                            context.startActivity(intent) }) {
                                            Icon(
                                                imageVector = Icons.Default.ArrowBack,
                                                tint = Color.White,
                                                contentDescription = "Go Back"
                                            )
                                        }
                                    },
                                    title = {
                                        Text(
                                            text = "출근, 퇴근 체크",
                                            fontStyle = FontStyle.Italic,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    },
                                    actions = {
                                        IconButton(onClick = { /*TODO*/ }) {
                                            Icon(
                                                imageVector = Icons.Default.Menu,
                                                tint = Color.White,
                                                contentDescription = "Go Menu",
                                            )
                                        }
                                    },
                                    colors = TopAppBarDefaults.smallTopAppBarColors(
                                        containerColor = Color(
                                            android.graphics.Color.parseColor("#ba55d3")
                                        )
                                    ),
                                )
                            },
                            content = {
                                Column {
                                    Spacer(modifier = Modifier.height(30.dp))

                                    Text(
                                        modifier = Modifier
                                            .padding(top = 50.dp),
                                        text = "출근 또는 퇴근 버튼을 탭하여, 출퇴근을 체크하세요",
                                        fontSize = 15.sp,
                                        fontStyle = FontStyle.Italic,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black,
                                    )
                                    Row(
                                        modifier = Modifier
                                            .padding(top = 120.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Button(
                                            onClick = {
                                                requestLocationPermission()
                                                val currentTime = System.currentTimeMillis()

                                                val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTime)
                                                val arrivalData = hashMapOf("arrivalTime" to FieldValue.arrayUnion(time))
                                                db.collection("commute").document(uid)
                                                    .set(arrivalData, SetOptions.merge())
                                                    .addOnSuccessListener {
                                                        arrivalTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTime)
                                                    }
//  .addOnFailureListener {
//  Log.d("test", it.toString())
//  }
                                            },
                                            modifier = Modifier
                                                .width(200.dp)
                                                .height(200.dp)
                                                .padding(10.dp)
                                                .border(
                                                    1.dp,
                                                    Color(android.graphics.Color.parseColor("#ba55d3")),
                                                    shape = RoundedCornerShape(10)
                                                ),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = if (isSelected) Color.Black else colorResource(
                                                    id = R.color.purple_5
                                                )
                                            ),
                                            shape = RoundedCornerShape(10)
                                        )
                                        {
                                            Text(
                                                text = "출근",
                                                textAlign = TextAlign.Center,
                                                color = if (isSelected) colorResource(id = R.color.purple_3) else Color.White,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                        Button(
                                            onClick = {
//
                                                val currentTime = System.currentTimeMillis()
                                                val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTime)
                                                val departureData = hashMapOf("departureTime" to FieldValue.arrayUnion(time))
                                                db.collection("commute").document(uid)
                                                    .set(departureData, SetOptions.merge())
                                                    .addOnSuccessListener {
                                                        departureTime =
                                                            SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                                                                currentTime
                                                            )
                                                    }
                                            },
                                            modifier = Modifier
                                                .width(200.dp)
                                                .height(200.dp)
                                                .padding(10.dp)
                                                .border(
                                                    1.dp,
                                                    Color(android.graphics.Color.parseColor("#ba55d3")),
                                                    shape = RoundedCornerShape(10)
                                                ),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = if (isSelected) Color.Black else colorResource(
                                                    id = R.color.purple_5
                                                )
                                            ),
                                            shape = RoundedCornerShape(10)
                                        )
                                        {
                                            Text(
                                                text = "퇴근",
                                                textAlign = TextAlign.Center,
                                                color = if (isSelected) colorResource(id = R.color.purple_3) else Color.White,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
//                                    val docRef = db.collection("commute").document("uid")
//                                    docRef.get()
//                                        .addOnSuccessListener { document ->
//                                            if (document != null) {
//                                                val arrivalTime = document["arrivalTime"] as? String
//                                                if (arrivalTime != null) {
//                                                    Log.d(TAG, "출근시간 : $arrivalTime")
//                                                }
//                                            } else {
//                                                Log.d(TAG, "출근하지 않음")
//                                            }
//                                        }
//                                        .addOnFailureListener { exception ->
//                                            Log.d(TAG, "get failed with ", exception)
//                                        }
//                                    val arrivalTimeList = mutableListOf<String>()
//                                    val lastArrivalTime = arrivalTimeList.last()
//                                    Spacer(modifier = Modifier.height(70.dp))
//                                    Text(
//
//                                        text = lastArrivalTime ,
//                                        textAlign = TextAlign.Center,
//                                        color = androidx.compose.ui.graphics.Color.Black,
//                                        fontSize = 18.sp,
//                                        fontWeight = FontWeight.Bold
//                                    )
                                    Spacer(modifier = Modifier.height(70.dp))
                                    Text(
                                        text = "출근 시간 : $",
                                        textAlign = TextAlign.Center,
                                        color = androidx.compose.ui.graphics.Color.Black,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(20.dp))


                                    Text(
                                        text = "퇴근 시간 : $",
                                        textAlign = TextAlign.Center,
                                        color = androidx.compose.ui.graphics.Color.Black,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Image(
                                        modifier = Modifier
                                            .padding(top = 50.dp),
                                        painter = painterResource(id = R.drawable.bottom_background),
                                        contentDescription = "",
                                        contentScale = ContentScale.FillBounds
                                    )

                                }

                            }
                        )
                    }
                }
            }
        }
    }
    @SuppressLint("MissingPermission")
    fun calculateDistance(currentLocation: Location, targetLocation: Location): Float {
        return currentLocation.distanceTo(targetLocation)
    }

    @SuppressLint("MissingPermission")
    fun recordTimeToFirestore(isArrival: Boolean, currentTimeMillis: Long) {
        val user = auth.currentUser
        val uid = user?.uid ?: ""

        val timeType = if (isArrival) "arrivalTime" else "departureTime"

        firestore.collection("commute").document(uid)
            .update(timeType, currentTimeMillis)
            .addOnSuccessListener {
                if (isArrival) {
                    // 출근 시간을 기록한 경우
                    arrivalTime =
                        SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTimeMillis)
                } else {
                    // 퇴근 시간을 기록한 경우
                    departureTime =
                        SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTimeMillis)
                }
            }
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    // 지정된 위치의 좌표를 생성합니다. (예시: 위도 37.123, 경도 128.456)
                    val targetLocation = Location("TargetLocation")
                    targetLocation.latitude = 37.560 //예시
                    targetLocation.longitude = 126.976 //예시

                    // 현재 위치와 지정된 위치 간의 거리를 계산합니다.
                    val distance = calculateDistance(location, targetLocation)

                    // 거리가 특정 범위 내에 있는 경우 출근 또는 퇴근 시간을 Firestore에 기록합니다.
                    val isArrival = (distance < 100) // 거리가 10 미터 이내면 출근으로 판단
                    val currentTimeMillis = System.currentTimeMillis()

                    recordTimeToFirestore(isArrival, currentTimeMillis)
                }
            }
    }
}


//@Composable
//fun Modifier.pulsateClick() = composed {
//    var buttonState by remember { mutableStateOf(buttonState.Idle) }
//    val scale by animateFloatAsState(if(buttonState == ButtonState.Pressed) 0.7f else 1f)
//    this.clickable(interactionSource =  remember {
//        MutableInteractionSource()
//    }, indication = = null){}
//}
//
//enum class buttonState { pressed, Idle}
