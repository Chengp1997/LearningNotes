package edu.vt.cs5254.fancygallery

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import edu.vt.cs5254.fancygallery.databinding.FragmentMapBinding
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MapFragment :Fragment(){
    private var _binding: FragmentMapBinding? = null
    private val binding
        get() = checkNotNull(_binding){
            "binding is null, can not access!"
        }

    private val vm : MapViewModel by viewModels()
    //access shared VM
    private val activityVM: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //to use map - must configured - send app ID value as user agent
        Configuration.getInstance().load(
            context,
            PreferenceManager.getDefaultSharedPreferences(requireContext())
        )
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
        //configure map
        with(binding.mapView) {
            //limit size
            minZoomLevel = 1.5
            maxZoomLevel = 15.0
            setScrollableAreaLimitLatitude(
                MapView.getTileSystem().maxLatitude,
                MapView.getTileSystem().minLatitude,
                0
            )
            //limit to single map
            isVerticalMapRepetitionEnabled = false
            isTilesScaledToDpi = true
            zoomController.setVisibility(CustomZoomButtonsController.Visibility.ALWAYS)
            setTileSource(TileSourceFactory.MAPNIK)
        }

        //collect value from activity view model -- use coroutine scope -- fast
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                activityVM.galleryItems.collect { giList ->
                    //create markers on the map -- for valid point
                    giList.filter { it.latitude != 0.0 && it.longitude != 0.0 }
                        .forEach { galleryItem ->
                            //load drawable from url
                            val photoDrawable = loadDrawableFromUrl(galleryItem.url)
                            //attach it
                            photoDrawable?.let {
                                val marker = Marker(binding.mapView).apply {
                                    position = GeoPoint(galleryItem.latitude, galleryItem.longitude)
                                    title = galleryItem.title
                                    icon = it //set the icon to be the photo
                                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)

                                    //relates to an object to make it reactive
                                    relatedObject = galleryItem.photoPageUri
                                    relatedObject = galleryItem.photoPageUri
                                    setOnMarkerClickListener { marker, mapView ->
                                        //resolve overlay problems
                                        //if clicked - move it to the top
                                        mapView.apply {
                                            controller.animateTo(marker.position)
                                            overlays.remove(marker)
                                            overlays.add(marker)
                                        }
                                        //show page
                                        //second click -- jump to page
                                        if(marker.isInfoWindowShown){
                                            val photoUri = marker.relatedObject as Uri
                                            findNavController().navigate(
                                                MapFragmentDirections.showPhotoFromMarker(photoUri)
                                            )
                                        }else{//first click - show info
                                            showInfoWindow()
                                        }
                                        true
                                    }
                                }
                                binding.mapView.overlays.add(marker)
                            }
                        }
                    //once loaded all the image, invalidate and reload again
                    binding.mapView.invalidate()
                }
            }
        }
    }

    //store value when pause// move to another nav
    override fun onPause() {
        super.onPause()
        with(binding.mapView){
            vm.saveMapState(zoomLevelDouble,mapCenter)
            onPause()
        }
    }

    //get prev states
    override fun onResume() {
        super.onResume()
        with(binding.mapView){
            onResume()
            controller.setZoom(vm.zoomLevel)
            controller.setCenter(vm.mapCenter)
//            Log.w("SHARED VM TEST","FOUND ${activityVM.galleryItems.value.size} items!")
        }
    }

    //load pic from url - suspend fun - run in background
    private suspend fun loadDrawableFromUrl(url: String): Drawable? {
        val loader = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data(url)
            .build()

        //may failed due to timeout/ unavailable problem
        return try {
            val result = loader.execute(request)
            (result as SuccessResult).drawable
        } catch (ex: Exception) {
            null
        }
    }
}