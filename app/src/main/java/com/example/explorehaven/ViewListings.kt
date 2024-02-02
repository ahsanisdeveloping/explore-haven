package com.example.explorehaven

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.explorehaven.databinding.ActivityViewListingsBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ViewListings : AppCompatActivity() {
    lateinit var binding:ActivityViewListingsBinding
    lateinit var adapter:ListingAdapter
    lateinit var database:DatabaseReference
    lateinit var myList:MutableList<Listing>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewListingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = FirebaseDatabase.getInstance().getReference("listings")
        myList = mutableListOf<Listing>()
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ListingAdapter(myList,applicationContext)
        binding.recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : ListingAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val listingIntent = Intent(applicationContext,ClickedView::class.java)
                listingIntent.putExtra("Name",myList[position].name)
                listingIntent.putExtra("Description",myList[position].description)
                listingIntent.putExtra("Rent",myList[position].rent)
                listingIntent.putExtra("Location",myList[position].location)
                listingIntent.putExtra("Property",myList[position].property)
                listingIntent.putExtra("Image",myList[position].image)
                startActivity(listingIntent)
            }

        })
        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    Log.d("TAG", "snapshot exists")

                    for (userSnapshot in snapshot.children) {
                        val list = userSnapshot.getValue(Listing::class.java)
                        myList.add(list!!)
                    }

//                    Toast.makeText(applicationContext,myList[0].name,Toast.LENGTH_SHORT).show()
                    adapter.notifyDataSetChanged()
//                    Toast.makeText(applicationContext,adapter.listingList[0].image,Toast.LENGTH_SHORT).show()

                }


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.message+"faliure", Toast.LENGTH_SHORT).show()
            }


        })
        binding.recyclerSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                myList.clear()
                adapter.notifyDataSetChanged()
                database.addValueEventListener(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {

                        if (snapshot.exists()) {
                            Log.d("TAG", "snapshot exists")

                            for (userSnapshot in snapshot.children) {
                                val list = userSnapshot.getValue(Listing::class.java)
                                if(list?.location!!.contains(newText.toString())){
                                    myList.add(list!!)
                                }

                            }

//                    Toast.makeText(applicationContext,myList[0].name,Toast.LENGTH_SHORT).show()
                            adapter.notifyDataSetChanged()
//                    Toast.makeText(applicationContext,adapter.listingList[0].image,Toast.LENGTH_SHORT).show()

                        }


                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(applicationContext, error.message+"faliure", Toast.LENGTH_SHORT).show()
                    }


                })
                return true
            }

        })
    }
}