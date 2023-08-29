//@OptIn(ExperimentalMaterial3Api::class)
package com.example.localtime
// EmployeeInfoActivity.kt

import Employee
import EmployeeInfoManager
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.util.Date


class EmployeeInfoActivity<Bundle> : ComponentActivity() {

    var employeeInfoManager = EmployeeInfoManager()


    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            // 직원정보를 EmployeeInfoScreen에 전달합니다.
            EmployeeInfoScreen()
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun EmployeeInfoScreen() {

        // 직원정보를 입력하는 텍스트필드입니다.
        var nameInput by remember { mutableStateOf("") }
        var phoneNumberInput by remember { mutableStateOf("") }
        var positionInput by remember { mutableStateOf("") }
        // DatePicker를 사용하여 입사일과 퇴사일을 선택합니다.
        var hireDate by remember { mutableStateOf(Date())}
        var resignDate by remember { mutableStateOf(Date()) }
        var basicSalary by remember { mutableStateOf(0) }


        // 직원정보를 등록 / 수정하는 버튼입니다.
        val registerButton = {
            val employee = Employee(
                name = nameInput,
                phoneNumber = phoneNumberInput,
                position = positionInput,
                hireDate = hireDate,
                resignDate = resignDate,
                basicSalary = basicSalary
            )
            if (employee.id.isNotEmpty()) {
                employeeInfoManager.updateEmployee(employee.id, employee)
            } else {
                employeeInfoManager.addEmployee(employee)
            }

        }

            Surface(
                modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
            ) {

            }

            // 직원정보 등록 / 수정 화면을 구성합니다.
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()

            ) {

                Text(text = "My Page")
                Spacer(modifier = Modifier.padding(top = 20.dp))

                // 직원 이름을 입력하는 텍스트필드입니다.
                TextField(value = nameInput,
                    onValueChange = { nameInput = it },
                    label = { Text(text = "이름") })

                // 직원 전화번호를 입력하는 텍스트필드입니다.
                TextField(
                    value = phoneNumberInput,
                    onValueChange = { phoneNumberInput = it },
                    label = { Text(text = "전화번호") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Phone
                    )
                )

                // 직원 직책을 입력하는 텍스트필드입니다.
                TextField(value = positionInput,
                    onValueChange = { positionInput = it },
                    label = { Text(text = "직책") })


//                // 직원 입사일을 입력하는 DatePicker입니다. 라이브러리 존재하지 않아서 오류-> 코드 수정해야 함 (smh 28082023)
//                DatePicker(
//                    value = hireDate.toDate().toEpochMilli(),
//                    onValueChange = { hireDate = it },
//                    displayMode = DisplayMode.Calendar,
//                    dateFormatter = DatePickerFormatter.YearMonthDay
//                )
//                // 직원 퇴사일을 입력하는 DatePicker입니다.
//                DatePicker(
//                    value = hireDate.toDate().toEpochMilli(),
//                    onValueChange = { resignDate = it },
//                    displayMode = DisplayMode.Calendar,
//                    dateFormatter = DatePickerFormatter.YearMonthDay
//                )

                TextField(
                    value = basicSalary.toString(),
                    onValueChange = {
                        basicSalary = it.toIntOrNull() ?: 0
                    },
                    label = { Text(text = "월급") }
                )


                // 직원정보를 등록 / 수정하는 버튼입니다.
                Button(
                    onClick = registerButton,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "summit")
                }
            }
        }// fun employeeScreen
    }//surface
