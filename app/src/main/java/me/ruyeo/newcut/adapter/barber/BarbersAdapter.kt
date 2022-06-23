package me.ruyeo.newcut.adapter.barber

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.ruyeo.newcut.databinding.ItemBarbersBinding

/*
class BarbersAdapter : RecyclerView.Adapter<BarbersAdapter.VH>() {
    private val dif = AsyncListDiffer(this, ITEM_DIFF)

    inner class VH(private val binding: ItemBarbersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val details = dif.currentList[adapterPosition]
            */
/*Glide.with(binding.root.context)
                .load(details.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.detailImageItem)*//*

        }
    }

    fun submitList(list: List<Barber>) {
        dif.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemBarbersBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind()

    override fun getItemCount(): Int = dif.currentList.size

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<Barber>() {
            override fun areItemsTheSame(oldItem: Barber, newItem: Barber): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Barber, newItem: Barber): Boolean =
                oldItem == newItem
        }
    }
}*/
