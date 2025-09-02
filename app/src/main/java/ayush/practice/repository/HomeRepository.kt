package ayush.practice.repository

import android.util.Log
import androidx.collection.emptyIntSet
import ayush.practice.room.FavouriteRecipeDao
import ayush.practice.room.FavouriteRecipeData
import ayush.practice.room.toFavouriteRecipe
import ayush.practice.util.UiState
import com.example.network.apiservice.RecipeApiService
import com.example.network.models.BaseRecipe
import com.example.network.models.PopularRecipeDetails
import com.example.network.util.GetMessageFromResponseCode
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val recipeApiService: RecipeApiService,
    private val favouriteRecipeDao: FavouriteRecipeDao
) {

    val tag = "HomeRepository"

    fun getPopularRecipes() = flow {
        emit(UiState.Loading)

        try {
            val response = recipeApiService.getPopularRecipes()
            val responseCode = response.code()

            Log.d(tag , "Popular recipe Response code is $responseCode")

            if (response.isSuccessful && response.body() != null) {
                val result = response.body()!!.recipes
                Log.d(tag , "Popular Recipes Success $result")

                emit(UiState.Success(result))
            } else {
                val message = GetMessageFromResponseCode(responseCode)
                emit(UiState.Error(message))
            }
        } catch (e: Exception) {
            emit(UiState.Error("Something went wrong"))
        }
    }


    fun getSearchRecipes(query : String) = flow {
        emit(UiState.Loading)
        try {
            val response = recipeApiService.getSearchRecipes(query = query)
            val responseCode = response.code()
            Log.d(tag , "Search Recipe Response code is $responseCode")

            if (response.isSuccessful && response.body() != null) {
                val result = response.body()!!.results
                Log.d(tag , "Search Recipes Success $result")
                emit(UiState.Success(result))
            }
            else {
                val message = GetMessageFromResponseCode(responseCode)

                emit(UiState.Error(message))
            }
        }
        catch (exception : Exception){
            Log.d(tag , "Error searching ${exception.localizedMessage}")
            emit(UiState.Error("Something went wrong"))
        }
    }


   suspend fun getFavouriteRecipes(onStateChange : (UiState<List<BaseRecipe>>) -> Unit) {

       onStateChange(UiState.Loading)
        try {

            favouriteRecipeDao.getAllFavouriteRecipes().collect { list ->
                Log.d(tag , "Favourite Recipes $list")
                onStateChange(UiState.Success(list))
            }
        }
        catch (exception : Exception){
            Log.d(tag , "Error getting favourite recipes ${exception.localizedMessage}")
            onStateChange(UiState.Error("Something went wrong"))
        }
    }

   suspend fun saveFavouriteRecipe(recipeDetails: PopularRecipeDetails, onStateChange: (UiState<Unit>) -> Unit){
       onStateChange(UiState.Loading)
        try {

            // Converting to FavouriteRecipeData
            val favouriteRecipeData = recipeDetails.toFavouriteRecipe()

            favouriteRecipeDao.saveRecipe(favouriteRecipeData)
            onStateChange(UiState.Success(Unit))
        }
        catch (exception : Exception){
            Log.d(tag , "Error saving ${exception.localizedMessage}")
            onStateChange(UiState.Error("Error while saving Recipe"))
        }
    }

    suspend fun unsaveFavouriteRecipe(id : Long, onStateChange: (UiState<Unit>) -> Unit){
        onStateChange(UiState.Loading)
        try {

            favouriteRecipeDao.unsaveRecipe(id)
            onStateChange(UiState.Success(Unit))
        }
        catch (exception : Exception){
            Log.d(tag , "Error unsaving ${exception.localizedMessage}")
            onStateChange(UiState.Error("Error while unsaving Recipe"))
        }
    }
}