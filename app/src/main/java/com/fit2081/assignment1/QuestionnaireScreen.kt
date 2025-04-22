package com.fit2081.assignment1


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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

import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.DateRange

import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.text.style.TextAlign


import java.util.*

class QuestionnaireScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val sharedPreferences = context.getSharedPreferences("UserPrefs", MODE_PRIVATE)

            QuestionnaireContent(sharedPreferences)
        }
    }
}

@Composable
fun QuestionnaireContent(sharedPreferences: SharedPreferences) {
    val foodCategories = listOf(
        "Fruits",
        "Vegetables",
        "Grains",
        "Red Meat",
        "Seafood",
        "Poultry",
        "Fish",
        "Eggs",
        "Nuts/Seeds"
    )
    val personas = listOf(
        "Health Devotee",
        "Mindful Eater",
        "Wellness Striver",
        "Balance Seeker",
        "Health Procrastinator",
        "Food Carefree"
    )
    val userID = sharedPreferences.getString("currentUserId", "") ?: ""
    val phoneNumber = sharedPreferences.getString("currentUserPhone", "") ?: ""


    val selectedFoods = remember { mutableStateMapOf<String, Boolean>() }
    val selectedPersona =
        remember { mutableStateOf(sharedPreferences.getString("${userID}_${phoneNumber}_persona", "") ?: "") }
    val showDialogPersona = remember { mutableStateOf(false) }
    val showDialogTime = remember { mutableStateOf(false) }
    val personaDialogText = remember { mutableStateOf("") }

    val mealTime =
        remember { mutableStateOf(sharedPreferences.getString("${userID}_${phoneNumber}_meal_time", "00:00") ?: "00:00") }
    val sleepTime =
        remember { mutableStateOf(sharedPreferences.getString("${userID}_${phoneNumber}_sleep_time", "00:00") ?: "00:00") }
    val wakeTime =
        remember { mutableStateOf(sharedPreferences.getString("${userID}_${phoneNumber}_wake_time", "00:00") ?: "00:00") }
    val context = LocalContext.current
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    val intent = Intent(context, LoginActivity::class.java)
                    context.startActivity(intent)
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.width(40.dp))
                Text(
                    "Food Intake Questionnaire",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentSize(Alignment.CenterEnd),
                    fontFamily = FontFamily.Cursive,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp,
                color = Color.Gray
            )
            Text(
                "Tick all the food categories you can eat",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )

            val selectedFoods = remember {
                mutableStateMapOf<String, Boolean>().apply {
                    foodCategories.forEach { category ->
                        val storedValue = sharedPreferences.getBoolean("${userID}_${phoneNumber}_food_$category", false)
                        this[category] = storedValue
                    }
                }
            }

            Column {
                for (i in foodCategories.indices step 3) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
                    ) {
                        for (j in 0 until 3) {
                            if (i + j < foodCategories.size) {
                                val category = foodCategories[i + j]

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Checkbox(
                                        checked = selectedFoods[category] ?: false,
                                        onCheckedChange = { isChecked ->
                                            selectedFoods[category] = isChecked
                                            sharedPreferences.edit()
                                                .putBoolean("${userID}_${phoneNumber}_food_$category", isChecked)
                                                .apply()
                                        }
                                    )
                                    Text(
                                        text = category,
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily.SansSerif
                                    )
                                }
                            }
                        }
                    }
                }
            }


            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp,
                color = Color.Gray
            )

            Text("Your Persona", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "People can be broadly classified into 6 different types based on their eating preferences. Click on each button to find out the different types, and select the type that best fits you!",
                fontSize = 14.sp,
                fontFamily = FontFamily.SansSerif
            )
            Spacer(modifier = Modifier.height(10.dp))

            val personaDescriptions = mapOf(
                "Health Devotee" to "I’m passionate about healthy eating & health plays a big part in my life. I use social media to follow active lifestyle personalities or get new recipes/exercise ideas. I may even buy superfoods or follow a particular type of diet. I like to think I am super healthy.",
                "Mindful Eater" to "I’m health-conscious and being healthy and eating healthy is important to me. Although health means different things to different people, I make conscious lifestyle decisions about eating based on what I believe healthy means. I look for new recipes and healthy eating information on social media.",
                "Wellness Striver" to "I aspire to be healthy (but struggle sometimes). Healthy eating is hard work! I’ve tried to improve my diet, but always find things that make it difficult to stick with the changes. Sometimes I notice recipe ideas or healthy eating hacks, and if it seems easy enough, I’ll give it a go.",
                "Balance Seeker" to "I try and live a balanced lifestyle, and I think that all foods are okay in moderation. I shouldn’t have to feel guilty about eating a piece of cake now and again. I get all sorts of inspiration from social media like finding out about new restaurants, fun recipes and sometimes healthy eating tips.",
                "Health Procrastinator" to "I’m contemplating healthy eating but it’s not a priority for me right now. I know the basics about what it means to be healthy, but it doesn’t seem relevant to me right now. I have taken a few steps to be healthier but I am not motivated to make it a high priority because I have too many other things going on in my life.",
                "Food Carefree" to "I’m not bothered about healthy eating. I don’t really see the point and I don’t think about it. I don’t really notice healthy eating tips or recipes and I don’t care what I eat."
            )

