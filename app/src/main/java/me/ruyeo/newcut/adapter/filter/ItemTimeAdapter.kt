package me.ruyeo.newcut.adapter.filter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.ruyeo.newcut.databinding.ItemTimeBinding
import me.ruyeo.newcut.model.filter.Time
class ItemTimeAdapter : RecyclerView.Adapter<ItemTimeAdapter.VH>() {
    private val dif = AsyncListDiffer(this, ITEM_DIFF)
    inner class VH(private val binding: ItemTimeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val item = dif.currentList[adapterPosition]
            binding.tvTime.text = item.time
        }
    }
    fun submitList(list: List<Time>) {
        dif.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            ItemTimeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind()
    override fun getItemCount(): Int = dif.currentList.size

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<Time>() {
            override fun areItemsTheSame(oldItem: Time, newItem: Time): Boolean =
                oldItem.time == newItem.time

            override fun areContentsTheSame(oldItem: Time, newItem: Time): Boolean =
                oldItem == newItem
        }
    }
}