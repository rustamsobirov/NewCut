package me.ruyeo.newcut.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.ruyeo.newcut.databinding.ItemPassAppointmentBinding
import me.ruyeo.newcut.model.PassAppointmentModel

class PassAppointmentAdapter : RecyclerView.Adapter<PassAppointmentAdapter.Vh>() {
    private val dif = AsyncListDiffer(this, ITEM_DIFF)
    var spinnerClick: (() -> Unit)? = null

    inner class Vh(var binding: ItemPassAppointmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {


            binding.apply {

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


    fun submitList(list: List<PassAppointmentModel>) {
        dif.submitList(list)
    }

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<PassAppointmentModel>() {
            override fun areItemsTheSame(
                oldItem: PassAppointmentModel,
                newItem: PassAppointmentModel
            ): Boolean {
                return true
            }

            override fun areContentsTheSame(
                oldItem: PassAppointmentModel,
                newItem: PassAppointmentModel
            ): Boolean {
                return true
            }


        }
    }
}
