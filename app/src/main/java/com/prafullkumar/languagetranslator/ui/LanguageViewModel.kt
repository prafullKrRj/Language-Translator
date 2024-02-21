package com.prafullkumar.languagetranslator.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.languagetranslator.data.LanguageRepository
import com.prafullkumar.languagetranslator.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val languageRepository: LanguageRepository
): ViewModel() {


    var sourceLang: String = "English"
    var targetLang: String = "Hindi"

    private val _uiState = MutableStateFlow<Resource<UIState>>(Resource.Initial)
    val uiState = _uiState.asStateFlow()

    fun getResponse(text: String) {
        viewModelScope.launch(Dispatchers.Default) {
            languageRepository.getResponse(text, sourceLang, targetLang).collect { response ->
                when (response) {
                    is Resource.Error -> {
                        _uiState.update {
                            Resource.Error(response.exception)
                        }
                    }
                    Resource.Initial -> {
                        _uiState.update {
                            Resource.Initial
                        }
                    }
                    Resource.Loading -> {
                        _uiState.update {
                            Resource.Loading
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            Resource.Success(
                                UIState(
                                    sourceText = text,
                                    targetText = response.data ?: ""
                                )
                            )
                        }
                    }
                }
            }
        }
        Log.d("LanguageViewModel", "getResponse: ${_uiState.value}}")
    }
}
data class UIState(
    val sourceText: String = "",
    val targetText: String = "",
)
