package me.ruyeo.newcut.ui.barber.account
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.newcut.R
import me.ruyeo.newcut.databinding.FragmentProfileGalleryBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding

@AndroidEntryPoint
class ProfileGalleryFragment : BaseFragment(R.layout.fragment_profile_gallery) {
    private val binding by viewBinding { FragmentProfileGalleryBinding.bind(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



  /*  private fun recyclerViewInstall() {
        binding.recyclerViewPortfolio.adapter = ProfileGalleryAdapter(galleryList, this)
    }

        findNavController().navigate(R.id.action_profileGalleryFragment_to_uploadImageFragment)
    */
}