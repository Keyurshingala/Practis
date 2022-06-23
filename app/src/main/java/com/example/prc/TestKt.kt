package com.example.prc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.prc.databinding.ActivityTestBinding


class TestKt : AppCompatActivity() {

    lateinit var bind: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityTestBinding.inflate(layoutInflater)
        setContentView(bind.root)

        /* val hot = TreeNode("Hot")
         val cold = TreeNode("Cold")
         val beverages = TreeNode("Beverages").run {
             add(hot)
             add(cold)
         }*/













        /*for (i in 1..5) {
            upTo(1,i)
            dwnTo(i-1)
            println()
        }*/
    }

    private fun dwnTo(n: Int) {
        if (n > 0) {
            print("$n ")
            dwnTo(n - 1)
        }
    }

    private fun upTo(i: Int, n: Int) {
        if (n > 0) {
            upTo(i, n - 1)
            print("$n ")
        }
    }
}
