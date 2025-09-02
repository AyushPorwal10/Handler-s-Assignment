package ayush.practice.screens

import android.provider.Contacts.Intents.UI
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ayush.practice.util.UiState
import ayush.practice.viewmodels.FavouriteRecipeViewModel

@Composable
fun FavouriteRecipeScreen(
    paddingValues: PaddingValues,
    favouriteRecipeViewModel: FavouriteRecipeViewModel,
    navController: NavController
) {


    val unsaveRecipeUiState by favouriteRecipeViewModel.unSavingRecipeUiState.collectAsState()
    val context = LocalContext.current

    BackHandler {
        navController.popBackStack()
    }

    if(unsaveRecipeUiState is UiState.Success){
        Toast.makeText(context, "Recipe Removed from Favourite List", Toast.LENGTH_SHORT).show()
        favouriteRecipeViewModel.resetUnSavingRecipeUiState()
    }

    LaunchedEffect(Unit) {
        // fetching user saved recipes from room
        favouriteRecipeViewModel.getFavouriteRecipe()
    }


    val favouriteRecipeUiState by favouriteRecipeViewModel.getAllFavouriteRecipesUiState.collectAsState()

    when (val state = favouriteRecipeUiState) {
        is UiState.Loading -> {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues), contentAlignment = Alignment.Center){
                CircularProgressIndicator(modifier = Modifier.size(36.dp), color = Color(0xFFFF5722))
            }
        }

        is UiState.Success -> {
            val savedList = state.data
            Log.d("FavouriteRecipeScreen" , "In Composable Saved List $savedList")

            LazyColumn(modifier = Modifier
                .background(Color.White)
                .padding(paddingValues)
                .fillMaxSize()
                .padding(20.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                items(savedList){
                    Log.d("FavouriteRecipeScreen" , "Each item is $it")
                    RecipeItem(it, isSaved = true){
                        favouriteRecipeViewModel.unsaveFavouriteRecipe(it.id)
                    }
                }
            }
        }
        is UiState.Error -> {

        }
        else -> {

        }
    }
}