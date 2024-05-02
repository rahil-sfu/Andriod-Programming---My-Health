package com.xyz.myhealth.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.slider.Slider
import com.xyz.myhealth.R
import com.xyz.myhealth.services.StressService

/**
 * This fragment is for seeing stress values of users
 * Save - Saves stress values of users
 * Reset - Sets all slider values to zero
 */
class StressFragment : Fragment() {
    private lateinit var saveButton : Button
    private lateinit var stressHistory : ImageView
    private lateinit var resetButton : Button

    //sliders
    private lateinit var slider1 : Slider
    private lateinit var slider2 : Slider
    private lateinit var slider3 : Slider
    private lateinit var slider4 : Slider
    private lateinit var slider5 : Slider
    private lateinit var slider6 : Slider
    private lateinit var slider7 : Slider
    private lateinit var slider8 : Slider
    private lateinit var slider9 : Slider
    private lateinit var slider10 : Slider

    private lateinit var intent: Intent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_stress, container, false)

        onStressSaveClicked(view)
        onStressResetClicked(view)

        return view;
    }

    // when save button is clicked
    private fun onStressSaveClicked(view: View){
        saveButton = view.findViewById(R.id.stressSave)
        saveButton.setOnClickListener(View.OnClickListener {
            val stressValue : Int = getAllSliderValue(view)
            setAllSliderToDefault(view)
            StressService.addStressEntry("w@w",stressValue)
        })
    }

    // when reset button is clicked
    private fun onStressResetClicked(view: View){
        resetButton = view.findViewById(R.id.stressReset)
        resetButton.setOnClickListener(View.OnClickListener {
            setAllSliderToDefault(view)
        })
    }

    // set all sliders to zero
    private fun setAllSliderToDefault(view: View){
        slider1 = view.findViewById(R.id.discreteSlider_1)
        slider2 = view.findViewById(R.id.discreteSlider_2)
        slider3 = view.findViewById(R.id.discreteSlider_3)
        slider4 = view.findViewById(R.id.discreteSlider_4)
        slider5 = view.findViewById(R.id.discreteSlider_5)
        slider6 = view.findViewById(R.id.discreteSlider_6)
        slider7 = view.findViewById(R.id.discreteSlider_7)
        slider8 = view.findViewById(R.id.discreteSlider_8)
        slider9 = view.findViewById(R.id.discreteSlider_9)
        slider10 = view.findViewById(R.id.discreteSlider_10)

        slider1.value = 0F;
        slider2.value = 0F;
        slider3.value = 0F;
        slider4.value = 0F;
        slider5.value = 0F;
        slider6.value = 0F;
        slider7.value = 0F;
        slider8.value = 0F;
        slider9.value = 0F;
        slider10.value = 0F;
    }

    // calculate sum of all slider values
    private fun getAllSliderValue(view:View):Int{
        // all sliders
        slider1 = view.findViewById(R.id.discreteSlider_1)
        slider2 = view.findViewById(R.id.discreteSlider_2)
        slider3 = view.findViewById(R.id.discreteSlider_3)
        slider4 = view.findViewById(R.id.discreteSlider_4)
        slider5 = view.findViewById(R.id.discreteSlider_5)
        slider6 = view.findViewById(R.id.discreteSlider_6)
        slider7 = view.findViewById(R.id.discreteSlider_7)
        slider8 = view.findViewById(R.id.discreteSlider_8)
        slider9 = view.findViewById(R.id.discreteSlider_9)
        slider10 = view.findViewById(R.id.discreteSlider_10)

        val firstHalf : Int = getSliderValue(slider1) + getSliderValue(slider2) + getSliderValue(slider3) + getSliderValue(slider4) + getSliderValue(slider5)
        val secondHalf : Int = getSliderValue(slider6) + getSliderValue(slider7) + getSliderValue(slider8) + getSliderValue(slider9) + getSliderValue(slider10)

        return firstHalf + secondHalf;
    }

    // get individual value of slider
    private fun getSliderValue(slider: Slider): Int {
        return slider.value.toInt()
    }

}