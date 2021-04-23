package ir.pmoslem.covid_19tracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.pmoslem.covid_19tracker.model.Country
import ir.pmoslem.covid_19tracker.model.repository.HomeRepository
import kotlinx.coroutines.*

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {

    val progressIndicator: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    val userName: String
        get() = homeRepository.getUserPreferredName()

    val userPreferredCountry: String? = homeRepository.getUserPreferredCountryName()

    init {
        progressIndicator.value = true
        viewModelScope.launch(Dispatchers.Main) {
            homeRepository.getCountriesDataFromServer()
        }
    }

    fun getCountriesName(): LiveData<List<String>> {
        return homeRepository.getCountriesNameFromDatabase()
    }

    suspend fun getCountryDataByName(countryName: String): Country? {
        val country = homeRepository.getCountryDataByNameFromDatabase(countryName)
        if(country != null){
            progressIndicator.value = false
        }
        return country
    }


}