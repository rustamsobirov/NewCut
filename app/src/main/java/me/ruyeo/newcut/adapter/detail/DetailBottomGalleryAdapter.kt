package me.ruyeo.newcut.adapter.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import me.ruyeo.newcut.databinding.DetailImageGalleryItemBinding
import me.ruyeo.newcut.model.detail.GalleryModel

class DetailBottomGalleryAdapter : RecyclerView.Adapter<DetailBottomGalleryAdapter.VH>() {
    private val dif = AsyncListDiffer(this, ITEM_DIFF)

    inner class VH(private val binding: DetailImageGalleryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val details = dif.currentList[adapterPosition]
            Glide.with(binding.root.context)
                .load(details.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.detailImageItem)
        }
    }

    fun submitList(list: List<GalleryModel>) {
        dif.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(DetailImageGalleryItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind()
    override fun getItemCount(): Int = dif.currentList.size

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<GalleryModel>() {
            override fun areItemsTheSame(oldItem: GalleryModel, newItem: GalleryModel): Boolean =
                oldItem.imageUrl == newItem.imageUrl

            override fun areContentsTheSame(oldItem: GalleryModel, newItem: GalleryModel): Boolean =
                oldItem == newItem
        }
    }
}