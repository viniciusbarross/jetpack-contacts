package com.example.appcontatos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.appcontatos.ui.contact.ContactsListScreen
import com.example.appcontatos.ui.theme.AppContatosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppContatosTheme {
                ContactsListScreen()
            }
        }
    }
}
