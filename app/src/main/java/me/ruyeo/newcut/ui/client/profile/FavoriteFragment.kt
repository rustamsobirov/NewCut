package me.ruyeo.newcut.ui.client.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.ruyeo.newcut.R
import me.ruyeo.newcut.SharedPref
import me.ruyeo.newcut.adapter.book.UserFavoriteAdapter
import me.ruyeo.newcut.databinding.FragmentFavoriteBinding
import me.ruyeo.newcut.model.book.BookModel
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.UiStateList
import me.ruyeo.newcut.utils.UiStateObject
import me.ruyeo.newcut.utils.extensions.viewBinding
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : BaseFragment(R.layout.fragment_favorite) {
    private val binding by viewBinding { FragmentFavoriteBinding.bind(it) }
    private val userFavoriteAdapter by lazy { UserFavoriteAdapter() }
    private val viewModel by viewModels<FavouriteViewModel>()

    @Inject
    lateinit var sharedPref: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getFavourites(sharedPref.getUser().id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        setupUI()
        setupObservers()

    }

    private fun setupUI() {
        binding.apply {
            rvFavorite.adapter = userFavoriteAdapter
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getFavouritesState.collect {
                    when(it){
                        is UiStateList.LOADING -> {
                            showProgress()
                        }
                        is UiStateList.SUCCESS -> {
                            hideProgress()
                            if (!it.data.isNullOrEmpty()){
                                userFavoriteAdapter.submitList(it.data)
                            }else{

                            }
                        }
                        is UiStateList.ERROR -> {
                            hideProgress()
                            showMessage(it.message)
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

}