package cl.alphacode.reigntest.domain

import cl.alphacode.reigntest.entity.News
import cl.alphacode.reigntest.repository.NewsRepositoryDb
import cl.alphacode.reigntest.util.wrapEspressoIdlingResource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetNewsFromDbUseCase @Inject constructor(
    private val newsRepositoryDb: NewsRepositoryDb
) {
    suspend operator fun invoke(): Flow<List<News>> {
        wrapEspressoIdlingResource {
            return newsRepositoryDb.getNews()
        }
    }
}