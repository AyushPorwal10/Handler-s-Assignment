package ayush.practice.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ayush.practice.screens.DashBoardScreen
import ayush.practice.screens.FavouriteRecipeScreen
import ayush.practice.screens.HomeScreen
import ayush.practice.viewmodels.FavouriteRecipeViewModel
import ayush.practice.viewmodels.HomeViewModel

fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation(startDestination = HomeRoutes.HomeScreen.route, route = "homeTab") {
        composable(HomeRoutes.HomeScreen.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(navController.graph.id)
            }

            val homeViewModel = hiltViewModel<HomeViewModel>(parentEntry)

            DashBoardScreen(navController, HomeRoutes.HomeScreen.route){paddingValues->
                HomeScreen(paddingValues , homeViewModel)
            }
        }
    }


    // Favourite Tab
    navigation(startDestination = FavouritesRoutes.FavouriteScreen.route, route = "favouriteTab") {
        composable(FavouritesRoutes.FavouriteScreen.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(navController.graph.id)
            }

            val favouriteRecipeViewModel = hiltViewModel<FavouriteRecipeViewModel>(parentEntry)
            DashBoardScreen(navController, FavouritesRoutes.FavouriteScreen.route){paddingValues->
                FavouriteRecipeScreen(paddingValues,favouriteRecipeViewModel, navController)
            }
        }
    }

}