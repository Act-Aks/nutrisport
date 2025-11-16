package com.actaks.nutirsport.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.actaks.nutrisport.auth.AuthScreen
import com.actaks.nutrisport.home.HomeGraph
import com.actaks.nutrisport.profile.ProfileScreen
import com.actaks.nutrisport.shared.navigation.Screen

@Composable
fun NavGraph(
    startDestination: Screen = Screen.Auth
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screen.Auth> {
            AuthScreen(
                navigateToHome = {
                    navController.navigate(Screen.HomeGraph) {
                        popUpTo<Screen.Auth> { inclusive = true }
                    }
                }
            )
        }
        composable<Screen.HomeGraph> {
            HomeGraph(
                navigateToAuth = {
                    navController.navigate(Screen.Auth) {
                        popUpTo<Screen.HomeGraph> { inclusive = true }
                    }
                },
                navigateToProfile = {
                    navController.navigate(Screen.Profile)
                }
            )
        }
        composable<Screen.Profile> {
            ProfileScreen()
        }
    }
}