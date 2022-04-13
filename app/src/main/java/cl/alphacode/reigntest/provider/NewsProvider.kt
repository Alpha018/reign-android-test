package cl.alphacode.reigntest.provider

import cl.alphacode.reigntest.model.NewsApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsProvider {

    @GET("search_by_date")
    suspend fun getNews(@Query("query") query: String): Response<NewsApiResponse>
}