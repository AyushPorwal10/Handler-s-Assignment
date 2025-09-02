package ayush.practice.navigation

sealed class HomeRoutes(val route : String){
    object HomeScreen : HomeRoutes("home_recipes")
    object RecipeDetailsScreen : HomeRoutes("recipe_details")
}