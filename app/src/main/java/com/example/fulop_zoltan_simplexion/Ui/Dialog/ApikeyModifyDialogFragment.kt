package com.example.fulop_zoltan_simplexion.Ui.Dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.fulop_zoltan_simplexion.Other.Utility
import com.example.fulop_zoltan_simplexion.R
import com.example.fulop_zoltan_simplexion.databinding.FragmentApikeyDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*

class ApikeyModifyDialogFragment : DialogFragment() {


    private var _binding: FragmentApikeyDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_apikey_dialog, container, false)
        _binding = FragmentApikeyDialogBinding.bind(rootView)
        navController = navHostFragment.findNavController()

        binding.apply {
            dialogButtonCancel.setOnClickListener {
                dismiss()
            }

            dialogButtonApply.setOnClickListener {

                val sharedValue = Utility.getApikey(requireContext())

                if (!sharedValue.isNullOrBlank() || !sharedValue.isNullOrEmpty()) {

                    val builder = MaterialAlertDialogBuilder(requireContext())

                    builder.setTitle(getString(R.string.modify_api_text))
                        .setMessage(getString(R.string.api_key_modify_dialog_message))
                        .setNegativeButton(
                            getString(R.string.No),
                            DialogInterface.OnClickListener { dialog, which ->

                            }).setPositiveButton(
                            getString(R.string.yes),
                            DialogInterface.OnClickListener { dialog, which ->
                                Utility.saveKey(
                                    binding.apiKeyTextinput.text.toString(),
                                    requireContext()
                                )
                                if (Utility.hasInternetConnection(requireContext())) {
                                    navController.navigate(R.id.action_global_currentFragment)
                                } else navController.navigate(R.id.action_global_errorFragment)

                            }).show()
                } else {

                    Utility.saveKey(binding.apiKeyTextinput.text.toString(), requireContext())
                    if (Utility.hasInternetConnection(requireContext())) {
                        navController.navigate(R.id.action_global_currentFragment)
                    } else navController.navigate(R.id.action_global_errorFragment)
                }
            }
        }

        return rootView
    }
}