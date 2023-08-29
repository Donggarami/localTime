package com.example.localtime

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localtime.ui.theme.LocalTimeTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Activity_6 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MytopAppBar()
            Spacer(modifier = Modifier.height(30.dp))
            Column {
                ReservationScreen()
            }
        }
    }
}

data class Room(val id: Int, val name: String, val imageResId: Int)

val sampleRooms = listOf(
    Room(1, "Room A", R.drawable.rooma),
    Room(2, "Room B", R.drawable.roomb),
    Room(3, "Room C", R.drawable.roomc),
    Room(4, "Room D", R.drawable.roomd),
    Room(5, "Room E", R.drawable.roome),
    Room(6, "Room F", R.drawable.roomf),
    // Add more rooms as needed
)

data class Reservation(
    val room: Room,
    var selectedDate: Date? = null,
    var selectedTime: Date? = null

)

@Composable
fun ReservationScreen() {
    val scrollState = rememberScrollState()
    val reservations =
        remember { mutableStateListOf(*sampleRooms.map { Reservation(it) }.toTypedArray()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        reservations.forEachIndexed { index, reservation ->
            ReservationItem(reservation, index)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ReservationItem(reservation: Reservation, index: Int) {
    var showDialog by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier.fillMaxWidth(),
        // elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,

            ) {

            Row() {

                val painter: Painter = painterResource(id = reservation.room.imageResId)
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = " ${reservation.room.name}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            DatePicker(
                selectedDate = reservation.selectedDate,
                onDateSelected = { date -> reservation.selectedDate = date },
                modifier = Modifier.padding(16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            TimePicker(
                selectedTime = reservation.selectedTime,
                onTimeSelected = { time -> reservation.selectedTime = time },
                modifier = Modifier.padding(16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
                    val roomName = reservation.room.name
                    val dateStr = reservation.selectedDate?.let { dateFormatter.format(it) }
                        ?: "Not selected"
                    val timeStr = reservation.selectedTime?.let { timeFormatter.format(it) }
                        ?: "Not selected"
                    val reservationDetails = "Room: $roomName, Date: $dateStr, Time: $timeStr"

                    showDialog = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Confirm Reservation")
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = {
                        showDialog = false
                    },
                    title = { Text(text = "Reservation Details") },
                    text = { Text(text = "Room:  \n Date:   \nTime: ") },
                    confirmButton = {
                        Button(
                            onClick = {
                                showDialog = false
                            }
                        ) {
                            Text(text = "OK")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun DatePicker(
    selectedDate: Date?,
    onDateSelected: (Date) -> Unit,
    modifier: Modifier = Modifier
) {
    // Date Picker Composable implementation goes here
}

@Composable
fun TimePicker(
    selectedTime: Date?,
    onTimeSelected: (Date) -> Unit,
    modifier: Modifier = Modifier
) {
    // Time Picker Composable implementation goes here
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MytopAppBar() {

    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                Alignment.Center
            ) { Text(text = "시설예약") }
        },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Icon"
                )
            }
        },
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .clip(shape = RoundedCornerShape(16)),
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color(
                android.graphics.Color.parseColor("#ba55d3")
            )
        ),

        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = ""
                )
            }
        }
    )
}






