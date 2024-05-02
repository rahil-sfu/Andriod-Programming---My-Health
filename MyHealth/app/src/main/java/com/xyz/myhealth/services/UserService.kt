package com.xyz.myhealth.services

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.xyz.myhealth.schema.DailyUserData
import com.xyz.myhealth.schema.UserProfile
import java.util.*

const val USER_PROFILE_TAG = "USER_PROFILE"

/**
 * Custom function for user related services
 */
object UserService {

    fun addOrUpdateUserProfile(name: String, email: String, age: Int, calorieGoal:Float, currentWeight:Float){
        val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("UserProfiles")
        val newUserProfile = UserProfile(name,email,age,calorieGoal,currentWeight, TimeService.getTimeStamp())
        database.child(email).setValue(newUserProfile).addOnSuccessListener {
            Log.i(USER_PROFILE_TAG,"UserProfile was Saved")
        }.addOnFailureListener {
            Log.e(USER_PROFILE_TAG,"UserProfile was not Saved")
        }
    }

}