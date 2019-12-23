package fr.ozoneprojects.currencyconverter.exceptions

sealed class ConversionError {
    class InvalidConvertFactorException : Exception()
    class InvalidConversionException: Exception()
}