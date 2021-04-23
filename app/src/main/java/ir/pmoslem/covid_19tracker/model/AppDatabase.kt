package ir.pmoslem.covid_19tracker.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Country::class, News::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCountryDao(): CountryDao
    abstract fun getNewsDao(): NewsDao

}
