package ir.pmoslem.covid_19tracker.model.repository

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ir.pmoslem.covid_19tracker.model.*
import ir.pmoslem.covid_19tracker.view.getStringDataFromSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

private const val ERROR_TAG: String = "ResponseError"
private var isErrorOccurred = MutableLiveData<Boolean>()
private val progressBarStatus: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

class HomeRepository @Inject constructor(
    private val api: ApiService,
    private val countryDao: CountryDao,
    private val sharedPreferences: SharedPreferences?
) {

    init {
        progressBarStatus.postValue(true)
    }

    fun getCountriesDataFromServer() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response: Response<List<Country>> = api.getCountriesData()
                if (response.isSuccessful) {
                    countryDao.insertCountriesData(response.body()!!)
                    isErrorOccurred.postValue(false)
                    progressBarStatus.postValue(false)
                }
            } catch (e: Exception) {
                cancel()
                Log.e(ERROR_TAG, "Error occurred while getting response")
                isErrorOccurred.postValue(true)
                progressBarStatus.postValue(true)
            }
        }
    }

    fun getCountriesNameFromDatabase(): LiveData<List<String>> {
        return countryDao.getCountriesName()
    }

    fun getUserPreferredName(): String {
        return "Hello ${
            sharedPreferences?.getStringDataFromSharedPreferences(
                USER_NAME_KEY,
                ""
            )
        } 👋🏻"
    }

    fun getUserPreferredCountryName(): String? {
        return sharedPreferences?.getStringDataFromSharedPreferences(USER_COUNTRY_NAME, "Iran")
    }

    suspend fun getCountryDataByNameFromDatabase(countryName: String): Country? {
        return countryDao.getCountryDataByName(countryName)
    }

    fun getErrorStatus(): LiveData<Boolean> {
        return isErrorOccurred
    }

    fun getProgressBarStatus(): LiveData<Boolean>{
        return progressBarStatus
    }

}