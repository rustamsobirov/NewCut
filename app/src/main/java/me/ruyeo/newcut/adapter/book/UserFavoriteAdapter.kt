package me.ruyeo.newcut.adapter.book

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import me.ruyeo.newcut.databinding.ItemBookStylistBinding
import me.ruyeo.newcut.databinding.ItemFavoriteBinding
import me.ruyeo.newcut.model.book.BookModel

class UserFavoriteAdapter: RecyclerView.Adapter<UserFavoriteAdapter.VH>() {
    private val dif = AsyncListDiffer(this, UserFavoriteAdapter.ITEM_DIFF)

    inner class VH(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val books = dif.currentList[adapterPosition]
            Glide.with(binding.root.context)
                .load(books.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.ivProfile)
            binding.tvAddress.text = books.userAddress
            binding.tvName.text = books.userName
            binding.tvLength.text = books.distance
        }
    }

    fun submitList(list: List<BookModel>) {
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
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<BookModel>() {
            override fun areItemsTheSame(
                oldItem: BookModel,
                newItem: BookModel
            ): Boolean {
//                oldItem.userImageUrl == newItem.userImageUrl
                return when {
                    oldItem.imageUrl == newItem.imageUrl -> true
                    oldItem.userName == newItem.userName -> true
                    oldItem.distance == newItem.distance -> true
                    oldItem.userRating == newItem.userRating -> true
                    oldItem.userAddress == newItem.userAddress -> true
                    else -> false
                }
            }

            override fun areContentsTheSame(
                oldItem: BookModel,
                newItem: BookModel
            ): Boolean =
                oldItem == newItem
        }
    }
}
