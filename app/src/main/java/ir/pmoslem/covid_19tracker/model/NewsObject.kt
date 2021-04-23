package ir.pmoslem.covid_19tracker.model

import com.google.gson.annotations.SerializedName

data class NewsObject(
    @SerializedName("news")
    val news: List<News>?
)
