package com.prafullkumar.languagetranslator

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val languageRepository: LanguageRepository
): ViewModel() {


    var sourceLang: String = "English"
    var targetLang: String = "Hindi"

    private val _uiState = MutableStateFlow(UIState())
    val uiState = _uiState.asStateFlow()
    fun getResponse(text: String) {
        _uiState.update {
            it.copy(
                sourceText = text,
                targetText = "नमस्ते"
            )
        }
        Log.d("LanguageViewModel", "getResponse: ${_uiState.value}}")
    }
}
data class UIState(
    val sourceText: String = "",
    val targetText: String = "",
)
