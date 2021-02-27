package ayush.foodie.repository

import ayush.foodie.domain.model.Recipe
import ayush.foodie.network.RecipeApi
import ayush.foodie.network.model.RecipeDtoMapper

class RepositoryImpl(
    private val recipeApi: RecipeApi,
    private val mapper: RecipeDtoMapper
) : Repository {

    override suspend fun searchRecipes(token: String, page: Int, query: String): List<Recipe> {
        return mapper.toDomainList(
            recipeApi.searchRecipes(
                token, page, query
            ).recipes
        )
    }

    override suspend fun getRecipe(token: String, id: Int): Recipe {
        return mapper.mapToDomainModel(
            recipeApi.getRecipe(
                token, id
            )
        )
    }

}