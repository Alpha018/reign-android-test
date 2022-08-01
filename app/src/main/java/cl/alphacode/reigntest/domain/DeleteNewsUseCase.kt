package cl.alphacode.reigntest.domain

import cl.alphacode.reigntest.entity.News
import cl.alphacode.reigntest.repository.NewsRepository
import cl.alphacode.reigntest.util.wrapEspressoIdlingResource
import javax.inject.Inject

class DeleteNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(news: News) {
        wrapEspressoIdlingResource {
            return newsRepository.deleteNews(news)
        }
    }
}