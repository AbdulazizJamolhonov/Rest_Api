package developer.abdulaziz.myrestapi.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MyApiClient {
    private fun getRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://hvax.pythonanywhere.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun getApi(): MyApiService = getRetrofit().create(MyApiService::class.java)
}