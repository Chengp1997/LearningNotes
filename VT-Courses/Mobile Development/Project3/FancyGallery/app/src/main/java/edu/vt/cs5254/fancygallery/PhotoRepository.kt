package edu.vt.cs5254.fancygallery

import edu.vt.cs5254.fancygallery.api.FlickrApi
import edu.vt.cs5254.fancygallery.api.GalleryItem
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

//wrap the retrofit config file, encapsulate the logic of data,
class PhotoRepository {
    private val flickrApi: FlickrApi
    init {
        //used retrofit object to create instance of api
        //converter:default message types: ResponseBody --> needs to convert to the type we want
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build();

        //api created by retrofit
        flickrApi = retrofit.create<FlickrApi>()
    }

    //use moshi factory to convert response into
    //default value = 100 -- make param optional
    suspend fun fetchPhotos(pageSize: Int = 100) : List<GalleryItem> {
        return flickrApi.fetchPhotos(pageSize).photos.galleryItems
    }
}