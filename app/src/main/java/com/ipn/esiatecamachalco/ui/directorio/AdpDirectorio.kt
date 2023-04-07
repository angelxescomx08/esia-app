package com.ipn.esiatecamachalco.ui.directorio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ipn.esiatecamachalco.library.alert.clases.KModels
import com.ipn.esiatecamachalco.databinding.ItemDirectorioBinding
import com.ipn.esiatecamachalco.databinding.ItemDirectorioHeaderBinding

class AdpDirectorio: PagingDataAdapter<KModels.DirectorioModelROOM, RecyclerView.ViewHolder>(DiffCallback) {

    var onItemClicked: (KModels.DirectorioModelROOM) -> Unit = {}

    class BodyViewHolder(private var binding: ItemDirectorioBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: KModels.DirectorioModelROOM) {
            binding.txtNombre.text = item.nombre
            binding.txtCorreo.text = (item.correo ?: "-").replace(" ","\n")
            binding.txtCargo.text = item.cargo
            binding.txtExtension.text = if (item.ext == null) "No aplica" else item.ext.toString()
        }
    }

    class HeaderViewHolder(private var binding: ItemDirectorioHeaderBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: KModels.DirectorioModelROOM) {
            binding.txtHeader.text = item.nombre
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0)
            BodyViewHolder(
                ItemDirectorioBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            )
        else HeaderViewHolder(
            ItemDirectorioHeaderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item?.seccion?.equals("SECCION AUXILIAR",true) == true) {
            1
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { itemModel ->
            holder.itemView.setOnClickListener { onItemClicked(itemModel) }
            when (holder) {
                is BodyViewHolder ->  holder.bind(itemModel)
                is HeaderViewHolder ->  holder.bind(itemModel)
            }
        }
    }

    companion object {

        private val DiffCallback = object : DiffUtil.ItemCallback<KModels.DirectorioModelROOM>() {
            override fun areItemsTheSame(oldItem: KModels.DirectorioModelROOM, newItem: KModels.DirectorioModelROOM): Boolean {
                return oldItem.nombre == newItem.nombre
            }

            override fun areContentsTheSame(oldItem: KModels.DirectorioModelROOM, newItem: KModels.DirectorioModelROOM): Boolean {
                return oldItem == newItem
            }
        }
    }
}