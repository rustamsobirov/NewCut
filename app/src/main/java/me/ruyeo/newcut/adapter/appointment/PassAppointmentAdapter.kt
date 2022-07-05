package me.ruyeo.newcut.adapter.appointment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import me.ruyeo.newcut.data.model.BarberPassedOrder
import me.ruyeo.newcut.databinding.ItemPassAppointmentBinding

class PassAppointmentAdapter : RecyclerView.Adapter<PassAppointmentAdapter.Vh>() {
    private val dif = AsyncListDiffer(this, ITEM_DIFF)
    var spinnerClick: (() -> Unit)? = null

    inner class Vh(var binding: ItemPassAppointmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.apply {
                val request = dif.currentList[adapterPosition]
                tvBookedTime.text = request.workingTime
                tvTypeHaircut.text = request.description
                tvUserAddress.text = request.address
                tvUserName.text = request.name
                Glide.with(binding.root.context)
                    .load(request.pictures)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(binding.ivProfile)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemPassAppointmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Vh, position: Int) = holder.bind()

    override fun getItemCount(): Int = dif.currentList.size


    fun submitList(list: List<BarberPassedOrder>) {
        dif.submitList(list)
    }

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<BarberPassedOrder>() {
            override fun areItemsTheSame(
                oldItem: BarberPassedOrder,
                newItem: BarberPassedOrder
            ): Boolean {
                return true
            }

            override fun areContentsTheSame(
                oldItem: BarberPassedOrder,
                newItem: BarberPassedOrder
            ): Boolean {
                return true
            }


        }
    }
}
