package cl.alphacode.reigntest.repository

import android.util.Log
import cl.alphacode.reigntest.dao.NewsDao
import cl.alphacode.reigntest.entity.News
import java.util.*
import javax.inject.Inject

interface NewsRepositoryDb {
    suspend fun getNews(): List<News>
    fun saveNews(news: News)
    fun deleteNews(news: News)
}

class NewsRepositoryDbImp @Inject constructor(
    private val newsDao: NewsDao
) : NewsRepositoryDb {
    private var news: List<News> = emptyList()

    override suspend fun getNews(): List<News> {
        kotlin.runCatching {
            news = newsDao.getAllNews()
            Log.i("AQUI!!", news.size.toString())
        }
        return news
    }

    override fun saveNews(news: News) {
        return newsDao.insertNewsOnConflict(news)
    }

    override fun deleteNews(news: News) {
        return newsDao.updateNews(
            News(
                news.uid,
                news.title,
                news.author,
                news.storyUrl,
                news.createdAt,
                news.createdAtI,
                deletedAt = Date()
            )
        )
    }
}