package com.javierf.planahorroapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color

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
                .fillMaxSize()
        ) {

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {

                // ---------------------------------------------------------
                // INFO DEL PLAN
                // ---------------------------------------------------------
                item {
                    plan.value?.let { p ->
                        Text(
                            text = p.name,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("${p.months} meses", fontSize = 16.sp)
                        Text("Meta: ${formatMoney(p.targetAmount)}", fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    // ---------------------------------------------------------
                    // MIEMBROS
                    // ---------------------------------------------------------
                    Text(
                        text = "Miembros",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(members.value) { member ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Miembro",
                                tint = Color(0xFF2E7D32),
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(member.name, fontSize = 17.sp)
                        }
                    }
                }

                // ---------------------------------------------------------
                // PAGOS
                // ---------------------------------------------------------
                item {
                    Spacer(modifier = Modifier.height(28.dp))
                    Text(
                        text = "Pagos registrados",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(payments.value) { payment ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(Modifier.padding(14.dp)) {
                            Text(
                                text = formatMoney(payment.amount),
                                color = Color(0xFF2E7D32),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = formatDate(payment.date),
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            // ---------------------------------------------------------
            // BOTÓN REGISTRAR PAGO (FIJO Y SIN CORTARSE)
            // ---------------------------------------------------------
            Button(
                onClick = onRegisterPayment,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E7D32)
                )
            ) {
                Text(
                    text = "Registrar pago",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
    }
}


