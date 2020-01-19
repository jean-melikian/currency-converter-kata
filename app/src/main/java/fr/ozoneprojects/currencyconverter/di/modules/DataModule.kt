package fr.ozoneprojects.currencyconverter.di.modules

import dagger.Module
import dagger.Provides
import fr.ozoneprojects.currencyconverter.datasources.CurrenciesMapper
import fr.ozoneprojects.currencyconverter.datasources.CurrenciesResponseEntity
import fr.ozoneprojects.currencyconverter.datasources.Mapper
import fr.ozoneprojects.currencyconverter.repository.Currency
import javax.inject.Singleton

@Module
open class DataModule {
    @Singleton
    @Provides
    open fun providesCurrenciesEntityToModelMapper(): Mapper<CurrenciesResponseEntity, Map<String, Currency>> =
        CurrenciesMapper()
}