package fr.ozoneprojects.currencyconverter.datasources

import com.xebialabs.restito.server.StubServer
import fr.ozoneprojects.currencyconverter.exceptions.ResponseFailure
import io.kotlintest.TestCase
import io.kotlintest.TestResult
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.mockito.Mockito.RETURNS_DEEP_STUBS
import retrofit2.Response

class CurrenciesNetworkDataSourceImplTest : StringSpec() {
    lateinit var currenciesRatesApi: CurrenciesRatesApi
    private lateinit var dataSource: CurrenciesNetworkDataSourceImpl

    private lateinit var server: StubServer

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        server = StubServer().run()
        currenciesRatesApi = Mockito.mock(CurrenciesRatesApi::class.java, RETURNS_DEEP_STUBS)
        dataSource = CurrenciesNetworkDataSourceImpl(currenciesRatesApi)
    }

    override fun afterTest(testCase: TestCase, result: TestResult) {
        server.stop()
    }

    init {
        "when call to EUR based conversion rates response and response contains an empty rates map then throw exception" {
            val notFoundError = Response.error<CurrenciesResponseEntity>(
                404,
                ResponseBody.create(
                    MediaType.parse("json"),
                    "content"
                )
            )
            BDDMockito.`when`(currenciesRatesApi.getLatestRatesForCurrency().execute())
                .thenReturn(notFoundError)
            BDDMockito.`when`(currenciesRatesApi.getLatestRatesForCurrency().execute().isSuccessful)
                .thenReturn(false)
            BDDMockito.`when`(currenciesRatesApi.getLatestRatesForCurrency().execute().code())
                .thenReturn(404)
            val exception = shouldThrow<ResponseFailure.CurrencyNotFoundException> {
                dataSource.getCurrencies()
            }
            exception::class.java shouldBe ResponseFailure.CurrencyNotFoundException::class.java
        }
    }
}