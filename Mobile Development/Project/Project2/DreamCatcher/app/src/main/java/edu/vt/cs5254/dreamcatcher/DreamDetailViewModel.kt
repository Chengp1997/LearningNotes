package edu.vt.cs5254.dreamcatcher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*

class DreamDetailViewModel(dreamId: UUID) : ViewModel() {

    private val dreamRepository = DreamRepository.get()

    private val _dream: MutableStateFlow<Dream?> = MutableStateFlow(null)
    //expose the read-only stateFlow object
    val dream : StateFlow<Dream?> = _dream.asStateFlow()

    init {
        viewModelScope.launch {
            _dream.value = dreamRepository.getDream(dreamId)
        }
    }

    //update data in viewModel
    fun updateDream(onUpdate: (Dream) -> Dream){
        _dream.update { oldDream->
            //check if changed or not first
            val newDream = oldDream?.let{onUpdate(it)}?: return
            if (newDream == oldDream && newDream.entries == oldDream.entries){
                return
            }
            //else, do update
            newDream.copy(lastUpdated = Date()).apply { entries = newDream.entries }
        }
    }

    //update data in db
    override fun onCleared() {
        super.onCleared()
        dream.value?.let { dreamRepository.updateDream(it) }
    }


}

//By default, viewModels can only have a constructor with no arg/single SavedStateHandle
//using ViewModelProvider.Factory - can add additional arguments
//responsible for creating viewModel
@Suppress("UNCHECKED_CAST")
class DreamDetailViewModelFactory(
    private val dreamId: UUID
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DreamDetailViewModel(dreamId) as T
    }
}
