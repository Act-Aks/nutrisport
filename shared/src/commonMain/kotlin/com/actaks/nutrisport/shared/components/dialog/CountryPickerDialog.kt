package com.actaks.nutrisport.shared.components.dialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.actaks.nutrisport.shared.FontSize
import com.actaks.nutrisport.shared.Resources
import com.actaks.nutrisport.shared.domain.Country
import org.jetbrains.compose.resources.painterResource

@Composable
fun CountryPickerDialog() {


}

@Composable
private fun CountryPicker(
    country: Country,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    val saturation = remember { Animatable(if (isSelected) 1f else 0f) }

    LaunchedEffect(isSelected) {
        saturation.animateTo(if (isSelected) 1f else 0f)
    }

    val colorMatrix = remember(saturation.value) {
        ColorMatrix().apply { setToSaturation(saturation.value) }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = modifier.size(14.dp),
            painter = painterResource(country.flag),
            contentDescription = "Country Flag",
            colorFilter = ColorFilter.colorMatrix(colorMatrix)

        )
        Spacer(modifier = modifier.width(12.dp))
        Text(
            modifier = modifier.weight(1f),
            text = "+${country.dialCode} ${country.name}",
            fontSize = FontSize.SMALL,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.primary
        )
        Selector(isSelected = isSelected)
    }
}


@Composable
private fun Selector(
    isSelected: Boolean = false,
    modifier: Modifier = Modifier
) {
    val animatedBackground by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.surfaceDim else MaterialTheme.colorScheme.surface
    )

    Box(
        modifier = modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(animatedBackground),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(visible = isSelected) {
            Icon(
                painter = painterResource(Resources.Icon.Checkmark),
                modifier = modifier.size(14.dp),
                contentDescription = "Checkmark Icon",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}