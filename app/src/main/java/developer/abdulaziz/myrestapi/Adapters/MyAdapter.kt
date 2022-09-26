package developer.abdulaziz.myrestapi.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import developer.abdulaziz.myrestapi.Models.MyTodo
import developer.abdulaziz.myrestapi.databinding.ItemRvBinding

class MyAdapter(private val list: List<MyTodo>, private val myClick: MyClick) :
    RecyclerView.Adapter<MyAdapter.VH>() {
    inner class VH(private val binding: ItemRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(myTodo: MyTodo) {
            binding.sarlavha.text = myTodo.sarlavha
            binding.holat.text = myTodo.holat
            binding.date.text = myTodo.oxirgi_muddat
            binding.popup.setOnClickListener { myClick.menuClick(it, myTodo) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) = holder.onBind(list[position])
    override fun getItemCount(): Int = list.size
    interface MyClick {
        fun menuClick(imageView: View, myTodo: MyTodo)
    }
}