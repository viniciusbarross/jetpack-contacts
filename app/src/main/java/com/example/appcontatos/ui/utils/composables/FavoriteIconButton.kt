package com.example.appcontatos.ui.utils.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.appcontatos.R
import com.example.appcontatos.ui.theme.AppContatosTheme

@Composable
fun FavoriteIconButton(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onPressed: () -> Unit
    ) {
    IconButton(onClick = onPressed) {
        Icon(
            imageVector = if(isFavorite){
                Icons.Filled.Favorite

            }else{
                Icons.Filled.FavoriteBorder
            },
            contentDescription = stringResource(R.string.favoritar),
            tint = if(isFavorite){
                Color.Red
            }else{
                LocalContentColor.current
            }
        )

    }
}

@Preview
@Composable
private fun IsFavoritePreview() {
    AppContatosTheme {
        FavoriteIconButton(isFavorite = true, onPressed = {} )
    }
}

@Preview
@Composable
private fun Preview() {
    AppContatosTheme {
        FavoriteIconButton(isFavorite = false, onPressed = {} )
    }
}