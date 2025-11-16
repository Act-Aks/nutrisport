package com.actaks.nutrisport.data

import com.actaks.nutrisport.data.domain.CustomerRepository
import com.actaks.nutrisport.shared.domain.Customer
import com.actaks.nutrisport.shared.utils.RequestState
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore

class CustomerRepositoryImpl : CustomerRepository {
    override fun getCurrentUserId(): String? {
        return Firebase.auth.currentUser?.uid
    }

    override suspend fun createCustomer(
        user: FirebaseUser?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            if (user != null) {
                val customerCollection = Firebase.firestore.collection(
                    collectionPath = "customer"
                )
                val userName = user.displayName?.split(" ")
                val customer = Customer(
                    id = user.uid,
                    firstName = userName?.firstOrNull() ?: "Unknown",
                    lastName = userName?.lastOrNull() ?: "Unknown",
                    email = user.email ?: "Unknown",
                )
                val customerExists = customerCollection.document(user.uid).get().exists
                if (customerExists) {
                    onSuccess()
                } else {
                    customerCollection.document(user.uid).set(customer)
                    onSuccess()
                }
            } else {
                onError("User is already available!")
            }

        } catch (e: Exception) {
            onError("Error while creating customer: ${e.message}")
        }
    }

    override suspend fun signOut(): RequestState<Unit> {
        try {
            Firebase.auth.signOut()
            return RequestState.Success(data = Unit)
        } catch (e: Exception) {
            return RequestState.Error("Error while signing out: ${e.message}")
        }
    }
}