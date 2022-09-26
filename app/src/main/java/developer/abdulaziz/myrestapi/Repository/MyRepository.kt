package developer.abdulaziz.myrestapi.Repository

import developer.abdulaziz.myrestapi.Models.MyPostRequest
import developer.abdulaziz.myrestapi.Models.MyPostResponse
import developer.abdulaziz.myrestapi.Retrofit.MyApiService

class MyRepository(private val apiService: MyApiService) {
    suspend fun getAllTodo() = apiService.getAllTodo()
    suspend fun addTodo(myPostRequest: MyPostRequest) = apiService.addTodo(myPostRequest)
    suspend fun updateTodo(id: Int, myPostRequest: MyPostRequest): MyPostResponse =
        apiService.updateTodo(id, myPostRequest)

    suspend fun deleteTodo(id: Int): Int = apiService.deleteTodo(id)
}