package com.actaks.nutrisport.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.actaks.nutrisport.home.domain.DrawerItem
import com.actaks.nutrisport.shared.BebasNeueFontFamily
import com.actaks.nutrisport.shared.FontSize

@Composable
fun CustomDrawer(
    onProfileClick: () -> Unit,
    onContactClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onAdminClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .fillMaxHeight()
            .padding(horizontal = 12.dp),
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "NUTRISPORT",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.secondary,
            fontFamily = BebasNeueFontFamily(),
            fontSize = FontSize.EXTRA_LARGE,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Healthy lifestyle",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            fontSize = FontSize.REGULAR,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(50.dp))
        DrawerItem.entries.take(5).forEach { drawerItem ->
            DrawerItemCard(
                drawerItem = drawerItem,
                onClick = {
                    when (drawerItem) {
                        DrawerItem.Profile -> onProfileClick()
                        DrawerItem.Contact -> onContactClick()
                        DrawerItem.SignOut -> onSignOutClick()
                        else -> {}
                    }
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
        Spacer(modifier = Modifier.weight(1f))
        DrawerItemCard(
            drawerItem = DrawerItem.Admin,
            onClick = { onAdminClick() }
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}