package ir.pmoslem.covid_19tracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pmoslem.covid_19tracker.model.Country
import ir.pmoslem.covid_19tracker.model.repository.HomeRepository
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(var homeRepository: HomeRepository) : ViewModel() {


    val userName: String
        get() = homeRepository.getUserPreferredName()

    val userPreferredCountry: String? = homeRepository.getUserPreferredCountryName()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            homeRepository.getCountriesDataFromServer()
        }
    }

    fun getCountriesName(): LiveData<List<String>> {
        return homeRepository.getCountriesNameFromDatabase()
    }

    suspend fun getCountryDataByName(countryName: String): Country? {
        return homeRepository.getCountryDataByNameFromDatabase(countryName)
    }

    fun getErrorStatus(): LiveData<Boolean> {
        return homeRepository.getErrorStatus()
    }

    fun getProgressBarStatus(): LiveData<Boolean>{
        return homeRepository.getProgressBarStatus()
    }


}