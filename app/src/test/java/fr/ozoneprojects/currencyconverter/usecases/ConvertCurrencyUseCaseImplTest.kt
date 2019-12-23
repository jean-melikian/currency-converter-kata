package fr.ozoneprojects.currencyconverter.usecases

import fr.ozoneprojects.currencyconverter.exceptions.ConversionError
import fr.ozoneprojects.currencyconverter.exceptions.InvalidArgument
import fr.ozoneprojects.currencyconverter.exceptions.ResponseFailure
import fr.ozoneprojects.currencyconverter.repository.CurrenciesRepository
import fr.ozoneprojects.currencyconverter.repository.Currency
import io.kotlintest.TestCase
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import org.mockito.BDDMockito
import org.mockito.Mockito
import java.math.BigDecimal

class ConvertCurrencyUseCaseImplTest : StringSpec() {

    private lateinit var converter: ConvertCurrencyUseCase

    private lateinit var repository: CurrenciesRepository

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        repository = Mockito.mock(CurrenciesRepository::class.java)
        converter = ConvertCurrencyUseCaseImpl(repository)
    }

    init {
        "when passing non-positive EUR with empty currency should throw exception" {
            val exception = shouldThrow<InvalidArgument.AmountException> {
                converter.convert(-1, "")
            }
            exception::class.java shouldBe InvalidArgument.AmountException::class.java
        }

        "when passing 1 EUR with empty currency should throw exception" {
            val exception = shouldThrow<InvalidArgument.CurrencyCodeException> {
                converter.convert(1, "")
            }
            exception::class.java shouldBe InvalidArgument.CurrencyCodeException::class.java
        }

        "when passing 1 EUR with USD currency and network call fails then should throw exception" {
            BDDMockito.given(repository.getCurrencies())
                .willThrow(ResponseFailure.UnreachableEndpointException::class.java)
            val exception = shouldThrow<ResponseFailure.UnreachableEndpointException> {
                converter.convert(1, "USD")
            }
            exception::class.java shouldBe ResponseFailure.UnreachableEndpointException::class.java
        }

        "when passing 1 EUR with USD currency and repository return empty list should throw exception" {
            BDDMockito.given(repository.getCurrencies()).willReturn(emptyList())
            val exception = shouldThrow<ResponseFailure.EmptyResponseException> {
                converter.convert(1, "USD")
            }
            exception::class.java shouldBe ResponseFailure.EmptyResponseException::class.java
        }

        "when passing 1 EUR with USD currency and repository that is not found in response then should throw exception" {
            BDDMockito.given(repository.getCurrencies())
                .willReturn(listOf(Currency("YEN", BigDecimal(0.002))))
            val exception = shouldThrow<ResponseFailure.CurrencyNotFoundException> {
                converter.convert(1, "USD")
            }
            exception::class.java shouldBe ResponseFailure.CurrencyNotFoundException::class.java
        }

        "when passing 1 EUR with USD currency and result factor is negative then should throw exception" {
            BDDMockito.given(repository.getCurrencies())
                .willReturn(listOf(Currency("USD", BigDecimal(-0.01))))
            val exception = shouldThrow<ConversionError.InvalidConvertFactorException> {
                converter.convert(1, "USD")
            }
            exception::class.java shouldBe ConversionError.InvalidConvertFactorException::class.java
        }

        "when passing 1 EUR with USD currency should return positive big decimal" {
            BDDMockito.given(repository.getCurrencies())
                .willReturn(listOf(Currency("USD", BigDecimal(1.1))))
            val result = converter.convert(1, "USD")
            result shouldBe BigDecimal(1.1)
        }
    }
}