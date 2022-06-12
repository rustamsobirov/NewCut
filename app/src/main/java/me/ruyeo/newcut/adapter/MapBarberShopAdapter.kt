package me.ruyeo.newcut.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import me.ruyeo.newcut.data.model.Barbershop
import me.ruyeo.newcut.databinding.MapCuttersItemBinding
import me.ruyeo.newcut.model.map.MapBarberShopModel

class MapBarberShopAdapter() :
    RecyclerView.Adapter<MapBarberShopAdapter.VH>() {
    private val dif = AsyncListDiffer(this, ITEM_DIFF)
    var itemClick: (() -> Unit)? = null

    inner class VH(private val binding: MapCuttersItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.apply {
                val details = dif.currentList[adapterPosition]
               /* Glide.with(root.context)
                    .load(details.)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(binding.barberShopImages)*/ // we need from bekend
                barberShopName.text = details.name
                barberShopLocationName.text = details.address
                barberShopRating.rating = 3.2f // we need from bekend
                barberShopLocationKM.text = "3.2KM" // we need from bekend

                mapCutterItem.setOnClickListener {
                    itemClick?.invoke()
                }

            }
        }
    }


    fun submitList(list: List<Barbershop>) {
        dif.submitList(list)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            MapCuttersItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind()

    override fun getItemCount(): Int = dif.currentList.size

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<Barbershop>() {
            override fun areItemsTheSame(
                oldItem: Barbershop,
                newItem: Barbershop,
            ): Boolean = false

            override fun areContentsTheSame(
                oldItem: Barbershop,
                newItem: Barbershop,
            ): Boolean =
                oldItem == newItem
        }
    }
}