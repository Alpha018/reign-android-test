package cl.alphacode.reigntest.repository

import cl.alphacode.reigntest.dao.NewsDao
import cl.alphacode.reigntest.entity.News
import cl.alphacode.reigntest.provider.NewsProvider
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

interface NewsRepository {
    suspend fun getNewsFromInternet(query: String)
    suspend fun getNews(): Flow<List<News>>
    suspend fun saveNews(news: News)
    suspend fun deleteNews(news: News)
}

class NewsRepositoryImp @Inject constructor(
    private val newsProvider: NewsProvider,
    private val newsDao: NewsDao
) : NewsRepository {

    override suspend fun getNewsFromInternet(query: String) {
        kotlin.runCatching {
            val apiResponse = newsProvider.getNews(query)

            if (apiResponse.isSuccessful) {
                val response = apiResponse.body()?.hits ?: emptyList()
                response.forEach {
                    saveNews(News(
                        title = it.storyTitle ?: it.title ?: "",
                        author = it.author,
                        createdAt = it.createdAt,
                        createdAtI = it.createdAtI,
                        storyUrl = it.storyUrl ?: ""
                    ))
                }
            }
        }
    }

    override suspend fun getNews(): Flow<List<News>> {
        return newsDao.getAllNews()
    }

    override suspend fun saveNews(news: News) {
        return newsDao.insertNewsOnConflict(news)
    }

    override suspend fun deleteNews(news: News) {
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