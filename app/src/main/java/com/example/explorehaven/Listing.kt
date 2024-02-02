package com.example.explorehaven

import java.io.Serializable

class Listing(val name:String = "",val description:String = "", val location:String = "",
    val rent:String = "",val property:String = "",val image:String = ""
    ) : Serializable {
}