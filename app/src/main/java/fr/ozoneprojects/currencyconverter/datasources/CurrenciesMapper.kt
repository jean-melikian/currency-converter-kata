package fr.ozoneprojects.currencyconverter.datasources

import fr.ozoneprojects.currencyconverter.repository.Currency

class CurrenciesMapper : Mapper<CurrenciesResponseEntity, Map<String, Currency>> {

    override fun toModel(entity: CurrenciesResponseEntity): Map<String, Currency> =
        entity.rates.map {
            Currency(it.key, it.value, entity.date)
        }.associateBy { it.currencyCode }
}