package me.ruyeo.newcut.ui.client.profile

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.newcut.R
import me.ruyeo.newcut.adapter.profile.NotificationAdapter
import me.ruyeo.newcut.databinding.FragmentNotificationBinding
import me.ruyeo.newcut.model.profile.NotificationModel
import me.ruyeo.newcut.ui.BaseFragment
import me.ruyeo.newcut.utils.extensions.viewBinding

@AndroidEntryPoint
class NotificationFragment : BaseFragment(R.layout.fragment_notification) {
private val binding by viewBinding { FragmentNotificationBinding.bind(it) }
    private val adapter by lazy { NotificationAdapter() }
    var notifList = ArrayList<NotificationModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewInstall()
        notifRecyclerList()
        callBack()
    }


    private fun notifRecyclerList() {
        notifList.add(NotificationModel())
        notifList.add(NotificationModel())
        notifList.add(NotificationModel())
        adapter.submitList(notifList)

    }

    private fun recyclerViewInstall() {
        binding.notificationsRv.adapter = adapter

    }
    
    fun callBack(){
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

}