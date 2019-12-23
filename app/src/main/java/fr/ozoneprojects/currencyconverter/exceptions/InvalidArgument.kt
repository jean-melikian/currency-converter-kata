package fr.ozoneprojects.currencyconverter.exceptions

sealed class InvalidArgument {
    class CurrencyCodeException : IllegalArgumentException()
    class AmountException : IllegalArgumentException()
}

