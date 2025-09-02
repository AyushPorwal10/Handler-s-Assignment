package ayush.practice.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ayush.practice.R
import ayush.practice.util.Constants
import ayush.practice.util.UiState
import ayush.practice.util.searchTextFieldColors
import ayush.practice.viewmodels.HomeViewModel
import com.example.network.models.PopularRecipeDetails
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(paddingValues: PaddingValues, homeViewModel: HomeViewModel) {

    val popularRecipesUiState by homeViewModel.popularRecipesUiState.collectAsState()

    var queryText by remember { mutableStateOf("") }


    val searchResultUiState by homeViewModel.searchResultUiState.collectAsState()
    val savingRecipeUiState by homeViewModel.savingRecipeUiState.collectAsState() // to track if recipe is saved or not

    val context = LocalContext.current

    if(savingRecipeUiState is UiState.Success){
        Toast.makeText(context, "Recipe marked as Favourite", Toast.LENGTH_SHORT).show()
        homeViewModel.resetSavingRecipeUiState()
    }
    LaunchedEffect(Unit) {
        homeViewModel.getPopularRecipes()
    }

    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .padding(paddingValues)
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            SearchBar(queryText, onValueChange = {
                queryText = it
                homeViewModel.onSearchTextChange(queryText)
            })
        }

        if (queryText.isNotBlank()) {

            item { ShowHeading(stringResource(R.string.search_results)) }

            when (val state = searchResultUiState) {
                is UiState.Loading -> {
                    item { FullWidthLoader() }
                }

                is UiState.Success -> {
                    items(state.data) { recipe ->


                        SearchRecipeItem(recipe, onSaveRecipeButtonClick = {
                            val favouriteRecipe = PopularRecipeDetails(
                                id = recipe.id * 1L,
                                title = recipe.title,
                                image = recipe.image,
                                readyInMinutes = 10          // hardcoded because no such field in search response

                            )
                            homeViewModel.saveFavouriteRecipe(favouriteRecipe)
                        })
                    }
                }

                is UiState.Error -> {
                    item { ErrorMessage(state.message) }
                }

                else -> Unit
            }
        } else {

            item { ShowHeading(stringResource(R.string.popular_recipes)) }

            when (val state = popularRecipesUiState) {
                is UiState.Loading -> {
                    item { FullWidthLoader() }
                }

                is UiState.Success -> {
                    val popularRecipes = state.data
                    item {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            items(popularRecipes) { PopularRecipeItem(it) }
                        }
                    }
                }

                is UiState.Error -> {
                    item { ErrorMessage(state.message) }
                }

                else -> Unit
            }

            item { ShowHeading(stringResource(R.string.all_recipes)) }

            when (val state = popularRecipesUiState) {
                is UiState.Loading -> {
                    item { FullWidthLoader() }
                }

                is UiState.Success -> {
                    items(state.data) { recipe ->
                        RecipeItem(recipe, isSaved =  false, onFavouriteRecipeButtonClick = {
                            homeViewModel.saveFavouriteRecipe(recipe)
                        })
                    }
                }

                is UiState.Error -> {
                    item { ErrorMessage(state.message) }
                }

                else -> Unit
            }
        }
    }

}


@Composable
fun FullWidthLoader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 200.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator(modifier = Modifier.size(36.dp))
    }
}

@Composable
fun ErrorMessage(message: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 200.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(message, color = Color.Red, style = MaterialTheme.typography.bodyMedium)
    }
}


@Composable
fun ShowHeading(heading: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            heading, style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )
    }
}

@Composable
fun SearchBar(queryText: String, onValueChange : (String) -> Unit) {


    var currentPlaceHolder by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        repeat(Int.MAX_VALUE) {
            delay(3000)
            currentPlaceHolder = (currentPlaceHolder + 1) % Constants.listOfOptions.size
        }
    }

    OutlinedTextField(
        value = queryText, onValueChange = {
            onValueChange(it)
        }, colors = searchTextFieldColors(),
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.search_icon),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        },
        placeholder = {
            Text(Constants.listOfOptions[currentPlaceHolder])
        }, modifier = Modifier.fillMaxWidth()
    )
}
