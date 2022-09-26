package developer.abdulaziz.myrestapi.Retrofit

import developer.abdulaziz.myrestapi.Models.MyPostRequest
import developer.abdulaziz.myrestapi.Models.MyPostResponse
import developer.abdulaziz.myrestapi.Models.MyTodo
import retrofit2.http.*

interface MyApiService {

    @GET("plan/")
    suspend fun getAllTodo(): List<MyTodo>

    @POST("plan/")
    suspend fun addTodo(@Body myPostRequest: MyPostRequest): MyPostResponse

    @PUT("plan/{id}/")
    suspend fun updateTodo(@Path("id") id: Int, @Body myPostRequest: MyPostRequest): MyPostResponse

    @DELETE("plan/{id}/")
    suspend fun deleteTodo(@Path("id") id: Int): Int
}