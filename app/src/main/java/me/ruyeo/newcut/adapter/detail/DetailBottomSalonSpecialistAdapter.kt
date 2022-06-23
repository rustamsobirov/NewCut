package me.ruyeo.newcut.adapter.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import me.ruyeo.newcut.data.model.Barbershop
import me.ruyeo.newcut.databinding.DetailSalonSpecialistItemBinding

class DetailBottomSalonSpecialistAdapter :
    RecyclerView.Adapter<DetailBottomSalonSpecialistAdapter.VH>() {
    private val dif = AsyncListDiffer(this, ITEM_DIFF)
    var itemClick: (() -> Unit)? = null

    inner class VH(private val binding: DetailSalonSpecialistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val details = dif.currentList[adapterPosition]
            binding.apply {
                Glide.with(root.context)
                    .load(details.pictures)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(userImage)
                userName.text = details.name
                userDescription.text = details.description
                userImage.setOnClickListener {
                    itemClick!!.invoke()
                }
            }
        }
    }

    fun submitList(list: List<Barbershop>) {
        dif.submitList(list)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): DetailBottomSalonSpecialistAdapter.VH {
        return VH(DetailSalonSpecialistItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }


    override fun onBindViewHolder(holder: DetailBottomSalonSpecialistAdapter.VH, position: Int) =
        holder.bind()

    override fun getItemCount(): Int = dif.currentList.size

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<Barbershop>() {
            override fun areItemsTheSame(
                oldItem: Barbershop,
                newItem: Barbershop,
            ): Boolean =
                false

            override fun areContentsTheSame(
                oldItem: Barbershop,
                newItem: Barbershop,
            ): Boolean =
                oldItem == newItem
        }
    }

}