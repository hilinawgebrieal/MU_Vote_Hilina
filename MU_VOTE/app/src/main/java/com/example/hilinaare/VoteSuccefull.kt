package com.example.hilinaare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class VoteSuccefull : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val personName = intent.getStringExtra("personName") ?: "Unknown"
        val textView = findViewById<TextView>(R.id.textViewMessage)
        val votesTextView = findViewById<TextView>(R.id.textViewVotes)
        textView.setTextColor(getResources().getColor(R.color.black))

        textView.text = "Thank you!\nYou have successfully voted for $personName!"


        fetchVotes(votesTextView)
    }

    private fun fetchVotes(votesTextView: TextView) {
        db.collection("col")
            .get()
            .addOnSuccessListener { result ->
                val votesString = StringBuilder("Candidate Votes:\n")
                for (document in result) {
                    val name = document.getString("name") ?: "Unknown"
                    val votes = document.getLong("votes") ?: 0
                    votesString.append("$name: $votes votes\n")
                }
                votesTextView.text = votesString.toString()
            }
            .addOnFailureListener {
                votesTextView.text = "Failed to load vote counts"
            }
        val logoutButton = findViewById<Button>(R.id.logout)  // Change btnLogout to your button's actual id
        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()  // Logs out the user from Firebase
            val intent = Intent(this, MainActivity::class.java) // Replace HomeActivity with your home page
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

    }
}
