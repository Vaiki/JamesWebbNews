package com.vaiki.jameswebbnews.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vaiki.jameswebbnews.models.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article):Long

    @Query("SELECT * FROM articles")
    fun getAllArticle():LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}