package ir.pmoslem.covid_19tracker.model.repository

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import ir.pmoslem.covid_19tracker.model.*
import ir.pmoslem.covid_19tracker.view.getStringDataFromSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeRepository(
    private val api: ApiService,
    private val countryDao: CountryDao,
    private val sharedPreferences: SharedPreferences?
) : Repository() {

    fun getCountriesDataFromServer() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getCountriesData()
            if (response.isSuccessful) {
                countryDao.insertCountriesData(response.body()!!)
            }
        }
        //todo Error handling
    }

    fun getCountriesNameFromDatabase(): LiveData<List<String>> {
        return countryDao.getCountriesName()
    }

    fun getUserPreferredName(): String {
        return "Hello ${sharedPreferences?.getStringDataFromSharedPreferences(USER_NAME_KEY, "")} 👋🏻"
    }

    fun getUserPreferredCountryName(): String? {
        return sharedPreferences?.getStringDataFromSharedPreferences(USER_COUNTRY_NAME, "Iran")
    }

    suspend fun getCountryDataByNameFromDatabase(countryName: String): Country? {
        return countryDao.getCountryDataByName(countryName)
    }


}