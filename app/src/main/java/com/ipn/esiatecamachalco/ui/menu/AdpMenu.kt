package com.ipn.esiatecamachalco.ui.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ipn.esiatecamachalco.databinding.ItemMenuBinding

class AdpMenu: ListAdapter<String, AdpMenu.ViewHolder>(DiffCallback) {

    var onItemClicked: (String, Int) -> Unit = {_,_ ->}

    inner class ViewHolder(private var binding: ItemMenuBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(model: String) {
            binding.btnTitulo.text = model
            binding.btnTitulo.setOnClickListener { onItemClicked(model,0) }
            binding.imgShare.setOnClickListener { onItemClicked(model,1) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMenuBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
    
}