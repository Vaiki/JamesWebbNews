package com.vaiki.jameswebbnews.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vaiki.jameswebbnews.di.NewsApp
import com.vaiki.jameswebbnews.models.Article
import com.vaiki.jameswebbnews.models.NewsResponse
import com.vaiki.jameswebbnews.repository.NewsRepository
import com.vaiki.jameswebbnews.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(app: Application, private val repository: NewsRepository) :
    AndroidViewModel(app) {

    private val noConnection = "Отсутствует интернет соединение"
    private val networkFailure = "Ошибка сети"
    private val conversionError = "Ошибка преобразования ответа сервера"
    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val followNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val openArticleLiveData: MutableLiveData<Article> = MutableLiveData()

    init {
        getFollowNews()
    }

    private fun getFollowNews() = viewModelScope.launch {
        safeFollowNewsCall()
    }

    fun getSearchNews(searchQuery: String) = viewModelScope.launch {
        safeSearchNewsCall(searchQuery)
    }

    private fun handleNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }



    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.upsert(article)
    }

    fun openArticle(article: Article) {
        openArticleLiveData.value = article
    }

    fun getSavedNews() = repository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        repository.deleteArticle(article)
    }

    private suspend fun safeSearchNewsCall(searchQuery: String) {
        searchNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = repository.searchNews(searchQuery)
                searchNews.postValue(handleNewsResponse(response))
            } else {
                searchNews.postValue(Resource.Error(noConnection))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> searchNews.postValue(Resource.Error(networkFailure))
                else -> searchNews.postValue(Resource.Error(conversionError))
            }
        }
    }

    private suspend fun safeFollowNewsCall() {
        followNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = repository.followNews()
                followNews.postValue(handleNewsResponse(response))
            } else {
                followNews.postValue(Resource.Error(noConnection))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> followNews.postValue(Resource.Error(networkFailure))
                else -> followNews.postValue(Resource.Error(conversionError))
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<NewsApp>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(TRANSPORT_WIFI) -> true
            capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}