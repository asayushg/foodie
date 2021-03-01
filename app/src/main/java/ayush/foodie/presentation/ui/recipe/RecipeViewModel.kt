package ayush.foodie.presentation.ui.recipe

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ayush.foodie.domain.model.Recipe
import ayush.foodie.repository.Repository
import kotlinx.coroutines.launch


const val SAVE_STATE_RECIPE_ID = "foodie.state.recipe.id"

class RecipeViewModel
@ViewModelInject
constructor(
    private val repository: Repository,
    private val token: String,
    @Assisted private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var recipeId: Int = 0
    val recipe: MutableState<Recipe?> = mutableStateOf(null)
    val loading = mutableStateOf(false)

    init {
        savedStateHandle.get<Int>(SAVE_STATE_RECIPE_ID)?.let {
            setRecipeId(it)
        }
    }

    private fun getRecipe() {
        viewModelScope.launch {
            loading.value = true
            val result = repository.getRecipe(
                token = token,
                id = recipeId
            )
            recipe.value = result
            loading.value = false
        }
    }

    fun setRecipeId(id: Int) {
        recipeId = id
        savedStateHandle.set(SAVE_STATE_RECIPE_ID, id)
        getRecipe()
    }

}