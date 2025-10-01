package com.example.pamietnik.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.pamietnik.data.PinStorage
import com.example.pamietnik.R

@Composable
fun PinScreen(onPinVerified: () -> Unit) {
    val context = LocalContext.current
    var pin by rememberSaveable { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }
    val isFirstRun = !PinStorage.isPinSet(context)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (isFirstRun) stringResource(R.string.setNewPin) else stringResource(R.string.writePIN),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = pin,
            onValueChange = {
                if (it.all { c -> c.isDigit() } && it.length <= 6) {
                    pin = it
                    if (error) error = false
                }
            },
            label = { Text(stringResource(R.string.pin)) },
            isError = error,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
        )
        if (error) {
            Text(
                text = stringResource(R.string.pinError),
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        Spacer(Modifier.height(24.dp))
        Button(onClick = {
            if (pin.length >= 4) {
                if (isFirstRun) {
                    PinStorage.savePin(context, pin)
                    onPinVerified()
                } else if (PinStorage.checkPin(context, pin)) {
                    onPinVerified()
                } else {
                    error = true
                }
            } else {
                error = true
            }
        }) {
            Text(if (isFirstRun) stringResource(R.string.pinSave) else stringResource(R.string.logIn))
        }
    }
}
