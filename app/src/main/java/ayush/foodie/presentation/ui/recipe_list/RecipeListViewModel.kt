package ayush.foodie.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ayush.foodie.domain.model.Recipe
import ayush.foodie.repository.Repository
import ayush.foodie.util.TAG
import kotlinx.coroutines.launch

class RecipeListViewModel
@ViewModelInject
constructor(
    private val repository: Repository,
    private val token: String
) : ViewModel() {

    val query = mutableStateOf("")
    val loading = mutableStateOf(false)
    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    var categoryScrollPosition: Float = 0f

    init {
        newSearch()
    }

    fun newSearch() {
        viewModelScope.launch {
            resetSearchState()
            loading.value = true
            val result = repository.searchRecipes(
                token = token,
                page = 1,
                query = query.value,
            )
           recipes.value = result
            loading.value = false
        }
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChanged(category)
    }

    private fun onQueryChanged(category: String) {
        query.value = category
    }

    fun onCategoryScrollPositionChanged(position: Float){
        categoryScrollPosition = position
    }

    fun onQueryValueChanged(string: String){
        query.value = string
    }

    private fun clearSelectedCategory(){
        selectedCategory.value = null
    }

    private fun resetSearchState(){
        recipes.value = listOf()
        if (selectedCategory.value?.value != query.value) clearSelectedCategory()
    }

}