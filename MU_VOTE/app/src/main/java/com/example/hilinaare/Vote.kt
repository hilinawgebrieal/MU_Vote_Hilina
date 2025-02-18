package com.example.hilinaare

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class Vote : AppCompatActivity() {
    private lateinit var radioGroup: RadioGroup
    private lateinit var voteButton: Button

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radioGroup = findViewById(R.id.radioGroupPeople)
        voteButton = findViewById<Button>(R.id.btnVote)

        fetchPeopleFromFirestore()

        voteButton.setOnClickListener(View.OnClickListener { v: View? ->
            val selectedId = radioGroup.getCheckedRadioButtonId()
            if (selectedId != -1) {
                val selectedRadioButton =
                    findViewById<RadioButton>(selectedId)
                val selectedPerson = selectedRadioButton.text.toString()

                updateVoteCount(selectedPerson)

                Toast.makeText(
                    this,
                    "You have successfully voted for $selectedPerson",
                    Toast.LENGTH_SHORT
                ).show()

                val intent = Intent(
                    this,
                    VoteSuccefull::class.java
                )
                intent.putExtra("personName", selectedPerson)
                startActivity(intent)
            } else {
                Toast.makeText(
                    this,
                    "Please select a candidate to vote",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun fetchPeopleFromFirestore() {
        db.collection("col")
            .get()
            .addOnSuccessListener { result: QuerySnapshot ->
                for (document in result) {
                    var name = document.getString("name")
                    var department = document.getString("department")
                    if (name == null) name = "Unknown"
                    if (department == null) department = "Unknown Department"

                    val radioButton =
                        RadioButton(this)
                    radioButton.text = "$name ($department)"
                    radioGroup!!.addView(radioButton)
                    radioButton.setTextColor(getResources().getColor(R.color.black))




                }
            }
            .addOnFailureListener { e: Exception? ->
                Toast.makeText(
                    this,
                    "Failed to load candidates",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun updateVoteCount(personName: String) {
        db.collection("col").whereEqualTo("name", personName)
            .get()
            .addOnSuccessListener { documents: QuerySnapshot ->
                for (document in documents) {
                    db.collection("col").document(document.id)
                        .update(
                            "votes",
                            if (document.getLong("votes") != null) document.getLong("votes")!! + 1 else 1
                        )
                        .addOnSuccessListener { aVoid: Void? ->
                            Toast.makeText(
                                this,
                                "Vote registered!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .addOnFailureListener { e: Exception? ->
                            Toast.makeText(
                                this,
                                "Error updating vote",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
            }
    }
}