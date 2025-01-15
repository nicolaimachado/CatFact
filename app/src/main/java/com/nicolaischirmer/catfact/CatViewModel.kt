package com.nicolaischirmer.catfact

import androidx.compose.foundation.layout.add
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolaischirmer.catfact.models.CatFacts
import com.nicolaischirmer.catfact.utils.RetrofitInstance
import kotlinx.coroutines.launch

class CatViewModel : ViewModel() {
    val catFacts = mutableStateListOf<CatFacts>()
    val isLoading = mutableStateOf(true)
    val errorMessage = mutableStateOf<String?>(null)

    init {
        fetchCatFacts()
    }

    private fun fetchCatFacts() {
        viewModelScope.launch {
            try {
                repeat(10) {
                    val fact = RetrofitInstance.api.getRandomFact()
                    fact?.let {
                        catFacts.add(it)
                    } ?: run {
                        errorMessage.value = "API returned null"
                    }
                }
                isLoading.value = false
            } catch (e: Exception) {
                errorMessage.value = "Failed to fetch data: ${e.message}"
                isLoading.value = false
            }
        }
    }
}