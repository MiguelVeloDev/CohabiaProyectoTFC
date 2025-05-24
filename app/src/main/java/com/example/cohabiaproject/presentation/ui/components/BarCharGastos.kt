package com.example.cohabiaproject.presentation.ui.components

import android.text.TextPaint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MiBarChart(
    datos: Map<String, Float>,
    modifier: Modifier = Modifier
) {
    val max = datos.values.maxOrNull() ?: 0f

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp) // aumentamos altura para dejar espacio a los textos
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        val barWidth = size.width / (datos.size * 2)
        val spaceBetween = barWidth

        val textPaint = TextPaint().apply {
            color = android.graphics.Color.BLACK
            textSize = 30f
            textAlign = android.graphics.Paint.Align.CENTER
        }

        datos.entries.forEachIndexed { index, entry ->
            val x = index * (barWidth + spaceBetween) + barWidth / 2
            val barHeight = if (max > 0f) (entry.value / max) * (size.height - 40.dp.toPx()) else 0f
            val top = size.height - barHeight - 40.dp.toPx()

            // Barra
            drawRect(
                color = Color(0xFF3F51B5),
                topLeft = Offset(x, top),
                size = Size(barWidth, barHeight)
            )

            // Valor encima de la barra
            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    "${entry.value.toInt()}€",
                    x + barWidth / 2,
                    top - 10,
                    textPaint
                )
            }

            // Nombre del mes debajo
            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    entry.key,
                    x + barWidth / 2,
                    size.height,
                    textPaint
                )
            }
        }
    }
}
