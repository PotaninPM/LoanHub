package com.potaninpm.uikit.presentation.text

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoanHubUiText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    textAlign: TextAlign = TextAlign.Start,
    textAnimation: LoanHubUiTextAnimation? = null,
    maxLines: Int = Int.MAX_VALUE,
    fontWeight: FontWeight? = null,
    fontSize: TextUnit = TextUnit.Unspecified,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    val mergedStyle = style.merge(
        TextStyle(
            fontWeight = fontWeight,
            fontSize = fontSize,
            letterSpacing = letterSpacing,
        )
    )

    if (textAnimation != null) {
        AnimatedContent(
            targetState = text,
            transitionSpec = textAnimation.animation
        ) { targetText ->
            Text(
                text = targetText,
                color = color,
                style = mergedStyle,
                modifier = modifier,
                textAlign = textAlign,
                maxLines = maxLines,
                overflow = overflow,
            )
        }
    } else {
        Text(
            text = text,
            color = color,
            style = mergedStyle,
            modifier = modifier,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow,
        )
    }
}