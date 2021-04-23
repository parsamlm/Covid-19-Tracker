package ir.pmoslem.covid_19tracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pmoslem.covid_19tracker.model.News
import ir.pmoslem.covid_19tracker.model.repository.NewsRepository
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    val progressIndicator: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    init {
        progressIndicator.value = true
        newsRepository.getNewsDataFromServer()

    }

    fun getNewsFromDatabase(): LiveData<List<News>> {
        return newsRepository.getNewsDataFromDatabase()
    }

}