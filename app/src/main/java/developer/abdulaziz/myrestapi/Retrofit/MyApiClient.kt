package developer.abdulaziz.myrestapi.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MyApiClient {
    private const val BASE_URL = "https://hvax.pythonanywhere.com/"
    private fun getRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun getApi(): MyApiService = getRetrofit().create(MyApiService::class.java)
}