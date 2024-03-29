package me.ruyeo.newcut.adapter.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.ruyeo.newcut.data.model.Notification
import me.ruyeo.newcut.databinding.ItemNotificationBinding

class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.Vh>() {
    private val dif = AsyncListDiffer(this, ITEM_DIFF)


    inner class Vh(var binding: ItemNotificationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val notification = dif.currentList[adapterPosition]

            binding.apply {
                notifyTv.text = notification.message
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemNotificationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    fun submitList(list: List<Notification>) {
        dif.submitList(list)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) = holder.bind()

    override fun getItemCount(): Int = dif.currentList.size

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<Notification>() {
            override fun areItemsTheSame(
                oldItem: Notification,
                newItem: Notification
            ): Boolean {
                return true
            }

            override fun areContentsTheSame(
                oldItem: Notification,
                newItem: Notification
            ): Boolean {
                return true
            }


        }
    }
}