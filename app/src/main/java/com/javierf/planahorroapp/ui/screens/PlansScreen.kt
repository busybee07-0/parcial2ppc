package com.javierf.planahorroapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.javierf.planahorroapp.viewmodel.PlansViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlansScreen(
    viewModel: PlansViewModel,
    onPlanClick: (String) -> Unit
) {
    // 🔥 AHORA SÍ recolecta el estado del ViewModel
    val plans = viewModel.plans.collectAsState()
    val loading = viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Plan Familiar de Ahorro") },
                actions = {
                    IconButton(onClick = { viewModel.loadPlans() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Actualizar")
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

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Planes de ahorro",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 🔥 Carga o lista
            if (loading.value) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn {
                    items(plans.value) { plan ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable { onPlanClick(plan._id) },
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {

                            Column(Modifier.padding(16.dp)) {

                                Text(
                                    plan.name,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text("Meta: ${plan.targetAmount}")
                                Text("${plan.months} meses")
                            }
                        }
                    }
                }
            }
        }
    }
}
