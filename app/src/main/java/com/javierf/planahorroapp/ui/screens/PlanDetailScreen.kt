package com.javierf.planahorroapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.Alignment
import com.javierf.planahorroapp.viewmodel.PlanDetailViewModel
import com.javierf.planahorroapp.ui.utils.formatMoney
import com.javierf.planahorroapp.ui.utils.formatDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanDetailScreen(
    viewModel: PlanDetailViewModel,
    onBack: () -> Unit,
    onRegisterPayment: () -> Unit
) {
    val plan = viewModel.plan.collectAsState()
    val members = viewModel.members.collectAsState()
    val payments = viewModel.payments.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detalle del Plan") },
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
                .padding(16.dp)
        ) {

            //-------------------------------------------------------------
            // TÍTULO DEL PLAN
            //-------------------------------------------------------------
            plan.value?.let { p ->
                Text(
                    text = p.name,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text("${p.months} meses", fontSize = 16.sp)
                Text("Meta: ${p.targetAmount}", fontSize = 16.sp)

                Spacer(modifier = Modifier.height(24.dp))
            }

            //-------------------------------------------------------------
            // MIEMBROS
            //-------------------------------------------------------------
            Text(
                text = "Miembros",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(members.value) { member ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Miembro",
                                modifier = Modifier.size(30.dp)
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(member.name, fontSize = 18.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            //-------------------------------------------------------------
            // PAGOS
            //-------------------------------------------------------------
            Text(
                text = "Pagos Registrados",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(payments.value) { payment ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text("Monto: ${formatMoney(payment.amount)}", fontSize = 18.sp)
                            Text("Fecha: ${formatDate(payment.date)}", fontSize = 14.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            //-------------------------------------------------------------
            // BOTÓN REGISTRAR PAGO
            //-------------------------------------------------------------
            Button(
                onClick = onRegisterPayment,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Registrar pago",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

