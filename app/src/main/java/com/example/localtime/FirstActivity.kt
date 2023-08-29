package com.example.localtime

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.draw.clip
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.localtime.ui.theme.LocalTimeTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class FirstActivity : ComponentActivity() {
    private val clickTime = mutableStateOf(0L)

    companion object {const val RC_SIGN_IN = 100}
    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        val defaultImageUrl = "https://example.com/default-image.jpg"

        installSplashScreen()
        setContent {
            LocalTimeTheme {

                //로티 추가함수
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie))

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .background(color = Color.White),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Image(
                            painterResource(id = R.drawable.top_background),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds
                        )
//                        LottieAnimation(
//                            composition = composition,
//                            modifier = Modifier
//                                .size(200.dp, 200.dp),
//                        )
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = null,
                            modifier = Modifier.height(150.dp)
                        )
                        Text(
                            text = "Welcome to GymMan",
                            fontSize = 30.sp,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold,
                            color = Color(android.graphics.Color.parseColor("#7d32a8"))
                        )

                        //비밀번호 아이디
                        var user by remember { mutableStateOf("Username") }
                        var pass by remember { mutableStateOf("Password") }
                        var passworsVisisble by rememberSaveable { mutableStateOf(false) }
                        TextField(
                            value = user, { text -> user = text },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(66.dp)
                                .padding(start = 64.dp, end = 64.dp, top = 8.dp, bottom = 8.dp)
                                .border(
                                    1.dp, Color(android.graphics.Color.parseColor("#7d32a8")),
                                    shape = RoundedCornerShape(50)
                                ),

                            shape = RoundedCornerShape(50),
                            textStyle = TextStyle(
                                textAlign = TextAlign.Center,
                                color = Color(android.graphics.Color.parseColor("#7d32a8")),
                                fontSize = 14.sp
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                containerColor = Color.Transparent
                            )
                        )
                        TextField(
                            value = pass, { text -> pass = text },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(66.dp)
                                .padding(start = 64.dp, end = 64.dp, top = 8.dp, bottom = 8.dp)
                                .border(
                                    1.dp, Color(android.graphics.Color.parseColor("#7d32a8")),
                                    shape = RoundedCornerShape(50)
                                ),

                            shape = RoundedCornerShape(50),
                            textStyle = TextStyle(
                                textAlign = TextAlign.Center,
                                color = Color(android.graphics.Color.parseColor("#7d32a8")),
                                fontSize = 14.sp
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                containerColor = Color.Transparent
                            ),
                            visualTransformation = if (passworsVisisble) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        )
                        Button(
                            onClick = {
                                val intent = Intent(context, Activity_3::class.java)
                                context.startActivity(intent)
                            },
                            Modifier
                                .fillMaxWidth()
                                .height(66.dp)
                                .padding(start = 64.dp, end = 64.dp, top = 8.dp, bottom = 8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(
                                    android.graphics.Color.parseColor("#7d32a8")
                                )
                            ),
                            shape = RoundedCornerShape(50)
                        )

                        {

                            Text(
                                text = "Login",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = "Don't remember password? chlick here",
                            Modifier.padding(top = 8.dp, bottom = 8.dp),
                            fontSize = 14.sp,
                            color = Color(android.graphics.Color.parseColor("#7d32a8"))
                        )
                        Row() {
                            if (mAuth.currentUser == null) {
                                // 로그인 버튼 표시
                                GoogleSignInButton {
                                    signIn()
                                }
                            } else {
                                // 프로필 화면 표시
                                val user: FirebaseUser = mAuth.currentUser!!
                                ProfileScreen(
                                    profileImage = user.photoUrl
                                        ?: Uri.parse(defaultImageUrl), // null 체크 및 기본 이미지 적용
                                    name = user.displayName!!,
                                    email = user.email!!,
                                    signOutClicked = {
                                        signOut()
                                    }
                                )
                            }
                            Image(
                                painter = painterResource(id = R.drawable.google),
                                contentDescription = "",
                                Modifier.padding(8.dp)
                            )
                            Image(
                                painter = painterResource(id = R.drawable.twitter),
                                contentDescription = "",
                                Modifier.padding(8.dp)

                            )
                            Image(
                                painter = painterResource(id = R.drawable.facebook),
                                contentDescription = "",
                                Modifier.padding(8.dp)

                            )

                        }
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
    private fun signIn() {
        // Google 로그인 인텐트 시작
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    // 로그인 결과를 처리하는 메서드
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // result returned from launching the intent from GoogleSignInApi.getSignInIntent()
        if (requestCode == RC_SIGN_IN) {
            // Google 로그인 인텐트 결과 처리
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    // Google 로그인 성공 시 Firebase와 인증
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: Exception) {
                    // Google SignIn Failed
                    Log.d("SignIn", "Google SignIn Failed")
                }
            } else {
                // 로그인 실패 로깅
                Log.d("SignIn", exception.toString())
            }
        }

    }

    // Firebase와 Google 인증 메서드(신버전)
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        val context = LocalContext.current

        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // 성공시 프로필 화면 표시
                    Toast.makeText(this, "SignIn Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Activity_3::class.java)
                    this.startActivity(intent)

                } else {
                    // 실패 토스트 메시지 표시
                    Toast.makeText(this, "SignIn Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

//    // Firebase와 Google 인증 메서드(구버전)
//    private fun firebaseAuthWithGoogle(idToken: String) {
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        mAuth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // 성공시 프로필 화면 표시
//                    Toast.makeText(this, "SignIn Successful", Toast.LENGTH_SHORT).show()
//                    setContent {
//                        LocalTimeTheme {
//                            val user: FirebaseUser = mAuth.currentUser!!
//                            ProfileScreen(
//                                profileImage = user.photoUrl!!,
//                                name = user.displayName!!,
//                                email = user.email!!,
//                                signOutClicked = {
//                                    signOut()
//                                }
//                            )
//                        }
//                    }
//                } else {
//                    // 실패 토스트 메시지 표시
//                    Toast.makeText(this, "SignIn Failed", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }

    // 로그아웃 메서드
    private fun signOut() {

        // 모든 계정 로그아웃
        mAuth.signOut()
        googleSignInClient.signOut()
            // 성공, 실패 리스너
            .addOnSuccessListener {
                Toast.makeText(this, "Sign Out Successful", Toast.LENGTH_SHORT).show()
                setContent {
                    LocalTimeTheme {
                        GoogleSignInButton {
                            signIn()
                        }
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Sign Out Failed", Toast.LENGTH_SHORT).show()
            }
    }

}

@Composable
fun GoogleSignInButton(
    signInClicked: () -> Unit
) {
    // 로그인 버튼 레이아웃 구성
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .padding(start = 30.dp, end = 30.dp)
                .height(55.dp)
                .fillMaxWidth()
                .clickable {
                    signInClicked()
                },
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(width = 1.5.dp, color = Color.Black),
        ) {
            Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .size(32.dp)
                        .padding(0.dp)
                        .align(Alignment.CenterVertically),
                    painter = painterResource(id = R.drawable.ic_google_logo),
                    contentDescription = "google_logo"
                )
                Text(
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .align(Alignment.CenterVertically),
                    text = "Sign In With Google",
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    profileImage: Uri,
    name: String,
    email: String,
    signOutClicked: () -> Unit
) {
    // 프로필 화면 레이아웃 구성
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 아래 코드는 로그인 시 보이는 사용자 사진이 없을때 회색칸으로 나오는 코드입니다.
//        Card(
//            modifier = Modifier
//                .size(150.dp)
//                .fillMaxHeight(0.4f)
//        )
        {
            // 여기에서 원할시 실제로 이미지를 화면에 로드하고 표시하는 코드가 필요합니다.
        }

        Column(
            modifier = Modifier
                .fillMaxHeight(0.6f)
                .padding(top = 60.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = {},
                readOnly = true,
                label = {
                    Text(text = "Name")
                },
            )

            OutlinedTextField(
                modifier = Modifier.padding(top = 20.dp),
                value = email,
                onValueChange = {},
                readOnly = true,
                label = {
                    Text(text = "Email")
                },
            )

            Button(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(top = 100.dp),
                onClick = { signOutClicked() }
            ) {
                Text(text = "LogOut")
            }
        }
    }
}

