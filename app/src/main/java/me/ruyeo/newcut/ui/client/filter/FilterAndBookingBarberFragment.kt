package me.ruyeo.newcut.ui.client.filter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.slider.LabelFormatter
import com.google.android.material.slider.RangeSlider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.ruyeo.newcut.R
import me.ruyeo.newcut.adapter.book.SpecialistProfileAdapter
import me.ruyeo.newcut.adapter.book.UserBookingAdapter
import me.ruyeo.newcut.adapter.book.UserFavoriteAdapter
import me.ruyeo.newcut.adapter.filter.ItemServiceAdapter
import me.ruyeo.newcut.adapter.filter.ItemTimeAdapter
import me.ruyeo.newcut.databinding.FragmentFilterBarberBinding
import me.ruyeo.newcut.model.book.BookModel
import me.ruyeo.newcut.model.book.PopularArtistModel
import me.ruyeo.newcut.model.filter.Service
import me.ruyeo.newcut.model.filter.Time
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.ui.client.profile.FavouriteViewModel
import me.ruyeo.newcut.utils.UiStateList
import me.ruyeo.newcut.utils.extensions.viewBinding
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class FilterAndBookingBarberFragment : BaseFragment(R.layout.fragment_filter_barber) {
    private val binding by viewBinding { FragmentFilterBarberBinding.bind(it) }
    var rvSpecialist = ArrayList<PopularArtistModel>()
    var rvBookList = ArrayList<BookModel>()
    lateinit var rvService: RecyclerView
    lateinit var rangeSlider: RangeSlider
    lateinit var btnApply: Button
    lateinit var rvTime: RecyclerView
    lateinit var bottomSheetView: View
    private val serViceAdapter by lazy { ItemServiceAdapter() }
    private val specialistProfileAdapter by lazy { SpecialistProfileAdapter() }
    private val userBookingAdapter by lazy { UserBookingAdapter() }
    private val viewModel by viewModels<FilterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAllServices()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        installRecyclerView()
        recyclerSpecialist()
        recyclerBookingList()
        reviewCountManager()
        bottomSheetManager()
        initViews(view)




    }
    private fun initViews(view: View) {
        bottomSheetView = LayoutInflater.from(requireContext()).inflate(
            R.layout.filter_bottom_sheet, view.findViewById(R.id.bottomsheet)
        )
        rangeSlider = bottomSheetView.findViewById(R.id.rangeSlider)
        btnApply = bottomSheetView.findViewById(R.id.btnApply)
        rvService = bottomSheetView.findViewById(R.id.rv_service)
        rvTime = bottomSheetView.findViewById(R.id.rv_time)
    }


    private fun bottomSheetManager() {
        binding.ivFilter.setOnClickListener {
            val bottomSheetDialog =
                BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
            btnApply.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            setupObservers()
            recyclerTimeManager()
            rangeSliderListener()

            if (bottomSheetView.parent != null) {
                (bottomSheetView.getParent() as ViewGroup).removeView(bottomSheetView) // <- fix

            }
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()


        }
    }

    private fun rangeSliderListener() {
        rangeSlider.setLabelFormatter(object : LabelFormatter {
            override fun getFormattedValue(value: Float): String {
                return String.format(Locale.US, "%.01f km", value)
            }
        })
    }

    private fun recyclerTimeManager() {
        val timeAdapter = ItemTimeAdapter()
        rvTime.adapter = timeAdapter
        val timeList = ArrayList<Time>()
        timeList.add(Time("30 Minute"))
        timeList.add(Time("30 Minute"))
        timeList.add(Time("30 Minute"))
        timeList.add(Time("30 Minute"))
        timeList.add(Time("30 Minute"))
        timeList.add(Time("30 Minute"))
        timeList.add(Time("30 Minute"))
        timeAdapter.submitList(timeList)
    }

    private fun setupObservers(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getServiceState.collect{
                    when(it){
                        is UiStateList.LOADING ->{
                            showProgress()
                        }
                        is UiStateList.SUCCESS ->{
                            hideKeyboard()
                            if (!it.data.isNullOrEmpty()){
                                serViceAdapter.submitList(it.data)
                            }
                        }
                        is UiStateList.ERROR ->{
                            hideProgress()
                            showMessage(it.message)
                        }
                        else -> Unit
                    }
                }
            }
        }
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
        rvSpecialist.add(
            PopularArtistModel(
                "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
                "Lily",
                "Hair Stylist"
            )
        )
        rvSpecialist.add(
            PopularArtistModel(
                "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
                "Lily",
                "Hair Stylist"
            )
        )
        rvSpecialist.add(
            PopularArtistModel(
                "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
                "Lily",
                "Hair Stylist"
            )
        )
        rvSpecialist.add(
            PopularArtistModel(
                "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
                "Lily",
                "Hair Stylist"
            )
        )
        rvSpecialist.add(
            PopularArtistModel(
                "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
                "Lily",
                "Hair Stylist"
            )
        )
        rvSpecialist.add(
            PopularArtistModel(
                "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
                "Lily",
                "Hair Stylist"
            )
        )
        rvSpecialist.add(
            PopularArtistModel(
                "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
                "Lily",
                "Hair Stylist"
            )
        )
        rvSpecialist.add(
            PopularArtistModel(
                "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
                "Lily",
                "Hair Stylist"
            )
        )
        rvSpecialist.add(
            PopularArtistModel(
                "https://firebasestorage.googleapis.com/v0/b/wallpapers-23e0e.appspot.com/o/Cona_Mobile_512x512.png?alt=media&token=88054f45-ea0e-40d8-82d2-37b6ffb7ebef",
                "Lily",
                "Hair Stylist"
            )
        )

        specialistProfileAdapter.submitList(rvSpecialist)
    }


}