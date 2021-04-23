package ir.pmoslem.covid_19tracker.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.pmoslem.covid_19tracker.model.ApiService
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val BASE_URL =
        "https://vaccovid-coronavirus-vaccine-and-treatment-tracker.p.rapidapi.com/api/"
    private const val RAPID_API_KEY = "x-rapidapi-key"
    private const val RAPID_API_VALUE = "0421336961msh45e56034a86e2f1p1f26a4jsnbe0bd3a69292"
    private const val RAPID_API_HOST = "x-rapidapi-host"
    private const val RAPID_API_HOST_VALUE =
        "vaccovid-coronavirus-vaccine-and-treatment-tracker.p.rapidapi.com"

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val oldRequest: Request = chain.request()
                val newRequestBuilder: Request.Builder = oldRequest.newBuilder()
                newRequestBuilder.addHeader(RAPID_API_KEY, RAPID_API_VALUE)
                newRequestBuilder.addHeader(RAPID_API_HOST, RAPID_API_HOST_VALUE)
                chain.proceed(newRequestBuilder.build())
            }.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)


}
