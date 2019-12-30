package fr.ozoneprojects.currencyconverter.usecases

import fr.ozoneprojects.currencyconverter.exceptions.ConversionError
import fr.ozoneprojects.currencyconverter.exceptions.InvalidArgument
import fr.ozoneprojects.currencyconverter.exceptions.ResponseFailure
import fr.ozoneprojects.currencyconverter.repository.CurrenciesRepository
import java.math.BigDecimal

class ConvertCurrencyUseCaseImpl(
    private val repository: CurrenciesRepository
) : ConvertCurrencyUseCase {
    override fun convert(amount: Int, currency: String): BigDecimal {
        if (amount <= 0) throw InvalidArgument.AmountException()
        if (currency.isEmpty()) throw InvalidArgument.CurrencyCodeException()

        val currencies = repository.getCurrencies()
        if (currencies.isEmpty()) throw ResponseFailure.EmptyResponseException()
        currencies.find { it.currencyCode == currency }
            ?.let {
                if (it.factor <= BigDecimal.ZERO) throw ConversionError.InvalidConvertFactorException()
                return it.factor * amount.toBigDecimal()
            } ?: throw ResponseFailure.CurrencyNotFoundException()
    }

}