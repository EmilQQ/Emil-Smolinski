package com.example.pamietnik.ui.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.Path
import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.toIntSize
import com.example.pamietnik.R
import com.example.pamietnik.model.DrawPath

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawOnImageScreen(
    imageUri: Uri,
    onSave: (Bitmap) -> Unit,
    onCancel: () -> Unit
) {
    val context = LocalContext.current
    val bitmap = remember(imageUri) {
        context.contentResolver.openInputStream(imageUri)
            ?.use { BitmapFactory.decodeStream(it) }
    }
    val paths = remember { mutableStateListOf<DrawPath>() }
    var currentPath by remember { mutableStateOf<DrawPath?>(null) }
    var canvasSize by remember { mutableStateOf(IntSize.Zero) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.drawOnImage)) },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(Icons.Default.Close, contentDescription = stringResource(R.string.exit))
                    }
                },
                actions = {
                    IconButton(onClick = {
                        bitmap?.let { orig ->
                            val scaleX = orig.width.toFloat() / canvasSize.width
                            val scaleY = orig.height.toFloat() / canvasSize.height
                            val result = Bitmap.createBitmap(orig.width, orig.height, Bitmap.Config.ARGB_8888)
                            val canvas = android.graphics.Canvas(result)
                            canvas.drawBitmap(orig, 0f, 0f, null)
                            paths.forEach { dp ->
                                val androidPath = scalePath(dp.path, scaleX, scaleY)
                                val paint = android.graphics.Paint().apply {
                                    color = dp.color.toArgb()
                                    strokeWidth = dp.strokeWidth
                                    style = android.graphics.Paint.Style.STROKE
                                    isAntiAlias = true
                                    strokeCap = android.graphics.Paint.Cap.ROUND
                                }
                                canvas.drawPath(androidPath, paint)
                            }
                            onSave(result)
                        }
                    }) {
                        Icon(Icons.Default.Check, contentDescription = stringResource(R.string.save))
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.Black)
        ) {
            bitmap?.let { bmp ->
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .onSizeChanged { canvasSize = it }
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    val newPath = Path().apply { moveTo(offset.x, offset.y) }
                                    currentPath = DrawPath(newPath, Color.Black, 8f)
                                    paths.add(currentPath!!)
                                },
                                onDrag = { change, offset ->
                                    change.consume()
                                    currentPath?.let {
                                        currentPath = it
                                            .copy(path = it.path.apply { lineTo(change.position.x, change.position.y) })
                                            .also {
                                                paths.removeAt(paths.lastIndex)
                                                paths.add(it)
                                            }
                                    }
                                },
                                onDragEnd = {
                                    currentPath = null
                                }
                            )
                        }
                ) {
                    drawImage(
                        image = bmp.asImageBitmap(),
                        dstSize = size.toIntSize()
                    )
                    paths.forEach { dp ->
                        drawPath(
                            path = dp.path,
                            color = dp.color,
                            style = Stroke(width = dp.strokeWidth)
                        )
                    }
                }
            }
        }
    }
}


fun scalePath(path: Path, scaleX: Float, scaleY: Float): android.graphics.Path {
    val original = path.asAndroidPath()
    val scaled = android.graphics.Path(original)
    val matrix = android.graphics.Matrix().apply {
        setScale(scaleX, scaleY)
    }
    scaled.transform(matrix)
    return scaled
}


