package com.example.testagm.ui.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testagm.R
import com.example.testagm.common.PlaceholderStateType

/**
 * Composable que se muestra cuando no se encuentran resultados o se produce un error.
 * @param type El tipo de estado a representar. Puede ser `ERROR` o `EMPTY`, lo que determina el título y la imagen (drawable) que se mostrarán.
 * @param message El mensaje que se mostrará debajo del título, proporcionando más contexto al usuario.
 * @param onRetry Acción que se ejecutará cuando el usuario haga clic en el botón de reintentar.
 */
@Composable
fun PlaceholderState(
    type: PlaceholderStateType,
    message: String,
    onRetry: (() -> Unit)? = null
) {
    val title: String
    val image: Painter
    when (type) {
        PlaceholderStateType.ERROR -> {
            title = stringResource(R.string.common_error_title)
            image = painterResource(id = R.drawable.error_state)
        }

        PlaceholderStateType.EMPTY -> {
            title = stringResource(R.string.common_loading_title)
            image = painterResource(id = R.drawable.empty_state)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = image,
            modifier = Modifier.fillMaxWidth(.80f),
            contentDescription = ""
        )
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )
        Text(
            text = message,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 32.dp, end = 32.dp, bottom = 32.dp)
        )
        if (onRetry != null) {
            Button(onClick = { onRetry() }) {
                Text(text = stringResource(R.string.button_retry))
            }
        }
    }
}