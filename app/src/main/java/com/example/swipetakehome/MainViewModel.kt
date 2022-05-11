package com.example.swipetakehome

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swipetakehome.service.SwipeApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel(): ViewModel() {

    // NETWORK
    private val url = "https://www.plugco.in/"
    private val retrofit = Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build()
    private val service = retrofit.create(SwipeApiService::class.java)

    // DATA UI
    private val _users = MutableLiveData<List<Profile>>()
    val users: LiveData<List<Profile>> = _users

    private val _matchCount = MutableLiveData<Int>(0)
    val matchCount: LiveData<Int> = _matchCount

    var currentProfileDisplayed: Profile? = null

    fun fetchUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = service.getAll().data
                _users.postValue(response)
            } catch (cause: Throwable) {
                Log.e("TAG", "FAILED TO FETCH PROFILES: ", cause)
            }
        }
    }

    fun checkForMatch() {
        if (currentProfileDisplayed?.is_match == true) {
            _matchCount.value = _matchCount.value!! + 1
        }
    }
}