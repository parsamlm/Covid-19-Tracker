package ir.pmoslem.covid_19tracker.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountriesData(countryStatsList: List<Country>)

    @Query("SELECT countryName FROM countries")
    fun getCountriesName(): LiveData<List<String>>

    @Query("SELECT * FROM countries WHERE countryName LIKE '%' || :query || '%'")
    suspend fun getCountryDataByName(query: String): Country?

}