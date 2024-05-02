package com.xyz.myhealth.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.xyz.myhealth.R
import com.xyz.myhealth.activities.InformationActivity
import com.xyz.myhealth.activities.UserProfileActivity
import com.xyz.myhealth.services.DailyUserDataService
import com.xyz.myhealth.services.USER_PROFILE_TAG

/**
 * This fragment contains information of the Users today's data
 * UserIcon - Starts UserProfileActivity
 * InformationIcon - Starts InformationActivity
 */
class HomeFragment : Fragment() {
    private lateinit var userProfileButton : ImageView
    private lateinit var informationButton : ImageView

    private lateinit var homeCalorieIntake : TextView
    private lateinit var homeCalorieLost : TextView
    private lateinit var homeCalorieGain : TextView
    private lateinit var homeGlassOfWater : TextView
    private lateinit var homeStress : TextView

    private lateinit var dbListener : ValueEventListener


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        homeCalorieIntake = view.findViewById(R.id.homeCalorieIntake)
        homeCalorieLost = view.findViewById(R.id.homeCalorieLost)
        homeCalorieGain = view.findViewById(R.id.homeCalorieGain)
        homeGlassOfWater = view.findViewById(R.id.homeGlassOfWater)
        homeStress = view.findViewById(R.id.homeStress)

        readData("w@w")

        onUserProfileIconClicked(view)
        onInfoIconClicked(view)

        return view;
    }

    private fun onUserProfileIconClicked(view:View){
        userProfileButton = view.findViewById(R.id.homeUserProfile)
        userProfileButton.setOnClickListener(View.OnClickListener {
            val intent : Intent = Intent(context, UserProfileActivity::class.java)
            startActivity(intent)
        })
    }

    private fun onInfoIconClicked(view:View){
        informationButton = view.findViewById(R.id.homeInformation)
        informationButton.setOnClickListener(View.OnClickListener {
            val intent : Intent = Intent(context, InformationActivity::class.java)
            startActivity(intent)
        })
    }

    private fun readData(email:String){
        val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("DailyUserData")
        dbListener = database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                database.child(email).get().addOnSuccessListener {
                    if(it.exists()){
                        homeCalorieIntake.text = it.child("calorieIntake").value.toString()
                        homeCalorieLost.text = it.child("calorieLost").value.toString()
                        homeCalorieGain.text = it.child("netCalorieGain").value.toString()
                        homeGlassOfWater.text = it.child("glassOfWater").value.toString()
                        homeStress.text = it.child("stress").value.toString()
                    }
                    else{
                        DailyUserDataService.addOrUpdateDailyUserData(email,0F, 0F, 0F,0,"Null")
                        homeCalorieIntake.text = "0"
                        homeCalorieLost.text = "0"
                        homeCalorieGain.text = "0"
                        homeGlassOfWater.text = "0"
                        homeStress.text = "Null"
                    }
                    Log.i(USER_PROFILE_TAG,"UserProfile was Read")
                }.addOnFailureListener {
                    Log.e(USER_PROFILE_TAG,"UserProfile was not Read")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }
}