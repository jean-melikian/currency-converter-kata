package fr.ozoneprojects.currencyconverter.repository

interface CurrenciesRepository {
    fun getCurrencies(): List<Currency>

}
