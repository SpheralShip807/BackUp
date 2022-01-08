package com.example.backup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.backup.databinding.ActivityGameBinding
import com.example.backup.storage.AppPreference

class GameActivity : AppCompatActivity() {
    lateinit var binding: ActivityGameBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var appPreferences = AppPreference(this)

        binding.tvHighScore.text = "${appPreferences.getHighScore()}"

        binding.tvCurrentScore.text = "0"
    }
}