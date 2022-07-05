package me.ruyeo.newcut.adapter.appointment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import me.ruyeo.newcut.App
import me.ruyeo.newcut.R
import me.ruyeo.newcut.data.model.Barbershop
import me.ruyeo.newcut.databinding.ItemUpcomingAppointmentBinding

class UpcomingAppointmentAdapter :  RecyclerView.Adapter<UpcomingAppointmentAdapter.Vh>(){
    private val dif = AsyncListDiffer(this, ITEM_DIFF)
    var spinnerClick: (() -> Unit)? = null
    var cancelClick: (()-> Unit)? = null

    inner class Vh(var binding: ItemUpcomingAppointmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(App.instance, R.array.times, R.layout.item_spinner_list)
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
                val request = dif.currentList[adapterPosition]
                tvBookedTime.text = request.workingTime
                tvUserName.text = request.name
                tvUserAddress.text = request.address
                tvTypeHaircut.text = request.description
                Glide.with(binding.root.context)
                    .load(request.pictures)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(binding.ivProfile)


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


    fun submitList(list: List<Barbershop>) {
        dif.submitList(list)
    }

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<Barbershop>() {
            override fun areItemsTheSame(
                oldItem: Barbershop,
                newItem: Barbershop
            ): Boolean {
                return true
            }

            override fun areContentsTheSame(
                oldItem: Barbershop,
                newItem: Barbershop
            ): Boolean {
                return true
            }

        }
    }
}
