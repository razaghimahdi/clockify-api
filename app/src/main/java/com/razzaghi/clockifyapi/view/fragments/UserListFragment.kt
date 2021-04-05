package com.razzaghi.clockifyapi.view.fragments

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.razzaghi.clockifyapi.R
import com.razzaghi.clockifyapi.adapters.UserPhotoAdapter
import com.razzaghi.clockifyapi.api.user.UserViewModel
import com.razzaghi.clockifyapi.model.User
import com.razzaghi.clockifyapi.model.Workspace
import com.razzaghi.clockifyapi.other.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.user_list_fragment.*
import javax.inject.Inject


@AndroidEntryPoint
class UserListFragment : Fragment(R.layout.user_list_fragment) {

    val TAG = "UserListFragment"
    private val userViewModel: UserViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    lateinit var userPhotoAdapter: UserPhotoAdapter

    private val args: UserListFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        loadUser()



    }

    private fun loadUser() {

        Log.i(TAG, "loadUser args.workspaceId: " + args.workspaceId)
        userViewModel.prepareGetUsers( args.workspaceId,getApiKey()!!)
        userViewModel.users.observe(viewLifecycleOwner, {
            userPhotoAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            it.map {
                Log.i(TAG, "loadUser it: ${it} ")
            }
        })



    }

    private fun setupRecyclerView() {

         userPhotoAdapter = UserPhotoAdapter{ selectedItem: User ->
             listItemClickedUser(selectedItem )
         }
        recyclerViewUser.apply {
            adapter = userPhotoAdapter
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
        }

        userPhotoAdapter.addLoadStateListener {loadState->
             if ( loadState.source.refresh is LoadState.Loading){
                 showLoading()
             }
             if ( loadState.source.refresh is LoadState.NotLoading){
                 hideLoading()
             }
             if ( loadState.source.refresh is LoadState.Error){
                 showError()
             }

        }


    }

    private fun listItemClickedUser(selectedItem: User) {
        val action =
            UserListFragmentDirections.actionUserListFragmentToTimeEntityListFragment(
                args.workspaceId,
                selectedItem.id
            )
        findNavController().navigate(action)
    }

    private fun showLoading() {
        dotsLoading.visibility=View.VISIBLE
        recyclerViewUser.visibility=View.GONE
        lineaError.visibility=View.GONE
    }
    private fun hideLoading() {
        dotsLoading.visibility=View.GONE
        recyclerViewUser.visibility=View.VISIBLE
        lineaError.visibility=View.GONE
    }
    private fun showError() {
        dotsLoading.visibility=View.GONE
        recyclerViewUser.visibility=View.GONE
        lineaError.visibility=View.VISIBLE
    }

    private fun getApiKey(): String? {
        return sharedPreferences.getString(Constants.KEY_API, "")
    }

    private fun isOnline(): Boolean {
        val connectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
            .isConnectedOrConnecting
    }
}