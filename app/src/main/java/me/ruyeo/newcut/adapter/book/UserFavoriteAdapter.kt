package me.ruyeo.newcut.adapter.book

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import me.ruyeo.newcut.data.model.Barbershop
import me.ruyeo.newcut.databinding.ItemBookStylistBinding
import me.ruyeo.newcut.databinding.ItemFavoriteBinding
import me.ruyeo.newcut.model.book.BookModel

class UserFavoriteAdapter : RecyclerView.Adapter<UserFavoriteAdapter.VH>() {
    private val dif = AsyncListDiffer(this, UserFavoriteAdapter.ITEM_DIFF)

    inner class VH(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val books = dif.currentList[adapterPosition]
            Glide.with(binding.root.context)
                .load(books.pictures)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.ivProfile)
            binding.tvAddress.text = books.address
            binding.tvName.text = books.name
            binding.tvLength.text = books.distance.toString()
        }
    }

    fun submitList(list: List<Barbershop>) {
        dif.submitList(list)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): UserFavoriteAdapter.VH {
        return VH(
            ItemFavoriteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: UserFavoriteAdapter.VH, position: Int) =
        holder.bind()

    override fun getItemCount(): Int = dif.currentList.size

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<Barbershop>() {
            override fun areItemsTheSame(
                oldItem: Barbershop,
                newItem: Barbershop
            ): Boolean =
                oldItem.isClosed == newItem.isClosed && oldItem.distance == newItem.distance


            override fun areContentsTheSame(
                oldItem: Barbershop,
                newItem: Barbershop
            ): Boolean =
                oldItem == newItem
        }
    }
}
