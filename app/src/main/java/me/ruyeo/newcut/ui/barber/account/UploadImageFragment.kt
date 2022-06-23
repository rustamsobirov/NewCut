package me.ruyeo.newcut.ui.barber.account

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.newcut.R
import me.ruyeo.newcut.databinding.FragmentUploadImageBinding
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding

@AndroidEntryPoint
class UploadImageFragment : BaseFragment(R.layout.fragment_upload_image) {
    private val binding by viewBinding { FragmentUploadImageBinding.bind(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initViews()


    }

    private fun initViews() {
        binding.itemImages.setOnClickListener {
            pickImageProfile()
        }

        binding.btnUpload.setOnClickListener {

        }
    }

    private fun pickImageProfile() {
        ImagePicker.with(this)
            .compress(1024)
            .crop()
            .galleryMimeTypes(
                mimeTypes = arrayOf(
                    "image/png",
                    "image/jpg",
                    "image/jpeg"
                )
            )//Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )  //Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!
                    val title = binding.tvTitle.text.toString().trim()
                    val description = binding.tvDescription.text.toString().trim()


               /*     binding.btnUpload.setOnClickListener {
                        val item = Gallery(
                            fileUri.toString(),
                            title,
                            description
                        )
                        UpgradeGuideObject.myGallery.add(item)

                        findNavController().popBackStack()
                    }
*/

                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Task cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }

}