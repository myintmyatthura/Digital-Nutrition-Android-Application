package com.fit2081.assignment1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fit2081.assignment1.ui.theme.Assignment1Theme

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text

import androidx.compose.runtime.*

import androidx.compose.material3.*

import androidx.compose.ui.Alignment

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons


import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.Box
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import androidx.compose.foundation.layout.*

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily

import android.content.Context


import android.app.TimePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background


import androidx.compose.foundation.border
import androidx.compose.foundation.clickable

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Settings
import java.io.BufferedReader
import java.io.InputStreamReader
import androidx.compose.material3.HorizontalDivider
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check

import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.RectangleShape


import java.util.*
import kotlin.math.roundToInt

class HomeScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var context = this
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreenWithBottomBar(this)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreenWithBottomBar(context: Context) {
    val items = listOf("Home", "Insights", "NutriCoach", "Settings")
    val icons = listOf(
        Icons.Filled.Home,
        Icons.Filled.Favorite,
        Icons.Filled.AccountBox,
        Icons.Filled.Settings
    )
    var currentScreen by remember { mutableStateOf("Home") }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                contentColor = Color.Gray,
                modifier = Modifier.height(80.dp)
            ) {
                items.forEachIndexed { index, item ->
                    val isSelected = item == currentScreen
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            IconButton(modifier = Modifier.size(20.dp), onClick = {
                                currentScreen = item
                                when (item) {
                                    "Insights" -> context.startActivity(Intent(context, InsightsScreen::class.java))
                                    "NutriCoach" -> context.startActivity(Intent(context, HomeScreenActivity::class.java))
                                    "Settings" -> context.startActivity(Intent(context, HomeScreenActivity::class.java))
                                }
                            }) {
                                Icon(
                                    imageVector = icons[index],
                                    contentDescription = item,
                                    tint = if (isSelected) Color(0xFF0A7326) else Color.Gray
                                )
                            }
                            Text(
                                text = item,
                                fontSize = 12.sp,
                                color = if (isSelected) Color(0xFF0A7326) else Color.Black
                            )
                        }
                    }
                }
            }
        }
    ) {
        // Remove innerPadding and directly place HomeScreen without modifying padding
        HomeScreen(context)
    }
}

@Composable
fun HomeScreen(context: Context) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    val userName = sharedPreferences.getString("currentUserId", "User") ?: "User"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp).background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Hello,", fontSize = 20.sp, color = Color.Gray)
        Text(text = "User $userName", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = Color.Black, fontFamily = FontFamily.Cursive)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "You've already filled in your Food Intake Questionnaire, but you can change details here:",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = {
                    val intent = Intent(context, QuestionnaireScreen::class.java)
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF068C30)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", tint = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Edit", color = Color.White)
            }


        }
        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = painterResource(id = R.drawable.foodplate),
            contentDescription = "Nutrition Logo",
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.CenterHorizontally)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "My Score",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive,
                modifier = Modifier.weight(1f)
            )

            TextButton(
                onClick = {
                    val intent = Intent(context, InsightsScreen::class.java)
                    context.startActivity(intent)
                }
            ) {
                Text(text = "See all scores", color = Color.Gray, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Arrow", tint = Color.Gray)
            }


        }

        val userId = sharedPreferences.getString("currentUserId", "") ?: ""
        val userPhone = sharedPreferences.getString("currentUserPhone", "") ?: ""
        var foodScore by remember { mutableStateOf(0) }
        var isMale by remember { mutableStateOf(true) }

        val reader = context.assets.open("participants.csv").bufferedReader()
        val lines = reader.readLines()
        val headers = lines.first().split(",")
        val userIdIndex = headers.indexOf("User_ID")
        val phoneIndex = headers.indexOf("PhoneNumber")
        val sexIndex = headers.indexOf("Sex")
        val maleScoreIndex = headers.indexOf("HEIFAtotalscoreMale")
        val femaleScoreIndex = headers.indexOf("HEIFAtotalscoreFemale")

        lines.drop(1).forEach { line ->
            val cols = line.split(",")
            if (cols[1] == userId && cols[0] == userPhone) {
                isMale = cols[2] == "Male"
                foodScore = cols[if (isMale) maleScoreIndex else femaleScoreIndex].toFloatOrNull()?.roundToInt() ?: 0

                return@forEach
            }
        }
        sharedPreferences.edit()
            .putInt("currentUserScore", foodScore)
            .apply()
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val arrowResource = if (foodScore >= 50) R.drawable.uparrow else R.drawable.downarrow
            Image(
                painter = painterResource(id = arrowResource),
                contentDescription = "Food Score Trend",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "Your Food Quality Score",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(25.dp))
            Text(
                text = "$foodScore/100",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive,
                color = if (foodScore >= 50) Color(0xFF0A7326) else Color(0xFFA80707)
            )
            Spacer(modifier = Modifier.height(15.dp))

        }
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 0.7.dp,
            color = Color.LightGray
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "What is the Food Quality Score?",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive,

        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Your Food Quality Score provides a snapshot of how well your eating patterns align with established food guidelines, helping you identify both strengths and opportunities for improvement in your diet. \n\nThis personalized measurement considers various food groups including vegetables, fruits, whole grains and proteins to give you practical insights for making healthier food choices.",
            fontSize = 13.sp,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(25.dp))






    }
}



