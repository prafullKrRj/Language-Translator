package com.prafullkumar.languagetranslator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.prafullkumar.languagetranslator.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageTransActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    private fun showDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Title")
            .setMessage("Message")
            .setPositiveButton("OK") { dialog, which ->
                // Respond to positive button press
            }
            .setNegativeButton("Cancel") { dialog, which ->
                // Respond to negative button press
            }
            .show()
    }
}
