package fr.ozoneprojects.currencyconverter.repository

import com.google.gson.Gson
import fr.ozoneprojects.currencyconverter.datasources.CurrenciesDataSource
import fr.ozoneprojects.currencyconverter.datasources.CurrenciesMapper
import fr.ozoneprojects.currencyconverter.datasources.CurrenciesResponseEntity
import fr.ozoneprojects.currencyconverter.datasources.Mapper
import fr.ozoneprojects.currencyconverter.exceptions.ResponseFailure
import io.kotlintest.TestCase
import io.kotlintest.matchers.numerics.shouldBeExactly
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import org.mockito.BDDMockito
import org.mockito.Mockito

class CurrenciesRepositoryImplTest : StringSpec() {

    private lateinit var repository: CurrenciesRepository
    private lateinit var dataSource: CurrenciesDataSource
    private lateinit var mapper: Mapper<CurrenciesResponseEntity, Map<String, Currency>>

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        mapper = CurrenciesMapper()
        dataSource = Mockito.mock(CurrenciesDataSource::class.java)
        repository = CurrenciesRepositoryImpl(dataSource, mapper)
    }

    init {
        "when call to EUR based conversion rates and response is an empty map should throw exception" {
            BDDMockito.given(dataSource.getCurrencies()).willReturn(
                Gson().fromJson(EMPTY_MAP_RESPONSE, CurrenciesResponseEntity::class.java)
            )

            val exception = shouldThrow<ResponseFailure.EmptyResponseException> {
                repository.getCurrencies()
            }
            exception::class.java shouldBe ResponseFailure.EmptyResponseException::class.java
        }

        "when call to EUR based conversion rates, the mapped Currency models should contain as many entries as the entity's rates map does" {
            val mockData = Gson().fromJson(VALID_RESPONSE, CurrenciesResponseEntity::class.java)
            BDDMockito.given(dataSource.getCurrencies()).willReturn(mockData)
            repository.getCurrencies().count() shouldBeExactly mockData.rates.size
        }

        "when call to EUR based conversion rates, the mapped Currency's date should be exactly the same as entity" {
            val mockData = Gson().fromJson(VALID_RESPONSE, CurrenciesResponseEntity::class.java)
            BDDMockito.given(dataSource.getCurrencies()).willReturn(mockData)
            repository.getCurrencies().forEach {
                it.date shouldBe mockData.date
            }
        }
    }

    companion object {
        const val EMPTY_MAP_RESPONSE = "{\"rates\":{},\"base\":\"EUR\",\"date\":\"2019-12-30\"}"
        const val VALID_RESPONSE =
            "{\"rates\":{\"CAD\":1.4471,\"HKD\":8.6713,\"ISK\":136.9,\"PHP\":56.983,\"DKK\":7.4731,\"HUF\":330.53,\"CZK\":25.36,\"AUD\":1.6031,\"RON\":4.7784,\"SEK\":10.4858,\"IDR\":15536.69,\"INR\":80.0085,\"BRL\":4.5271,\"RUB\":69.119,\"HRK\":7.4463,\"JPY\":120.54,\"THB\":33.614,\"CHF\":1.084,\"SGD\":1.5047,\"PLN\":4.2493,\"BGN\":1.9558,\"TRY\":6.6587,\"CNY\":7.7712,\"NOK\":9.8315,\"NZD\":1.6718,\"ZAR\":15.9222,\"USD\":1.1147,\"MXN\":21.1433,\"ILS\":3.8766,\"GBP\":0.85115,\"KRW\":1300.32,\"MYR\":4.5725},\"base\":\"EUR\",\"date\":\"2020-01-03\"}"
    }
}