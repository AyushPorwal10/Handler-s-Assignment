package ayush.practice.navigation

import ayush.practice.R

sealed class BottomNavRoutes(val route : String, val selectedIcon : Int , val unselectedIcon : Int , val label : String){
    object HomeTab : BottomNavRoutes("homeTab",R.drawable.filled_home_tab, R.drawable.home_tab,"Home")
    object FavouriteTab : BottomNavRoutes("favouriteTab" ,R.drawable.filled_favourite_tab, R.drawable.favourite_tab, "Favourite")
}