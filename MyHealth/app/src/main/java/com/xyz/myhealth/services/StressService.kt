package com.xyz.myhealth.services

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.xyz.myhealth.schema.Stress

private const val STRESS_TAG = "STRESS"

/**
 * Custom functions to store stress entry of user
 */
object StressService {
    fun addStressEntry(email:String, stressValue: Int){
        val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Stress")
        val newStress = Stress(stressValue, TimeService.getTimeStamp())
        database.child(email).push().setValue(newStress).addOnSuccessListener {
            val db : DatabaseReference= FirebaseDatabase.getInstance().getReference("DailyUserData")
            db.child(email).get().addOnSuccessListener {
                if(it.exists()){
                    if(stressValue < 34){
                        AllTimeDataService.addOrUpdateDailyUserData(
                            email,
                            it.child("calorieIntake").value.toString().toFloat(),
                            it.child("calorieLost").value.toString().toFloat(),
                            it.child("netCalorieGain").value.toString().toFloat(),
                            it.child("glassOfWater").value.toString().toInt(),
                            "low"
                        )
                    }
                    else if(stressValue < 67){
                        AllTimeDataService.addOrUpdateDailyUserData(
                            email,
                            it.child("calorieIntake").value.toString().toFloat(),
                            it.child("calorieLost").value.toString().toFloat(),
                            it.child("netCalorieGain").value.toString().toFloat(),
                            it.child("glassOfWater").value.toString().toInt(),
                            "mid"
                        )
                    } else {
                        AllTimeDataService.addOrUpdateDailyUserData(
                            email,
                            it.child("calorieIntake").value.toString().toFloat(),
                            it.child("calorieLost").value.toString().toFloat(),
                            it.child("netCalorieGain").value.toString().toFloat(),
                            it.child("glassOfWater").value.toString().toInt(),
                            "high"
                        )
                    }
                }
            }.addOnFailureListener {
                Log.e(STRESS_TAG,"Stress was not Read")
            }
            Log.i(STRESS_TAG,"Stress was Saved")
        }.addOnFailureListener {
            Log.e(STRESS_TAG,"Stress was Saved")
        }
    }
}