package ayush.practice.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.network.models.BaseRecipe
import com.example.network.models.PopularRecipeDetails


@Entity
data class FavouriteRecipeData(
    @PrimaryKey(autoGenerate = true)
    override val id: Long = 0L,
    override val title: String,
    override val image: String,
    override val readyInMinutes: Int,
) : BaseRecipe


fun PopularRecipeDetails.toFavouriteRecipe(): FavouriteRecipeData {
    return FavouriteRecipeData(
        id = this.id,
        title = this.title,
        image = this.image,
        readyInMinutes = this.readyInMinutes

    )
}



