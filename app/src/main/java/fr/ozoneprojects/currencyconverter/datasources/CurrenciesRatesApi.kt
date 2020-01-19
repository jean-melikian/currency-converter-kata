package fr.ozoneprojects.currencyconverter.datasources

import fr.ozoneprojects.currencyconverter.Conf
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrenciesRatesApi {
    @GET("latest")
    fun getLatestRatesForCurrency(@Query("base") currencyCode: String? = Conf.DEFAULT_BASE_CURRENCY): Call<CurrenciesResponseEntity>
}