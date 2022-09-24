package developer.abdulaziz.myrestapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import developer.abdulaziz.myrestapi.Adapters.MyAdapter
import developer.abdulaziz.myrestapi.Utils.MyStatus
import developer.abdulaziz.myrestapi.ViewModel.MyViewModel
import developer.abdulaziz.myrestapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myViewModel: MyViewModel
    private lateinit var myAdapter: MyAdapter
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {

            myViewModel = ViewModelProvider(this@MainActivity)[MyViewModel::class.java]

            myViewModel.getTodo()
                .observe(this@MainActivity) {
                    when (it.myStatus) {
                        MyStatus.LOADING -> Log.d(TAG, "onCreate: Loading")
                        MyStatus.ERROR -> Log.d(TAG, "onCreate: Error ${it.message}")
                        MyStatus.SUCCESS -> {
                            Log.d(TAG, "onCreate: ${it.data}")
                            myAdapter = MyAdapter(it.data!!)
                            rv.adapter = myAdapter
                        }
                    }
                }
        }
    }
}