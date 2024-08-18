package edu.vt.cs5254.dreamcatcher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DreamListViewModel : ViewModel() {
    private val dreamRepository = DreamRepository.get()

    //using Flow: step3 - get the flow
    //mutableStateFlow -- can used to update value within the stream
    //                  -- to protect access to the steam, make it private
    private val _dreams: MutableStateFlow<List<Dream>> = MutableStateFlow(emptyList())
    //expose the read-only stateFlow object
    val dreams : StateFlow<List<Dream>> get() = _dreams.asStateFlow()

    init {
        //launch coroutine
        viewModelScope.launch {
            dreamRepository.getDreams().collect {
                _dreams.value=it
            }
        }
    }

    suspend fun addDream(dream: Dream) {
        dreamRepository.addDream(dream)
    }

    suspend fun deleteDream(dream: Dream) {
        dreamRepository.deleteDream(dream)
    }

}