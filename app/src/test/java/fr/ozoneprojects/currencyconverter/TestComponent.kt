package fr.ozoneprojects.currencyconverter

import dagger.Component
import fr.ozoneprojects.currencyconverter.datasources.CurrenciesNetworkDataSourceImplTest
import fr.ozoneprojects.currencyconverter.di.AppComponent
import fr.ozoneprojects.currencyconverter.di.modules.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface TestComponent : AppComponent {
    fun inject(currenciesNetworkDataSourceImplTest: CurrenciesNetworkDataSourceImplTest)
}