package ir.pmoslem.covid_19tracker.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.pmoslem.covid_19tracker.model.AppDatabase
import ir.pmoslem.covid_19tracker.model.CountryDao
import ir.pmoslem.covid_19tracker.model.NewsDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase{
        return Room.databaseBuilder(context, AppDatabase::class.java, "db_country_stats").build()
    }

    @Singleton
    @Provides
    fun provideCountryDao(appDatabase: AppDatabase): CountryDao{
        return appDatabase.getCountryDao()
    }

    @Singleton
    @Provides
    fun provideNewsDao(appDatabase: AppDatabase): NewsDao{
        return appDatabase.getNewsDao()
    }

}