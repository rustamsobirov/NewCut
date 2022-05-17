package me.ruyeo.newcut.ui.client.filter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.newcut.R
import me.ruyeo.newcut.adapter.book.SpecialistProfileAdapter
import me.ruyeo.newcut.adapter.book.UserBookingAdapter
import me.ruyeo.newcut.databinding.FragmentFilterBarberBinding
import me.ruyeo.newcut.model.book.BookModel
import me.ruyeo.newcut.model.book.PopularArtistModel
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding


@AndroidEntryPoint
class FilterAndBookingBarberFragment : BaseFragment(R.layout.fragment_filter_barber) {
    private val binding by viewBinding { FragmentFilterBarberBinding.bind(it) }
    var rvSpecialist = ArrayList<PopularArtistModel>()
    var rvBookList = ArrayList<BookModel>()

    private val specialistProfileAdapter by lazy { SpecialistProfileAdapter() }
    private val userBookingAdapter by lazy { UserBookingAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        installRecyclerView()
        recyclerSpecialist()
        recyclerBookingList()

        reviewCountManager()

    }

    @SuppressLint("SetTextI18n")
    private fun reviewCountManager() {
        binding.tvResultCount.text = "Result found(${rvBookList.size})"
    }


    private fun installRecyclerView() {
        binding.apply {
            rvBook.adapter = userBookingAdapter
            rvSpecialist.adapter = specialistProfileAdapter
        }
    }

    private fun recyclerBookingList() {
        rvBookList.add(
            BookModel(
                "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
                "Green Apple",
                "6391 Elgin St. Celina, Delaware...",
                "5.0",
                "15 km"
            )
        )
        rvBookList.add(
            BookModel(
                "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
                "Green Apple",
                "6391 Elgin St. Celina, Delaware...",
                "5.0",
                "15 km"
            )
        )
        rvBookList.add(
            BookModel(
                "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
                "Green Apple",
                "6391 Elgin St. Celina, Delaware...",
                "5.0",
                "15 km"
            )
        )
        rvBookList.add(
            BookModel(
                "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
                "Green Apple",
                "6391 Elgin St. Celina, Delaware...",
                "5.0",
                "15 km"
            )
        )
        rvBookList.add(
            BookModel(
                "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
                "Green Apple",
                "6391 Elgin St. Celina, Delaware...",
                "5.0",
                "15 km"
            )
        )
        rvBookList.add(
            BookModel(
                "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
                "Green Apple",
                "6391 Elgin St. Celina, Delaware...",
                "5.0",
                "15 km"
            )
        )
        rvBookList.add(
            BookModel(
                "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
                "Green Apple",
                "6391 Elgin St. Celina, Delaware...",
                "5.0",
                "15 km"
            )
        )
        rvBookList.add(
            BookModel(
                "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
                "Green Apple",
                "6391 Elgin St. Celina, Delaware...",
                "5.0",
                "15 km"
            )
        )
        rvBookList.add(
            BookModel(
                "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
                "Green Apple",
                "6391 Elgin St. Celina, Delaware...",
                "5.0",
                "15 km"
            )
        )

        userBookingAdapter.submitList(rvBookList)

    }

    private fun recyclerSpecialist() {
        rvSpecialist.add(PopularArtistModel("https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef", "Lily", "Hair Stylist"))
        rvSpecialist.add(PopularArtistModel("https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef", "Lily", "Hair Stylist"))
        rvSpecialist.add(PopularArtistModel("https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef", "Lily", "Hair Stylist"))
        rvSpecialist.add(PopularArtistModel("https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef", "Lily", "Hair Stylist"))
        rvSpecialist.add(PopularArtistModel("https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef", "Lily", "Hair Stylist"))
        rvSpecialist.add(PopularArtistModel("https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef", "Lily", "Hair Stylist"))
        rvSpecialist.add(PopularArtistModel("https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef", "Lily", "Hair Stylist"))
        rvSpecialist.add(PopularArtistModel("https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef", "Lily", "Hair Stylist"))
        rvSpecialist.add(PopularArtistModel("https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef", "Lily", "Hair Stylist"))

        specialistProfileAdapter.submitList(rvSpecialist)
    }


}