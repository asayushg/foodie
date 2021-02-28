package ayush.foodie.presentation.ui.recipe_list


import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ayush.foodie.presentation.BaseApplication
import ayush.foodie.presentation.components.LoadingListShimmer
import ayush.foodie.presentation.components.RecipeCard
import ayush.foodie.presentation.components.SearchAppBar
import ayush.foodie.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                AppTheme(
                    darkTheme = application.isDarkTheme.value
                ) {
                    Column {
                        val recipes = viewModel.recipes.value

                        SearchAppBar(
                            query = viewModel.query.value,
                            onQueryValueChanged = viewModel::onQueryValueChanged,
                            onExecuteSearch = viewModel::newSearch,
                            scrollPosition = viewModel.categoryScrollPosition,
                            selectedCategory = viewModel.selectedCategory.value,
                            onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                            onCategoryScrollPositionChanged =
                            viewModel::onCategoryScrollPositionChanged,
                            onToggleTheme = application::toggleLightTheme
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = MaterialTheme.colors.background)
                        ) {
                            if (viewModel.loading.value) {
                                LoadingListShimmer(
                                    cardHeight = 250.dp
                                )
                            } else {
                                LazyColumn {
                                    itemsIndexed(
                                        items = recipes
                                    ) { index, recipe ->
                                        RecipeCard(recipe = recipe, onClick = { })
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}

