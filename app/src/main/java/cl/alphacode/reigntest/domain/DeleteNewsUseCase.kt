package cl.alphacode.reigntest.domain

import cl.alphacode.reigntest.entity.News
import cl.alphacode.reigntest.repository.NewsRepositoryDb
import cl.alphacode.reigntest.util.wrapEspressoIdlingResource
import javax.inject.Inject

class DeleteNewsUseCase @Inject constructor(
    private val newsRepositoryDb: NewsRepositoryDb
) {
    operator fun invoke(news: News) {
        wrapEspressoIdlingResource {
            return newsRepositoryDb.deleteNews(news)
        }
    }
}