package com.example.prc.fire_store

import android.os.Bundle
import com.example.prc.Base
import com.example.prc.databinding.ActivityFireStoreBinding
import com.example.prc.gson
import com.example.prc.log
import com.example.prc.print
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FireStoreActivity : Base() {

    lateinit var bind: ActivityFireStoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityFireStoreBinding.inflate(layoutInflater)
        setContentView(bind.root)

        try {
            initUI()
        } catch (e: Exception) {
            e.print()
        }
    }

    class AB(val a: String, val b: String) {
        constructor() : this("", "")
    }

    private fun initUI() {
        signIn()
//        getData()
    }

    private fun signIn() {
        val auth = Firebase.auth

        val currentUser = auth.currentUser
        if (currentUser == null) {
            auth.signInAnonymously()
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            ("signInAnonymously:success").log()
                            val user = auth.currentUser
                            getData()
                        } else {
                            // If sign in fails, display a message to the user.
                            "Auth filed: ${task.exception}".log()
                        }
                    }
        } else {
            getData()
        }
    }

    private fun getData() {
        //to read specific object pass collection and object id
        /*"obj start".log()
        Firebase.firestore.collection("base").document("lx2jW0es3J7XyjX5k5CB")
                .get().addOnSuccessListener { documentSnapshot ->

                    val city = documentSnapshot.toObject<AB>()

                    "obj end".log()
                    city.gson().log()
                }.addOnFailureListener { e ->
                    e.message.log()
                    e.printStackTrace()
                }*/

        //to read object of collection pass collection name
        "base start".log()
        Firebase.firestore.collection("base")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        document.data.gson().log()
                    }
                    "base end".log()

                }.addOnFailureListener { e ->
                    e.log()
                }
    }
}