package com.example.gamblingapp.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

/*object AppViewModelProvider {
    val Factory = viewModelFactory {
        //Initializer for GamblingAppViewModel
        initializer {
            GamblingAppViewModel(gamblingApplication().container.usersRepository)
        }
    }
}*/

//Extension function to queries for [Application] object and returns an instance of [GamblingApplication].
//fun CreationExtras.gamblingApplication(): GamblingApplication = (this[AndroidViewModelFactory.APPLICATION_KEY] as GamblingApplication)
