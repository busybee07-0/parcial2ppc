package com.javierf.planahorroapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.javierf.planahorroapp.ui.utils.formatMoneyNumber
import com.javierf.planahorroapp.ui.utils.parseFormattedNumberToDouble
import com.javierf.planahorroapp.viewmodel.CreatePlanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePlanScreen(
    viewModel: CreatePlanViewModel,
    onBack: () -> Unit
) {

    var motive by remember { mutableStateOf("") }
    var targetAmount by remember { mutableStateOf("") }
    var months by remember { mutableStateOf("") }
    var members by remember { mutableStateOf("") }

    val loading = viewModel.loading.collectAsState().value
    val error = viewModel.error.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear plan") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {

            //---------------------------------------------------
            // ERROR (si ocurre)
            //---------------------------------------------------
            if (error != null) {
                Text(
                    text = "Error: $error",
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            //---------------------------------------------------
            // Campo: Motivo / Nombre
            //---------------------------------------------------
            OutlinedTextField(
                value = motive,
                onValueChange = { motive = it },
                label = { Text("Motivo / Nombre del plan") },
                enabled = !loading,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            //---------------------------------------------------
            // Campo: Meta formateada
            //---------------------------------------------------
            OutlinedTextField(
                value = "$" + formatMoneyNumber(targetAmount),
                onValueChange = { newText ->
                    val clean = newText.replace("$", "").trim()
                    targetAmount = clean
                },
                label = { Text("Meta") },
                prefix = { Text("$") },
                enabled = !loading,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            //---------------------------------------------------
            // Campo: Meses
            //---------------------------------------------------
            OutlinedTextField(
                value = months,
                onValueChange = { months = it },
                label = { Text("Meses") },
                enabled = !loading,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            //---------------------------------------------------
            // Campo: Miembros separados por coma
            //---------------------------------------------------
            OutlinedTextField(
                value = members,
                onValueChange = { members = it },
                label = { Text("Miembros (separados por coma)") },
                enabled = !loading,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(30.dp))

            //---------------------------------------------------
            // Botón CREAR
            //---------------------------------------------------
            Button(
                onClick = {

                    val meta = parseFormattedNumberToDouble(targetAmount)
                    val mesesInt = months.toIntOrNull()
                    val membersList = members.split(",")
                        .map { it.trim() }
                        .filter { it.isNotEmpty() }

                    if (motive.isNotBlank() && meta > 0 && mesesInt != null && membersList.isNotEmpty()) {

                        viewModel.createPlan(
                            name = motive,
                            motive = motive,
                            targetAmount = meta,
                            months = mesesInt,
                            members = membersList
                        )
                    }
                },
                enabled = !loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(28.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Crear")
                }
            }
        }
    }
}
