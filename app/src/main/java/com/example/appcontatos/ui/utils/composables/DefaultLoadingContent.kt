package com.example.appcontatos.ui.utils.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.appcontatos.R

@Composable
fun DefaultLoadingContent(
    modifier: Modifier = Modifier,
    text: String = stringResource(R.string.carregando)
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.size(60.dp)

        )
        Spacer(Modifier.size(8.dp))
        Text(
            text= stringResource(R.string.loading_contacts),
            style = MaterialTheme.typography.titleLarge.copy(
                color =  Color.Blue
            ),
            color = MaterialTheme.colorScheme.primary
        )
    }
}
