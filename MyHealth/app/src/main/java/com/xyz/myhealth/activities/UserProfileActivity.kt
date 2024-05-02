package com.xyz.myhealth.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.xyz.myhealth.databinding.ActivityUserProfileBinding
import com.xyz.myhealth.services.USER_PROFILE_TAG
import com.xyz.myhealth.services.UserService

/**
 * This activity is for creating or editing
 * information about User Profile
 */
class UserProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        readUserData("w@w")

        onUserProfileSaveClick(binding.profileEmail.text.toString())
        onUserProfileCancelClick()
    }

    private fun onUserProfileSaveClick(email:String){
        binding.userProfileSaveButton.setOnClickListener{
            UserService.addOrUpdateUserProfile(
                binding.profileName.text.toString(),
                binding.profileEmail.text.toString(),
                binding.profileAge.text.toString().toInt(),
                binding.profileCalorieGoal.text.toString().toFloat(),
                binding.profileCurrentWeight.text.toString().toFloat()
            )
            finish()
        }
    }

    private fun onUserProfileCancelClick(){
        binding.userProfileCancelButton.setOnClickListener{
            finish()
        }
    }

    private fun readUserData(email:String){
        val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("UserProfiles")
        database.child(email).get().addOnSuccessListener {
            if(it.exists()){
                binding.profileName.setText(it.child("name").value.toString())
                binding.profileEmail.setText(it.child("email").value.toString())
                binding.profileAge.setText(it.child("age").value.toString())
                binding.profileCalorieGoal.setText(it.child("calorieGoal").value.toString())
                binding.profileCurrentWeight.setText(it.child("currentWeight").value.toString())
            }
            Log.i(USER_PROFILE_TAG,"UserProfile was Read")
        }.addOnFailureListener {
            Log.e(USER_PROFILE_TAG,"UserProfile was not Read")
        }
    }
}