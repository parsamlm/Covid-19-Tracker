package ir.pmoslem.covid_19tracker.model

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("npm-covid-data/countries")
    suspend fun getCountriesData(): Response<List<Country>>

    @GET("news/get-health-news/1")
    suspend fun getNewsData(): Response<NewsObject>

}
