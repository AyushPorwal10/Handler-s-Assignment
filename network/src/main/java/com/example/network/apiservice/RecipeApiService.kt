package com.example.network.apiservice

import com.example.network.models.PopularRecipesResponse
import com.example.network.models.SearchRecipeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface RecipeApiService{

    // Fetching 10 Popular Recipes
    @GET("recipes/random")
    suspend fun getPopularRecipes(
        @Query("number")number : Int = 10  // Number of recipes to be fetched
    ) : Response<PopularRecipesResponse>


    // Default list of all recipes
//
//    @GET("recipes/complexSearch")
//    suspend fun getAllRipes(
//        @Query("number")number : Int = 1
//    ) : Response<AllRecipeResponse>


    // when user search
    @GET("recipes/complexSearch")
    suspend fun getSearchRecipes(
        @Query("number")number : Int = 10,
        @Query("query")query : String
    )  : Response<SearchRecipeResponse>
}