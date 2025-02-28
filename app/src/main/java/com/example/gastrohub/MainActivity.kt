package com.example.gastrohub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GastroHubApp() // Inicia la aplicación con la composición principal
        }
    }
}

@Composable
fun GastroHubApp() {
    // Estado para gestionar la pantalla actual
    var pantallaActual by remember { mutableStateOf("bienvenida") }

    // Estados para almacenar la información del usuario
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var eventoSeleccionado by remember { mutableStateOf("") }

    // Navegación entre pantallas según el estado actual
    when (pantallaActual) {
        "bienvenida" -> PantallaBienvenida { pantallaActual = "formulario" }
        "formulario" -> FormularioInscripcion { inputNombre, inputCorreo, inputEvento ->
            // Guardar los datos ingresados y cambiar a la pantalla de confirmación
            nombre = inputNombre
            correo = inputCorreo
            eventoSeleccionado = inputEvento
            pantallaActual = "confirmacion"
        }
        "confirmacion" -> PantallaConfirmacion(nombre, correo, eventoSeleccionado) {
            // Volver a la pantalla de bienvenida al confirmar
            pantallaActual = "bienvenida"
        }
    }
}

@Composable
fun PantallaBienvenida(onJoinClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Bienvenido a GastroHub",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = onJoinClick) {
            Text("Únete al Club") // Botón que cambia a la pantalla del formulario
        }
    }
}

@Composable
fun FormularioInscripcion(onSubmit: (String, String, String) -> Unit) {
    // Estados para almacenar la información del formulario
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var eventoSeleccionado by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Formulario de Inscripción", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            // Campo de entrada para el nombre
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            // Campo de entrada para el correo electrónico
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo Electrónico") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Opciones de selección de evento
        Text("Selecciona un evento gastronómico:")
        Column {
            RadioButtonOption("Cata de vinos", eventoSeleccionado) { eventoSeleccionado = it }
            RadioButtonOption("Taller de repostería", eventoSeleccionado) { eventoSeleccionado = it }
            RadioButtonOption("Cena gourmet", eventoSeleccionado) { eventoSeleccionado = it }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Botón para enviar el formulario
        Button(onClick = { onSubmit(nombre, correo, eventoSeleccionado) }) {
            Text("Enviar inscripción")
        }
    }
}

@Composable
fun RadioButtonOption(texto: String, selectedOption: String, onSelect: (String) -> Unit) {
    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
        // Botón de radio para seleccionar un evento
        RadioButton(
            selected = selectedOption == texto,
            onClick = { onSelect(texto) }
        )
        Text(texto)
    }
}

@Composable
fun PantallaConfirmacion(nombre: String, correo: String, evento: String, onExit: () -> Unit) {
    val contexto = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Inscripción Confirmada",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Muestra los datos ingresados por el usuario
        Text("Nombre: $nombre")
        Text("Correo: $correo")
        Text("Evento seleccionado: $evento")

        Spacer(modifier = Modifier.height(20.dp))

        // Botón para volver a la pantalla de bienvenida
        Button(onClick = { onExit() }) {
            Text("Volver al inicio")
        }
    }
}
// Vistas previas para comprobar el diseño en el editor
@Preview(showBackground = true)
@Composable
fun PreviewPantallaBienvenida() {
    PantallaBienvenida {}
}

@Preview(showBackground = true)
@Composable
fun PreviewFormularioInscripcion() {
    FormularioInscripcion { _, _, _ -> }
}

@Preview(showBackground = true)
@Composable
fun PreviewPantallaConfirmacion() {
    PantallaConfirmacion("Juan Pérez", "juan@example.com", "Cata de vinos") {}
}


