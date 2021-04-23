package ir.pmoslem.covid_19tracker.view.news

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ir.pmoslem.covid_19tracker.R
import ir.pmoslem.covid_19tracker.model.News
import javax.inject.Inject

class NewsAdapter @Inject constructor(val context: Context) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private lateinit var newsList: List<News>

    fun setNews(news: List<News>) {
        newsList = news
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(context).inflate(R.layout.news_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(R.id.news_title_item)
        private val description: TextView = itemView.findViewById(R.id.news_description_item)
        private val image: ImageView = itemView.findViewById(R.id.news_image_item)

        fun bind(news: News) {
            title.text = news.title
            description.text = news.description
            Picasso.get().load(news.imageUrl).into(image)
        }
    }
}