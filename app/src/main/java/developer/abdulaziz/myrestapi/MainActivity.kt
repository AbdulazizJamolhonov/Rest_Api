package developer.abdulaziz.myrestapi

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import developer.abdulaziz.myrestapi.Adapters.MyAdapter
import developer.abdulaziz.myrestapi.Models.MyPostRequest
import developer.abdulaziz.myrestapi.Models.MyTodo
import developer.abdulaziz.myrestapi.Repository.MyRepository
import developer.abdulaziz.myrestapi.Retrofit.MyApiClient
import developer.abdulaziz.myrestapi.Utils.MyStatus
import developer.abdulaziz.myrestapi.ViewModel.MyVMFactory
import developer.abdulaziz.myrestapi.ViewModel.MyViewModel
import developer.abdulaziz.myrestapi.databinding.ActivityMainBinding
import developer.abdulaziz.myrestapi.databinding.ItemAddDialogBinding

class MainActivity : AppCompatActivity(), MyAdapter.MyClick {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myViewModel: MyViewModel
    private lateinit var myAdapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {

            myViewModel = ViewModelProvider(
                this@MainActivity,
                MyVMFactory(MyRepository(MyApiClient.getApi()))
            )[MyViewModel::class.java]

            myViewModel.getTodo()
                .observe(this@MainActivity) {
                    when (it.myStatus) {
                        MyStatus.LOADING -> {
                            myProgress.visibility = View.VISIBLE
                        }
                        MyStatus.ERROR -> {
                            Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                            myProgress.visibility = View.INVISIBLE
                        }
                        MyStatus.SUCCESS -> {
                            myAdapter = MyAdapter(it.data!!, this@MainActivity)
                            rv.adapter = myAdapter
                            myProgress.visibility = View.INVISIBLE
                        }
                    }
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val alert = AlertDialog.Builder(this).create()
        val item2 = ItemAddDialogBinding.inflate(layoutInflater).apply {
            save.setOnClickListener {
                val s = edtTitle.text.toString().trim()
                val m = edtAbout.text.toString().trim()
                val o = edtDate.text.toString().trim()
                val h = edtHolat.text.toString().trim()
                if (s.isNotEmpty() && m.isNotEmpty() && o.isNotEmpty() && h.isNotEmpty()) {
                    val myPostRequest = MyPostRequest(h, m, o, s)
                    myViewModel.addTodo(myPostRequest)
                        .observe(this@MainActivity) {
                            when (it.myStatus) {
                                MyStatus.LOADING -> {
                                    binding.myProgress.visibility = View.VISIBLE
                                    this.root.isClickable = false
                                }
                                MyStatus.ERROR -> {
                                    Toast.makeText(
                                        this@MainActivity,
                                        it.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    binding.myProgress.visibility = View.INVISIBLE
                                    this.root.isClickable = true
                                }
                                MyStatus.SUCCESS -> {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Added for ${it.data?.id}",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    binding.myProgress.visibility = View.INVISIBLE
                                    this.root.isClickable = true
                                }
                            }
                        }
                    alert.cancel()
                } else Toast.makeText(this@MainActivity, "Info is Empty", Toast.LENGTH_SHORT).show()
            }
        }
        alert.setView(item2.root)
        alert.show()

        return super.onOptionsItemSelected(item)
    }

    override fun menuClick(imageView: View, myTodo: MyTodo) {
        val popupMenu = PopupMenu(this, imageView)
        popupMenu.inflate(R.menu.menu_popup)
        popupMenu.setOnMenuItemClickListener { menu ->
            when (menu.itemId) {
                R.id.menu_edit -> editItem(myTodo)
                R.id.menu_delete -> {
                    myViewModel.deleteTodo(myTodo.id)
                    Toast.makeText(this, "${myTodo.id} was Deleted", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
        popupMenu.show()
    }

    private fun editItem(myTodo: MyTodo) {
        val dialog = AlertDialog.Builder(this).create()
        val item = ItemAddDialogBinding.inflate(layoutInflater).apply {
            edtTitle.setText(myTodo.sarlavha)
            edtAbout.setText(myTodo.matn)
            edtDate.setText(myTodo.oxirgi_muddat)
            edtHolat.setText(myTodo.holat)
            save.setOnClickListener { _ ->
                val s = edtTitle.text.toString().trim()
                val m = edtAbout.text.toString().trim()
                val o = edtDate.text.toString().trim()
                val h = edtHolat.text.toString().trim()
                if (s.isNotEmpty() && m.isNotEmpty() && o.isNotEmpty() && h.isNotEmpty()) {
                    val myPostRequest = MyPostRequest(h, m, o, s)
                    myViewModel.updateTodo(myTodo.id, myPostRequest)
                        .observe(this@MainActivity) {
                            when (it.myStatus) {
                                MyStatus.LOADING -> {
                                    binding.myProgress.visibility = View.VISIBLE
                                    this.root.isClickable = false
                                }
                                MyStatus.ERROR -> {
                                    Toast.makeText(
                                        this@MainActivity,
                                        it.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    binding.myProgress.visibility = View.INVISIBLE
                                    this.root.isClickable = true
                                    dialog.cancel()
                                }
                                MyStatus.SUCCESS -> {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "${it.data?.id} was Edited",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    binding.myProgress.visibility = View.INVISIBLE
                                    this.root.isClickable = true
                                    dialog.cancel()
                                }
                            }
                        }
                } else Toast.makeText(this@MainActivity, "Info is Empty", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.setView(item.root)
        dialog.show()
    }
}