package ir.pmoslem.covid_19tracker.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.pmoslem.covid_19tracker.model.UserData

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("user", Context.MODE_PRIVATE)


    @Provides
    fun provideUserData(@ApplicationContext context: Context): UserData = UserData(context)

}