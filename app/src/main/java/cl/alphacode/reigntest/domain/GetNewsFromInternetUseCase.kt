package cl.alphacode.reigntest.domain

import cl.alphacode.reigntest.model.News
import cl.alphacode.reigntest.repository.NewsRepository
import cl.alphacode.reigntest.util.wrapEspressoIdlingResource
import javax.inject.Inject

class GetNewsFromInternetUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(query: String): List<News> {
        wrapEspressoIdlingResource {
            return newsRepository.getNews(query)
        }
    }
}