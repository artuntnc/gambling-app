package com.example.gamblingapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamblingapp.data.GamblingAppState
import com.example.gamblingapp.data.LoadingScreenState
import com.example.gamblingapp.data.LocalData
import com.example.gamblingapp.data.LocalDataRepository
import com.example.gamblingapp.data.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoadingScreenViewModel (private val localData: LocalDataRepository) : ViewModel()
{
    private val _uiState = MutableStateFlow(LoadingScreenState())
    val uiState: StateFlow<LoadingScreenState> = _uiState.asStateFlow()

    private val _appState = MutableStateFlow(GamblingAppState())
    val appState: StateFlow<GamblingAppState> = _appState.asStateFlow()

    private var dataLoaded: Boolean = false

    //function sets up the progress and starts connection to the database
    init
    {
        _uiState.value = LoadingScreenState(progress = 0f)
        getUserData()
    }

    fun getEmail(): String
    {
        return _appState.value.email
    }

    fun getTheme(): Boolean
    {
        return _appState.value.altThemeOn
    }

    fun getNotifications(): Boolean
    {
        return _appState.value.areNotificationsOn
    }
    fun getSoundVolume(): Float
    {
        return _appState.value.soundVolume
    }
    fun getMusicVolume(): Float
    {
        return _appState.value.musicVolume
    }

    //starts connection to the "server" (which we can simulate by having the app just wait and return locally stored data)
    private fun getUserData()
    {
        viewModelScope.launch {

            if(localData.doesExist())
            {
                val settingsFlow = localData.getLocalDataStream()
                val settings = settingsFlow.first()

                _appState.update { currentState ->
                    currentState.copy(email = settings.email,)
                }

                _appState.update { currentState ->
                    currentState.copy(
                        soundVolume = settings.soundVolume,
                        musicVolume = settings.musicVolume,
                        areNotificationsOn = settings.areNotificationsOn,
                        altThemeOn = settings.isAltThemeOn
                    )
                }
            }
            else
            {
                localData.insertLocalData(
                    LocalData(
                        _appState.value.musicVolume,
                        _appState.value.soundVolume,
                        "",
                        _appState.value.altThemeOn,
                        _appState.value.areNotificationsOn
                    )
                )
            }

            dataLoaded = true
        }
    }

    fun updateProgress() : Boolean
    {
        var newProgress = _uiState.value.progress
        if(newProgress < 1.0f)
        {
            newProgress = _uiState.value.progress.plus(0.003f)
        }
        _uiState.value = LoadingScreenState(progress = newProgress)
        return newProgress >= 1.0f && dataLoaded
    }

    fun saveUser(email: String)
    {
        viewModelScope.launch {
            val settingsFlow = localData.getLocalDataStream()
            val settings = settingsFlow.first()

            localData.deleteLocalData(
                localData = settings
            )

            localData.insertLocalData(
                LocalData(
                    _appState.value.musicVolume,
                    _appState.value.soundVolume,
                    email,
                    _appState.value.altThemeOn,
                    _appState.value.areNotificationsOn
                )
            )
        }
    }

    fun saveMusicVolume(volume: Float)
    {
        viewModelScope.launch {
            localData.updateLocalData(
                LocalData(
                    volume,
                    _appState.value.soundVolume,
                    _appState.value.email,
                    _appState.value.altThemeOn,
                    _appState.value.areNotificationsOn
                )
            )
        }
    }

    fun saveSoundVolume(volume: Float)
    {
        viewModelScope.launch {
            localData.updateLocalData(
                LocalData(
                    _appState.value.musicVolume,
                    volume,
                    _appState.value.email,
                    _appState.value.altThemeOn,
                    _appState.value.areNotificationsOn
                )
            )
        }
    }

    fun saveNotifications(notificationsOn: Boolean)
    {
        viewModelScope.launch {
            localData.updateLocalData(
                LocalData(
                    _appState.value.musicVolume,
                    _appState.value.soundVolume,
                    _appState.value.email,
                    _appState.value.altThemeOn,
                    notificationsOn
                )
            )
        }
    }

    fun saveAltTheme(altThemeOn: Boolean)
    {
        viewModelScope.launch {
            localData.updateLocalData(
                LocalData(
                    _appState.value.musicVolume,
                    _appState.value.soundVolume,
                    _appState.value.email,
                    altThemeOn,
                    _appState.value.areNotificationsOn
                )
            )
        }
    }
}

/*class LoadingScreenViewModelFactory(
    private val dataStoreManager: LocalDataStoreManager
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoadingScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoadingScreenViewModel(dataStoreManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/