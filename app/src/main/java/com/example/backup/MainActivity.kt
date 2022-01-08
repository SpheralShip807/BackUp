package com.example.backup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.backup.databinding.ActivityMainBinding
import java.lang.Exception
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnNewGame.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }



        binding.btnExit.setOnClickListener {
            finish()
        }
    }
}