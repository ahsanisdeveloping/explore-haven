package com.example.explorehaven

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.Toast
import com.example.explorehaven.databinding.ActivityAddAlistingBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AddAListing : AppCompatActivity() {
    lateinit var binding:ActivityAddAlistingBinding
    lateinit var nameETV:EditText
    lateinit var descETV:EditText
//    lateinit var locationETV:EditText
    lateinit var rentAmountETV:EditText
    lateinit var apartmentRB:RadioButton
    lateinit var houseRB:RadioButton
    lateinit var condoRB:RadioButton
    lateinit var galleryButton:Button
    lateinit var galleryImage: ImageView
    lateinit var addListingButton:Button
    lateinit var imageUri : Uri
    lateinit var location:String
    var IMAGE_REQUEST_CODE = 999
    lateinit var storageRef: StorageReference
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAlistingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        initializeVariables()
        val options = arrayOf("Naran", "Nathia Gali", "Murree", "Sawat","Gilgit","Khunjerab","Balakot")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = findViewById<Spinner>(R.id.spinnerLocation)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               location =  options[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(applicationContext,"Must select the location",Toast.LENGTH_SHORT).show()
            }
        }
        galleryButton.setOnClickListener{
            getImage()
        }
        
        addListingButton.setOnClickListener{
            var name = nameETV.text.toString()
            var description = descETV.text.toString()
//            var location = locationETV.text.toString()


            var rent = rentAmountETV.text.toString()
            var property = ""
            var image = imageUri.toString()

            if(apartmentRB.isChecked)
            {
                property = "Apartment"
            }
            else if(houseRB.isChecked)
            {
                property = "House"
            }
            else if(condoRB.isChecked)
            {
                property = "Condo"
            }
            else
            {
                property = "N/A"
            }
            storageRef = FirebaseStorage.getInstance().reference.child("Images")
            storageRef = storageRef.child(System.currentTimeMillis().toString())
            val database = Firebase.database.getReference("listings")
            imageUri?.let {
                storageRef.putFile(it).addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        storageRef.downloadUrl.addOnSuccessListener { uri ->

                            //val map = HashMap<String, Any>()
                            //map["pic"] = uri.toString()

                            val listingObject = Listing(name,description,location,rent,property,uri.toString())
                            database.child(name).setValue(listingObject).addOnSuccessListener { it: Void? ->



                                Toast.makeText(this,"Successfully Saved", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(applicationContext,Dashboard::class.java))
                            }.addOnFailureListener{

                                Toast.makeText(this,"Failed"+it.message, Toast.LENGTH_SHORT).show()


                            }
                            // binding.progressBar.visibility = View.GONE
//                            binding.imageView.setImageResource(R.drawable.vector)

                        }
                    }
                    else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                        // binding.progressBar.visibility = View.GONE
//                        binding.imageView.setImageResource(R.drawable.vector)
                    }
                }
            }
        }
    }
    fun initializeVariables(){
        nameETV = binding.nameETV
        descETV = binding.descETV
//        locationETV = binding.locationETV
        rentAmountETV = binding.rentETV
        apartmentRB = binding.apartmentRB
        houseRB = binding.houseRB
        condoRB = binding.condoRB
        galleryButton = binding.galleryButton
        galleryImage = binding.galleryImage
        addListingButton = binding.addListingButton
    }
    fun getImage(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK)
        {
                imageUri = data?.data!!
                galleryImage.setImageURI(imageUri)
        }
    }
}