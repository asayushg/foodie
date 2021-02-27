package ayush.foodie.di

import ayush.foodie.network.RecipeApi
import ayush.foodie.network.model.RecipeDtoMapper
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {


    @Singleton
    @Provides
    fun provideMapper() : RecipeDtoMapper{
        return RecipeDtoMapper()
    }


    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://food2fork.ca/api/recipe/")
            .addConverterFactory(
                GsonConverterFactory.create(GsonBuilder().create())
            ).build()
    }

    @Singleton
    @Provides
    fun provideRecipeApi(retrofit: Retrofit) : RecipeApi{
        return retrofit.create(RecipeApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthToken() : String{
        return "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
    }


}