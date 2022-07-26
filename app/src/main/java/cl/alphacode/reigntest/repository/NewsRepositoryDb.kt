package cl.alphacode.reigntest.repository

import cl.alphacode.reigntest.dao.NewsDao
import cl.alphacode.reigntest.entity.News
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

interface NewsRepositoryDb {
    suspend fun getNews(): Flow<List<News>>
    fun saveNews(news: News)
    fun deleteNews(news: News)
}

class NewsRepositoryDbImp @Inject constructor(
    private val newsDao: NewsDao
) : NewsRepositoryDb {


    override suspend fun getNews(): Flow<List<News>> {
        return newsDao.getAllNews()
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