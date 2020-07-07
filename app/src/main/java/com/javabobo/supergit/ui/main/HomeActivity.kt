package com.javabobo.supergit.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.javabobo.supergit.R

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_github_image)
        supportActionBar?.title = ""

    }
}