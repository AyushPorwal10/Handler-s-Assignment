package com.example.network.models

data class SearchRecipeResponse(
    val results : List<SearchRecipeDetails>
)
data class SearchRecipeDetails(
    val id : Int ,
    val title : String ,
    val image : String ,
)