package me.ruyeo.newcut.ui.client.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import me.ruyeo.newcut.R
import me.ruyeo.newcut.adapter.book.UserBookingAdapter
import me.ruyeo.newcut.databinding.FragmentFavoriteBinding
import me.ruyeo.newcut.databinding.FragmentHomeBinding
import me.ruyeo.newcut.model.book.BookModel
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.ui.client.home.HomeViewModel
import me.ruyeo.newcut.utils.extensions.viewBinding

class FavoriteFragment : BaseFragment(R.layout.fragment_favorite) {
    private val binding by viewBinding { FragmentFavoriteBinding.bind(it) }
    private val userFavoriteAdapter by lazy { UserBookingAdapter() }
    var rvFavoriteList = ArrayList<BookModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        installRecyclerView()
        recyclerFavoriteList()
    }


    private fun installRecyclerView() {
        binding.apply {
            rvFavorite.adapter = UserBookingAdapter()
        }
    }


    private fun recyclerFavoriteList() {
        rvFavoriteList.add(
            BookModel(
                "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
                "Green Apple",
                "6391 Elgin St. Celina, Delaware...",
                null,
                "15 km"
            )
        )
        rvFavoriteList.add(
            BookModel(
                "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
                "Green Apple",
                "6391 Elgin St. Celina, Delaware...",
                null,
                "15 km"
            )
        )
        rvFavoriteList.add(
            BookModel(
                "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
                "Green Apple",
                "6391 Elgin St. Celina, Delaware...",
                null,
                "15 km"
            )
        )
        rvFavoriteList.add(
            BookModel(
                "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
                "Green Apple",
                "6391 Elgin St. Celina, Delaware...",
                null,
                "15 km"
            )
        )
        rvFavoriteList.add(
            BookModel(
                "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
                "Green Apple",
                "6391 Elgin St. Celina, Delaware...",
                null,
                "15 km"
            )
        )
        rvFavoriteList.add(
            BookModel(
                "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
                "Green Apple",
                "6391 Elgin St. Celina, Delaware...",
                null,
                "15 km"
            )
        )
        rvFavoriteList.add(
            BookModel(
                "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
                "Green Apple",
                "6391 Elgin St. Celina, Delaware...",
                null,
                "15 km"
            )
        )
        rvFavoriteList.add(
            BookModel(
                "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
                "Green Apple",
                "6391 Elgin St. Celina, Delaware...",
                null,
                "15 km"
            )
        )
        rvFavoriteList.add(
            BookModel(
                "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
                "Green Apple",
                "6391 Elgin St. Celina, Delaware...",
                null,
                "15 km"
            )
        )

        userFavoriteAdapter.submitList(rvFavoriteList)

    }

}