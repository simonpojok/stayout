package com.example.stayout.presentation.detail.comments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.stayout.domain.model.CommentDomainModel
import com.example.stayout.presentation.theme.Dimens

@Composable
fun CommentsSection(
    modifier: Modifier = Modifier,
    viewModel: CommentsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(modifier = modifier) {
        when (val s = state) {
            is CommentsState.Loading -> CommentsSectionLoading()
            is CommentsState.Error ->
                CommentsSectionError(
                    message = s.message,
                    onRetry = { viewModel.onIntent(CommentsIntent.Retry) },
                )
            is CommentsState.Success -> CommentsSectionContent(comments = s.comments)
        }
    }
}

@Composable
private fun CommentsSectionLoading() {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.spacing16),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(modifier = Modifier.size(Dimens.spacing32))
    }
}

@Composable
private fun CommentsSectionError(
    message: String,
    onRetry: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.spacing8),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error,
        )
        TextButton(onClick = onRetry) {
            Text(text = "Retry")
        }
    }
}

@Composable
private fun CommentsSectionContent(comments: List<CommentDomainModel>) {
    Column {
        Text(
            text = "Guest Reviews (${comments.size})",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(Dimens.spacing8))
        if (comments.isEmpty()) {
            Text(
                text = "No reviews yet.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        } else {
            comments.forEachIndexed { index, comment ->
                CommentItem(comment = comment)
                if (index < comments.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
private fun CommentItem(
    comment: CommentDomainModel,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(vertical = Dimens.spacing8)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(Dimens.spacing40),
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text =
                            comment.user.name
                                .take(1)
                                .uppercase(),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
            }
            Spacer(modifier = Modifier.width(Dimens.spacing12))
            Column {
                Text(
                    text = comment.user.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = comment.user.email,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        Spacer(modifier = Modifier.height(Dimens.spacing8))
        Text(
            text = comment.body,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
