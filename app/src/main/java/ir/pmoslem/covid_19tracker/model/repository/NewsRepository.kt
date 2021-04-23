package ir.pmoslem.covid_19tracker.model.repository

import androidx.lifecycle.LiveData
import ir.pmoslem.covid_19tracker.model.ApiService
import ir.pmoslem.covid_19tracker.model.News
import ir.pmoslem.covid_19tracker.model.NewsDao
import ir.pmoslem.covid_19tracker.model.NewsObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val api: ApiService,
    private val newsDao: NewsDao
) {

    fun getNewsDataFromServer() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getNewsData()
            if (response.isSuccessful) {
                val newsObject: NewsObject? = response.body()
                val newsList: List<News>? = newsObject?.news
                if (newsList != null) {
                    newsDao.insertNews(newsList)
                }
            }
            //todo Error handling
        }
    }

    fun getNewsDataFromDatabase(): LiveData<List<News>> {
        return newsDao.getAllNews()
    }

}