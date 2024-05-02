package com.xyz.myhealth.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import com.xyz.myhealth.databinding.ActivityCalorieEntryBinding
import com.xyz.myhealth.services.AllTimeDataService

import com.xyz.myhealth.services.CalorieService
import com.xyz.myhealth.services.USER_PROFILE_TAG

/**
 * Calorie Entry Acitvity where the user inputs the food Item they have eaten
 * and the amount of calories they have consumed
 */
class CalorieEntryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalorieEntryBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalorieEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onCalorieEntrySaveClicked("w@w")
        onCalorieEntryCancelClicked()
    }

    private fun onCalorieEntrySaveClicked(email:String) {
        binding.calorieEntrySaveButton.setOnClickListener{
            CalorieService.addCalorieEntry(
                email,
                binding.foodItem.text.toString(),
                binding.foodCalorie.text.toString().toFloat()
            )
            database= FirebaseDatabase.getInstance().getReference("DailyUserData")
            database.child(email).get().addOnSuccessListener {
                if(it.exists()){
                    AllTimeDataService.addOrUpdateDailyUserData(
                        email,
                        it.child("calorieIntake").value.toString().toFloat()+binding.foodCalorie.text.toString().toFloat(),
                        it.child("calorieLost").value.toString().toFloat(),
                        it.child("netCalorieGain").value.toString().toFloat()+binding.foodCalorie.text.toString().toFloat(),
                        it.child("glassOfWater").value.toString().toInt(),
                        it.child("stress").value.toString()
                    )

                }
                Log.i(USER_PROFILE_TAG,"Stress was Read")
            }.addOnFailureListener {
                Log.e(USER_PROFILE_TAG,"Stress was not Read")
            }
            finish()
        }
    }

    private fun onCalorieEntryCancelClicked() {
        binding.calorieEntryCancelButton.setOnClickListener{
            finish()
        }
    }
}