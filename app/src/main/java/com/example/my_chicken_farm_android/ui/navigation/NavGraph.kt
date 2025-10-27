package com.example.my_chicken_farm_android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.my_chicken_farm_android.ui.screens.*

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(navController)
        }

        composable(Screen.AyamIndukanList.route) {
            AyamIndukanListScreen(navController)
        }

        composable(
            route = Screen.AyamIndukanForm.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: "new"
            AyamIndukanFormScreen(navController, id)
        }

        composable(Screen.BreedingList.route) {
            BreedingListScreen(navController)
        }

        composable(
            route = Screen.BreedingForm.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: "new"
            BreedingFormScreen(navController, id)
        }

        composable(Screen.AyamAnakanList.route) {
            AyamAnakanListScreen(navController)
        }

        composable(
            route = Screen.AyamAnakanForm.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: "new"
            AyamAnakanFormScreen(navController, id)
        }
    }
}
