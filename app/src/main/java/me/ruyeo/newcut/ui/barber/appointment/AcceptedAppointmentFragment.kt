package me.ruyeo.newcut.ui.barber.appointment

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.newcut.R
import me.ruyeo.newcut.databinding.FragmentAcceptedAppintmentBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding

/*
@AndroidEntryPoint
class AcceptedAppointmentFragment : BaseFragment(R.layout.fragment_accepted_appintment) {
    private val binding by viewBinding { FragmentAcceptedAppintmentBinding.bind(it) }
    var rvBookList = ArrayList<Request>()
    private val myAdapter by lazy { BarberAcceptedAdapter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        installRecyclerView()
        recyclerBookRequestList()
    }

    private fun recyclerBookRequestList() {
        rvBookList.add(
            Request(
                "https://media.istockphoto.com/photos/closeup-of-hairstylists-hands-cutting-strand-of-mans-hair-picture-id915640558?s=612x612",
                "Barber", "+998974697907", "10/10/2022 08:00"
            )
        )
        rvBookList.add(
            Request(
                "https://media.istockphoto.com/photos/closeup-of-hairstylists-hands-cutting-strand-of-mans-hair-picture-id915640558?s=612x612",
                "Barber", "+998974697907", "10/10/2022 08:00"
            )
        )
        rvBookList.add(
            Request(
                "https://media.istockphoto.com/photos/closeup-of-hairstylists-hands-cutting-strand-of-mans-hair-picture-id915640558?s=612x612",
                "Barber", "+998974697907", "10/10/2022 08:00"
            )
        )
        rvBookList.add(
            Request(
                "https://media.istockphoto.com/photos/closeup-of-hairstylists-hands-cutting-strand-of-mans-hair-picture-id915640558?s=612x612",
                "Barber", "+998974697907", "10/10/2022 08:00"
            )
        )
        rvBookList.add(
            Request(
                "https://media.istockphoto.com/photos/closeup-of-hairstylists-hands-cutting-strand-of-mans-hair-picture-id915640558?s=612x612",
                "Barber", "+998974697907", "10/10/2022 08:00"
            )
        )
        rvBookList.add(
            Request(
                "https://media.istockphoto.com/photos/closeup-of-hairstylists-hands-cutting-strand-of-mans-hair-picture-id915640558?s=612x612",
                "Barber", "+998974697907", "10/10/2022 08:00"
            )
        )
        rvBookList.add(
            Request(
                "https://media.istockphoto.com/photos/closeup-of-hairstylists-hands-cutting-strand-of-mans-hair-picture-id915640558?s=612x612",
                "Barber", "+998974697907", "10/10/2022 08:00"
            )
        )
        rvBookList.add(
            Request(
                "https://media.istockphoto.com/photos/closeup-of-hairstylists-hands-cutting-strand-of-mans-hair-picture-id915640558?s=612x612",
                "Barber", "+998974697907", "10/10/2022 08:00"
            )
        )
        rvBookList.add(
            Request(
                "https://media.istockphoto.com/photos/closeup-of-hairstylists-hands-cutting-strand-of-mans-hair-picture-id915640558?s=612x612",
                "Barber", "+998974697907", "10/10/2022 08:00"
            )
        )
        myAdapter.submitList(rvBookList)

    }

    private fun installRecyclerView() {
        binding.apply {
            rvRequest.adapter = myAdapter
        }
    }

}*/
