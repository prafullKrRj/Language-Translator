package com.prafullkumar.languagetranslator.ui

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.prafullkumar.languagetranslator.R
import com.prafullkumar.languagetranslator.databinding.ActivityMainBinding
import com.prafullkumar.languagetranslator.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LanguageTransActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var sourceLang: TextView? = null
    private var targetLang: TextView? = null
    private var swapLang: ImageButton? = null
    private var sourceText: EditText? = null
    private var targetText: TextView? = null
    private var copyButton: ImageView? = null
    private var transLateButton: MaterialButton? = null

    private val viewModel: LanguageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        swapLang = binding.swapLangs.findViewById(R.id.swapLangs)
        sourceLang = binding.sourceLang.findViewById(R.id.sourceLang)
        targetLang = binding.targetLang.findViewById(R.id.targetLang)
        copyButton = binding.copyButton.findViewById(R.id.copyButton)
        sourceText = binding.sourceText.findViewById(R.id.sourceText)
        targetText = binding.targetText.findViewById(R.id.targetText)
        transLateButton = binding.translateButton.findViewById(R.id.translateButton)

        lifecycleScope.launch {
            var loadingDialog: AlertDialog? = null
            sourceLang?.text = viewModel.sourceLang
            targetLang?.text = viewModel.targetLang
            viewModel.uiState.collect {
                when (it) {
                    is Resource.Error -> {
                        loadingDialog?.dismiss()
                        MaterialAlertDialogBuilder(this@LanguageTransActivity)
                            .setTitle("Error")
                            .setMessage(it.exception.message)
                            .setPositiveButton("OK") { dialog, which ->
                                dialog.dismiss()
                            }
                            .show()
                    }
                    Resource.Initial -> {  }
                    Resource.Loading -> {
                        loadingDialog = loadingDialog()
                    }
                    is Resource.Success -> {
                        loadingDialog?.dismiss()
                        sourceText?.setText(it.data?.sourceText)
                        targetText?.text = it.data?.targetText
                    }
                }
            }
        }

        binding.translateButton.setOnClickListener {
            viewModel.getResponse(sourceText?.text.toString())
        }
        binding.swapLangs.setOnClickListener {
            val temp = sourceLang?.text
            sourceLang?.text = targetLang?.text
            targetLang?.text = temp
            viewModel.sourceLang = sourceLang?.text.toString()
            viewModel.targetLang = targetLang?.text.toString()
        }
        binding.sourceLangCard.setOnClickListener {
            selectionDialog { sl ->
                sourceLang?.text = sl
                viewModel.sourceLang = sl
            }
        }
        binding.targetLangCard.setOnClickListener {
            selectionDialog { tl ->
                targetLang?.text = tl
                viewModel.targetLang = tl
            }
        }
    }

    private fun selectionDialog(language: (String) -> Unit) {

        MaterialAlertDialogBuilder(this)
            .setTitle("Title")
            .setMessage("Message")
            .setOnDismissListener {

            }
            .setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
                language("English")
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
                language("Hindi")
            }
            .show()
    }

    private fun loadingDialog(): AlertDialog {
        return MaterialAlertDialogBuilder(this)
            .setTitle("Loading")
            .setMessage("Please wait")
            .setCancelable(false)
            .setView(R.layout.loading_bar)
            .show()
    }
}
