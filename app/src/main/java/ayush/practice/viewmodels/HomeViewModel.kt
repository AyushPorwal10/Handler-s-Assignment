package ayush.practice.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ayush.practice.repository.HomeRepository
import ayush.practice.room.FavouriteRecipeData
import ayush.practice.util.UiState
import com.example.network.models.BaseRecipe
import com.example.network.models.PopularRecipeDetails
import com.example.network.models.SearchRecipeDetails
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import okhttp3.internal.userAgent
import javax.inject.Inject


@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    val tag = "HomeViewModel"

    private val _popularRecipesUiState = MutableStateFlow<UiState<List<PopularRecipeDetails>>>(UiState.Idle)
    val popularRecipesUiState : StateFlow<UiState<List<PopularRecipeDetails>>> = _popularRecipesUiState.asStateFlow()




    private val _searchResultUiState = MutableStateFlow<UiState<List<SearchRecipeDetails>>>(UiState.Idle)
    val searchResultUiState : StateFlow<UiState<List<SearchRecipeDetails>>> = _searchResultUiState.asStateFlow()


    private val _savingRecipeUiState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val savingRecipeUiState : StateFlow<UiState<Unit>> = _savingRecipeUiState.asStateFlow()


    private val _searchText = MutableStateFlow("")


    init {
        Log.d(tag , "HomeViewModel initialized")

        viewModelScope.launch {
            _searchText.debounce(500)
                .filter { it.isNotEmpty() && it.isNotBlank() && it.length >= 3} // allowing user to enter atleast three characters
                .flatMapLatest {
                    homeRepository.getSearchRecipes(it)
                }
                .collect{
                    _searchResultUiState.value = it
                }
        }
    }

    fun getPopularRecipes() {

        if(popularRecipesUiState.value is UiState.Success){
            Log.d(tag , "Preventing multiple api calls")
            return
        }

        viewModelScope.launch {
            homeRepository.getPopularRecipes().collect{
                _popularRecipesUiState.value = it
            }
        }
    }


    fun onSearchTextChange(searchText : String){
        _searchText.value = searchText
    }


    fun saveFavouriteRecipe(recipeDetails: PopularRecipeDetails){

        viewModelScope.launch {
            homeRepository.saveFavouriteRecipe(recipeDetails, onStateChange = {
                _savingRecipeUiState.value = it
            })
        }
    }

    fun resetSavingRecipeUiState(){
        _savingRecipeUiState.value = UiState.Idle
    }


}