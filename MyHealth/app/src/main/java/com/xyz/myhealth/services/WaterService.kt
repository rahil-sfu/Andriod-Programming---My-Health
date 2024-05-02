package com.xyz.myhealth.services

import android.util.Log
import com.google.firebase.database.*
import com.xyz.myhealth.schema.DrinkWater

private const val TAG = "DRINK_WATER"

/**
 * Water services
 */
object WaterService {
    fun addDrinkWater(email:String){
        val database : DatabaseReference = FirebaseDatabase.getInstance().getReference("DrinkWater")
        val newDrinkTime = DrinkWater(TimeService.getTimeStamp())
        database.child(email).push().setValue(newDrinkTime).addOnSuccessListener {
            val db : DatabaseReference= FirebaseDatabase.getInstance().getReference("DailyUserData")
            db.child(email).get().addOnSuccessListener {
                if(it.exists()){
                    AllTimeDataService.addOrUpdateDailyUserData(
                        email,
                        it.child("calorieIntake").value.toString().toFloat(),
                        it.child("calorieLost").value.toString().toFloat(),
                        it.child("netCalorieGain").value.toString().toFloat(),
                        it.child("glassOfWater").value.toString().toInt()+1,
                        it.child("stress").value.toString()
                    )

                }
                Log.i(USER_PROFILE_TAG,"Stress was Read")
            }.addOnFailureListener {
                Log.e(USER_PROFILE_TAG,"Stress was not Read")
            }
            Log.i(TAG,"DrinkWater was Saved")
        }.addOnFailureListener {
            Log.e(TAG,"DrinkWater was not Saved")
        }
    }
}