package com.razzaghi.clockifyapi.view.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.razzaghi.clockifyapi.R
import com.razzaghi.clockifyapi.adapters.TimeEntryAdapter
import com.razzaghi.clockifyapi.adapters.UserPhotoAdapter
import com.razzaghi.clockifyapi.api.timeEntry.TimeEntryViewModel
import com.razzaghi.clockifyapi.api.user.UserViewModel
import com.razzaghi.clockifyapi.model.User
import com.razzaghi.clockifyapi.other.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.time_entity_fragment.* 
import javax.inject.Inject

@AndroidEntryPoint
class TimeEntityListFragment:Fragment(R.layout.time_entity_fragment) {


    val TAG = "TimeEntityListFragment"
    private val timeEntryViewModel: TimeEntryViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    lateinit var timeEntryAdapter: TimeEntryAdapter

    private val args: TimeEntityListFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        loadUser()



    }

    private fun loadUser() {

        Log.i(TAG, "loadUser args.workspaceId: " + args.workspaceId)
        Log.i(TAG, "loadUser args.userId: " + args.userId)
        timeEntryViewModel.prepareGetTimeEntries( args.workspaceId,getApiKey()!!,args.userId)
        timeEntryViewModel.timeEntries.observe(viewLifecycleOwner, {
            timeEntryAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            it.map {
                Log.i(TAG, "loadUser it: ${it} ")
            }
        })



    }

    private fun setupRecyclerView() {

        timeEntryAdapter = TimeEntryAdapter()
        recyclerViewTimeEntity.apply {
            adapter = timeEntryAdapter
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
        }

        timeEntryAdapter.addLoadStateListener {loadState->
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

    private fun showLoading() {
        dotsLoading.visibility=View.VISIBLE
        recyclerViewTimeEntity.visibility=View.GONE
        lineaError.visibility=View.GONE
    }
    private fun hideLoading() {
        dotsLoading.visibility=View.GONE
        recyclerViewTimeEntity.visibility=View.VISIBLE
        lineaError.visibility=View.GONE
    }
    private fun showError() {
        dotsLoading.visibility=View.GONE
        recyclerViewTimeEntity.visibility=View.GONE
        lineaError.visibility=View.VISIBLE
    }

    private fun getApiKey(): String? {
        return sharedPreferences.getString(Constants.KEY_API, "")
    }
}