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
import com.javierf.planahorroapp.data.remote.dto.MemberDto
import com.javierf.planahorroapp.viewmodel.PaymentViewModel
import com.javierf.planahorroapp.ui.utils.formatMoneyInput
import com.javierf.planahorroapp.ui.utils.parseMoneyToDouble


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
    var amountText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar pago") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            // ------------------------------
            //      SELECT MIEMBRO
            // ------------------------------
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {

                OutlinedTextField(
                    value = selectedMember?.name ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Miembro") },
                    trailingIcon = {
                        Icon(
                            if (expanded) Icons.Filled.KeyboardArrowUp
                            else Icons.Filled.KeyboardArrowDown,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
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

            Spacer(modifier = Modifier.height(20.dp))

            // ------------------------------
            //        MONTO
            // ------------------------------
            OutlinedTextField(
                value = amountText,
                onValueChange = { newValue ->
                    amountText = formatMoneyInput(newValue)
                },
                label = { Text("Monto") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(30.dp))

            // ------------------------------
            //      BOTÓN REGISTRAR
            // ------------------------------
            Button(
                onClick = {
                    val amount = parseMoneyToDouble(amountText)

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
                    .height(55.dp)
            ) {
                Text("Registrar")
            }
        }
    }
}

