package me.ruyeo.newcut.adapter.barber

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.ruyeo.newcut.databinding.ItemPortfolioBinding
import me.ruyeo.newcut.databinding.ItemPortfolioPlusImageBinding

/*
class ProfileGalleryAdapter(var items: ArrayList<Gallery>, var listener: ItemListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_POST = 0
    val ITEM_POST_FOOTER = 1

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return ITEM_POST_FOOTER

        return ITEM_POST
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_POST_FOOTER) {
            val binding =
                ItemPortfolioPlusImageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return FooterViewHolder(binding)
        }
        val binding =
            ItemPortfolioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TripViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is TripViewHolder) {
            holder.bind()
        }

        if (holder is FooterViewHolder) {
            holder.bind()
        }

    }

    inner class TripViewHolder(val binding: ItemPortfolioBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val gallery: Gallery = items[items.size - adapterPosition - 1]
            binding.apply {
                Glide.with(itemImages.context).load(gallery.image).into(itemImages)
                tvTitle.text = gallery.title
                tvDescription.text = gallery.description
            }
        }
    }

    inner class FooterViewHolder(val binding: ItemPortfolioPlusImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.apply {
                binding.icPluss.setOnClickListener {
                    listener.listener()
                }

            }
        }
    }

    interface ItemListener{
            fun listener()
        }

}*/
