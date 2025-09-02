package ayush.practice.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ayush.practice.R
import ayush.practice.navigation.BottomNavRoutes
import ayush.practice.navigation.FavouritesRoutes
import ayush.practice.navigation.HomeRoutes
import ayush.practice.ui.theme.AppPrimaryColor

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DashBoardScreen(
        navController: NavController,
        currentRoute: String,
        content: @Composable (PaddingValues) -> Unit = {}
    ) {


        val bottomNavButton = listOf(
            BottomNavRoutes.HomeTab,
            BottomNavRoutes.FavouriteTab,
        )

        val selectedItemRoute = when (currentRoute) {
            HomeRoutes.HomeScreen.route -> BottomNavRoutes.HomeTab.route
            FavouritesRoutes.FavouriteScreen.route -> BottomNavRoutes.FavouriteTab.route
            else -> ""
        }


        val topBarHeading =
            if (currentRoute == HomeRoutes.HomeScreen.route) stringResource(R.string.greeting) else stringResource(
                R.string.favourite_recipes
            )

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = topBarHeading,
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.Black
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White
                    )
                )
            },
            bottomBar = {
                BottomAppBar(containerColor = Color.White) {
                    bottomNavButton.forEach { item ->

                        NavigationBarItem(
                            selected = selectedItemRoute == item.route,
                            icon = {
                                Icon(
                                    painter = if(selectedItemRoute == item.route) painterResource(id = item.selectedIcon) else painterResource(id = item.unselectedIcon),
                                    contentDescription = null,
                                    modifier = Modifier.size(26.dp),
                                    tint = if (selectedItemRoute == item.route) AppPrimaryColor else Color.Gray,
                                    )
                            }, onClick = {
                                if (selectedItemRoute != item.route) {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            label = {
                                Text(item.label, fontWeight = FontWeight.Bold,
                                    color = if (selectedItemRoute == item.route) AppPrimaryColor else Color.Gray)
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent
                            )
                        )

                    }
                }
            }
        ) { paddingValues ->
            content(paddingValues)
        }
    }
