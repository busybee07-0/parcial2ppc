package com.javierf.planahorroapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.javierf.planahorroapp.ui.screens.PlanDetailScreen
import com.javierf.planahorroapp.ui.screens.PlansScreen
import com.javierf.planahorroapp.ui.screens.RegisterPaymentScreen
import com.javierf.planahorroapp.viewmodel.PaymentViewModel
import com.javierf.planahorroapp.viewmodel.PlanDetailViewModel
import com.javierf.planahorroapp.viewmodel.PlansViewModel
import com.javierf.planahorroapp.ui.screens.CreatePlanScreen


@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "plans"
    ) {

        //---------------------------------------------------------
        // 1. LISTA DE PLANES
        //---------------------------------------------------------
        composable("plans") { backStackEntry ->

            val vm: PlansViewModel = viewModel(
                viewModelStoreOwner = backStackEntry
            )

            LaunchedEffect(Unit) {
                vm.loadPlans()
            }

            PlansScreen(
                viewModel = vm,
                onPlanClick = { planId ->
                    navController.navigate("planDetail/$planId")
                },
                onCreatePlan = {
                    navController.navigate("createPlan")
                }
            )
        }

        //---------------------------------------------------------
        // 2. DETALLE DE PLAN
        //---------------------------------------------------------
        composable("planDetail/{planId}") { backStackEntry ->

            val planId = backStackEntry.arguments?.getString("planId") ?: ""

            val vm: PlanDetailViewModel = viewModel(
                viewModelStoreOwner = backStackEntry
            )

            LaunchedEffect(planId) {
                vm.loadPlan(planId)
            }

            PlanDetailScreen(
                viewModel = vm,
                onBack = { navController.popBackStack() },
                onRegisterPayment = {
                    navController.navigate("registerPayment/$planId")
                }
            )
        }

        //---------------------------------------------------------
        // 3. CREAR PLAN (NUEVA PANTALLA)
        //---------------------------------------------------------
        composable("createPlan") {

            CreatePlanScreen(
                onBack = { navController.popBackStack() },
                onPlanCreated = { name, motive, target, months, members ->

                    // Cuando agregues tu backend aquí lo conectas:
                    // viewModel.createPlan(...)

                    navController.popBackStack()
                }
            )
        }

        //---------------------------------------------------------
        // 4. REGISTRAR PAGO
        //---------------------------------------------------------
        composable("registerPayment/{planId}") { backStackEntry ->

            val planId = backStackEntry.arguments?.getString("planId") ?: ""

            // 🔥 Obtenemos el backstack de la pantalla de detalle
            val planDetailEntry = remember(backStackEntry) {
                navController.getBackStackEntry("planDetail/{planId}")
            }

            val detailVM: PlanDetailViewModel = viewModel(
                viewModelStoreOwner = planDetailEntry
            )

            val paymentVM: PaymentViewModel = viewModel(
                viewModelStoreOwner = backStackEntry
            )

            val members = detailVM.members.collectAsState().value

            RegisterPaymentScreen(
                members = members,
                planId = planId,
                viewModel = paymentVM,

                onPaymentRegistered = {
                    navController.popBackStack()
                    detailVM.loadPlan(planId) // 🔥 Recargar
                },

                onBack = { navController.popBackStack() }
            )
        }
    }
}

