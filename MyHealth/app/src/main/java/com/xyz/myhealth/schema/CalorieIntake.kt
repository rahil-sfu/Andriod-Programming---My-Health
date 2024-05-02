package com.xyz.myhealth.schema

/**
 * Calorie Intake Schema
 */
data class CalorieIntake(
    val foodItem: String? = null,
    val foodCalorie: Float? = null,
    val timeStamp: String? = null,
)