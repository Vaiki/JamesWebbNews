package com.vaiki.jameswebbnews.repository

import androidx.room.Query
import com.vaiki.jameswebbnews.api.NewsApi
import com.vaiki.jameswebbnews.data.ArticleDao
import com.vaiki.jameswebbnews.models.Article
import java.text.SimpleDateFormat
import java.util.*

class NewsRepository(private val articleDao: ArticleDao) {

    suspend fun searchNews(searchQuery: String) =
        NewsApi.api.searchNews(searchQuery)

    suspend fun followNews()=NewsApi.api.followNews()

    suspend fun upsert(article: Article) = articleDao.upsert(article)

    fun getSavedNews() = articleDao.getAllArticles()

    suspend fun deleteArticle(article: Article) = articleDao.deleteArticle(article)


}
