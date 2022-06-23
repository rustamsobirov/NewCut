package me.ruyeo.newcut.adapter.barber

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import me.ruyeo.newcut.databinding.ItemBookRequstBinding

/*
class BarberRequestAdapter : RecyclerView.Adapter<BarberRequestAdapter.Vh>() {
    private val dif = AsyncListDiffer(this, ITEM_DIFF)


    inner class Vh(var binding: ItemBookRequstBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.apply {
                val request = dif.currentList[adapterPosition]
                Glide.with(root.context).load(request.image)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(binding.ivProfile)

                tvUserName.text = request.name
                tvDescription.text = request.description
                tvTime.text = request.time
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemBookRequstBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Vh, position: Int) = holder.bind()

    override fun getItemCount(): Int = dif.currentList.size


    fun submitList(list: List<Request>) {
        dif.submitList(list)
    }

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<Request>() {
            override fun areItemsTheSame(
                oldItem: Request,
                newItem: Request
            ): Boolean {
                return true
            }

            override fun areContentsTheSame(
                oldItem: Request,
                newItem: Request
            ): Boolean {
                return true
            }


        }
    }
}*/
