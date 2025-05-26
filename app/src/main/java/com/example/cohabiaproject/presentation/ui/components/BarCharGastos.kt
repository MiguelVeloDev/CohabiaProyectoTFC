package com.example.cohabiaproject.presentation.ui.components

import android.text.TextPaint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import com.example.cohabiaproject.ui.theme.AzulGastos

@Composable
fun MiBarChart(
    datos: Map<String, Float>,
    modifier: Modifier = Modifier
) {
    val max = datos.values.maxOrNull() ?: 0f

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 16.dp).padding(top = 35.dp).padding(bottom = 10.dp)
    ) {
        val barWidth = size.width / (datos.size * 3)
        val spaceBetween = barWidth*2

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
                color = Color(0xFF4C6EFC),
                topLeft = Offset(x, top),
                size = Size(barWidth, barHeight)
            )

            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    "${entry.value.toInt()}€",
                    x + barWidth / 2,
                    top - 10,
                    textPaint
                )
            }

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
