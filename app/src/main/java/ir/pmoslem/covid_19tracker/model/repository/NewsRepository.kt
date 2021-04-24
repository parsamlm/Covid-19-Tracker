package ir.pmoslem.covid_19tracker.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ir.pmoslem.covid_19tracker.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

private const val ERROR_TAG: String = "ResponseError"
private val isErrorOccurred = MutableLiveData<Boolean>()
private val progressBarStatus: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

class NewsRepository @Inject constructor(
    private val api: ApiService,
    private val newsDao: NewsDao
) {

    init {
        progressBarStatus.postValue(true)
    }

    fun getNewsDataFromServer() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getNewsData()
                if (response.isSuccessful) {
                    val newsObject: NewsObject? = response.body()
                    val newsList: List<News>? = newsObject?.news
                    if (newsList != null) {
                        newsDao.insertNews(newsList)
                        isErrorOccurred.postValue(false)
                        progressBarStatus.postValue(false)
                    }
                }
            } catch (e: Exception) {
                cancel()
                Log.e(ERROR_TAG, "Error occurred while getting response")
                isErrorOccurred.postValue(true)
                progressBarStatus.postValue(true)
            }
        }
    }

    fun getNewsDataFromDatabase(): LiveData<List<News>> {
        return newsDao.getAllNews()
    }

    fun getErrorStatus(): LiveData<Boolean> {
        return isErrorOccurred
    }

    fun getProgressBarStatus(): LiveData<Boolean>{
        return progressBarStatus
    }

}