package ayush.practice.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [FavouriteRecipeData::class] , version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun recipeDao() : FavouriteRecipeDao

}