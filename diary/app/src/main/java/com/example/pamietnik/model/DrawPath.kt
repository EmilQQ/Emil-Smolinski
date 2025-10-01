package com.example.pamietnik.model

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Color

data class DrawPath(
    val path: Path,
    val color: Color,
    val strokeWidth: Float
)