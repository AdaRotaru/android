package com.relearn.app.feature.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        gender: String,
        preferences: List<String>,
        onResult: (Boolean, String?) -> Unit
    ) {
        //Trimite cererea către Firebase să creeze un user cu acel email și parola
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //Preia utilizatorul curent
                    val user = firebaseAuth.currentUser

                    // Trimite emailul de conmfirmare
                    user?.sendEmailVerification()

                    val uid = user?.uid
                    val userData = hashMapOf(
                        "firstName" to firstName,
                        "lastName" to lastName,
                        "email" to email,
                        "gender" to gender,
                        "preferences" to preferences,
                        "createdAt" to com.google.firebase.Timestamp.now()
                    )

                    uid?.let {
                        //Creează un document în colecția users, cu ID-ul egal cu uid-ul userului.
                        FirebaseFirestore.getInstance().collection("users")
                            .document(it)
                            .set(userData)
                            .addOnSuccessListener {
                                onResult(true, null) //tat normal
                            }
                            .addOnFailureListener { e ->
                                onResult(false, e.message) //hopa
                            }
                    } ?: run {
                        onResult(false, "Eroare la preluarea UID")
                    }
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }



    fun login(
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    user?.reload()?.addOnSuccessListener {
                        if (user.isEmailVerified) {
                            onResult(true, null)
                        } else {
                            firebaseAuth.signOut() // preventiv
                            onResult(false, "Trebuie să îți confirmi adresa de email.")
                        }
                    }
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    fun logout() {
        firebaseAuth.signOut()
    }

    fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }
}
