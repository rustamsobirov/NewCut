package me.ruyeo.newcut.adapter.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.ruyeo.newcut.databinding.ItemServiceBinding
import me.ruyeo.newcut.model.filter.Service

class ItemServiceAdapter : RecyclerView.Adapter<ItemServiceAdapter.VH>() {
    private val dif = AsyncListDiffer(this, ITEM_DIFF)

    inner class VH(private val binding: ItemServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val item = dif.currentList[adapterPosition]
            binding.tvService.text = item.service
        }
    }

    fun submitList(list: List<Service>) {
        dif.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            ItemServiceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind()
    override fun getItemCount(): Int = dif.currentList.size

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<Service>() {
            override fun areItemsTheSame(oldItem: Service, newItem: Service): Boolean =
                oldItem.service == newItem.service

            override fun areContentsTheSame(oldItem: Service, newItem: Service): Boolean =
                oldItem == newItem
        }
    }

}