package com.example.playlist_maker_android_nikolotovayulia.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat.startActivity
import com.example.playlist_maker_android_nikolotovayulia.R

@Composable
fun SettingsScreen(onNavigateBack: () -> Unit) {
    val context = LocalContext.current

    val shareMessage = stringResource(id = R.string.share_message)
    val developerEmail = stringResource(id = R.string.developer_email)
    val emailSubject = stringResource(id = R.string.email_subject)
    val emailBody = stringResource(id = R.string.email_body)
    val legalDocumentUrl = stringResource(id = R.string.legal_document_url)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_default)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_medium))
    ) {
        Button(onClick = {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareMessage)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(context, shareIntent, null)
        }) {
            Text(text = stringResource(id = R.string.share_app))
        }

        Button(onClick = {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(developerEmail))
                putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                putExtra(Intent.EXTRA_TEXT, emailBody)
            }
            startActivity(context, emailIntent, null)
        }) {
            Text(text = stringResource(id = R.string.contact_developers))
        }

        Button(onClick = {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(legalDocumentUrl))
            startActivity(context, browserIntent, null)
        }) {
            Text(text = stringResource(id = R.string.legal_agreement))
        }
    }
}
