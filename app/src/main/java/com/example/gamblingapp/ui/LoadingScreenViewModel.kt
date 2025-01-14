package com.example.gamblingapp.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamblingapp.data.GamblingAppState
import com.example.gamblingapp.data.LoadingScreenState
import com.example.gamblingapp.data.LocalDataStoreManager
import com.example.gamblingapp.data.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoadingScreenViewModel(private val dataStore: LocalDataStoreManager) : ViewModel()
{
    private val _uiState = MutableStateFlow(LoadingScreenState())
    val uiState: StateFlow<LoadingScreenState> = _uiState.asStateFlow()

    private val _appState = MutableStateFlow(GamblingAppState())
    val appState: StateFlow<GamblingAppState> = _appState.asStateFlow()

    val email = dataStore.emailFlow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")
    val musicVolume = dataStore.volumeMusicFlow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.5f)
    val soundVolume = dataStore.volumeSoundFlow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.5f)
    val notificationsOn = dataStore.areNotificationsOnFlow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)
    val altThemeOn = dataStore.isAltThemeOnFlow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    //function sets up the progress and starts connection to the database
    init
    {
        _uiState.value = LoadingScreenState(progress = 0f)
        getUserData()
    }

    //starts connection to the "server" (which we can simulate by having the app just wait and return locally stored data)
    private fun getUserData()
    {
        viewModelScope.launch {
            val currentEmail = email.value

            if(currentEmail != "")
            {
                _appState.update { currentState ->
                    currentState.copy(email = currentEmail,)
                }
            }

            _appState.update { currentState ->
                currentState.copy(soundVolume = soundVolume.value, musicVolume = musicVolume.value, areNotificationsOn = notificationsOn.value, altThemeOn = altThemeOn.value)
            }

        }
    }

    fun updateProgress() : Boolean
    {
        var newProgress = _uiState.value.progress
        if(newProgress < 1.0f)
        {
            newProgress = _uiState.value.progress.plus(0.01f)
        }
        _uiState.value = LoadingScreenState(progress = newProgress)
        if(newProgress >= 1.0f)
            return true
        return false
    }

    fun saveUser(email: String)
    {
        viewModelScope.launch {
            dataStore.saveEmail(email)
        }
    }

    fun saveMusicVolume(volume: Float)
    {
        viewModelScope.launch {
            dataStore.saveMusicVolume(volume)
        }
    }

    fun saveSoundVolume(volume: Float)
    {
        viewModelScope.launch {
            dataStore.saveSoundVolume(volume)
        }
    }

    fun saveNotifications(notificationsOn: Boolean)
    {
        viewModelScope.launch {
            dataStore.saveAreNotificationsOn(notificationsOn)
        }
    }

    fun saveAltTheme(altThemeOn: Boolean)
    {
        viewModelScope.launch {
            dataStore.saveIsAltThemeOn(altThemeOn)
        }
    }
}