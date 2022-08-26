package com.vaiki.jameswebbnews.repository

import androidx.room.Query
import com.vaiki.jameswebbnews.api.NewsApi
import com.vaiki.jameswebbnews.data.ArticleDao

class NewsRepository(val articleDao: ArticleDao) {
    suspend fun getBreakingNews(country: String, pageNumber: Int) =
        NewsApi.api.getBreakingNews("Space", country, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        NewsApi.api.searchNews(searchQuery, "en", pageNumber)

}
