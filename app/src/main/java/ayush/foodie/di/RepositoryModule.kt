package ayush.foodie.di

import ayush.foodie.network.RecipeApi
import ayush.foodie.network.model.RecipeDtoMapper
import ayush.foodie.repository.Repository
import ayush.foodie.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule{

    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeApi: RecipeApi,
        recipeDtoMapper: RecipeDtoMapper
    ) : Repository {
        return RepositoryImpl(recipeApi,recipeDtoMapper)
    }
}