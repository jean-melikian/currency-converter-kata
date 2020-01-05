package fr.ozoneprojects.currencyconverter.repository

import fr.ozoneprojects.currencyconverter.datasources.CurrenciesDataSource
import fr.ozoneprojects.currencyconverter.datasources.CurrenciesResponseEntity
import fr.ozoneprojects.currencyconverter.datasources.Mapper
import fr.ozoneprojects.currencyconverter.exceptions.ResponseFailure

class CurrenciesRepositoryImpl(
    private val dataSource: CurrenciesDataSource,
    private val mapper: Mapper<CurrenciesResponseEntity, Map<String, Currency>>
) : CurrenciesRepository {
    override fun getCurrencies(): List<Currency> {

        val response = mapper.toModel(dataSource.getCurrencies())
        if (response.isEmpty()) throw ResponseFailure.EmptyResponseException()
        return response.values.toList()
    }
}