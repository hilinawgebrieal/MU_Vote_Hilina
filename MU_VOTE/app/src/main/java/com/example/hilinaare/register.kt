package com.example.hilinaare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register) // Ensure your layout file name is correct

        // Initialize Firebase Authentication and Firestore
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val emailEditText = findViewById<EditText>(R.id.Emailtext)
        val idEditText = findViewById<EditText>(R.id.Idreg)
        val passwordEditText = findViewById<EditText>(R.id.Pwd)
        val confirmPasswordEditText = findViewById<EditText>(R.id.pwdc)
        val doneButton = findViewById<Button>(R.id.buttondone)

        doneButton.setOnClickListener {
            if (isRegistrationOpen()) {
                val email = emailEditText.text.toString().trim()
                val id = idEditText.text.toString().trim()
                val password = passwordEditText.text.toString()
                val confirmPassword = confirmPasswordEditText.text.toString()

                if (email.isEmpty() || id.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (password != confirmPassword) {
                    Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                registerUser(email, id, password)
            } else {
                // If registration is closed, show a toast
                Toast.makeText(this, "Registration is closed. The deadline has passed.", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Register the user with Firebase Authentication
    private fun registerUser(email: String, id: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                val user = authResult.user
                if (user != null) {
                    saveUserData(user.uid, email, id)
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Registration Failed: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    // Save user data to Firestore
    private fun saveUserData(uid: String, email: String, id: String) {
        val userMap = hashMapOf(
            "email" to email,
            "id" to id
        )

        db.collection("users").document(uid).set(userMap)
            .addOnSuccessListener {
                Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, Login::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error saving user data: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    // Check if registration is open based on the current date and the deadline stored in Firestore
    private fun isRegistrationOpen(): Boolean {
        var isOpen = false

        // Fetch the registration deadline from Firestore
        db.collection("settings").document("appConfig")
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val deadlineStr = document.getString("registrationDeadline") ?: ""
                    if (deadlineStr.isNotEmpty()) {
                        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        try {
                            val deadlineDate = sdf.parse(deadlineStr)
                            val currentDate = Calendar.getInstance().time

                            // Compare the current date with the deadline
                            isOpen = currentDate.before(deadlineDate)
                        } catch (e: Exception) {
                            // Handle any parsing errors
                            e.printStackTrace()
                        }
                    }
                }
            }
            .addOnFailureListener {
                // Handle failure to fetch deadline
                Toast.makeText(this, "Error fetching registration deadline.", Toast.LENGTH_SHORT).show()
            }

        return isOpen
    }
}
