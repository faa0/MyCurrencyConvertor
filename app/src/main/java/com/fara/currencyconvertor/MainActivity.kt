package com.fara.currencyconvertor

import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.fara.currencyconvertor.databinding.ActivityMainBinding
import com.fara.currencyconvertor.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnConvert.setOnClickListener {
                viewModel.convert(
                    etFrom.text.toString(),
                    spFromCurrency.selectedItem.toString(),
                    spToCurrency.selectedItem.toString()
                )
            }

            lifecycleScope.launchWhenCreated {
                viewModel.conversion.collect {
                    when (it) {
                        is MainViewModel.CurrencyEvent.Success -> {
                            progressBar.isVisible = false
                            tvResult.setTextColor(Color.BLACK)
                            tvResult.text = it.resultText
                        }
                        is MainViewModel.CurrencyEvent.Failure -> {
                            progressBar.isVisible = true
                            tvResult.setTextColor(Color.RED)
                            tvResult.text = it.errorText
                        }
                        is MainViewModel.CurrencyEvent.Loading -> {
                            progressBar.isVisible = true
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}