package ayush.foodie.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ayush.foodie.presentation.BaseApplication
import ayush.foodie.presentation.components.ViewRecipe
import ayush.foodie.presentation.components.ViewRecipeShimmer
import ayush.foodie.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {

            setContent {

                val recipeId = arguments?.getInt("recipeId")
                val recipe = viewModel.recipe.value
                recipeId?.let {
                    viewModel.setRecipeId(it)
                }

                AppTheme(darkTheme = application.isDarkTheme.value) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = MaterialTheme.colors.background),
                    ) {

                        if (viewModel.loading.value) {
                            ViewRecipeShimmer(
                                cardHeight = 250.dp
                            )
                        } else {
                            recipe?.let {
                                ViewRecipe(recipe = recipe)
                            }
                        }

                    }
                }

            }
        }
    }


}