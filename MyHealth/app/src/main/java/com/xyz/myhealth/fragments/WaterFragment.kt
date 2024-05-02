package com.xyz.myhealth.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.xyz.myhealth.R
import com.xyz.myhealth.services.USER_PROFILE_TAG
import com.xyz.myhealth.services.WaterService

/***
 * This fragment is for User manually entering when he/she drank water
 * Drink button - Enters Users time of when they drank water
 */
class WaterFragment : Fragment() {
    private lateinit var drinkIcon : ImageView
    private lateinit var drinkWater : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_water, container, false)

        onClickDrinkIcon(view)
        onClickDrinkButton(view, "w@w")

        return view;
    }

    private fun onClickDrinkIcon(view: View){
        drinkIcon = view.findViewById(R.id.drinkIcon)
        drinkIcon.setOnClickListener(View.OnClickListener {
            Toast.makeText(this.context, "You clicked on DrinkIcon", Toast.LENGTH_SHORT).show()
        })
    }

    private fun onClickDrinkButton(view: View, email:String){
        drinkWater = view.findViewById(R.id.drinkWater)
        drinkWater.setOnClickListener(View.OnClickListener {
            WaterService.addDrinkWater(email)
        })

    }

}