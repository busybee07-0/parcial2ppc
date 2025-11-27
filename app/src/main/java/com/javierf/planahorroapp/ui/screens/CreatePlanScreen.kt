package com.javierf.planahorroapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.javierf.planahorroapp.ui.utils.formatMoneyNumber
import com.javierf.planahorroapp.ui.utils.parseFormattedNumberToDouble
import com.javierf.planahorroapp.viewmodel.CreatePlanViewModel
import androidx.compose.ui.graphics.Color

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
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Crear plan",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
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
                .fillMaxSize()
        ) {

            //---------------------------------------------------
            // ERROR
            //---------------------------------------------------
            if (error != null) {
                Text(
                    text = "Error: $error",
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            //---------------------------------------------------
            // TÍTULO SECCIÓN
            //---------------------------------------------------
            Text(
                "Información del plan",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            //---------------------------------------------------
            // CAMPO: Motivo / Nombre
            //---------------------------------------------------
            OutlinedTextField(
                value = motive,
                onValueChange = { motive = it },
                label = { Text("Motivo / Nombre del plan") },
                enabled = !loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            //---------------------------------------------------
            // CAMPO: Meta formateada
            //---------------------------------------------------
            OutlinedTextField(
                value = formatMoneyNumber(targetAmount),
                onValueChange = { newText ->
                    val clean = newText.replace("$", "").trim()
                    targetAmount = clean
                },
                label = { Text("Meta de ahorro") },
                prefix = { Text("$") },
                enabled = !loading,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            //---------------------------------------------------
            // CAMPO: Meses
            //---------------------------------------------------
            OutlinedTextField(
                value = months,
                onValueChange = { months = it },
                label = { Text("Duración (meses)") },
                enabled = !loading,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            //---------------------------------------------------
            // CAMPO: Miembros
            //---------------------------------------------------
            OutlinedTextField(
                value = members,
                onValueChange = { members = it },
                label = { Text("Miembros (separados por coma)") },
                enabled = !loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            //---------------------------------------------------
            // BOTÓN CREAR (VERDE MOCKUP)
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
                    .height(55.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF27AE60), // verde mockup
                    contentColor = Color.White
                )
            ) {

                if (loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(26.dp),
                        color = Color.White,
                        strokeWidth = 3.dp
                    )
                } else {
                    Text(
                        "Crear plan",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
