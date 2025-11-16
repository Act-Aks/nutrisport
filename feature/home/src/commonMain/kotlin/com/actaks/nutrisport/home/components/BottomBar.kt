package com.actaks.nutrisport.home.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.actaks.nutrisport.home.domain.BottomBarDestination
import org.jetbrains.compose.resources.painterResource

@Composable
fun BottomBar(
    selected: BottomBarDestination,
    onSelect: (BottomBarDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(
                vertical = 24.dp,
                horizontal = 36.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BottomBarDestination.entries.forEach { destination ->
            val animatedTint by animateColorAsState(
                targetValue = if (selected == destination) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
            Icon(
                painter = painterResource(destination.icon),
                contentDescription = "Bottom bar destination icon",
                tint = animatedTint,
                modifier = modifier.clickable { onSelect(destination) }
            )
        }

    }
}