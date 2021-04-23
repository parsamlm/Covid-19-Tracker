package ir.pmoslem.covid_19tracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.pmoslem.covid_19tracker.model.repository.HomeRepository
import ir.pmoslem.covid_19tracker.model.repository.NewsRepository
import ir.pmoslem.covid_19tracker.model.repository.Repository

class HomeViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository as HomeRepository) as T
            modelClass.isAssignableFrom(NewsViewModel::class.java) -> NewsViewModel(repository as NewsRepository) as T
            else -> throw IllegalArgumentException("view model is not valid")
        }
    }
}