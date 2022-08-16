package com.example.myapplication


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.CustomerItemViewBinding


class CustomAdapter(private val dataSet: ArrayList<Customers>, val listener: Listener) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: CustomerItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(customers: Customers) {
            binding.apply {
                textViewName.text = customers.Name
                textViewCode.text = customers.Code
                mainCardView.setOnClickListener {
                    listener.onItemClick(customers.Name)
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = CustomerItemViewBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

}