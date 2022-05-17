package me.ruyeo.newcut.ui.client.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.newcut.R
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding

@AndroidEntryPoint
class FilterBarberFragment : BaseFragment(R.layout.fragment_filter_barber) {
//    private var _binding: FragmentFilterBarberBinding? = null
//    private val binding get() = _binding!!
    private val binding by viewBinding { FilterBarberFragment.bind(it) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return super.onCreateView(inflater, container, savedInstanceState)
    }



    override fun onDestroyView() {
        super.onDestroyView()
    }


}