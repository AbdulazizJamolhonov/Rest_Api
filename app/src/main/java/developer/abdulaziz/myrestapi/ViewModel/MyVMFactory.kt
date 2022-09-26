package developer.abdulaziz.myrestapi.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import developer.abdulaziz.myrestapi.Repository.MyRepository

class MyVMFactory(private val myRepository: MyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java))
            return MyViewModel(myRepository) as T
        throw IllegalArgumentException("Error in MVVM")
    }
}