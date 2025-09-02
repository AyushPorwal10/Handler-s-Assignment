package ayush.practice.navigation


sealed class FavouritesRoutes(val route : String){
    object FavouriteScreen : HomeRoutes("favourite_recipe")
    object FavouriteDetailsScreen : HomeRoutes("favourite_recipe_details")

}