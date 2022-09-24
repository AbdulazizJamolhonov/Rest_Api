package developer.abdulaziz.myrestapi.Repository

import developer.abdulaziz.myrestapi.Retrofit.MyApiService

class MyRepository(val apiService: MyApiService) {
    suspend fun getAllTodo() = apiService.getAllTodo()
}