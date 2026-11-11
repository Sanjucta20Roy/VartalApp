package com.example.vartalapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.vartalapp.ui.MainScreen
import com.example.vartalapp.ui.theme.VartalAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VartalAppTheme {
                MainScreen()
            }
        }
    }
}
