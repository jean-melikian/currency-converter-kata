package fr.ozoneprojects.currencyconverter.datasources

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.util.*

data class CurrenciesResponseEntity(
    @SerializedName("rates") val rates: Map<String, BigDecimal>,
    @SerializedName("base") val base: String,
    @SerializedName("date") val date: Date
)
