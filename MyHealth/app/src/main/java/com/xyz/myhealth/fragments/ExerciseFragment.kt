package com.xyz.myhealth.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.xyz.myhealth.R
import com.xyz.myhealth.activities.AutomaticCalorieEntryActivity
import com.xyz.myhealth.activities.CalorieEntryActivity
import com.xyz.myhealth.activities.ManualEntryActivity

/**
 * This is the calorie checker where the user inputs their calories
 * in three ways: calorieEntry, CalorieIntake and AutomaticEntry
 */
class ExerciseFragment : Fragment() {

    private lateinit var calorieEntryButton : ImageView
    private lateinit var manualEntryButton : ImageView
    private lateinit var automaticEntryButton : ImageView

    private lateinit var intent: Intent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_exercise, container, false)

        calorieEntryButton = view.findViewById(R.id.calorieEntryIcon)
        calorieEntryButton.setOnClickListener(View.OnClickListener {
            //Toast.makeText(this.context, "You clicked on CalorieEntry", Toast.LENGTH_SHORT).show()
            intent = Intent(context, CalorieEntryActivity::class.java)
            startActivity(intent)
        })

        manualEntryButton = view.findViewById(R.id.manualEntryIcon)
        manualEntryButton.setOnClickListener(View.OnClickListener {
            intent = Intent(context, ManualEntryActivity::class.java)
            startActivity(intent)
        })

        automaticEntryButton = view.findViewById(R.id.automaticEntryIcon)
        automaticEntryButton.setOnClickListener(View.OnClickListener {
            intent = Intent(context, AutomaticCalorieEntryActivity::class.java)
            startActivity(intent)
        })

        return view;
    }

}