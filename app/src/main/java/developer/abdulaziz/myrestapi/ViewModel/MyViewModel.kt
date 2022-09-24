package developer.abdulaziz.myrestapi.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import developer.abdulaziz.myrestapi.Models.MyTodo
import developer.abdulaziz.myrestapi.Retrofit.MyApiClient
import developer.abdulaziz.myrestapi.Utils.MyResource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MyViewModel : ViewModel() {
    private val liveData = MutableLiveData<MyResource<List<MyTodo>>>()
    fun getTodo(): MutableLiveData<MyResource<List<MyTodo>>> {
        val apiService = MyApiClient.getApi()
        viewModelScope.launch {
            liveData.postValue(MyResource.loading("Loading..."))
            try {
                coroutineScope {
                    val list = async {
                        apiService.getAllTodo()
                    }.await()
                    liveData.postValue(MyResource.success(list))
                }
            } catch (e: Exception) {
                liveData.postValue(MyResource.error(e.message))
            }
        }
        return liveData
    }
}