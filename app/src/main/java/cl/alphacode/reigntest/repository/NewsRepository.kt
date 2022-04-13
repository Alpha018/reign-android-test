package cl.alphacode.reigntest.repository

import cl.alphacode.reigntest.model.News
import cl.alphacode.reigntest.provider.NewsProvider
import javax.inject.Inject

interface NewsRepository {
    suspend fun getNews(query: String): List<News>
    fun getNew(id: Int): News
}

class NewsRepositoryImp @Inject constructor(
    private val newsProvider: NewsProvider
): NewsRepository {

    private var news: List<News> = emptyList()

    override suspend fun getNews(query: String): List<News> {
        val apiResponse = newsProvider.getNews(query).body()
        news = apiResponse?.hits ?: emptyList()
        return news
    }

    override fun getNew(id: Int): News =
        news.first { it.createdAtI == id }
}