package ayush.foodie.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ayush.foodie.domain.model.Recipe
import ayush.foodie.repository.Repository
import ayush.foodie.util.PAGE_SIZE
import ayush.foodie.util.TAG
import kotlinx.coroutines.launch

const val SAVE_STATE_QUERY = "foodie.state.query"
const val SAVE_STATE_CATEGORY = "foodie.state.category"
const val SAVE_STATE_CATEGORY_SCROLL = "foodie.state.category.scroll"
const val SAVE_STATE_LIST_PAGE = "foodie.state.list.page"
const val SAVE_STATE_LIST_SCROLL = "foodie.state.lst.scroll"

class RecipeListViewModel
@ViewModelInject
constructor(
    private val repository: Repository,
    private val token: String,
    @Assisted private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val query = mutableStateOf("")
    val loading = mutableStateOf(false)
    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    var categoryScrollPosition: Float = 0f
    val page = mutableStateOf(1)
    private var recipeListScrollPosition = 0


    init {

        savedStateHandle.get<Int>(SAVE_STATE_LIST_PAGE)?.let {
            page.value = it
            Log.d(TAG, "page: $it")
        }

        savedStateHandle.get<Float>(SAVE_STATE_CATEGORY_SCROLL)?.let {
            categoryScrollPosition = it
        }

        savedStateHandle.get<Int>(SAVE_STATE_LIST_SCROLL)?.let {
            recipeListScrollPosition = it
        }

        savedStateHandle.get<String>(SAVE_STATE_QUERY)?.let {
            query.value = it
        }

        savedStateHandle.get<FoodCategory>(SAVE_STATE_CATEGORY)?.let {
            selectedCategory.value = it
        }

        if (recipeListScrollPosition != 0) {
            restoreState()
        } else
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
            if (recipeListScrollPosition + 1 >= (PAGE_SIZE * page.value)) {
                loading.value = true
                incrementPage()
                val result = repository.searchRecipes(
                    token = token,
                    page = page.value,
                    query = query.value,
                )
                appendRecipes(result)
                loading.value = false
            }
        }
    }

    private fun restoreState() {
        // restore state after process death
        viewModelScope.launch {
            loading.value = true
            val results: MutableList<Recipe> = mutableListOf()
            for(p in 1..page.value) {
                val result = repository.searchRecipes(
                    token = token,
                    page = p,
                    query = query.value,
                )
                results.addAll(result)
                if (p == page.value){
                    recipes.value = results
                    loading.value = false
                }
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
        savePageToState(page.value)
    }

    fun onChangeListScrollPosition(position: Int) {
        recipeListScrollPosition = position
        saveListScrollToState()
    }


    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        saveCategoryToState()
        onQueryChanged(category)
    }

    private fun onQueryChanged(category: String) {
        query.value = category
        saveQueryToState(category)
    }

    fun onCategoryScrollPositionChanged(position: Float) {
        categoryScrollPosition = position
        savedStateHandle.set(SAVE_STATE_CATEGORY_SCROLL, position)
    }

    fun onQueryValueChanged(string: String) {
        query.value = string
        saveQueryToState(string)
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
        saveCategoryToState()
    }

    private fun resetSearchState() {
        recipes.value = listOf()
        page.value = 1
        savePageToState(page.value)
        recipeListScrollPosition = 0
        saveListScrollToState()
        if (selectedCategory.value?.value != query.value) clearSelectedCategory()
    }

    private fun saveCategoryToState() {
        savedStateHandle.set(SAVE_STATE_CATEGORY, selectedCategory.value)
    }

    private fun savePageToState(value: Int) {
        savedStateHandle.set(SAVE_STATE_LIST_PAGE, value)
    }

    private fun saveListScrollToState() {
        savedStateHandle.set(SAVE_STATE_LIST_SCROLL, recipeListScrollPosition)
    }

    private fun saveQueryToState(s: String) {
        savedStateHandle.set(SAVE_STATE_QUERY, s)
    }

}