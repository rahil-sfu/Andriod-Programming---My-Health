package com.xyz.myhealth.schema

/**
 * Calorie Entry Schema
 */
data class CalorieEntry(
    val activityPerformed: String? = null,
    val activityDuration: Int? = null,
    val activityCalorieLost: Float? = null,
    val activityHeartRate: Float? = null,
    val timeStamp: String? = null,
)