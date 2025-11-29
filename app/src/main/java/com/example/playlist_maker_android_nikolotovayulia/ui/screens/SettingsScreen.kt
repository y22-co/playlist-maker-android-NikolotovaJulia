package com.example.playlist_maker_android_nikolotovayulia.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.playlist_maker_android_nikolotovayulia.R
import com.example.playlist_maker_android_nikolotovayulia.ui.viewmodel.ThemeViewModel
import androidx.compose.ui.res.dimensionResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onNavigateBack: () -> Unit) {
    val context = LocalContext.current
    val themeVm: ThemeViewModel = viewModel()
    val isDark by themeVm.darkTheme.collectAsState()

    val shareText = stringResource(R.string.share_message)
    val emailTo = stringResource(R.string.developer_email)
    val emailSubject = stringResource(R.string.email_subject)
    val emailBody = stringResource(R.string.email_body)
    val termsUrl = stringResource(R.string.legal_document_url)

    val paddingSettings = dimensionResource(R.dimen.padding_default_settings)
    val rowMinHeight = dimensionResource(R.dimen.row_height_min)
    val spacingMedium = dimensionResource(R.dimen.spacing_medium_settings)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.settings_title)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = paddingSettings)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = rowMinHeight)
                    .padding(vertical = spacingMedium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.theme_switch_title),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                Switch(checked = isDark, onCheckedChange = { themeVm.setDarkTheme(it) })
            }

            Divider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = rowMinHeight)
                    .clickable {
                        val send = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, shareText)
                        }
                        context.startActivity(Intent.createChooser(send, null))
                    }
                    .padding(vertical = spacingMedium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.share_app), style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
                Icon(Icons.Filled.Share, contentDescription = null)
            }

            Divider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = rowMinHeight)
                    .clickable {
                        val email = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:")
                            putExtra(Intent.EXTRA_EMAIL, arrayOf(emailTo))
                            putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                            putExtra(Intent.EXTRA_TEXT, emailBody)
                        }
                        context.startActivity(email)
                    }
                    .padding(vertical = spacingMedium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.contact_developers), style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
                Icon(Icons.Filled.HeadsetMic, contentDescription = null)
            }

            Divider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = rowMinHeight)
                    .clickable {
                        val browser = Intent(Intent.ACTION_VIEW, Uri.parse(termsUrl))
                        context.startActivity(browser)
                    }
                    .padding(vertical = spacingMedium),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.legal_agreement), style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
                Icon(Icons.Filled.ChevronRight, contentDescription = null)
            }
        }
    }
}