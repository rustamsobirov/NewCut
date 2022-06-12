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

class MapBarberShopAdapter() :
    RecyclerView.Adapter<MapBarberShopAdapter.VH>() {
    private val dif = AsyncListDiffer(this, ITEM_DIFF)
    var itemClick: ((Int) -> Unit)? = null

    inner class VH(private val binding: MapCuttersItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.apply {
                val details = dif.currentList[adapterPosition]
                Glide.with(root.context)
                    .load("https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Salon-image.png?alt=media&token=88fe9b51-1a16-442b-a994-bcdbf6b37559")
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(binding.barberShopImages)
                barberShopName.text = details.address
                barberShopLocationName.text = details.description
                barberShopRating.rating = details.rating
                barberShopLocationKM.text = "${String.format("%.02f",details.distance)} KM"

                mapCutterItem.setOnClickListener {
                    itemClick?.invoke(details.id)
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