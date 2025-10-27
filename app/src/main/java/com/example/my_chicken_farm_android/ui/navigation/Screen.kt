package com.example.my_chicken_farm_android.ui.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object AyamIndukanList : Screen("ayam_indukan_list")
    object AyamIndukanForm : Screen("ayam_indukan_form/{id}") {
        fun createRoute(id: String = "new") = "ayam_indukan_form/$id"
    }
    object BreedingList : Screen("breeding_list")
    object BreedingForm : Screen("breeding_form/{id}") {
        fun createRoute(id: String = "new") = "breeding_form/$id"
    }
    object AyamAnakanList : Screen("ayam_anakan_list")
    object AyamAnakanForm : Screen("ayam_anakan_form/{id}") {
        fun createRoute(id: String = "new") = "ayam_anakan_form/$id"
    }
}
