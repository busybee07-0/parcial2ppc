package com.javierf.planahorroapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.javierf.planahorroapp.data.remote.dto.MemberDto
import com.javierf.planahorroapp.viewmodel.PaymentViewModel
import com.javierf.planahorroapp.ui.utils.formatMoneyNumber
import com.javierf.planahorroapp.ui.utils.parseFormattedNumberToDouble
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterPaymentScreen(
    members: List<MemberDto>,
    planId: String,
    viewModel: PaymentViewModel,
    onPaymentRegistered: () -> Unit,
    onBack: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedMember by remember { mutableStateOf<MemberDto?>(null) }
    var cleanNumber by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Registrar pago",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        containerColor = Color(0xFFF5F5F5)   // MISMO FONDO QUE CREAR PLAN
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // CARD CENTRAL (MISMO DISEÑO QUE CREAR PLAN)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {

                Column(modifier = Modifier.padding(20.dp)) {

                    // -----------------------------------
                    //             MIEMBRO
                    // -----------------------------------
                    Text(
                        "Miembro",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {

                        OutlinedTextField(
                            value = selectedMember?.name ?: "",
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFFCCCCCC),
                                focusedBorderColor = Color(0xFF00A859)
                            ),
                            trailingIcon = {
                                Icon(
                                    if (expanded) Icons.Filled.KeyboardArrowUp
                                    else Icons.Filled.KeyboardArrowDown,
                                    contentDescription = null
                                )
                            }
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            members.forEach { member ->
                                DropdownMenuItem(
                                    text = { Text(member.name) },
                                    onClick = {
                                        selectedMember = member
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(22.dp))

                    // -----------------------------------
                    //              MONTO
                    // -----------------------------------
                    Text(
                        "Monto",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )

                    OutlinedTextField(
                        value = cleanNumber,
                        onValueChange = { cleanNumber = formatMoneyNumber(it) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        leadingIcon = {
                            Text(
                                "$",
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xFFCCCCCC),
                            focusedBorderColor = Color(0xFF00A859)
                        )
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // -----------------------------------
                    //         BOTÓN REGISTRAR
                    // -----------------------------------
                    Button(
                        onClick = {
                            val amount = parseFormattedNumberToDouble(cleanNumber)

                            if (selectedMember != null && amount != null) {
                                viewModel.registerPayment(
                                    memberId = selectedMember!!._id,
                                    planId = planId,
                                    amount = amount,
                                    onSuccess = onPaymentRegistered
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00A859),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            "Registrar",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 18.sp
                            )
                        )
                    }
                }
            }
        }
    }
}
