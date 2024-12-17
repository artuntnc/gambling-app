package com.example.gamblingapp.ui

import androidx.lifecycle.ViewModel
import com.example.gamblingapp.data.GamblingAppState
import com.example.gamblingapp.data.LoadingScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoadingScreenViewModel : ViewModel()
{
    private val _uiState = MutableStateFlow(LoadingScreenState())
    val uiState: StateFlow<LoadingScreenState> = _uiState.asStateFlow()

    private val _appState = MutableStateFlow(GamblingAppState())
    val appState: StateFlow<GamblingAppState> = _appState.asStateFlow()

    //function sets up the progress and starts connection to the database
    init
    {
        _uiState.value = LoadingScreenState(progress = 0f)
        getUserData()
    }

    //starts connection to the "server" (which we can simulate by having the app just wait and return locally stored data)
    private fun getUserData()
    {
        //temporarily leave empty
    }

    fun updateProgress() : Boolean
    {
        //function should check progress on getting connection to the "server", for now i just increment the progress
        val newProgress = _uiState.value.progress.plus(0.01f)
        _uiState.value = LoadingScreenState(progress = newProgress)
        if(newProgress >= 100.0f)
            return true
        return false
    }
}