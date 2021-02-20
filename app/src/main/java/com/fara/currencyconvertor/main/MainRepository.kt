package com.fara.currencyconvertor.main

import androidx.lifecycle.ViewModel
import com.fara.currencyconvertor.data.CurrencyApi
import com.fara.currencyconvertor.data.model.CurrencyResponse
import com.fara.currencyconvertor.util.Resource
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: CurrencyApi
) : ViewModel() {

    suspend fun getRates(base: String): Resource<CurrencyResponse> {
        return try {
            val response = api.getRates(base)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occured")
        }
    }
}