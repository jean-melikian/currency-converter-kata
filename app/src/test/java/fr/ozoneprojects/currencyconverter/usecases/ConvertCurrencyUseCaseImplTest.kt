package fr.ozoneprojects.currencyconverter.usecases

import fr.ozoneprojects.currencyconverter.repository.CurrenciesRepository
import fr.ozoneprojects.currencyconverter.repository.Currency
import io.kotlintest.Spec
import io.kotlintest.TestCase
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import org.mockito.BDDMockito
import org.mockito.Mockito
import java.io.IOException
import java.math.BigDecimal

class ConvertCurrencyUseCaseImplTest : StringSpec() {

    private lateinit var converter: ConvertCurrencyUseCase

    private lateinit var repository: CurrenciesRepository

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        repository = Mockito.mock(CurrenciesRepository::class.java)
        converter = ConvertCurrencyUseCaseImpl(repository)
    }

    override fun beforeSpec(spec: Spec) {
        super.beforeSpec(spec)
    }

    init {
        "when passing non-positive EUR with empty currency should throw exception" {
            val exception = shouldThrow<IllegalArgumentException> {
                converter.convert(-1, "")
            }
            exception::class.java shouldBe IllegalArgumentException::class.java
        }

        "when passing 1 EUR with empty currency should throw exception" {
            val exception = shouldThrow<IllegalArgumentException> {
                converter.convert(1, "")
            }
            exception::class.java shouldBe IllegalArgumentException::class.java
        }

        "when passing 1 EUR with USD currency and network call fails then should throw exception" {
            BDDMockito.given(repository.getCurrencies()).willThrow(IOException::class.java)
            val exception = shouldThrow<IOException> {
                converter.convert(1, "USD")
            }
            exception::class.java shouldBe IOException::class.java
        }

        "when passing 1 EUR with USD currency and repository return empty list should throw exception" {
            BDDMockito.given(repository.getCurrencies()).willReturn(emptyList())
            val exception = shouldThrow<Exception> {
                converter.convert(1, "USD")
            }
            exception::class.java shouldBe Exception::class.java
        }

        "when passing 1 EUR with USD currency and repository that is not found in response then should throw exception" {
            BDDMockito.given(repository.getCurrencies())
                .willReturn(listOf(Currency("YEN", BigDecimal(0.002))))
            val exception = shouldThrow<Exception> {
                converter.convert(1, "USD")
            }
            exception::class.java shouldBe Exception::class.java
        }

        "when passing 1 EUR with USD currency and result factor is negative then should throw exception" {
            BDDMockito.given(repository.getCurrencies())
                .willReturn(listOf(Currency("USD", BigDecimal(-0.01))))
            val exception = shouldThrow<Exception> {
                converter.convert(1, "USD")
            }
            exception::class.java shouldBe Exception::class.java
        }

        "when passing 1 EUR with USD currency should return positive big decimal" {
            BDDMockito.given(repository.getCurrencies())
                .willReturn(listOf(Currency("USD", BigDecimal(1.1))))
            val result = converter.convert(1, "USD")
            result shouldBe BigDecimal(1.1)
        }
    }
}