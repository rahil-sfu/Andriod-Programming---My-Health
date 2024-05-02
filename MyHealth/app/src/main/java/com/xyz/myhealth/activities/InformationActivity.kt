package com.xyz.myhealth.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.xyz.myhealth.R

/**
 * This activity is for information about the project ?
 * We could add all project related links here with the link of website.
 */
class InformationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)
    }

    fun onWebsiteClick(view: View){
        val openURL = Intent(android.content.Intent.ACTION_VIEW)
        openURL.data = Uri.parse("https://github.com/orgs/CMPT-362/repositories")
        startActivity(openURL)
    }

    fun onIdeaPitchClick(view: View){
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse("https://www.youtube.com/watch?v=EiwcIM5nCJs&t=7s")
        startActivity(openURL)
    }

    fun onShowAndTell1Click(view: View){
        val openURL = Intent(android.content.Intent.ACTION_VIEW)
        openURL.data = Uri.parse("https://www.youtube.com/watch?v=oVA249I5oaQ&t=35s")
        startActivity(openURL)
    }

    fun onShowAndTell2Click(view: View){
        val openURL = Intent(android.content.Intent.ACTION_VIEW)
        openURL.data = Uri.parse("https://www.youtube.com/watch?v=pPqIAxNsqtI")
        startActivity(openURL)
    }

    fun onInformationBackClicked(view: View){
        finish()
    }
}