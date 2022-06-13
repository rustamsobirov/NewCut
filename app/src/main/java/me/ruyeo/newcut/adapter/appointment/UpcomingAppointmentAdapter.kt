package me.ruyeo.newcut.adapter.appointment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.ruyeo.newcut.App
import me.ruyeo.newcut.R
import me.ruyeo.newcut.data.model.Order
import me.ruyeo.newcut.databinding.ItemUpcomingAppointmentBinding
import me.ruyeo.newcut.model.appointment.UpcomingAppointment

class UpcomingAppointmentAdapter :  RecyclerView.Adapter<UpcomingAppointmentAdapter.Vh>(){
    private val dif = AsyncListDiffer(this, ITEM_DIFF)
    var spinnerClick: (() -> Unit)? = null
    var cancelClick: (()-> Unit)? = null

    inner class Vh(var binding: ItemUpcomingAppointmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
                App.instance,
                R.array.times,
                R.layout.item_spinner_list
            )
            adapter.setDropDownViewResource(R.layout.item_spinner_list)

            binding.apply {
                spinner.adapter = adapter
                spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        spinnerClick?.invoke()
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {

                    }

                }

                bCancel.setOnClickListener{
                    cancelClick?.invoke()
                }
            }
        }
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemUpcomingAppointmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Vh, position: Int) = holder.bind()

    override fun getItemCount(): Int = dif.currentList.size


    fun submitList(list: List<Order>) {
        dif.submitList(list)
    }

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<Order>() {
            override fun areItemsTheSame(
                oldItem: Order,
                newItem: Order
            ): Boolean {
                return true
            }

            override fun areContentsTheSame(
                oldItem: Order,
                newItem: Order
            ): Boolean {
                return true
            }

        }
    }
}
