package ayush.foodie.repository

import ayush.foodie.domain.model.Recipe

interface Repository {

    suspend fun searchRecipes(
        token: String,
        page: Int,
        query: String
    ): List<Recipe>


    suspend fun getRecipe(
        token: String,
        id: Int
    ): Recipe

}