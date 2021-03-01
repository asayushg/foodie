package ayush.foodie.presentation.ui.recipe_list


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import ayush.foodie.R
import ayush.foodie.presentation.BaseApplication
import ayush.foodie.presentation.components.LoadingListShimmer
import ayush.foodie.presentation.components.RecipeCard
import ayush.foodie.presentation.components.SearchAppBar
import ayush.foodie.presentation.theme.AppTheme
import ayush.foodie.util.PAGE_SIZE
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
                            onToggleTheme = application::toggleLightTheme,
                            darkMode = application.isDarkTheme.value,
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = MaterialTheme.colors.background)
                        ) {
                            if (viewModel.loading.value && recipes.isEmpty()) {
                                LoadingListShimmer(
                                    cardHeight = 250.dp
                                )
                            } else {
                                LazyColumn {
                                    itemsIndexed(
                                        items = recipes
                                    ) { index, recipe ->
                                        viewModel.onChangeListScrollPosition(index)
                                        if ((index + 1) >= (viewModel.page.value * PAGE_SIZE) &&
                                            !viewModel.loading.value
                                        ) {
                                            viewModel.nextPage()
                                        }

                                        RecipeCard(recipe = recipe, onClick = {
                                            val bundle = bundleOf("recipeId" to recipe.id)
                                            view?.findNavController()
                                                ?.navigate(R.id.viewRecipe, bundle)
                                        })
                                        if (viewModel.loading.value && recipes.isNotEmpty()
                                            && index == recipes.size - 1
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(8.dp),
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                CircularProgressIndicator()
                                            }
                                        }
                                    }
                                }
                                if (recipes.isEmpty())
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(8.dp),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "No Results to show!",
                                            style = MaterialTheme.typography.h3,
                                        )
                                    }
                            }
                        }
                    }
                }

            }
        }
    }
}

