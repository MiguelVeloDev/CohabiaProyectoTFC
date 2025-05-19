
package com.example.cohabiaproject.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cohabiaproject.R
import com.example.cohabiaproject.domain.model.Electrodomestico
import com.example.cohabiaproject.domain.model.UsoPrograma
import com.example.cohabiaproject.presentation.ui.viewmodel.ElectrodomesticoViewModel
import com.example.cohabiaproject.ui.theme.NaranjaPrincipal
import org.koin.androidx.compose.koinViewModel

@Composable
fun TablonAnuncios(
 ) {

    var anuncio by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Box(
                Modifier.background(Color.Gray)
            )
            {
                Text(text = "Anuncios")
            }
            TextField(
                value = anuncio,
                onValueChange = { anuncio = it },
                modifier = Modifier.fillMaxWidth().padding(70.dp),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = Color.Black,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
            ))
        }
    }
}
