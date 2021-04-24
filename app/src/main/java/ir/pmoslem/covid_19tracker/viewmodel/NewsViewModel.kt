package ir.pmoslem.covid_19tracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pmoslem.covid_19tracker.model.News
import ir.pmoslem.covid_19tracker.model.repository.NewsRepository
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {


    init {
        newsRepository.getNewsDataFromServer()
    }

    fun getNewsFromDatabase(): LiveData<List<News>> {
        return newsRepository.getNewsDataFromDatabase()
    }

    fun getErrorStatus(): LiveData<Boolean> {
        return newsRepository.getErrorStatus()
    }

    fun getProgressBarStatus(): LiveData<Boolean>{
        return newsRepository.getProgressBarStatus()
    }

}