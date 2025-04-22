package com.fit2081.assignment1


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import java.io.BufferedReader
import java.io.InputStreamReader
import androidx.compose.material3.Scaffold
import androidx.compose.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.material3.*
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.fit2081.assignment1.ui.theme.Assignment1Theme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.fit2081.assignment1.ui.theme.Assignment1Theme
import android.content.Context
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment1Theme {
                val context = LocalContext.current
                val sharedPreferences =
                    context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

                // Load the list of IDs for the dropdown
                val userIdList = remember { loadUserIds(context) }

                // State for the text fields
                var id by remember { mutableStateOf(TextFieldValue("")) }
                var phoneNumber by remember { mutableStateOf(TextFieldValue("")) }

                // State for dropdown expansion
                var isDropdownExpanded by remember { mutableStateOf(false) }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Log In",
                        fontSize = 70.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Cursive,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )


                    Box {
                        OutlinedTextField(
                            value = id,
                            onValueChange = { newValue ->
                                id = newValue
                            },
                            label = { Text("My ID (Provided by your Clinician)") },
                            modifier = Modifier
                                .fillMaxWidth(0.9f),
                            singleLine = true,
                            readOnly = true,
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Dropdown Arrow",
                                    modifier = Modifier.clickable {
                                        isDropdownExpanded = !isDropdownExpanded
                                    }
                                )
                            }
                        )

                        DropdownMenu(
                            expanded = isDropdownExpanded,
                            onDismissRequest = { isDropdownExpanded = false },
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                        ) {
                            userIdList.forEach { userId ->
                                DropdownMenuItem(
                                    text = { Text(userId) },
                                    onClick = {
                                        id = TextFieldValue(userId)
                                        isDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        label = { Text("Phone number") },
                        modifier = Modifier.fillMaxWidth(0.9f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "This app is only for pre-registered users. Please have your ID and phone number handy before continuing.",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            val userMap = loadUserData(context)
                            val enteredId = id.text.trim()
                            val enteredPhone = phoneNumber.text.trim()

                            if (userMap[enteredId] == enteredPhone) {
                                Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT)
                                    .show()


                                sharedPreferences.edit()
                                    .putString("currentUserId", enteredId)
                                    .putString("currentUserPhone", enteredPhone)
                                    .apply()

                                val userSex = getUserSex(context, enteredId)
                                userSex?.let {
                                    sharedPreferences.edit()
                                        .putString("currentUserSex", it)
                                        .apply()
                                }

                                val intent = Intent(context, QuestionnaireScreen::class.java)
                                context.startActivity(intent)
                            } else {
                                Toast.makeText(context, "Invalid credentials!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF068C30))
                    ) {
                        Text("Continue", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    private fun loadUserIds(context: Context): List<String> {
        val userIds = mutableListOf<String>()
        try {
            context.assets.open("participants.csv").bufferedReader().use { reader ->
                val lines = reader.readLines().drop(1)
                for (line in lines) {
                    val tokens = line.split(",")
                    if (tokens.size >= 2) {
                        val userId = tokens[1].trim()
                        userIds.add(userId)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return userIds
    }


    private fun loadUserData(context: Context): Map<String, String> {
        val userMap = mutableMapOf<String, String>()

        try {
            context.assets.open("participants.csv").bufferedReader().use { reader ->
                val lines = reader.readLines()
                for (line in lines.drop(1)) {
                    val tokens = line.split(",")
                    if (tokens.size >= 2) {
                        val userId = tokens[1].trim()
                        val phoneNumber = tokens[0].trim()
                        userMap[userId] = phoneNumber
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return userMap
    }

    private fun getUserSex(context: Context, userId: String): String? {
        try {
            context.assets.open("participants.csv").bufferedReader().use { reader ->
                val lines = reader.readLines()
                for (line in lines.drop(1)) {
                    val tokens = line.split(",")
                    if (tokens.size >= 3) {
                        val storedUserId = tokens[1].trim()
                        val sex = tokens[2].trim()

                        if (storedUserId == userId) {
                            return sex
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }



}



