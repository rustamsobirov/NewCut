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
import me.ruyeo.newcut.databinding.ItemAppointmentWithMapBinding
import me.ruyeo.newcut.model.appointment.UpcomingAppointment
import me.ruyeo.newcut.model.appointment.UpcomingAppointmentMapModel

class UpcomingAppointmentWithMapAdapter:RecyclerView.Adapter<UpcomingAppointmentWithMapAdapter.Vh>() {
    private val dif = AsyncListDiffer(this, ITEM_DIFF)
    var spinnerClick: (() -> Unit)? = null

    inner class Vh(var binding:ItemAppointmentWithMapBinding):RecyclerView.ViewHolder(binding.root){
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
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingAppointmentWithMapAdapter.Vh {
        return Vh(
            ItemAppointmentWithMapBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Vh, position: Int) =holder.bind()

    override fun getItemCount(): Int = dif.currentList.size
    fun submitList(list: List<UpcomingAppointmentMapModel>) {
        dif.submitList(list)
    }


    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<UpcomingAppointmentMapModel>() {
            override fun areItemsTheSame(
                oldItem: UpcomingAppointmentMapModel,
                newItem: UpcomingAppointmentMapModel
            ): Boolean {
                return true
            }

            override fun areContentsTheSame(
                oldItem: UpcomingAppointmentMapModel,
                newItem: UpcomingAppointmentMapModel
            ): Boolean {
                return true
            }


        }
    }
}