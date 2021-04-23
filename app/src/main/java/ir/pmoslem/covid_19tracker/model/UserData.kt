package ir.pmoslem.covid_19tracker.model

import android.content.Context
import android.content.SharedPreferences

const val USER_NAME_KEY = "user_name"
const val USER_COUNTRY_NAME = "user_country_name"

class UserData(val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user", Context.MODE_PRIVATE)

    fun saveUserName(name: String) {
        val editor = sharedPreferences.edit()
        editor.putString(USER_NAME_KEY, name)
        editor.apply()
    }

    fun savePreferredCountry(countryName: String) {
        val editor = sharedPreferences.edit()
        editor.putString(USER_COUNTRY_NAME, countryName)
        editor.apply()
    }

    fun getUserName(): String? {
        return sharedPreferences.getString(USER_NAME_KEY, "")
    }

}