package ayush.foodie.repository

import android.util.Log
import ayush.foodie.domain.model.Recipe
import ayush.foodie.network.RecipeApi
import ayush.foodie.network.model.RecipeDtoMapper
import ayush.foodie.util.TAG

class RepositoryImpl(
    private val recipeApi: RecipeApi,
    private val mapper: RecipeDtoMapper
) : Repository {

    override suspend fun searchRecipes(token: String, page: Int, query: String): List<Recipe> {
        var recipes = listOf<Recipe>()
        try {
            recipes = mapper.toDomainList(
                recipeApi.searchRecipes(
                    token, page, query
                ).recipes
            )
        } catch (e: Exception) {
            Log.d(TAG, "searchRecipes: $e")
        }
        return recipes

    }

    override suspend fun getRecipe(token: String, id: Int): Recipe? {
        var recipe: Recipe? = null
        try {
            recipe = mapper.mapToDomainModel(
                recipeApi.getRecipe(
                    token, id
                )
            )
        } catch (e: Exception) {
        }
        return recipe
    }

}