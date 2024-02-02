//package com.example.explorehaven
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.TextView
//import android.widget.Toast
//import com.example.explorehaven.databinding.ActivityLoginBinding
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.ValueEventListener
//import com.google.firebase.database.ktx.database
//import com.google.firebase.ktx.Firebase
//
//class LoginActivity : AppCompatActivity() {
//
//
//    lateinit var binding: ActivityLoginBinding
//    lateinit var registerTV: TextView
//    lateinit var emailETV: EditText
//    lateinit var passwordETV: EditText
//    lateinit var loginButton: Button
//    lateinit var theUser:users
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityLoginBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        emailETV = binding.emailETVLogin
//        passwordETV = binding.passwordETVLogin
//        loginButton = binding.loginButtonLogin
//        registerTV = binding.registerTV
//        var database = Firebase.database.getReference("users")
//
//
//        loginButton.setOnClickListener {
//
//            var email = emailETV.text.toString()
//            var password = passwordETV.text.toString()
//
//            if (email.isEmpty() || password.isEmpty()) {
//                Toast.makeText(
//                    applicationContext,
//                    "Both email and password must be entered!",
//                    Toast.LENGTH_SHORT
//                ).show()
//            } else {
//                database.addListenerForSingleValueEvent(object : ValueEventListener {
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        var userFound = false
//                        for (userSnapshot in snapshot.children) {
//                            val user = userSnapshot.getValue(users::class.java)
//                            theUser = user!!
//                            if (user != null && user.email == email && user.password == password) {
//                                userFound = true
//                                break
//                            }
//                        }
//                        if(userFound)
//                        {
//                            Toast.makeText(
//                                applicationContext,
//                                "Login Successfull!",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                            val userIntent = Intent(applicationContext,Dashboard::class.java)
//                            userIntent.putExtra("user",theUser)
//                            startActivity(userIntent)
//                        }
//                        else{
//                            Toast.makeText(
//                                applicationContext,
//                                "Invalid Credentials!",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
//                    override fun onCancelled(error: DatabaseError) {
//                        Toast.makeText(
//                            applicationContext,
//                            "Database error occurred: ${error.message}",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                })
//            }
//        }
//
//        registerTV.setOnClickListener {
//            startActivity(Intent(applicationContext, RegisterActivity::class.java))
//        }
//    }
//}