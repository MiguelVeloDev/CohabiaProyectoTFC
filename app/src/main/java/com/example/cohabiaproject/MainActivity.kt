package com.example.cohabiaproject


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.cohabiaproject.presentation.navigation.navigation.NavGraph
import com.example.cohabiaproject.ui.theme.CohabiaProjectTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CohabiaProjectTheme {
                NavGraph()

            }
            }
        }
    }


