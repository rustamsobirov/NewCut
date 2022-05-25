package me.ruyeo.newcut.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import me.ruyeo.newcut.databinding.MapCuttersItemBinding
import me.ruyeo.newcut.model.map.MapBarberShopModel

class MapBarberShopAdapter : RecyclerView.Adapter<MapBarberShopAdapter.VH>() {
    private val dif = AsyncListDiffer(this, ITEM_DIFF)

    inner class VH(private val binding: MapCuttersItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.apply {
                val details = dif.currentList[adapterPosition]
                Glide.with(root.context)
                    .load(details.barberShopImage)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(binding.barberShopImages)
                barberShopName.text = details.barberShopName
                barberShopLocationName.text = details.barberShopLocationName
                barberShopRating.rating = details.barberShopStarCount.toFloat()
                barberShopLocationKM.text = details.barberShopLocationKM
            }
        }
    }

    fun submitList(list: List<MapBarberShopModel>) {
        dif.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(MapCuttersItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind()

    override fun getItemCount(): Int = dif.currentList.size

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<MapBarberShopModel>() {
            override fun areItemsTheSame(
                oldItem: MapBarberShopModel,
                newItem: MapBarberShopModel,
            ): Boolean = false

            override fun areContentsTheSame(
                oldItem: MapBarberShopModel,
                newItem: MapBarberShopModel,
            ): Boolean =
                oldItem == newItem
        }
    }
}