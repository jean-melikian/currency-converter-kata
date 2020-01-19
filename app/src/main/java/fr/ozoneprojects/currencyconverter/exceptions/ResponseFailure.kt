package fr.ozoneprojects.currencyconverter.exceptions

import java.io.IOException

sealed class ResponseFailure {
    class UnreachableEndpointException : IOException()
    class CurrencyNotFoundException : Exception()
    class EmptyResponseException : Exception()
    class ErrorException : Exception()
}