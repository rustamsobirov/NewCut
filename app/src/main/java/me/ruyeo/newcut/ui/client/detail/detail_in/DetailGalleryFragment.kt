package me.ruyeo.newcut.ui.client.detail.detail_in

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.newcut.R
import me.ruyeo.newcut.adapter.detail.DetailBottomGalleryAdapter
import me.ruyeo.newcut.adapter.detail.manager.SpannedGridLayoutManager
import me.ruyeo.newcut.adapter.detail.manager.SpannedGridLayoutManager.GridSpanLookup
import me.ruyeo.newcut.adapter.detail.manager.SpannedGridLayoutManager.SpanInfo
import me.ruyeo.newcut.databinding.FragmentDetailGalleryBinding
import me.ruyeo.newcut.model.detail.DetailModel
import me.ruyeo.newcut.model.detail.GalleryModel
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding

@AndroidEntryPoint
class DetailGalleryFragment : BaseFragment(R.layout.fragment_detail_gallery) {
    private val binding by viewBinding { FragmentDetailGalleryBinding.bind(it) }
    private val detailImageAdapter by lazy { DetailBottomGalleryAdapter() }
    var photosList = ArrayList<GalleryModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        installRecyclerView()
        loadImageFromRecycler()
    }

    private fun installRecyclerView() {
        val manager = SpannedGridLayoutManager(
            object : GridSpanLookup {
                override fun getSpanInfo(position: Int): SpanInfo {
                    // Conditions for 2x2 items
                    return if (position % 6 == 0 || position % 6 == 4) {
                        SpanInfo(2, 2)
                    } else {
                        SpanInfo(1, 1)
                    }
                }
            },
            3,  // number of columns
            1f // how big is default item
        )
        binding.apply {
            galleryRecyclerView.adapter = detailImageAdapter
            galleryRecyclerView.layoutManager = manager

        }
    }

    private fun loadImageFromRecycler() {
        photosList.add(GalleryModel("https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Salon-image.png?alt=media&token=88fe9b51-1a16-442b-a994-bcdbf6b37559"))
        photosList.add(GalleryModel("https://beautyhealthtips.in/wp-content/uploads/2019/02/Quiff-Haircuts.jpg"))
        photosList.add(GalleryModel("https://i.pinimg.com/originals/e5/1f/e7/e51fe72785398953ab9095023bc98505.jpg"))
        photosList.add(GalleryModel("https://i.pinimg.com/736x/b2/06/1e/b2061e2dbd494778dfd620b2acb6ddd6.jpg"))
        photosList.add(GalleryModel("https://i0.wp.com/www.hadviser.com/wp-content/uploads/2020/08/18-cute-long-boho-style-CCgr-3aJKmM.jpg?resize=1017%2C1326&ssl=1"))
        photosList.add(GalleryModel("https://i.pinimg.com/originals/eb/f2/1c/ebf21cb5e88a06e51605da20481c376c.jpg"))
        photosList.add(GalleryModel("https://i2.wp.com/www.hadviser.com/wp-content/uploads/2020/08/1-easy-hairstyle-for-long-hair-CVEPXxtvo6F.jpg?resize=1080%2C1349&ssl=1"))
        photosList.add(GalleryModel("https://www.supercuts.co.uk/wp-content/uploads/2018/11/750-Mens-hair-banner.jpg"))
        photosList.add(GalleryModel("https://wallpaperaccess.com/full/3696056.jpg"))
        photosList.add(GalleryModel("https://assets.sentinelassam.com/h-upload/2020/12/08/179542-parted-high.webp?w=400&dpr=2.6"))
        photosList.add(GalleryModel("https://www.cutegirlshairstyles.com/wp-content/uploads/2020/12/DN0A9998-500x500.jpg"))
        detailImageAdapter.submitList(photosList)
    }
}