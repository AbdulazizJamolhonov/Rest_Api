package developer.abdulaziz.myrestapi.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import developer.abdulaziz.myrestapi.Models.MyPostRequest
import developer.abdulaziz.myrestapi.Models.MyPostResponse
import developer.abdulaziz.myrestapi.Models.MyTodo
import developer.abdulaziz.myrestapi.Repository.MyRepository
import developer.abdulaziz.myrestapi.Utils.MyResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewModel(private val myRepository: MyRepository) : ViewModel() {
    private val getLiveData = MutableLiveData<MyResource<List<MyTodo>>>()
    fun getTodo(): MutableLiveData<MyResource<List<MyTodo>>> {
        viewModelScope.launch {
            getLiveData.postValue(MyResource.loading())
            try {
                coroutineScope {
                    val list =
                        withContext(Dispatchers.Default) {
                            myRepository.getAllTodo()
                        }
                    getLiveData.postValue(MyResource.success(list))
                }
            } catch (e: Exception) {
                getLiveData.postValue(MyResource.error(e.message))
            }
        }
        return getLiveData
    }

    private val addLiveData = MutableLiveData<MyResource<MyPostResponse>>()
    fun addTodo(myPostRequest: MyPostRequest): MutableLiveData<MyResource<MyPostResponse>> {
        viewModelScope.launch {
            addLiveData.postValue(MyResource.loading())
            try {
                coroutineScope {
                    val list =
                        withContext(Dispatchers.Default) {
                            myRepository.addTodo(myPostRequest)
                        }
                    addLiveData.postValue(MyResource.success(list))
                    getTodo()
                }
            } catch (e: Exception) {
                addLiveData.postValue(MyResource.error(e.message))
            }
        }
        return addLiveData
    }

    private val updateLiveData = MutableLiveData<MyResource<MyPostResponse>>()
    fun updateTodo(
        id: Int,
        myPostRequest: MyPostRequest
    ): MutableLiveData<MyResource<MyPostResponse>> {
        viewModelScope.launch {
            updateLiveData.postValue(MyResource.loading())
            try {
                coroutineScope {
                    val list =
                        withContext(Dispatchers.Default) {
                            myRepository.updateTodo(id, myPostRequest)
                        }
                    updateLiveData.postValue(MyResource.success(list))
                    getTodo()
                }
            } catch (e: Exception) {
                updateLiveData.postValue(MyResource.error(e.message))
            }
        }
        return updateLiveData
    }

    fun deleteTodo(id: Int) {
        viewModelScope.launch {
            try {
                coroutineScope {
                    launch {
                        myRepository.deleteTodo(id)
                    }
                    getTodo()
                }
            } catch (e: Exception) {
            }
        }
    }
}