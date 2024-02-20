package com.prafullkumar.languagetranslator

import com.google.ai.client.generativeai.GenerativeModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLanguageRepository(generativeModel: GenerativeModel): LanguageRepository {
        return LanguageRepository(generativeModel)
    }
    @Provides
    @Singleton
    fun providesSApiKey() = "AIzaSyCgQlTwJ9-zMZmyK8qHrUkvZezbztD0iMw"
    @Provides
    @Singleton
    fun providesGenerative(apiKey: String): GenerativeModel {
        return GenerativeModel(
            apiKey = apiKey,
            modelName = "gemini-pro"
        )
    }
}