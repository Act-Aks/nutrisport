package com.actaks.nutrisport.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.actaks.nutrisport.shared.components.ProfileForm

@Composable
fun ProfileScreen(

) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .systemBarsPadding(),
    ) {
        ProfileForm(
            firstName = "Aks",
            onFirstNameChange = {},
            lastName = "",
            onLastNameChange = {},
            email = "",
            city = "",
            onCityChange = {},
            postalCode = null,
            onPostalCodeChange = {},
            address = "",
            onAddressChange = {},
            phoneNumber = "",
            onPhoneNumberChange = {},
        )


    }

}