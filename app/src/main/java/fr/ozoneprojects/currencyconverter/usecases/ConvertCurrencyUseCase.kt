package fr.ozoneprojects.currencyconverter.usecases

import java.math.BigDecimal

interface ConvertCurrencyUseCase {
    fun convert(amount: Int, currency: String): BigDecimal
}