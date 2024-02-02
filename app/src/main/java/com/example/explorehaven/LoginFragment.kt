package com.example.explorehaven

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.explorehaven.databinding.FragmentLoginBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {


    lateinit var registerTV: TextView
    lateinit var emailETV: EditText
    lateinit var passwordETV: EditText
    lateinit var loginButton: Button
    lateinit var theUser:users
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_login, container, false)
        // Inflate the layout for this fragment
        emailETV = view.findViewById(R.id.emailETVLogin)
        passwordETV = view.findViewById(R.id.passwordETVLogin)
        loginButton = view.findViewById(R.id.loginButtonLogin)
        registerTV = view.findViewById(R.id.registerTV)
        var database = Firebase.database.getReference("users")

        loginButton.setOnClickListener {

            var email = emailETV.text.toString()
            var password = passwordETV.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Both email and password must be entered!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                database.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var userFound = false
                        for (userSnapshot in snapshot.children) {
                            val user = userSnapshot.getValue(users::class.java)
                            theUser = user!!
                            if (user != null && user.email == email && user.password == password) {
                                userFound = true
                                break
                            }
                        }
                        if(userFound)
                        {
                            Toast.makeText(
                                requireContext(),
                                "Login Successfull!",
                                Toast.LENGTH_SHORT
                            ).show()
                            val userIntent = Intent(requireContext(),Dashboard::class.java)
                            userIntent.putExtra("user",theUser)
                            startActivity(userIntent)
                        }
                        else{
                            Toast.makeText(
                                requireContext(),
                                "Invalid Credentials!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            requireContext(),
                            "Database error occurred: ${error.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        }

        registerTV.setOnClickListener {
//            startActivity(Intent(requireContext(), RegisterActivity::class.java))
        }
        return view
    }


}