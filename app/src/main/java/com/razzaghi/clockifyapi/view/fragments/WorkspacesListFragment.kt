package com.razzaghi.clockifyapi.view.fragments

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.razzaghi.clockifyapi.R
import com.razzaghi.clockifyapi.adapters.WorkspaceAdapter
import com.razzaghi.clockifyapi.api.workspace.WorkspaceViewModel
import com.razzaghi.clockifyapi.model.Workspace
import com.razzaghi.clockifyapi.other.Constants
import com.razzaghi.clockifyapi.other.Constants.KEY_API
import com.razzaghi.clockifyapi.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_exit.*
import kotlinx.android.synthetic.main.workspaces_list_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WorkspacesListFragment : Fragment(R.layout.workspaces_list_fragment) {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    lateinit var workspaceAdapter: WorkspaceAdapter

    private val workspaceViewModel: WorkspaceViewModel by viewModels()
    val TAG = "WorkspacesListFragment"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: getApiKey ${getApiKey()}")
        setupRecyclerView()
        checkInternetAndLoad()

        reload()

        imgExit.setOnClickListener {
            showExitDialog()
        }
    }

    private fun checkInternetAndLoad() {
        if(isOnline()) {
            loadData()
            Log.i(TAG, "checkInternetAndLoad: NET IN ON")
        }else{
            showError()
            Log.i(TAG, "checkInternetAndLoad: NET IN OFF")
        }
    }

    private fun reload() {
        lineaError.setOnClickListener {
            checkInternetAndLoad()
        }
    }

    private fun showExitDialog() {

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_exit)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        dialog.apply {
            btnCancel.setOnClickListener {
                cancel()
            }
            btnExit.setOnClickListener {
                exitProfile()
                dismiss()
            }
        }
    }

    private fun exitProfile() {
        sharedPreferences.edit()
            .putString(KEY_API, "")
            .putBoolean(Constants.KEY_FIRST_TIME_TOGGLE, true)
            .apply()

/* sharedPreferences.edit().clear().apply()*/
        popUpTpLoginFragment()
    }

    private fun popUpTpLoginFragment() {

        val action =
            WorkspacesListFragmentDirections.actionClockifyListFragmentToLoginFragment()

        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.workspacesListFragment, true)
            .setPopExitAnim(R.anim.slide_out_right)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .build()
        findNavController().navigate(
            action,
            navOptions
        )
    }

    private fun loadData() {
        if (getApiKey() != null) {
            getApiKey()?.let { workspaceViewModel.getWorkspaces(it) }
            workspaceViewModel.workspaces.observe(viewLifecycleOwner, { response ->
                when (response) {
                    is Resource.Success -> {
                        Log.i(TAG, "loadData: Success")
                        hideLoading()
                        response.data?.let { workspacesResponse ->
                            workspaceAdapter.differ.submitList(workspacesResponse)

                           // Log.i(TAG, "loadData: $workspacesResponse")


                        }
                    }
                    is Resource.Error -> {
                        Log.i(TAG, "loadData: Error")
                        hideLoading()
                        showError()
                        response.message?.let { message ->
                            Log.i(TAG, "onViewCreated: $message")
                        }
                    }
                    is Resource.Loading -> {
                        Log.i(TAG, "loadData: Loading")
                        showLoading()
                    }
                }
            })


        } else {
            Snackbar.make(requireView(), "Please enter Api Key", Snackbar.LENGTH_SHORT).show()

        }

    }

    private fun getApiKey(): String? {
        return sharedPreferences.getString(KEY_API, "")
    }

    private fun showError() {

        CoroutineScope(Dispatchers.Main).launch {
            errorAnimation.playAnimation()
        }
        lineaError.visibility = View.VISIBLE
        dotsLoading.visibility = View.GONE
        recyclerViewWorkspace.visibility = View.GONE
    }

    private fun hideLoading() {
        dotsLoading.visibility = View.GONE
        recyclerViewWorkspace.visibility = View.VISIBLE
        errorAnimation.visibility = View.GONE
    }

    private fun showLoading() {
        dotsLoading.visibility = View.VISIBLE
        recyclerViewWorkspace.visibility = View.GONE
        lineaError.visibility = View.GONE
    }

    private fun setupRecyclerView() {
        workspaceAdapter = WorkspaceAdapter { selectedItem: Workspace  ->
            listItemClickedWorkspace(selectedItem )
        }
        recyclerViewWorkspace.apply {
            adapter = workspaceAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun listItemClickedWorkspace(selectedItem: Workspace ) {
        val action =
            WorkspacesListFragmentDirections.actionWorkspacesListFragmentToUserListFragment(
                selectedItem.id
            )
        findNavController().navigate(action)
    }


    private fun isOnline(): Boolean {
        val connectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
            .isConnectedOrConnecting
    }


}