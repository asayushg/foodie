package ayush.foodie.presentation.ui.recipe

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import ayush.foodie.repository.Repository

class RecipeViewModel
@ViewModelInject
constructor(
    private val repository: Repository,
    private val token: String
) : ViewModel() {

}