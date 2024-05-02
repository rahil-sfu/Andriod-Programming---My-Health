package com.xyz.myhealth.services

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar

/**
 * Time Stamp
 */
object TimeService {
    @SuppressLint("SimpleDateFormat")
    fun getTimeStamp():String{
        val c: Calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("MM/dd/yyyy HH:mm aa")
        return sdf.format(c)
    }

}