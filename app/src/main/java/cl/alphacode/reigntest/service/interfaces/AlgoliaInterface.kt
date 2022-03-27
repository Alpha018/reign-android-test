package cl.alphacode.reigntest.service.interfaces

import cl.alphacode.reigntest.service.algolia.responses.AlgoliaResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://hn.algolia.com/api/v1/"

interface RequestService {
    @GET("search_by_date?query=mobile")
    suspend fun getResult(): AlgoliaResponse

    companion object {
        private var apiService: RequestService? = null
        fun getInstance(): RequestService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(RequestService::class.java)
            }
            return apiService!!
        }
    }
}