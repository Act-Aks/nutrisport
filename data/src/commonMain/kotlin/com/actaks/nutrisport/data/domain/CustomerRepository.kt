package com.actaks.nutrisport.data.domain

import com.actaks.nutrisport.shared.utils.RequestState
import dev.gitlive.firebase.auth.FirebaseUser

interface CustomerRepository {

    fun getCurrentUserId(): String?

    suspend fun createCustomer(
        user: FirebaseUser?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )

    suspend fun signOut(): RequestState<Unit>
}