// Updated Persona Button Section
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                personas.chunked(3).forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(
                            8.dp,
                            Alignment.CenterHorizontally
                        )
                    ) {
                        row.forEach { persona ->
                            Button(
                                onClick = {
                                    personaDialogText.value = persona
                                    showDialogPersona.value = true
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFF068C30
                                    )
                                ),
                                contentPadding = PaddingValues(horizontal = 7.dp, vertical = 4.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = persona, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }


            if (showDialogPersona.value) {
                AlertDialog(
                    onDismissRequest = { showDialogPersona.value = false },
                    text = {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Load image
                            val imageResId = when (personaDialogText.value) {
                                "Health Devotee" -> R.drawable.healthdevotee
                                "Mindful Eater" -> R.drawable.mindfuleater
                                "Wellness Striver" -> R.drawable.wellnessstriver
                                "Balance Seeker" -> R.drawable.balanceseeker
                                "Health Procrastinator" -> R.drawable.healthprocrastinator
                                "Food Carefree" -> R.drawable.foodcarefree
                                else -> null
                            }

                            // Image
                            imageResId?.let {
                                Image(
                                    painter = painterResource(id = it),
                                    contentDescription = personaDialogText.value,
                                    modifier = Modifier
                                        .size(120.dp)
                                        .padding(bottom = 8.dp)
                                )
                            }

                            // Title moved here
                            Text(
                                text = personaDialogText.value,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Cursive,
                                fontSize = 25.sp,
                                modifier = Modifier.padding(bottom = 8.dp),
                                textAlign = TextAlign.Center
                            )

                            // Description text
                            Text(
                                text = personaDescriptions[personaDialogText.value]
                                    ?: "No description available.",
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = { showDialogPersona.value = false },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF068C30))
                        ) {
                            Text("Dismiss")
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(5.dp))
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp,
                color = Color.Gray
            )



            var expanded by remember { mutableStateOf(false) }



            Column {
                Text("Which persona best fits you?", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(10.dp))


                Box(
                    modifier = Modifier
                        .width(380.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                        .clickable { expanded = true }
                        .padding(5.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = selectedPersona.value.ifEmpty { "Select option" },
                            fontSize = 16.sp,
                            modifier = Modifier.weight(1f)
                        )

                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown Arrow",
                            tint = Color.Gray
                        )
                    }
                }


                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier

                        .width(380.dp)
                        .heightIn(max = 200.dp)
                        .background(Color.White)
                        .border(1.dp, Color.Black)
                ) {
                    personas.chunked(2).forEachIndexed { rowIndex, rowItems ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            rowItems.forEachIndexed { colIndex, persona ->
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .border(0.5.dp, Color.LightGray)
                                ) {
                                    DropdownMenuItem(
                                        text = { Text(text = persona, fontSize = 14.sp) },
                                        onClick = {
                                            selectedPersona.value = persona
                                            sharedPreferences.edit().putString("persona", persona).apply()
                                            expanded = false
                                        }
                                    )
                                }

                                if (colIndex == 0) {
                                    Divider(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .width(0.5.dp),
                                        color = Color.LightGray
                                    )
                                }
                            }
                        }

                        if (rowIndex < personas.chunked(2).size - 1) {
                            Divider(
                                modifier = Modifier.fillMaxWidth().height(0.5.dp),
                                color = Color.LightGray
                            )
                        }
                    }
                }
            }






            Spacer(modifier = Modifier.height(20.dp))
            Text("Timings", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            val timeStates = listOf(mealTime, sleepTime, wakeTime)
            val labels = listOf(
                "What time of the day approx. do you normally eat your biggest meal?",
                "What time of the day approx. do you go to sleep at night?",
                "What time of the day approx. do you wake up in the morning?"
            )
            val prefKeys = listOf("meal_time", "sleep_time", "wake_time")

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                labels.forEachIndexed { index, label ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = label,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 16.dp)
                        )
                        TimePickerField(
                            label = "Pick Time",
                            timeState = timeStates[index],
                            sharedPreferences = sharedPreferences,
                            prefKey = prefKeys[index]
                        )
                    }
                }
            }



            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF068C30)),
                    onClick = {
                        val userID = sharedPreferences.getString("currentUserId", "") ?: ""
                        val phoneNumber = sharedPreferences.getString("currentUserPhone", "") ?: ""

                        // Apply defaults if fields are empty
                        if (selectedPersona.value.isEmpty()) {
                            selectedPersona.value = "Balance Seeker"
                        }

                        if (mealTime.value == "00:00") {
                            mealTime.value = "11:00"
                        }

                        if (wakeTime.value == "00:00") {
                            wakeTime.value = "07:00"
                        }

                        if (sleepTime.value == "00:00") {
                            sleepTime.value = "23:00"
                        }

                        val isAtLeastOneFoodSelected = selectedFoods.any { it.value }
                        val isDataComplete = isAtLeastOneFoodSelected // because other values are guaranteed now

                        if (userID.isNotEmpty() && phoneNumber.isNotEmpty()) {
                            val editor = sharedPreferences.edit()

                            editor.putString("${userID}_${phoneNumber}_persona", selectedPersona.value)
                            editor.putString("${userID}_${phoneNumber}_meal_time", mealTime.value)
                            editor.putString("${userID}_${phoneNumber}_sleep_time", sleepTime.value)
                            editor.putString("${userID}_${phoneNumber}_wake_time", wakeTime.value)

                            selectedFoods.forEach { (category, isChecked) ->
                                editor.putBoolean("${userID}_${phoneNumber}_food_$category", isChecked)
                            }

                            // Save if data is complete (only food selection matters now)
                            editor.putBoolean("${userID}_${phoneNumber}_entered", isDataComplete)

                            editor.apply()

                            val intent = Intent(context, HomeScreenActivity::class.java)
                            context.startActivity(intent)
                            Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.e("SaveError", "Missing userID or phoneNumber")
                            Toast.makeText(context, "Login failed!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .width(150.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text("Save")
                }


            }



        }
    }

    if (showDialogTime.value) {
        AlertDialog(
            onDismissRequest = { showDialogTime.value = false },
            title = { Text("Persona Info") },
            text = { Text(personaDialogText.value) },
            confirmButton = {
                Button(onClick = { showDialogTime.value = false }) { Text("OK") }
            }
        )
    }
}

@Composable
fun TimePickerField(
    label: String,
    timeState: MutableState<String>,
    sharedPreferences: SharedPreferences,
    prefKey: String
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .background(Color.White, RoundedCornerShape(8.dp))
            .clickable { showTimePicker(context, timeState, sharedPreferences, prefKey) }
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .height(30.dp)
            .width(100.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.DateRange,
                contentDescription = "Clock Icon",
                tint = Color.Black,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (timeState.value == "00:00") "00:00" else timeState.value,
                color = if (timeState.value == "00:00") Color.Gray else Color.Black,
                fontSize = 16.sp
            )
        }
    }
}


fun showTimePicker(
    context: Context,
    timeState: MutableState<String>,
    sharedPreferences: SharedPreferences,
    prefKey: String
) {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    TimePickerDialog(context, { _, selectedHour, selectedMinute ->
        val timeString = String.format("%02d:%02d", selectedHour, selectedMinute)
        timeState.value = timeString
        sharedPreferences.edit().putString(prefKey, timeString).apply()
    }, hour, minute, false).show()
}