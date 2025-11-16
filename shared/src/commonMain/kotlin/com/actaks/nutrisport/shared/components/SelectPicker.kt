package com.actaks.nutrisport.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.actaks.nutrisport.shared.FontSize
import com.actaks.nutrisport.shared.domain.SelectOption
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun <T> SelectPicker(
    selectedOption: SelectOption<T>,
    icon: DrawableResource? = null,
    onOptionSelected: (SelectOption<T>) -> Unit,
    options: List<SelectOption<T>>,
    modifier: Modifier = Modifier
) {
    var showPicker by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(size = 6.dp)
            )
            .clip(RoundedCornerShape(size = 6.dp))
            .clickable { showPicker = true }
            .padding(
                vertical = 20.dp,
                horizontal = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Image(
                painter = painterResource(icon),
                contentDescription = "Selected Icon",
                modifier = modifier.size(14.dp)
            )
            Spacer(modifier = modifier.width(8.dp))
            Text(
                text = selectedOption.text,
                fontSize = FontSize.REGULAR,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

    if (showPicker) {
        AlertDialog(
            onDismissRequest = { showPicker = false },
            confirmButton = {},
            title = { Text("Select option") },
            text = {
                Column {
                    options.forEach { option ->
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .clickable {
                                    onOptionSelected(option)
                                    showPicker = false
                                }
                                .padding(vertical = 12.dp)
                        ) {
                            Text(option.text)
                        }
                    }
                }
            }
        )
    }
}