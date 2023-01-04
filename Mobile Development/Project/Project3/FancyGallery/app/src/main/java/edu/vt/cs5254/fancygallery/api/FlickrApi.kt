package edu.vt.cs5254.fancygallery.api

import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "766cd55aeca93081b189d8105ab17669"

interface FlickrApi {
    //@Query - for parameters
    //@Path - change path
    @GET(
        "services/rest/?method=flickr.interestingness.getList" +
                "&api_key=$API_KEY" +
                "&format=json" +
                "&nojsoncallback=1" +
                "&extras=url_s,geo")
    suspend fun fetchPhotos(@Query("per_page") pageSize: Int): FlickrResponse
}