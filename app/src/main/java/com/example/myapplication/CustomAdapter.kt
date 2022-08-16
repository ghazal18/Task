package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding.bind
import com.example.myapplication.databinding.CustomerItemViewBinding
import kotlinx.coroutines.NonDisposableHandle.parent

typealias CategoriesClickHandler = (Customers) -> Unit

class CustomAdapter(private val dataSet: ArrayList<Customers>,val listener: Listener) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView
        val mainCardView: CardView
        val textViewCode: TextView
        fun bind(customers:  Customers) {
            mainCardView.setOnClickListener {
                listener.onItemClick(customers.Name)
            }
        }
        init {
            textViewName = view.findViewById(R.id.textViewName)
            mainCardView = view.findViewById(R.id.mainCardView)
            textViewCode = view.findViewById(R.id.textViewCode)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.customer_item_view, viewGroup, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.textViewName.text = dataSet[position].Name
        viewHolder.textViewCode.text = " کد : ${dataSet[position].Code}"
        viewHolder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

}