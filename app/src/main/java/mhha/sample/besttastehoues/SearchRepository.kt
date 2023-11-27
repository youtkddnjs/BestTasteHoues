package mhha.sample.besttastehoues


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object SearchRepository {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AppInterceptor())
        .build()


    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retoryfit = Retrofit.Builder()
        .baseUrl("https://openapi.naver.com/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()

    private val service = retoryfit.create(SearchService::class.java)

    class AppInterceptor: Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response {
            val myRequest = chain.request().newBuilder()
//                .addHeader("X-Naver-Client-Id","${R.string.naver_search_client_id}")
//                .addHeader("X-Naver-Client-Secret","${R.string.naver_search_client_secret_id}")
                .addHeader("X-Naver-Client-Id","ndaof6cIMwHxQpQ_LBH9")
                .addHeader("X-Naver-Client-Secret","5oI_uScGAk")
                .build()
            return chain.proceed(myRequest)
        }//override fun intercept(chain: Interceptor.Chain): Response
    }//class AppInterceptor: Interceptor


    fun getBestTasteHouse( q: String): Call<SearchResult>{
        return service.getBestTasteHouse( query = "${q} 맛집", display = 5)
    }

} //object SearchRepository