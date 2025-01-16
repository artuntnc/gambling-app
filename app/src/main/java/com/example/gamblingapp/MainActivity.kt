package com.example.gamblingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.gamblingapp.data.GamblingDatabase
import com.example.gamblingapp.data.LocalDataDatabase
import com.example.gamblingapp.data.LocalDataRepository
import com.example.gamblingapp.data.LocalDataStoreManager
import com.example.gamblingapp.data.UsersRepository
import com.example.gamblingapp.ui.GamblingAppViewModel
import com.example.gamblingapp.ui.LoadingScreenViewModel
import com.example.gamblingapp.ui.theme.GamblingAppTheme

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            GamblingDatabase::class.java,
            name = "gambling_database.db"
        ).build()
    }
    private val mainViewModel by viewModels<GamblingAppViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return GamblingAppViewModel(UsersRepository(db)) as T
                }
            }
        }
    )

    private val ldb by lazy {
        Room.databaseBuilder(
            applicationContext,
            LocalDataDatabase::class.java,
            name = "local_database.db"
        ).build()
    }
    private val loadingScreenViewModel by viewModels<LoadingScreenViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return LoadingScreenViewModel(LocalDataRepository(ldb)) as T
                }
            }
        }
    )


    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class) //This will be important for making the app work on non-standard devices
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //Nothing should be called from MainActivity, only the standard padding should be given and size values
        setContent {
            GamblingAppTheme {
                val layoutDirection = LocalLayoutDirection.current
                Surface(
                    modifier = Modifier
                        .padding(
                            start = WindowInsets.safeDrawing.asPaddingValues()
                                .calculateStartPadding(layoutDirection),
                            end = WindowInsets.safeDrawing.asPaddingValues()
                                .calculateEndPadding(layoutDirection)
                        )
                ) {
                    GamblingApp(context = this, gamblingAppViewModel = mainViewModel, loadingScreenViewModel = loadingScreenViewModel)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReplyAppCompactPreview() {
    GamblingAppTheme {
        //GamblingApp(context = this)
    }
}