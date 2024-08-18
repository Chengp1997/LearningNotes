package edu.vt.cs5254.fancygallery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.vt.cs5254.fancygallery.api.GalleryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "GalleryViewModel"
class MainViewModel : ViewModel() {
    private val photoRepository = PhotoRepository()
    //store the values
    private val _galleryItems: MutableStateFlow<List<GalleryItem>> =
        MutableStateFlow(emptyList())

    //expose the data to view model
    val galleryItems: StateFlow<List<GalleryItem>>
        get() = _galleryItems.asStateFlow()

    init {
        //get data when initialized (created once)
        fetchData()
    }

    private fun fetchData(){
        viewModelScope.launch {
            try {
                val items = photoRepository.fetchPhotos(99)
                Log.d(TAG, "Items received: $items")
                _galleryItems.value = items
            } catch (ex: Exception) {
                Log.e(TAG, "Failed to fetch gallery items", ex)
            }
        }
    }

     fun reloadGalleryItems(){
        _galleryItems.value = emptyList()
        fetchData()
    }
}
