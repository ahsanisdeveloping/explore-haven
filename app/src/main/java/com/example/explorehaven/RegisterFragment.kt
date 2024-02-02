package com.example.explorehaven

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.database.database

class RegisterFragment : Fragment() {

    lateinit var nameETVReg: EditText
    lateinit var emailETVReg: EditText
    lateinit var passwordETVReg: EditText
    lateinit var cPasswordETVReg: EditText
    lateinit var regButtonReg: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_register, container, false)
        nameETVReg = view.findViewById(R.id.nameETVReg)
        emailETVReg = view.findViewById(R.id.emailETVReg)
        passwordETVReg = view.findViewById(R.id.passwordETVReg)
        cPasswordETVReg = view.findViewById(R.id.cPasswordETVReg)
        regButtonReg = view.findViewById(R.id.regButtonReg)
        regButtonReg.setOnClickListener {
            var name = nameETVReg.text.toString()
            var email = emailETVReg.text.toString()
            var password = passwordETVReg.text.toString()
            var cPassword = cPasswordETVReg.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || cPassword.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "All the fields must be filled!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (password == cPassword) {
                    val userObject = users(name, email, password)
                    val database = Firebase.database.getReference("users")
                    database.child(name).setValue(userObject).addOnSuccessListener {
                        Toast.makeText(
                            requireContext(),
                            "Registered Successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
//                        startActivity(Intent(requireContext(),LoginActivity::class.java))
                    }
                }
                else{
                    Toast.makeText(
                        requireContext(),
                        "Passwords doesn't match!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        // Inflate the layout for this fragment
        return view
    }


}