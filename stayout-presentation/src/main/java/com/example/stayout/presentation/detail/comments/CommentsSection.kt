package com.example.stayout.presentation.detail.comments

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    val transition = rememberInfiniteTransition(label = "commentsShimmer")
    val alpha by transition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(animation = tween(durationMillis = 900), repeatMode = RepeatMode.Reverse),
        label = "commentsShimmerAlpha",
    )
    val shimmer = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha)

    Column {
        Box(
            modifier =
                Modifier
                    .width(Dimens.spacing130)
                    .height(Dimens.spacing20)
                    .background(shimmer, MaterialTheme.shapes.extraSmall),
        )
        Spacer(modifier = Modifier.height(Dimens.spacing8))
        repeat(3) { index ->
            CommentItemSkeleton(shimmer = shimmer)
            if (index < 2) HorizontalDivider()
        }
    }
}

@Composable
private fun CommentItemSkeleton(
    shimmer: Color,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(vertical = Dimens.spacing8)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(Dimens.spacing40).background(shimmer, CircleShape))
            Spacer(modifier = Modifier.width(Dimens.spacing12))
            Column {
                Box(
                    modifier =
                        Modifier
                            .width(Dimens.spacing80)
                            .height(Dimens.spacing14)
                            .background(shimmer, MaterialTheme.shapes.extraSmall),
                )
                Spacer(modifier = Modifier.height(Dimens.spacing4))
                Box(
                    modifier =
                        Modifier
                            .width(Dimens.spacing130)
                            .height(Dimens.spacing12)
                            .background(shimmer, MaterialTheme.shapes.extraSmall),
                )
            }
        }
        Spacer(modifier = Modifier.height(Dimens.spacing8))
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(Dimens.spacing14)
                    .background(shimmer, MaterialTheme.shapes.extraSmall),
        )
        Spacer(modifier = Modifier.height(Dimens.spacing4))
        Box(
            modifier =
                Modifier
                    .fillMaxWidth(0.8f)
                    .height(Dimens.spacing14)
                    .background(shimmer, MaterialTheme.shapes.extraSmall),
        )
        Spacer(modifier = Modifier.height(Dimens.spacing4))
        Box(
            modifier =
                Modifier
                    .fillMaxWidth(0.6f)
                    .height(Dimens.spacing14)
                    .background(shimmer, MaterialTheme.shapes.extraSmall),
        )
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
