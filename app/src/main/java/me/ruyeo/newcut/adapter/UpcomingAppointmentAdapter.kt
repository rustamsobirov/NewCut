package me.ruyeo.newcut.adapter

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
import me.ruyeo.newcut.databinding.DetailImageItemBinding
import me.ruyeo.newcut.databinding.ItemUpcomingAppointmentBinding
import me.ruyeo.newcut.model.DetailModel
import me.ruyeo.newcut.model.UpcomingAppointment

class UpcomingAppointmentAdapter : RecyclerView.Adapter<UpcomingAppointmentAdapter.Vh>() {
    private val dif = AsyncListDiffer(this, ITEM_DIFF)
    var spinnerClick: (() -> Unit)? = null

    inner class Vh(var binding: ItemUpcomingAppointmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val adapter:  ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(App.instance,R.array.times,android.R.layout.simple_spinner_item)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            binding.apply {
                spinner.adapter = adapter
                spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        spinnerClick?.invoke()
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {

                    }

                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemUpcomingAppointmentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) = holder.bind()

    override fun getItemCount(): Int = dif.currentList.size


    fun submitList(list: List<UpcomingAppointment>) {
        dif.submitList(list)
    }

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<UpcomingAppointment>() {
            override fun areItemsTheSame(
                oldItem: UpcomingAppointment,
                newItem: UpcomingAppointment
            ): Boolean {
                return true
            }

            override fun areContentsTheSame(
                oldItem: UpcomingAppointment,
                newItem: UpcomingAppointment
            ): Boolean {
                return true
            }

        }
    }
}
