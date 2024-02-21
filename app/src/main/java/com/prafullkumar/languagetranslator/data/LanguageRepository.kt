package com.prafullkumar.languagetranslator.data

import com.google.ai.client.generativeai.GenerativeModel
import com.prafullkumar.languagetranslator.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LanguageRepository @Inject constructor(
    private val generativeModel: GenerativeModel
) {


    /**
     *  val prompt = "Write a story about a magic backpack."
     * val response = generativeModel.generateContent(prompt)
     * print(response.text)
     *
     * */
    suspend fun getResponse(prompt: String, from: String, to: String): Flow<Resource<String?>> = flow{
        emit(Resource.Loading)
        try {
            val response = generativeModel.generateContent("translate from $from to $to: $prompt")
            emit(Resource.Success(response.text))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }
}
