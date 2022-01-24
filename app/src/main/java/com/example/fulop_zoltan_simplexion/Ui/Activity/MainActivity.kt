package com.example.fulop_zoltan_simplexion.Ui.Activity

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.fulop_zoltan_simplexion.Const.Consts
import com.example.fulop_zoltan_simplexion.Other.Utility
import com.example.fulop_zoltan_simplexion.R
import com.example.fulop_zoltan_simplexion.R.string.ApiKey
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var navController: NavController

    @Inject
    lateinit var utility: Utility

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = navHostFragment.findNavController()
        title = ""
        requestPermissions()
        navigateToRightFragment()

        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences(
                Consts.SHAREDPREF,
                Context.MODE_PRIVATE
            )
        val shareddValue = sharedPreferences.getString(Consts.API_KEY_STRING_KEY, "")

        if (shareddValue.isNullOrEmpty() || shareddValue.isNullOrBlank()) {
            val builer = MaterialAlertDialogBuilder(this)

            builer.setTitle(getString(R.string.ApikeyTitle))
                .setMessage(getString(R.string.FirstStartkDialogmessage))
                .setNegativeButton(
                    getString(R.string.No), DialogInterface.OnClickListener { dialog, which ->
                        navController.navigate(R.id.action_global_apikeyModifyDialogFragment)
                    }
                )
                .setPositiveButton(getString(R.string.yes),
                    DialogInterface.OnClickListener { dialog, which ->
                        Utility.saveKey(
                            getString(ApiKey), this
                        )
                        if (Utility.hasInternetConnection(this)) {
                            navController.navigate(R.id.action_global_currentFragment)
                        } else navController.navigate(R.id.action_global_errorFragment)
                    }).show()

        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.menu_Refresh -> {
                if (Utility.hasInternetConnection(this)) {
                    item.isEnabled = false
                    utility.loading(true,this)
                    navigateToRightFragment()
                    item.isEnabled = true

                } else {
                    navigateToRightFragment()
                }
                true

            }
            R.id.menu_ChangeKey -> {
                showKeyChangeingfragment()
                true
            }
            R.id.menu_Quit -> {
                makeQuitAlertDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    }


    private fun showKeyChangeingfragment() {
        navController.navigate(R.id.action_global_apikeyModifyDialogFragment)
    }

    private fun makeQuitAlertDialog() {
        val builder = MaterialAlertDialogBuilder(this)
        builder.setTitle(getString(R.string.exit))
            .setPositiveButton(
                getString(R.string.yes)
            ) { _, _ ->
                finish()
                exitProcess(0)
            }
            .setNegativeButton(
                getString(R.string.No)
            ) { _, _ ->

            }
            .setTitle(getString(R.string.ExitTitle))
            .setMessage(getString(R.string.exitdialog_message))
            .create()
            .show()


    }

    private fun navigateToRightFragment() {
        if (!utility.hasInternetConnection(this)) {

            navController.navigate(R.id.action_global_errorFragment)
        } else navController.navigate(R.id.action_global_currentFragment)


    }


    private fun requestPermissions() {

        if (utility.hasLocationPermissions(this)) {
            return
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.PermissionDialog),
                Consts.REQUEST_CODE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.PermissionDialog),
                Consts.REQUEST_CODE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


}