package ayush.foodie.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ayush.foodie.domain.model.Recipe
import ayush.foodie.repository.Repository
import ayush.foodie.util.PAGE_SIZE
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
    val page = mutableStateOf(1)
    private var recipeListScrollPosition = 0


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

    fun nextPage() {
        viewModelScope.launch {
            Log.d(TAG, "nextPage: $recipeListScrollPosition")
            if (recipeListScrollPosition + 1 >= (PAGE_SIZE * page.value)) {
                loading.value = true
                incrementPage()
                Log.d(TAG, "nextPage: ${page.value}")
                val result = repository.searchRecipes(
                    token = token,
                    page = 1,
                    query = query.value,
                )
                appendRecipes(result)
                loading.value = false
            }
        }
    }

    private fun appendRecipes(recipes: List<Recipe>) {
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value = current
    }

    private fun incrementPage() {
        page.value = page.value + 1
    }

    fun onChangeListScrollPosition(position: Int) {
        recipeListScrollPosition = position
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChanged(category)
    }

    private fun onQueryChanged(category: String) {
        query.value = category
    }

    fun onCategoryScrollPositionChanged(position: Float) {
        categoryScrollPosition = position
    }

    fun onQueryValueChanged(string: String) {
        query.value = string
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }

    private fun resetSearchState() {
        recipes.value = listOf()
        page.value = 1
        recipeListScrollPosition = 0
        if (selectedCategory.value?.value != query.value) clearSelectedCategory()
    }


}