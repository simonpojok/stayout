package com.example.stayout.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.stayout.presentation.theme.Dimens

@Composable
fun SectionDivider(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(Dimens.spacing16))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(Dimens.spacing16))
    }
}
