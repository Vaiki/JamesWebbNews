package com.vaiki.jameswebbnews.api

import com.vaiki.jameswebbnews.models.NewsResponse
import com.vaiki.jameswebbnews.util.Constants.Companion.API_KEY
import com.vaiki.jameswebbnews.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*

interface NewsApi {

    @GET("/v2/everything")
    suspend fun searchNews(
        @Query("q")
        search: String,
        @Query("language")
        language: String = "ru",
        @Query("sortBy")
        sortBy: String = "publishedAt",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("/v2/everything")
    suspend fun followNews(
        @Query("q")
        search: String = "space",
        @Query("language")
        language: String = "ru",
        @Query("sortBy")
        sortBy: String = "publishedAt",
        @Query("from")
        from: String = getCurrentDateTime(),
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>


    companion object {
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val api by lazy {
            retrofit.create(NewsApi::class.java)
        }
    }

    private fun getCurrentDateTime(): String {
        val now = Date().time
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(now)
    }
}