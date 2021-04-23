package ir.pmoslem.covid_19tracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "countries")
data class Country(
        @PrimaryKey(autoGenerate = false)
        @SerializedName("Country")
        val countryName: String = "",
        @SerializedName("TotalDeaths")
        val totalDeaths: Long = 0,
        @SerializedName("TotalRecovered")
        val totalRecovered: Long = 0,
        @SerializedName("TotalCases")
        val totalCases: Long = 0
)
