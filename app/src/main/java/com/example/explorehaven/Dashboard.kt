package com.example.explorehaven

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.explorehaven.databinding.ActivityDashboardBinding

class Dashboard : AppCompatActivity() {

    lateinit var binding:ActivityDashboardBinding
    lateinit var outputETV:TextView
    lateinit var addAListingButton:Button
    lateinit var viewListingButton:Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        outputETV = binding.outputETV
        addAListingButton = binding.addAListingButton
        viewListingButton = binding.viewListingButton

        val intent = getIntent()
        var userObject = intent.getSerializableExtra("user") as? users
        outputETV.text = "Welcome! " + userObject?.name

        addAListingButton.setOnClickListener{
            startActivity(Intent(applicationContext,AddAListing::class.java))
        }
        viewListingButton.setOnClickListener{
            startActivity(Intent(applicationContext,ViewListings::class.java))
        }

    }
}