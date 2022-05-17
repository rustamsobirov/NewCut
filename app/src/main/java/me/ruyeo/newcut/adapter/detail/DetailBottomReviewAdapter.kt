package me.ruyeo.newcut.adapter.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import me.ruyeo.newcut.databinding.DetailReviewItemBinding
import me.ruyeo.newcut.model.detail.ReviewModel


class DetailBottomReviewAdapter : RecyclerView.Adapter<DetailBottomReviewAdapter.VH>() {
    private val dif = AsyncListDiffer(this, ITEM_DIFF)

    inner class VH(private val binding: DetailReviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val details = dif.currentList[adapterPosition]
            Glide.with(binding.root.context)
                .load(details.userImageUrl)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.rvUserImage)
            binding.rvUserName.text = details.userName
            binding.daysAgo.text = details.daysAgo
            binding.starCount.rating = details.numStars!!.toFloat()
            binding.review.text = details.review
        }
    }

    fun submitList(list: List<ReviewModel>) {
        dif.submitList(list)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): DetailBottomReviewAdapter.VH {
        return VH(DetailReviewItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }


    override fun onBindViewHolder(holder: DetailBottomReviewAdapter.VH, position: Int) =
        holder.bind()

    override fun getItemCount(): Int = dif.currentList.size

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<ReviewModel>() {
            override fun areItemsTheSame(oldItem: ReviewModel, newItem: ReviewModel): Boolean {
//                oldItem.userImageUrl == newItem.userImageUrl
                return when {
                    oldItem.userImageUrl == newItem.userImageUrl -> true
                    oldItem.userName == newItem.userName -> true
                    oldItem.daysAgo == newItem.daysAgo -> true
                    oldItem.numStars == newItem.numStars -> true
                    oldItem.review == newItem.review -> true
                    else -> false
                }
            }

            override fun areContentsTheSame(oldItem: ReviewModel, newItem: ReviewModel): Boolean =
                oldItem == newItem
        }
    }

}