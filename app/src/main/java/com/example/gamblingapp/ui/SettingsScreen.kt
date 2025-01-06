package com.example.gamblingapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamblingapp.R
import com.example.gamblingapp.ui.theme.GamblingAppTheme


@Composable
fun SettingsScreen(
    onMusicVolumeChange: (Float) -> Unit,
    onSoundVolumeChange: (Float) -> Unit,
    onAccountClick: () -> Unit,
    onNotificationsClick: (Boolean) -> Unit,
    onThemesClick: (Boolean) -> Unit,
    onHelpClick: () -> Unit,
    onSignOutClick: () -> Unit,
    musicVolume: Float = 0.5f,
    soundVolume: Float = 0.5f,
    notificationsOn: Boolean = false,
    altThemeOn: Boolean = false,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.settings_gradient),
                contentScale = ContentScale.FillBounds
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
        )
    {
        Column(
            modifier = modifier
                .padding(2.dp)
                .background(color = colorResource(R.color.settings_background), shape = RoundedCornerShape(10f))
                .border(1.dp, colorResource(R.color.settings_background_border))
                .padding(2.dp)
        ) {
            Row {
                Icon(
                    Icons.Rounded.PlayArrow,
                    contentDescription = "Volume slider icon",
                    tint = colorResource(R.color.settings_main)
                )
                Text(
                    text = stringResource(R.string.sound_settings),
                    color = colorResource(R.color.settings_main),
                    fontWeight = FontWeight.Bold,
                )
            }
            Slider(
                value = soundVolume,
                onValueChange = onSoundVolumeChange,
                valueRange = 0f..1f,
                colors = SliderDefaults.colors(
                    thumbColor = colorResource(R.color.settings_secondary),
                    activeTrackColor = colorResource(R.color.settings_main),
                    inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
            )
        }

        Column(
            modifier = modifier
                .padding(2.dp)
                .background(color = colorResource(R.color.settings_background), shape = RoundedCornerShape(10f))
                .border(1.dp, colorResource(R.color.settings_background_border))
                .padding(2.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.music_note_icon),
                    contentDescription = "Volume slider icon",
                    tint = colorResource(R.color.settings_main),
                    modifier = modifier
                        .height(20.dp)
                        .padding(2.dp)
                )
                Text(
                    text = stringResource(R.string.music_settings),
                    color = colorResource(R.color.settings_main),
                    fontWeight = FontWeight.Bold
                )
            }
            Slider(
                value = musicVolume,
                onValueChange = onMusicVolumeChange,
                valueRange = 0f..1f,
                colors = SliderDefaults.colors(
                    thumbColor = colorResource(R.color.settings_secondary),
                    activeTrackColor = colorResource(R.color.settings_main),
                    inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(2.dp)
                .background(color = colorResource(R.color.settings_background), shape = RoundedCornerShape(10f))
                .border(1.dp, colorResource(R.color.settings_background_border))
                .padding(2.dp)
        ) {
            Icon(
                Icons.Rounded.Notifications,
                contentDescription = "Volume slider icon",
                tint = colorResource(R.color.settings_main)
            )
            Text(text = stringResource(R.string.notifications_settings), color = colorResource(R.color.settings_main), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(0.8f))
            Switch(
                checked = notificationsOn,
                onCheckedChange = onNotificationsClick
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(2.dp)
                .background(color = colorResource(R.color.settings_background), shape = RoundedCornerShape(10f))
                .border(1.dp, colorResource(R.color.settings_background_border))
                .padding(2.dp)
        ) {
            Icon(
                Icons.Rounded.Star,
                contentDescription = "Volume slider icon",
                tint = colorResource(R.color.settings_main)
            )
            Text(text = stringResource(R.string.themes_settings), color = colorResource(R.color.settings_main), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = altThemeOn,
                onCheckedChange = onThemesClick
            )
        }

        Button(
            onClick = onAccountClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.settings_button)
            ),
            modifier = Modifier
                .fillMaxWidth(0.95f)
        ) {
            Icon(
                Icons.Rounded.AccountCircle,
                contentDescription = "Volume slider icon",
                tint = colorResource(R.color.settings_main)
            )
            Text(text = stringResource(R.string.account_settings), color = colorResource(R.color.settings_main), fontWeight = FontWeight.Bold)
        }

        Button(
            onClick = onHelpClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.settings_button)
            ),
            modifier = Modifier
                .fillMaxWidth(0.95f)
        ) {
            Icon(
                Icons.Rounded.Info,
                contentDescription = "Volume slider icon",
                tint = colorResource(R.color.settings_main)
            )
            Text(text = stringResource(R.string.help_settings), color = colorResource(R.color.settings_main), fontWeight = FontWeight.Bold)
        }

        Button(
            onClick = onSignOutClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onErrorContainer
            ),
            modifier = Modifier
                .fillMaxWidth(0.95f)
        ) {
            Icon(
                Icons.Rounded.Close,
                contentDescription = "Volume slider icon",
                tint = colorResource(R.color.settings_main)
            )
            Text(text = stringResource(R.string.sign_out_settings), color = colorResource(R.color.settings_secondary), fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview()
{
    GamblingAppTheme()
    {
        Surface()
        {
            SettingsScreen({},{},{},{},{},{},{})
        }
    }
}