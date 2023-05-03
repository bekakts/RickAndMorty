package com.example.rickandmortytest

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.rickandmortytest.databinding.ActivityMainBinding
import com.example.rickandmortytest.presentation.utils.ConnectionLiveData

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val cld = ConnectionLiveData(application)
        cld.observe(this) { answer ->
            binding.noConnection.noConnectionContainer.isVisible = !answer
        }
    }
}

