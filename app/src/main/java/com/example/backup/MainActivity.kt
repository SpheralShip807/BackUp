package com.example.backup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.backup.databinding.ActivityMainBinding
import com.example.backup.storage.AppPreference

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

        binding.btnResetScore.setOnClickListener {
            val preference=AppPreference(this)
            preference.clearHighScore()

            binding.tvHighScore.text="High score: ${preference.getHighScore()}"

            Toast.makeText(this,"Score reset successfully",Toast.LENGTH_SHORT).show()
        }

        binding.btnExit.setOnClickListener {
            finish()
        }
    }
}