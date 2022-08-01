package cl.alphacode.reigntest.domain

import cl.alphacode.reigntest.entity.News
import cl.alphacode.reigntest.repository.NewsRepository
import cl.alphacode.reigntest.util.wrapEspressoIdlingResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(): Flow<List<News>> {
        wrapEspressoIdlingResource {
            return newsRepository.getNews()
        }
    }

    suspend fun updateInternetNews() {
        wrapEspressoIdlingResource {
            return newsRepository.getNewsFromInternet("mobile")
        }
    }
}