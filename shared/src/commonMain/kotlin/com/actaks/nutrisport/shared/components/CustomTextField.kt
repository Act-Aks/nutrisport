package com.actaks.nutrisport.shared.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.actaks.nutrisport.shared.Alpha
import com.actaks.nutrisport.shared.FontSize

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
    enabled: Boolean = true,
    error: Boolean = false,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    modifier: Modifier = Modifier
) {
    val borderColor by animateColorAsState(
        targetValue = if (error) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
    )

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(size = 6.dp)
            )
            .clip(RoundedCornerShape(size = 6.dp)),
        enabled = enabled,
        value = value,
        onValueChange = onValueChange,
        placeholder = if (placeholder != null) {
            {
                Text(
                    text = placeholder,
                    fontSize = FontSize.REGULAR,
                    modifier = modifier.alpha(Alpha.HALF),
                )
            }
        } else null,
        singleLine = singleLine,
        shape = RoundedCornerShape(size = 6.dp),
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            disabledContainerColor = MaterialTheme.colorScheme.background.copy(alpha = Alpha.DISABLED),
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            disabledTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = Alpha.DISABLED),
            focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = Alpha.HALF),
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = Alpha.HALF),
            disabledPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = Alpha.DISABLED),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            selectionColors = TextSelectionColors(
                handleColor = MaterialTheme.colorScheme.secondary,
                backgroundColor = Color.Unspecified
            )
        )
    )

}