import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.domain.model.Tarea
import com.example.cohabiaproject.presentation.ui.components.TopAppBarConFlecha
import com.example.cohabiaproject.presentation.ui.viewmodel.TareaViewModel
import com.example.cohabiaproject.ui.theme.FondoTextField
import com.example.cohabiaproject.ui.theme.NaranjaPrincipal
import com.example.cohabiaproject.ui.theme.AzulTareas
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AnadirTarea(navController: NavController) {
    var contenido by remember { mutableStateOf("") }
    var fechaHora by remember { mutableStateOf(LocalDateTime.now()) }

    var recurrente by remember { mutableStateOf(false) }

    val tareasViewModel: TareaViewModel = koinViewModel()
    val context = LocalContext.current

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                fechaHora = fechaHora.withYear(year)
                    .withMonth(month + 1)
                    .withDayOfMonth(dayOfMonth)
                showDatePicker = false
            }, 
            fechaHora.year,
            fechaHora.monthValue - 1,
            fechaHora.dayOfMonth
        ).show()
    }

    if (showTimePicker) {
        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                fechaHora = fechaHora.withHour(hourOfDay)
                    .withMinute(minute)
                    .withSecond(0)
                    .withNano(0)
                showTimePicker = false
            },
            fechaHora.hour,
            fechaHora.minute,
            true
        ).show()
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBarConFlecha(
                titulo = "Añadir Tarea",
                navController = navController
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TextField(
                value = contenido,
                onValueChange = { contenido = it },
                label = { Text("Contenido") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = Color.Black,
                    unfocusedContainerColor = FondoTextField,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = FondoTextField
                ),
                singleLine = true
                )

            Spacer(modifier = Modifier.height(18.dp))

            OutlinedButton(
                onClick = { showDatePicker = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AzulTareas,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White,
                    ),
                enabled = !recurrente
            ) {
                Text("Seleccionar Fecha: ${fechaHora.dayOfMonth}/${fechaHora.monthValue}/${fechaHora.year}")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = { showTimePicker = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AzulTareas,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White),
                enabled = !recurrente
            ) {
                val horaFormateada = String.format("%02d:%02d", fechaHora.hour, fechaHora.minute)
                Text("Seleccionar Hora: $horaFormateada")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = recurrente,
                    onCheckedChange = { recurrente = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = AzulTareas,
                        uncheckedColor = AzulTareas
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Recurrente")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val nuevaTarea = Tarea(
                        contenido = contenido,
                        dia = if (!recurrente) fechaHora.dayOfMonth.toString() else null,
                        mes = if(!recurrente)fechaHora.monthValue else null,
                        año = if (!recurrente)fechaHora.year else null,
                        hora = if (!recurrente) String.format("%02d:%02d", fechaHora.hour, fechaHora.minute) else null,
                        usuario = Sesion.userId,
                        recurrente = recurrente
                    )
                    tareasViewModel.save(nuevaTarea)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = contenido.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor =NaranjaPrincipal,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White
                )
            ) {
                Text("Guardar Tarea")
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "Al marcar la tarea como recurrente, esta se quedará guardada lista para añadir cada vez que quieras, sin necesidad de crearla de nuevo.",
                    color = Color.Gray,
                    modifier = Modifier.padding(56.dp),
                    textAlign = TextAlign.Center,
                )
            }

        }
    }
}
