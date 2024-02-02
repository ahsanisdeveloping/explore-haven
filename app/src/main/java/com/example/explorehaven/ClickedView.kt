package com.example.explorehaven

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import com.example.explorehaven.databinding.ActivityClickedViewBinding
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class ClickedView : AppCompatActivity() {
    lateinit var binding:ActivityClickedViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClickedViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val listIntent = getIntent()
        binding.name.text = listIntent.getStringExtra("Name")
        binding.location.text = listIntent.getStringExtra("Location")
        binding.rent.text = listIntent.getStringExtra("Rent")
        binding.property.text = listIntent.getStringExtra("Property")
        binding.description.text = listIntent.getStringExtra("Description")
        Picasso.get().load(listIntent.getStringExtra("Image")?.toUri()).into(binding.clickedViewImage)

        binding.btnUpdate.setOnClickListener{
            openUpdateDialog(
                listIntent.getStringExtra("Name").toString(),
                listIntent.getStringExtra("Location").toString(),
                listIntent.getStringExtra("Rent").toString(),
                listIntent.getStringExtra("Property").toString(),
                listIntent.getStringExtra("Description").toString(),
                listIntent.getStringExtra("Image")?.toUri()
            )
        }
        binding.btnDelete.setOnClickListener {
            deleteRecord(
                listIntent.getStringExtra("Name").toString()
            )
        }
    }
    private fun deleteRecord(nameDelete: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("listings").child(nameDelete)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Listing deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, ViewListings::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }

    }
    private fun openUpdateDialog(
        Name: String,
        Location: String,
        Rent: String,
        Property: String,
        Description: String,
        Image: Uri?
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.updatedialogue, null)

        mDialog.setView(mDialogView)

        val etName = mDialogView.findViewById<EditText>(R.id.etName)
        val etRent = mDialogView.findViewById<EditText>(R.id.etRent)
        val etProperty = mDialogView.findViewById<EditText>(R.id.etType)
        val etLocation = mDialogView.findViewById<EditText>(R.id.etLocation)
        val etDescription = mDialogView.findViewById<EditText>(R.id.etDescription)
        val imageUpdate = mDialogView.findViewById<ImageView>(R.id.imageUpdate)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etName.setText(intent.getStringExtra("Name").toString())
        etRent.setText(intent.getStringExtra("Rent").toString())
        etProperty.setText(intent.getStringExtra("Property").toString())
        etLocation.setText(intent.getStringExtra("Location").toString())
        etDescription.setText(intent.getStringExtra("Property").toString())
        Picasso.get().load(intent.getStringExtra("Image")?.toUri()).into(imageUpdate)

        mDialog.setTitle("Updating $Name Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateListData(
                etName.text.toString(),
                etRent.text.toString(),
                etProperty.text.toString(),
                etLocation.text.toString(),
                etDescription.text.toString(),
                intent.getStringExtra("Image")?.toUri()
            )

            Toast.makeText(applicationContext, "List Data Updated", Toast.LENGTH_LONG).show()

            binding.name.text = etName.text.toString()
            binding.rent.text = etRent.text.toString()
            binding.property.text = etProperty.text.toString()
            binding.location.text = etLocation.text.toString()
            binding.description.text = etDescription.text.toString()
            Picasso.get().load(intent.getStringExtra("Image")?.toUri()).into(binding.clickedViewImage)

            alertDialog.dismiss()
        }
    }

    private fun updateListData(
        name: String,
        rent: String,
        property: String,
        location: String,
        description: String,
        image: Uri?
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("listings").child(intent.getStringExtra("Name").toString())
        val listInfo = Listing( name, description,location,rent,property,image.toString())
        dbRef.setValue(listInfo)
    }
}