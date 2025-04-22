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
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check

import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.ui.text.style.TextAlign


import java.util.*
import kotlin.math.roundToInt

class InsightsScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var context = this
        getHEIFAScores(context)
        super.onCreate(savedInstanceState)
        setContent {
            InsightsScreenWithBottomBar(this)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InsightsScreenWithBottomBar(context: Context) {
    val items = listOf("Home", "Insights", "NutriCoach", "Settings")
    val icons = listOf(
        Icons.Filled.Home,
        Icons.Filled.Favorite,
        Icons.Filled.AccountBox,
        Icons.Filled.Settings
    )
    var currentScreen by remember { mutableStateOf("Insights") }

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
                                    "Home" -> context.startActivity(Intent(context,
                                        HomeScreenActivity::class.java))
                                    "NutriCoach" -> context.startActivity(Intent(context, InsightsScreen::class.java))
                                    "Settings" -> context.startActivity(Intent(context, InsightsScreen()::class.java))
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
            InsightsScreen(context)

    }
}

@Composable
fun InsightsScreen(context: Context) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    val userName = sharedPreferences.getString("currentUserId", "User") ?: "User"
    val userSex = sharedPreferences.getString("currentUserSex", "User") ?: "User"
    val userScore = sharedPreferences.getInt("currentUserScore", 0) ?: 0
    val heifaTotal = sharedPreferences.getInt("${userName}_HEIFAtotalscore", 0) ?: 0


    val categories = listOf(
        "VegetablesHEIFAscore" to "Vegetables",
        "FruitHEIFAscore" to "Fruits",
        "GrainsandcerealsHEIFAscore" to "Grains & Cereals",
        "WholegrainsHEIFAscore" to "Whole Grains",
        "MeatandalternativesHEIFAscore" to "Meat & Alternatives",
        "DairyandalternativesHEIFAscore" to "Dairy",
        "WaterHEIFAscore" to "Water",
        "UnsaturatedFatHEIFAscore" to "Unsaturated Fats",
        "SaturatedFatHEIFAscore" to "Saturated Fats",
        "SodiumHEIFAscore" to "Sodium",
        "SugarHEIFAscore" to "Sugar",
        "AlcoholHEIFAscore" to "Alcohol",
        "DiscretionaryHEIFAscore" to "Discretionary Foods"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = "Insights: Food Score",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontFamily = FontFamily.Cursive,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))


        val specialKeys = setOf(
            "VegetablesHEIFAscore",
            "FruitHEIFAscore",
            "GrainsandcerealsHEIFAscore",
            "WaterHEIFAscore",
            "UnsaturatedFatHEIFAscore",
            "AlcoholHEIFAscore",
            "WholegrainsHEIFAscore",
            "SaturatedFatHEIFAscore"
        )

        categories.forEach { (key, label) ->
            val score = sharedPreferences.getInt("${userName}_$key", 0)

            val maxPoints = if (key in specialKeys) 5 else 10

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )


                Slider(
                    value = score.toFloat(),
                    onValueChange = {},
                    valueRange = 0f..maxPoints.toFloat(),
                    steps = maxPoints - 1,
                    modifier = Modifier
                        .weight(2.5f)
                        .padding(horizontal = 4.dp)
                        .height(30.dp),
                )

                Text(
                    text = "$score/$maxPoints",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(0.8f),
                    textAlign = TextAlign.End
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp).background(Color.White),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Total Food Quality Score",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = FontFamily.Cursive,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Slider(
                value = heifaTotal.toFloat(),
                onValueChange = {},
                valueRange = 0f..100f,
                steps = 9,
                modifier = Modifier
                    .weight(2.5f)
                    .padding(horizontal = 4.dp)
                    .height(40.dp).width(200.dp)

            )

                Text(
                    text = "${userScore}/100",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(0.8f),
                    textAlign = TextAlign.End
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF690A8F)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(230.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Icon(imageVector = Icons.Default.Share, contentDescription = "Share", tint = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Share with someone", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF690A8F)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .width(200.dp) // Increased width
                    .align(Alignment.CenterHorizontally)
            ) {
                Icon(imageVector = Icons.Default.Build, contentDescription = "Edit", tint = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Improve my diet!", color = Color.White)
            }




        }



    }
}


// This function gets the scores of the specified user for insights screen
private fun getHEIFAScores(context: Context) {
    val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    val userId = sharedPreferences.getString("currentUserId", null)
    val userPhone = sharedPreferences.getString("currentUserPhone", null)
    val userSex = sharedPreferences.getString("currentUserSex", null) ?: return

    val scoreCategories = listOf(
        "DiscretionaryHEIFAscore",
        "VegetablesHEIFAscore",
        "FruitHEIFAscore",
        "GrainsandcerealsHEIFAscore",
        "WholegrainsHEIFAscore",
        "MeatandalternativesHEIFAscore",
        "DairyandalternativesHEIFAscore",
        "SodiumHEIFAscore",
        "AlcoholHEIFAscore",
        "WaterHEIFAscore",
        "SugarHEIFAscore",
        "SaturatedFatHEIFAscore",
        "UnsaturatedFatHEIFAscore",
        "HEIFAtotalscore"
    )

    try {
        context.assets.open("participants.csv").bufferedReader().use { reader ->
            val lines = reader.readLines()
            val headers = lines.first().split(",")

            val indexId = headers.indexOf("User_ID")
            val indexPhone = headers.indexOf("PhoneNumber")

            if (indexId == -1 || indexPhone == -1) return


            for (line in lines.drop(1)) {
                val tokens = line.split(",")


                if (tokens.size > maxOf(indexId, indexPhone)) {
                    val csvUserId = tokens[indexId].trim()
                    val csvPhone = tokens[indexPhone].trim()

                    if (csvUserId == userId && csvPhone == userPhone) {
                        val editor = sharedPreferences.edit()

                        for (category in scoreCategories) {
                            val columnName = if (userSex.lowercase() == "male") {
                                "${category}Male"

                            } else {

                                "${category}Female"

                            }

                            val columnIndex = headers.indexOf(columnName)
                            if (columnIndex != -1 && columnIndex < tokens.size) {
                                val value = tokens[columnIndex].trim().toDoubleOrNull()?.let {
                                    it.toInt()
                                } ?: 0


                                editor.putInt("${userId}_$category", value)
                            }
                        }

                        editor.apply()
                        return
                    }
                }
            }
        }
    } catch (e: Exception) {

        e.printStackTrace()
    }
}





