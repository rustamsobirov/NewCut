package me.ruyeo.newcut.adapter.book
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import me.ruyeo.newcut.databinding.ItemPopularArtistBinding
import me.ruyeo.newcut.model.book.PopularArtistModel

class SpecialistProfileAdapter : RecyclerView.Adapter<SpecialistProfileAdapter.VH>() {
    private val dif = AsyncListDiffer(this, SpecialistProfileAdapter.ITEM_DIFF)

    inner class VH(private val binding: ItemPopularArtistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val specialists = dif.currentList[adapterPosition]
            Glide.with(binding.root.context)
                .load(specialists.profile)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.bookImageProfileStory)
            binding.textViewFullNameStory.text = specialists.userName
            binding.tvSpecialist.text = specialists.specialist
        }
    }

    fun submitList(list: List<PopularArtistModel>) {
        dif.submitList(list)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SpecialistProfileAdapter.VH {
        return VH(
            ItemPopularArtistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: SpecialistProfileAdapter.VH, position: Int) =
        holder.bind()

    override fun getItemCount(): Int = dif.currentList.size

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<PopularArtistModel>() {
            override fun areItemsTheSame(
                oldItem: PopularArtistModel,
                newItem: PopularArtistModel
            ): Boolean {
//                oldItem.userImageUrl == newItem.userImageUrl
                return when {
                    oldItem.profile == newItem.profile -> true
                    oldItem.userName == newItem.userName -> true
                    oldItem.specialist == newItem.specialist -> true
                    else -> false
                }
            }

            override fun areContentsTheSame(
                oldItem: PopularArtistModel,
                newItem: PopularArtistModel
            ): Boolean =
                oldItem == newItem
        }
    }

}