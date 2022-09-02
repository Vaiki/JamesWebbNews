package com.vaiki.jameswebbnews.repository

import androidx.room.Query
import com.vaiki.jameswebbnews.api.NewsApi
import com.vaiki.jameswebbnews.data.ArticleDao
import com.vaiki.jameswebbnews.models.Article

class NewsRepository(val articleDao: ArticleDao) {
    suspend fun getBreakingNews() =
        NewsApi.api.getBreakingNews()

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        NewsApi.api.searchNews(searchQuery, "ru","publishedAt", pageNumber)

    suspend fun upsert(article: Article) = articleDao.upsert(article)

    fun getSavedNews() = articleDao.getAllArticles()

    suspend fun deleteArticle(article: Article) = articleDao.deleteArticle(article)

}
