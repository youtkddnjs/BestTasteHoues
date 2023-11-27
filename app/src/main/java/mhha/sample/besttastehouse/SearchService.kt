package mhha.sample.besttastehouse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("v1/search/local.json")
    fun getBestTasteHouse(
        @Query("query") query: String,
        @Query("display") display: Int,
    ): Call<SearchResult>

}//interface SearchService