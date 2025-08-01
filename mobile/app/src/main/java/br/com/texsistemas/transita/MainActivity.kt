package br.com.texsistemas.transita

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.android.gms.maps.MapsInitializer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicializa o Maps
        MapsInitializer.initialize(applicationContext)

        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}