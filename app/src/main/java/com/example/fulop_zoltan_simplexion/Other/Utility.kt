package com.example.fulop_zoltan_simplexion.Other

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.fulop_zoltan_simplexion.Const.Consts
import com.example.fulop_zoltan_simplexion.Const.Consts.API_KEY_STRING_KEY
import com.example.fulop_zoltan_simplexion.Const.Consts.SHAREDPREF
import pub.devrel.easypermissions.EasyPermissions


object Utility {
    fun hasLocationPermissions(context: Context)=
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.Q){
            EasyPermissions.hasPermissions(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION

            )
        }
    else{
            EasyPermissions.hasPermissions(
                    context,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    )
        }


    fun hasInternetConnection(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    fun saveKey(key:String,context: Context){
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(SHAREDPREF,Context.MODE_PRIVATE)
        val edit:SharedPreferences.Editor=sharedPreferences.edit()
        edit.putString(API_KEY_STRING_KEY,key)
        edit.apply()
        edit.commit()

    }

    fun getApikey(context: Context):String{

        val sharedPreferences: SharedPreferences = context.getSharedPreferences(Consts.SHAREDPREF,Context.MODE_PRIVATE)
        val sharedIdValue = sharedPreferences.getString(Consts.API_KEY_STRING_KEY,"")
        return sharedIdValue.toString()
    }



     fun loading(load: Boolean,activity:Activity,) {

            if (load) {

                activity.window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            } else {

                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }
        }
    }
