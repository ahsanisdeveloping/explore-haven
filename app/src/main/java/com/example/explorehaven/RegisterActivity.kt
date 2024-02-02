//package com.example.explorehaven
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import com.example.explorehaven.databinding.ActivityRegisterBinding
//import com.google.firebase.Firebase
//import com.google.firebase.database.database
//
//class RegisterActivity : AppCompatActivity() {
//
//    lateinit var binding: ActivityRegisterBinding
//    lateinit var nameETVReg: EditText
//    lateinit var emailETVReg: EditText
//    lateinit var passwordETVReg: EditText
//    lateinit var cPasswordETVReg: EditText
//    lateinit var regButtonReg: Button
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        binding = ActivityRegisterBinding.inflate(layoutInflater)
//        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
//
//        nameETVReg = binding.nameETVReg
//        emailETVReg = binding.emailETVReg
//        passwordETVReg = binding.passwordETVReg
//        cPasswordETVReg = binding.cPasswordETVReg
//        regButtonReg = binding.regButtonReg
//
//        regButtonReg.setOnClickListener {
//            var name = nameETVReg.text.toString()
//            var email = emailETVReg.text.toString()
//            var password = passwordETVReg.text.toString()
//            var cPassword = cPasswordETVReg.text.toString()
//
//            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || cPassword.isEmpty()) {
//                Toast.makeText(
//                    applicationContext,
//                    "All the fields must be filled!",
//                    Toast.LENGTH_SHORT
//                ).show()
//            } else {
//                if (password == cPassword) {
//                    val userObject = users(name, email, password)
//                    val database = Firebase.database.getReference("users")
//                    database.child(name).setValue(userObject).addOnSuccessListener {
//                        Toast.makeText(
//                            applicationContext,
//                            "Registered Successfully!",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        startActivity(Intent(applicationContext,LoginActivity::class.java))
//                    }
//                }
//                else{
//                    Toast.makeText(
//                        applicationContext,
//                        "Passwords doesn't match!",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//        }
//
//    }
//}