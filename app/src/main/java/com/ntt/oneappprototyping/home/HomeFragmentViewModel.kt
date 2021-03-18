package com.ntt.oneappprototyping.home

import androidx.lifecycle.*
import com.ntt.oneappprototyping.networking.DataRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeFragmentViewModel(val repo: DataRepository): ViewModel() {
    val liveUser: LiveData<String> = Transformations.map(repo.userLiveData) {
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
    }.distinctUntilChanged()

    fun onButtonClicked() = viewModelScope.launch {
        repo.refreshUser()
    }
}