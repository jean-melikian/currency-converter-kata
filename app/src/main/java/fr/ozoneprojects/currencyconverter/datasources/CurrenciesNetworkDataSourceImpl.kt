package fr.ozoneprojects.currencyconverter.datasources

import fr.ozoneprojects.currencyconverter.exceptions.ResponseFailure
import javax.inject.Inject

open class CurrenciesNetworkDataSourceImpl
@Inject
constructor(
    val currenciesRatesApi: CurrenciesRatesApi
) : CurrenciesDataSource {
    override fun getCurrencies(baseCurrency: String?): CurrenciesResponseEntity {

        val call = currenciesRatesApi.getLatestRatesForCurrency(baseCurrency)
        val response = call.execute()
        if (response.code() == 404) throw ResponseFailure.CurrencyNotFoundException()
        if (response.isSuccessful) {
            return response.body()!!
        }
        throw ResponseFailure.ErrorException()
    }
}