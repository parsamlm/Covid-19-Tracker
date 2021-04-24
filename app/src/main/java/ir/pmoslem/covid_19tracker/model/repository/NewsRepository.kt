package ir.pmoslem.covid_19tracker.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ir.pmoslem.covid_19tracker.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

private const val ERROR_TAG: String = "ResponseError"
private var isErrorOccurred = MutableLiveData<Boolean>()

class NewsRepository @Inject constructor(
    private val api: ApiService,
    private val newsDao: NewsDao
) {

    fun getNewsDataFromServer() {
        GlobalScope.launch(Dispatchers.IO) {
            try{
                val response = api.getNewsData()
                if (response.isSuccessful) {
                    val newsObject: NewsObject? = response.body()
                    val newsList: List<News>? = newsObject?.news
                    if (newsList != null) {
                        newsDao.insertNews(newsList)
                        isErrorOccurred.postValue(false)
                    }
                }
            }catch (e: Exception){
                cancel()
                Log.e(ERROR_TAG, "Error occurred while getting response")
                isErrorOccurred.postValue(true)
            }
        }
    }

    fun getNewsDataFromDatabase(): LiveData<List<News>> {
        return newsDao.getAllNews()
    }

    fun getErrorStatus(): LiveData<Boolean>{
        return isErrorOccurred
    }

}