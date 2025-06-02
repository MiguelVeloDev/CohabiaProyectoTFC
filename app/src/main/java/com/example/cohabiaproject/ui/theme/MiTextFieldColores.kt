package com.example.cohabiaproject.ui.theme

import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun coloresTextField(): TextFieldColors = TextFieldDefaults.colors(
    unfocusedTextColor = Color.Black,
    unfocusedContainerColor = FondoTextField,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent
)