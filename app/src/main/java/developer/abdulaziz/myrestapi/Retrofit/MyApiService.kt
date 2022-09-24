package developer.abdulaziz.myrestapi.Retrofit

import developer.abdulaziz.myrestapi.Models.MyTodo
import retrofit2.http.GET

interface MyApiService {
    @GET("plan/")
    suspend fun getAllTodo(): List<MyTodo>
}