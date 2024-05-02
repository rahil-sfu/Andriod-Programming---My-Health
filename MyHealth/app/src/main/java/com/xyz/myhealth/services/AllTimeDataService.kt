package com.xyz.myhealth.services

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.xyz.myhealth.schema.DailyUserData

private const val DAILY_USER_TAG = "DAILY_USER_TAG"

/**
 * Custom functions to store all time data
 */
object AllTimeDataService {
    fun addOrUpdateDailyUserData(
        email:String,
        calorieIntake: Float,
        calorieLost: Float,
        netCalorieGain: Float,
        glassOfWater: Int,
        stress : String,
    ){
        val database : DatabaseReference = FirebaseDatabase.getInstance().getReference("DailyUserData")
        val newUserData = DailyUserData(calorieIntake,calorieLost, netCalorieGain,glassOfWater,stress)
        database.child(email).setValue(newUserData).addOnSuccessListener {
            Log.i(DAILY_USER_TAG,"DailyUserData was Saved")
        }.addOnFailureListener {
            Log.e(DAILY_USER_TAG,"DailyUserData was not Saved")
        }
    }
}