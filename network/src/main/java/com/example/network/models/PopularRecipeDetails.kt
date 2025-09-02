package com.example.network.models





interface BaseRecipe {
    val id : Long
    val title : String
    val image : String
    val readyInMinutes : Int
}


data class PopularRecipesResponse(
    val recipes: List<PopularRecipeDetails>
)

data class PopularRecipeDetails(
   override val title: String,
    override val id: Long,
   override val image: String,
   override val readyInMinutes : Int ,
) : BaseRecipe



