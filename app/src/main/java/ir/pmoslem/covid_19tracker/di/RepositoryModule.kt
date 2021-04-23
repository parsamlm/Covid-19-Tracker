package ir.pmoslem.covid_19tracker.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.pmoslem.covid_19tracker.model.*
import ir.pmoslem.covid_19tracker.model.repository.HomeRepository
import ir.pmoslem.covid_19tracker.model.repository.NewsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideHomeRepository(
        api: ApiService,
        countryDao: CountryDao,
        sharedPreferences: SharedPreferences?
    ): HomeRepository{
        return HomeRepository(api, countryDao, sharedPreferences)
    }


    @Singleton
    @Provides
    fun provideNewsRepository(
        api: ApiService,
        newsDao: NewsDao
    ): NewsRepository{
        return NewsRepository(api, newsDao)
    }

}