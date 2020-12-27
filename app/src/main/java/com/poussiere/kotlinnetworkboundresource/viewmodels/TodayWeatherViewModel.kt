package com.poussiere.kotlinnetworkboundresource.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poussiere.kotlinnetworkboundresource.model.local.WeatherForADay
import com.poussiere.kotlinnetworkboundresource.repositories.WeatherRepository
import com.poussiere.kotlinnetworkboundresource.services.remote.*
import com.poussiere.kotlinnetworkboundresource.ui.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@FlowPreview
class TodayWeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    //Viewmodel fields that will automatically update UI on change
    val displayedDate = ObservableField("")
    val displayedTemperature = ObservableField("")
    val displayedFeelsLike = ObservableField("")
    val displayedMinimum = ObservableField("")
    val displayedMaximum = ObservableField("")
    val displayedPressure = ObservableField("")
    val displayedHumidity = ObservableField("")
    val displayedWindSpeed = ObservableField("")
    val status = ObservableField(Status.COMPLETE)


    //Trigger UI refresh by passing a city and store last emitted value
    @ExperimentalCoroutinesApi
    private val todayWeatherChannel = ConflatedBroadcastChannel<String>()

    //Expose the refresh data trigger
    @ExperimentalCoroutinesApi
    fun triggerCitySearch(city: String) {
        todayWeatherChannel.offer(city)
    }

    init {
        loadTodaysWeatherForACity()
    }

    /**
     * Load data for today weather in a city
     */
    @InternalCoroutinesApi
    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun loadTodaysWeatherForACity() {
        todayWeatherChannel.asFlow()
            .onEach { status.set(Status.LOADING) }
            .flowOn(Dispatchers.Main)
            .flatMapLatest { city -> weatherRepository.getWeatherOrError(city) }
            .flowOn(Dispatchers.Default)
            .onEach { result -> result.updateUi()}
            .flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }

    /**
     * Update UI according to status and data
     */
    private fun FetchResult<WeatherForADay>.updateUi(){
      when(this) {
          is FetchSuccess -> {
              this.data.populateViewModel()
              status.set(Status.COMPLETE)
          }
          is FetchRefreshing -> {
              this.data.populateViewModel()
              status.set(Status.REFRESHING)
          }
          is FetchLoading -> {
              status.set(Status.LOADING)
          }
          is FetchError -> {
              status.set(Status.ERROR)
          }
      }
    }

    /**
     * Populate viewModel fields from a WeatherForADay object
     */
    private fun WeatherForADay.populateViewModel() {
        displayedDate.set(this.date.toString("dd/MM/yyyy"))
        displayedTemperature.set(this.temp.toString())
        displayedFeelsLike.set(this.feelsLike.toString())
        displayedMinimum.set(this.tempMin.toString())
        displayedMaximum.set(this.tempMax.toString())
        displayedPressure.set(this.pressure.toString())
        displayedHumidity.set(this.humidity.toString())
        displayedWindSpeed.set(this.windSpeed.toString())
    }
}