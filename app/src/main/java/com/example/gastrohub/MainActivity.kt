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
            GastroHubApp()
        }
    }
}

@Composable
fun GastroHubApp() {
    var pantallaActual by remember { mutableStateOf("bienvenida") }

    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var eventoSeleccionado by remember { mutableStateOf("") }

    when (pantallaActual) {
        "bienvenida" -> PantallaBienvenida { pantallaActual = "formulario" }
        "formulario" -> FormularioInscripcion { inputNombre, inputCorreo, inputEvento ->
            nombre = inputNombre
            correo = inputCorreo
            eventoSeleccionado = inputEvento
            pantallaActual = "confirmacion"
        }
        "confirmacion" -> PantallaConfirmacion(nombre, correo, eventoSeleccionado) {
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
            Text("Únete al Club")
        }
    }
}

@Composable
fun FormularioInscripcion(onSubmit: (String, String, String) -> Unit) {
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
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo Electrónico") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text("Selecciona un evento gastronómico:")
        Column {
            RadioButtonOption("Cata de vinos", eventoSeleccionado) { eventoSeleccionado = it }
            RadioButtonOption("Taller de repostería", eventoSeleccionado) { eventoSeleccionado = it }
            RadioButtonOption("Cena gourmet", eventoSeleccionado) { eventoSeleccionado = it }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { onSubmit(nombre, correo, eventoSeleccionado) }) {
            Text("Enviar inscripción")
        }
    }
}

@Composable
fun RadioButtonOption(texto: String, selectedOption: String, onSelect: (String) -> Unit) {
    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
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
        Text("Nombre: $nombre")
        Text("Correo: $correo")
        Text("Evento seleccionado: $evento")

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { onExit() }) {
            Text("Volver al inicio")
        }
    }
}
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


