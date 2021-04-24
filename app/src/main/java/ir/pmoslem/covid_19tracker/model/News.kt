package ir.pmoslem.covid_19tracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "news")
data class News(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("title")
    val title: String,
    @SerializedName("urlToImage")
    val imageUrl: String,
    @SerializedName("content")
    val description: String
)

data class NewsObject(
    @SerializedName("news")
    val news: List<News>?
)
