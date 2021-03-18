package com.ntt.oneappprototyping.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntt.oneappprototyping.networking.DataRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeFragmentViewModel(val repo: DataRepository): ViewModel() {
    val liveUser: LiveData<String> = Transformations.map(repo.userLiveData) {
        Timber.d("ENZOOO Error $it")
        it.fold(
            succeeded = {
                return@map it.name + " " + it.surname
            },
            failed = {
                // Show a toast sending an event?!
                return@map "Si è verificato un errore"
            },
            loading = {
                // Do nothing or show a loading bar?
            }
        )
        "Initial Value"
    }

    fun onButtonClicked() = viewModelScope.launch {
        Timber.d("ENZOOO Qui")
        repo.refreshUser()
    }
}