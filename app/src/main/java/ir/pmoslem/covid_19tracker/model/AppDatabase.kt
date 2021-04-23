package ir.pmoslem.covid_19tracker.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Country::class, News::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        lateinit var appDatabase: AppDatabase

        @JvmStatic
        fun getInstance(context: Context): AppDatabase {
            appDatabase =
                Room.databaseBuilder(context, AppDatabase::class.java, "db_country_stats").build()
            return appDatabase
        }
    }

    abstract fun getCountryDao(): CountryDao
    abstract fun getNewsDao(): NewsDao

}
