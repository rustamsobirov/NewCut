package me.ruyeo.newcut.ui.client.detail.detail_in

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.newcut.R
import me.ruyeo.newcut.adapter.detail.DetailBottomReviewAdapter
import me.ruyeo.newcut.databinding.FragmentDetailReviewBinding
import me.ruyeo.newcut.model.detail.ReviewModel
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding
import okhttp3.internal.notify
import okhttp3.internal.notifyAll

@AndroidEntryPoint
class DetailReviewFragment : BaseFragment(R.layout.fragment_detail_review) {
    private val binding by viewBinding { FragmentDetailReviewBinding.bind(it) }
    var reviewList = ArrayList<ReviewModel>()
    private val detailReviewAdapter by lazy { DetailBottomReviewAdapter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        installRecyclerView()
        recyclerList()
        reviewCountManager()
    }

    @SuppressLint("SetTextI18n")
    private fun reviewCountManager() {
        binding.reviewCountText.text = "All Reviews(${reviewList.size})"
    }

    private fun installRecyclerView() {
        binding.apply {
            reviewRecyclerView.adapter = detailReviewAdapter
        }
    }

    private fun recyclerList() {
        reviewList.add(ReviewModel("https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
            "Nurmuhammad", "2 days ago", 4.1, "Review Message"))
        reviewList.add(ReviewModel("https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
            "Nurmuhammad", "2 days ago", 4.1, "Review Message"))
        reviewList.add(ReviewModel("https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
            "Nurmuhammad", "2 days ago", 4.1, "Review Message"))
        reviewList.add(ReviewModel("https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
            "Nurmuhammad", "2 days ago", 4.1, "Review Message"))
        reviewList.add(ReviewModel("https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
            "Nurmuhammad", "2 days ago", 4.1, "Review Message"))
        reviewList.add(ReviewModel("https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
            "Nurmuhammad", "2 days ago", 4.1, "Review Message"))
        detailReviewAdapter.submitList(reviewList)
    }
}