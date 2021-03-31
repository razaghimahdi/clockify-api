package com.razzaghi.clockifyapi.view.fragments

import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.razzaghi.clockifyapi.R
import com.razzaghi.clockifyapi.other.Constants
import com.razzaghi.clockifyapi.other.Constants.KEY_API
import com.razzaghi.clockifyapi.other.Constants.KEY_FIRST_TIME_TOGGLE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.login_fragment) {


    val TAG = "LoginFragment"


    @Inject
    lateinit var sharedPref: SharedPreferences


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playAnimation()

        checkIsFirstTime(savedInstanceState)

        tvContinue.setOnClickListener {
            if(checkPresidentName()){
                writePersonalDataSharedPref()
                //findNavController().navigate(R.id.action_loginFragment_to_clockifyListFragment)



                val action =
                    LoginFragmentDirections.actionLoginFragmentToClockifyListFragment()

                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.loginFragment, true)
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

        }


    }


    private fun playAnimation() {
        CoroutineScope(Dispatchers.Main).launch {
            //workspaceAnimation.playAnimation()

        }
        }

    private fun checkIsFirstTime(savedInstanceState: Bundle?) {

        if (!getFirstTimeOpen()) {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.loginFragment, true)
                .build()
            findNavController().navigate(
                R.id.action_loginFragment_to_clockifyListFragment,
                savedInstanceState,
                navOptions
            )
        }
    }

    fun getFirstTimeOpen(): Boolean {
        return sharedPref.getBoolean(Constants.KEY_FIRST_TIME_TOGGLE, true)
    }

    private fun writePersonalDataSharedPref()  {
        val name = edtApiKey.text.toString().trim()
        Log.i(TAG, "writePersonalDataSharedPref name: $name ")
        sharedPref.edit()
            .putString(KEY_API, name)
            .putBoolean(KEY_FIRST_TIME_TOGGLE, false)
            .apply()


    }



    private fun checkPresidentName(): Boolean {
        if (edtApiKey.text.isNullOrEmpty()||edtApiKey.text.toString().length<47){
            inputLayoutApi.apply {
                error = "لطفا کلید وب سرویس را به درستی وارد کنید!\nحداقل47 حرف"
                val tf = Typeface.createFromAsset(context.assets, "fonts/vazir.ttf")
                typeface = tf
            }
            return false
        }
        return true
    }



}