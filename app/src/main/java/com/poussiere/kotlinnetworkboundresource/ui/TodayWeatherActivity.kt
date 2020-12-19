package com.poussiere.kotlinnetworkboundresource.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.poussiere.kotlinnetworkboundresource.R
import com.poussiere.kotlinnetworkboundresource.databinding.ActivityTodayWeatherBinding
import com.poussiere.kotlinnetworkboundresource.viewmodels.TodayWeatherViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel


class TodayWeatherActivity : AppCompatActivity() {

    @FlowPreview
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    private val viewModel: TodayWeatherViewModel by viewModel()

    @FlowPreview
    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set databinding
        val binding = DataBindingUtil.setContentView<ActivityTodayWeatherBinding>(
            this,
            R.layout.activity_today_weather
        )
        binding.viewModel = viewModel

        //On click on search button, search a city filled into the edit text
        binding.searchButton.setOnClickListener {
            viewModel.triggerCitySearch(binding.weatherCityId.text.toString())
        }
    }
}