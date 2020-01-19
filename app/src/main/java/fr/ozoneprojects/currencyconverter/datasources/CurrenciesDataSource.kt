package fr.ozoneprojects.currencyconverter.datasources

interface CurrenciesDataSource {
    fun getCurrencies(baseCurrency: String? = null): CurrenciesResponseEntity
}
