package com.example.stayout.presentation.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.core.text.HtmlCompat
import com.example.stayout.presentation.R
import com.example.stayout.presentation.theme.Dimens
import com.example.stayout.presentation.theme.PreviewThemes
import com.example.stayout.presentation.theme.StayScoutTheme

@Composable
internal fun PropertyOverviewSection(
    overview: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.label_about),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.semantics { heading() },
        )
        Spacer(modifier = Modifier.height(Dimens.spacing8))
        Text(
            text = HtmlCompat.fromHtml(overview, HtmlCompat.FROM_HTML_MODE_COMPACT).toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@PreviewThemes
@Composable
private fun PropertyOverviewSectionPreview() {
    StayScoutTheme {
        PropertyOverviewSection(
            overview =
                "<p>A <b>fantastic hostel</b> in the heart of Dublin with great amenities and a " +
                    "friendly atmosphere. Perfect for solo travellers and groups alike.</p>",
        )
    }
}
