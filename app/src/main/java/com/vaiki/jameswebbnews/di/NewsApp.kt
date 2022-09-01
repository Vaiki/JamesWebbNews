package com.vaiki.jameswebbnews.di

import android.app.Application
import androidx.room.Room
import com.vaiki.jameswebbnews.data.ArticleDao
import com.vaiki.jameswebbnews.data.ArticleDatabase
import com.vaiki.jameswebbnews.repository.NewsRepository
import com.vaiki.jameswebbnews.ui.NewsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class NewsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@NewsApp)
            modules(newsDB, newsViewModel)

        }

    }

    private val newsDB = module {
        fun provideDataBase(application: Application): ArticleDatabase {
            return Room.databaseBuilder(application, ArticleDatabase::class.java, "ArticlesDB")
                .build()
        }

        fun provideDao(articleDatabase: ArticleDatabase): ArticleDao {
            return articleDatabase.getArticleDao()
        }
        single { provideDataBase(androidApplication()) }
        single { provideDao(get()) }

    }
    private val newsViewModel = module {
        single { NewsRepository(get()) }
        viewModel { NewsViewModel(get()) }
    }

}

