package com.ipn.esiatecamachalco.ui.formatos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ipn.esiatecamachalco.databinding.ItemFormatosBinding

class AdpFormatos: ListAdapter<ModelFormato, AdpFormatos.ViewHolder>(DiffCallback) {

    var onItemClicked: (ModelFormato, Int) -> Unit = {_,_ -> }

    inner class ViewHolder(private var binding: ItemFormatosBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ModelFormato) {
            binding.txtFormato.text = model.nombre
            binding.imgCopy.setOnClickListener { onItemClicked(model,0) }
            binding.imgDrive.setOnClickListener { onItemClicked(model,1) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFormatosBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ModelFormato>() {
            override fun areItemsTheSame(oldItem: ModelFormato, newItem: ModelFormato): Boolean {
                return oldItem.nombre == newItem.nombre
            }

            override fun areContentsTheSame(oldItem: ModelFormato, newItem: ModelFormato): Boolean {
                return oldItem == newItem
            }
        }
    }
    
}