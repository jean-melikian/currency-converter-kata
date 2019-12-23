package fr.ozoneprojects.currencyconverter.usecases

import fr.ozoneprojects.currencyconverter.repository.CurrenciesRepository
import java.math.BigDecimal

class ConvertCurrencyUseCaseImpl(
    private val repository: CurrenciesRepository
) : ConvertCurrencyUseCase {
    override fun convert(amount: Int, currency: String): BigDecimal {
        require(!(amount < 0 || currency.isEmpty()))
        val currencies = repository.getCurrencies()
        currencies.find { it.currencyCode == currency }
            ?.let { if (it.factor > BigDecimal.ZERO) return it.factor * amount.toBigDecimal() }
        throw Exception()
    }

}