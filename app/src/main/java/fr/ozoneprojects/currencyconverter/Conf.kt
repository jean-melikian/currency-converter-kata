package fr.ozoneprojects.currencyconverter

class Conf {
    companion object {
        const val BASE_URL = "https://api.exchangeratesapi.io/"
        const val DEFAULT_BASE_CURRENCY: String = "EUR"
        const val EUR_CURRENCY_URI = "${BASE_URL}latest?base=EUR"
    }
}