package dev.borisochieng.autocaretag.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.borisochieng.autocaretag.ui.manage.ManageScreen
import dev.borisochieng.autocaretag.ui.screens.AddScreen
import dev.borisochieng.autocaretag.ui.screens.HomeScreen
import dev.borisochieng.autocaretag.utils.animatedComposable

typealias ShouldScan = Boolean

@Composable
fun AppRoute(
    navActions: NavActions,
    navController: NavHostController,
    paddingValues: PaddingValues,
    scanNfc: (ShouldScan) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = AppRoute.HomeScreen.route,
        modifier = Modifier
            .padding(paddingValues)
    ) {
        composable(AppRoute.HomeScreen.route) {
            HomeScreen(
                onNavigateToScan = { scanNfc(true) },
                onNavigateToNotifications = { /*TODO*/ },
                onNavigateToClient = {
                    navActions.navigate(Screens.ClientDetailsScreen("client_id"))
                },
                clients = emptyList()
            )
        }
        composable(AppRoute.AddScreen.route) {
            AddScreen(
                onNavigateToScanTag = { /*TODO(Navigate to Scanning Screen)*/ },
                onNavigateUp = {
                    navController.navigateUp()
                }
            )


        }
        animatedComposable(AppRoute.AddRepairDetailsScreen.route) { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId") ?: ""
        }
        composable(AppRoute.ManageScreen.route) {
            ManageScreen()
        }
        animatedComposable(AppRoute.ClientDetailsScreen.route) {}
        animatedComposable(AppRoute.VehicleDetailsScreen.route) { backStackEntry ->
            val clientId = backStackEntry.arguments?.getString("clientId") ?: ""
        }
        animatedComposable(AppRoute.RepairHistoryScreen.route) { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId") ?: ""
        }
    }
}
