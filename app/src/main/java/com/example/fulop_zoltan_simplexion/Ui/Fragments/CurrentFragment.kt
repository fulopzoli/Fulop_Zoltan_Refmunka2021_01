package com.example.fulop_zoltan_simplexion.Ui.Fragments


import android.Manifest
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import retrofit2.Callback
import com.bumptech.glide.Glide
import com.example.fulop_zoltan_simplexion.Const.Consts
import com.example.fulop_zoltan_simplexion.DataModels.Json.WeatherData
import com.example.fulop_zoltan_simplexion.Other.Utility
import com.example.fulop_zoltan_simplexion.R
import com.example.fulop_zoltan_simplexion.ViewModel.RetrofitViewModel
import com.example.fulop_zoltan_simplexion.databinding.FragmentCurrentBinding
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Response
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class CurrentFragment : Fragment(R.layout.fragment_current),
    LocationListener {

    private var _binding: FragmentCurrentBinding? = null
    private val binding get() = _binding!!

    private val viewmodel by viewModels<RetrofitViewModel>()

    @Inject
    lateinit var utility: Utility

    lateinit var locationManager: LocationManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_current, container, false)
        _binding = FragmentCurrentBinding.bind(view)
        getLocation()
        return view
    }


    override fun onLocationChanged(location: Location) {

        viewmodel.searchWeather(
            utility.getApikey(requireContext()),
            location.latitude.toString() + "," + location.longitude.toString(),
            getLang()
        )
        viewmodel.getData.observe(this, Observer {


            if (it is WeatherData) {

                Glide.with(this).load("https:" + it.current.condition.icon)
                    .into(binding.weatherImage)

                binding.apply {
                    binding.weatherImage.visibility = View.VISIBLE
                    cityTextview.text = it.location.name
                    temperatureText.text =
                        it.current.temp_c.toString() + getString(R.string.Celsius)
                    descriptionTextview.text = it.current.condition.text
                    windSpeedNumberTextview.text =
                        it.current.wind_kph.toString() + getString(R.string.kmh)
                    windDirectionNumberTextview.text = it.current.wind_dir
                    windpowrTextview.text = getString(R.string.wind_power)
                    windDirectionTextview.text = getString(R.string.wind_dir)


                }
            } else

                when (it) {
                    401 -> Toast.makeText(
                        requireContext(),
                        getString(R.string.Error) + getString(R.string.Error_401),
                        Toast.LENGTH_SHORT
                    ).show()
                    else -> Toast.makeText(
                        requireContext(),
                        getString(R.string.Error) + it.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }


        })
        stopUsingGps()

    }


    private fun stopUsingGps() {
        locationManager.removeUpdates(this)
    }


    private fun getLang(): String = Locale.getDefault().language


    private fun getLocation() {

        locationManager = requireContext().getSystemService(LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
