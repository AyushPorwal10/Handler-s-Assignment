package ayush.practice.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface FavouriteRecipeDao{


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRecipe(favouriteRecipeData: FavouriteRecipeData)

    @Query("SELECT * FROM FavouriteRecipeData")
    fun getAllFavouriteRecipes() : Flow<List<FavouriteRecipeData>>


    @Query("DELETE FROM FavouriteRecipeData WHERE id = :id")
    suspend fun unsaveRecipe(id: Long)
}