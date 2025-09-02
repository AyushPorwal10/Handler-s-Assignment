package ayush.practice.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ayush.practice.repository.HomeRepository
import ayush.practice.room.FavouriteRecipeData
import ayush.practice.util.UiState
import com.example.network.models.BaseRecipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavouriteRecipeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel(){

    val tag = "FavouriteRecipeViewModel"
    init {
        Log.d(tag , "Favourite Recipe ViewModel initialized")
    }

    private val _getAllFavouriteRecipesUiState = MutableStateFlow<UiState<List<BaseRecipe>>>(UiState.Idle)
    val getAllFavouriteRecipesUiState : StateFlow<UiState<List<BaseRecipe>>> = _getAllFavouriteRecipesUiState.asStateFlow()

    private val _unSavingRecipeUiState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val unSavingRecipeUiState : StateFlow<UiState<Unit>> = _unSavingRecipeUiState.asStateFlow()


    fun getFavouriteRecipe() {
        viewModelScope.launch {
            homeRepository.getFavouriteRecipes(onStateChange = {
                _getAllFavouriteRecipesUiState.value = it
            })
        }
    }

    fun unsaveFavouriteRecipe(id : Long){
        viewModelScope.launch {
            homeRepository.unsaveFavouriteRecipe(id, onStateChange = {
                _unSavingRecipeUiState.value = it
            })
        }
    }


    fun resetUnSavingRecipeUiState(){
        _unSavingRecipeUiState.value = UiState.Idle
    }

}