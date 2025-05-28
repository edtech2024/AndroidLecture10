package com.example.myapplicationsixth.fragment

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun DetailScreen(modifier: Modifier = Modifier, navController: NavController) {
//fun DetailScreen(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val id = remember { mutableStateOf("") }
            val uid = remember { mutableStateOf("") }
            val title = remember { mutableStateOf("") }
            val description = remember { mutableStateOf("") }
            val priority = remember { mutableStateOf("") }
            val type = remember { mutableStateOf("") }
            val count = remember { mutableStateOf("") }
            val frequency = remember { mutableStateOf("") }
            val color = remember { mutableStateOf("") }
            val date = remember { mutableStateOf("") }
            val done_dates = remember { mutableStateOf("") }

            Text(text = "Detail screen")

            OutlinedTextField(
                value = id.value,
                onValueChange = { id.value = it },
                label = { Text(text = "id") }
            )

            OutlinedTextField(
                value = uid.value,
                onValueChange = { uid.value = it },
                label = { Text(text = "uid") }
            )

            OutlinedTextField(
                value = title.value,
                onValueChange = { title.value = it },
                label = { Text(text = "title") }
            )

            OutlinedTextField(
                value = description.value,
                onValueChange = { description.value = it },
                label = { Text(text = "description") }
            )

            OutlinedTextField(
                value = priority.value,
                onValueChange = { priority.value = it },
                label = { Text(text = "priority") }
            )

            OutlinedTextField(
                value = type.value,
                onValueChange = { type.value = it },
                label = { Text(text = "type") }
            )

            OutlinedTextField(
                value = count.value,
                onValueChange = { count.value = it },
                label = { Text(text = "count") }
            )

            OutlinedTextField(
                value = frequency.value,
                onValueChange = { frequency.value = it },
                label = { Text(text = "frequency") }
            )

            OutlinedTextField(
                value = color.value,
                onValueChange = { color.value = it },
                label = { Text(text = "color") }
            )

            OutlinedTextField(
                value = date.value,
                onValueChange = { date.value = it },
                label = { Text(text = "date") }
            )

            OutlinedTextField(
                value = done_dates.value,
                onValueChange = { done_dates.value = it },
                label = { Text(text = "done_dates") }
            )

            // call vm.method
            Button(onClick = { /*TODO*/ }) {
                Text(text = "sign up")
            }

        }
    }
}

sealed class Screen(var route: String) {
    object Detail : Screen("detail")
}
