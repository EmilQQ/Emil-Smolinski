package com.example.pamietnik.ui.view

import android.Manifest
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.pamietnik.R
import com.example.pamietnik.model.Entry
import com.example.pamietnik.ui.viewmodel.EntryDraftViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryEditScreen(
    draft: EntryDraftViewModel,
    onSave: (Entry) -> Unit,
    onNavigationUp: () -> Unit,
    onDrawRequested: () -> Unit
) {
    var titleError by remember { mutableStateOf(false) }
    var descriptionError by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }
    var isRecording by remember { mutableStateOf(false) }
    var mediaRecorder: MediaRecorder? by remember { mutableStateOf(null) }
    var mediaPlayer: MediaPlayer? by remember { mutableStateOf(null) }
    var recordPermissionGranted by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        draft.photoUri = uri
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                LocationHelper(context).fetchCity { city ->
                    draft.location = city
                    isLoading = false
                }
            }
        }
    )
    val audioPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> recordPermissionGranted = granted }
    )

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        audioPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .clickable(onClick = onNavigationUp)
                            .padding(12.dp),
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = stringResource(R.string.goback)
                    )
                }
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    draft.load()
                    titleError = draft.title.isBlank()
                    descriptionError  = draft.description.isBlank()
                    if (!titleError && !descriptionError) {
                        onSave(
                            Entry(
                                id = draft.id,
                                title = draft.title,
                                description = draft.description,
                                location = draft.location,
                                photoUri = draft.photoUri,
                                audioUri = draft.audioUri
                            )
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(stringResource(R.string.save))
            }
        }
    ) { innerPaddings ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddings)
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = draft.photoUri
                    ?.let { rememberAsyncImagePainter(it) }
                    ?: rememberAsyncImagePainter(
                        Uri.parse("android.resource://com.example.pamietnik/${R.drawable.photostand}")
                    ),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .clickable {
                        launcher.launch("image/*")
                    }
            )
            if (draft.photoUri != null) {
                IconButton(onClick = onDrawRequested) {
                    Icon(Icons.Default.Edit, contentDescription = stringResource(R.string.edit))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = draft.title,
                onValueChange = {
                    draft.updateTitle(it)
                    titleError = false
                },
                isError = titleError,
                label = { Text(stringResource(R.string.title)) },
                supportingText = {
                    if (titleError)
                        Text(stringResource(R.string.titleError), color = MaterialTheme.colorScheme.error)
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = draft.description,
                onValueChange = {
                    draft.updateDescription(it)
                    descriptionError = false
                },
                isError = descriptionError,
                label = { Text(stringResource(R.string.content)) },
                supportingText = {
                    if (descriptionError)
                        Text(stringResource(R.string.descriptionError), color = MaterialTheme.colorScheme.error)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (isRecording) Icons.Default.Stop else Icons.Default.Mic,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        if (!recordPermissionGranted) {
                            audioPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                        } else {
                            if (!isRecording) {
                                val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                                val fileName = "REC_${sdf.format(Date())}.mp3"
                                val file = File(context.getExternalFilesDir(Environment.DIRECTORY_MUSIC), fileName)
                                draft.audioUri = file.absolutePath
                                mediaRecorder = MediaRecorder().apply {
                                    setAudioSource(MediaRecorder.AudioSource.MIC)
                                    setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                                    setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                                    setOutputFile(draft.audioUri!!)
                                    prepare()
                                    start()
                                }
                                isRecording = true
                            } else {
                                mediaRecorder?.stop()
                                mediaRecorder?.release()
                                mediaRecorder = null
                                isRecording = false
                            }
                        }
                    }
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = when {
                        isRecording -> stringResource(R.string.recording)
                        draft.audioUri != null -> stringResource(R.string.audio_recorded)
                        else -> stringResource(R.string.no_audio_recorded)
                    }
                )
                if (!isRecording && draft.audioUri != null) {
                    IconButton(onClick = {
                        mediaPlayer?.release()
                        mediaPlayer = MediaPlayer().apply {
                            setDataSource(draft.audioUri!!)
                            prepare()
                            start()
                        }
                    }) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null)
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Text(text = stringResource(R.string.location) + draft.location)
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun ScreenPreview() {
//    PamietnikTheme {
//        EntryEditScreen(entry = EntryRepository.entries[0], {}, {})
//    }
//}
