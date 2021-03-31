package com.razzaghi.clockifyapi.view.fragments

import android.app.Dialog
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import com.razzaghi.clockifyapi.R
import com.razzaghi.clockifyapi.adapters.WorkspaceAdapter
import com.razzaghi.clockifyapi.api.ClockifyViewModel
import com.razzaghi.clockifyapi.model.HourlyRate
import com.razzaghi.clockifyapi.model.Workspace
import com.razzaghi.clockifyapi.other.Constants
import com.razzaghi.clockifyapi.other.Constants.KEY_API
import com.razzaghi.clockifyapi.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.clockify_list_fragment.*
import kotlinx.android.synthetic.main.layout_exit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class ClockifyListFragment : Fragment(R.layout.clockify_list_fragment) {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    lateinit var workspaceAdapter: WorkspaceAdapter

    private val clockifyViewModel: ClockifyViewModel by viewModels()
    val TAG = "ClockifyListFragment"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: getApiKey ${getApiKey()}")
        setupRecyclerView()
        loadData()

        reload()

        imgExit.setOnClickListener {
            showExitDialog()
        }
    }

    private fun reload() {
        errorAnimation.setOnClickListener {
            loadData()
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
            ClockifyListFragmentDirections.actionClockifyListFragmentToLoginFragment()

        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.clockifyListFragment, true)
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
            getApiKey()?.let { clockifyViewModel.getWorkspaces(it) }
            clockifyViewModel.workspaces.observe(viewLifecycleOwner, { response ->
                when (response) {
                    is Resource.Success -> {
                        hideLoading()
                        response.data?.let { workspacesResponse ->
                            workspaceAdapter.differ.submitList(workspacesResponse)

                            Log.i(TAG, "loadData: $workspacesResponse")


                        }
                    }
                    is Resource.Error -> {
                        hideLoading()
                        showError()
                        response.message?.let { message ->
                            Log.i(TAG, "onViewCreated: $message")
                        }
                    }
                    is Resource.Loading -> {
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
        errorAnimation.visibility = View.VISIBLE
        dotsLoading.visibility = View.GONE
        linearMainFragment.visibility = View.GONE
    }

    private fun hideLoading() {
        dotsLoading.visibility = View.GONE
        linearMainFragment.visibility = View.VISIBLE
        errorAnimation.visibility = View.GONE
    }

    private fun showLoading() {
        dotsLoading.visibility = View.VISIBLE
        linearMainFragment.visibility = View.GONE
        errorAnimation.visibility = View.GONE
    }


    private fun setupRecyclerView() {
        workspaceAdapter = WorkspaceAdapter()
        recyclerViewWorkspace.apply {
            adapter = workspaceAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}