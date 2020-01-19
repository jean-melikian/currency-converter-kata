package fr.ozoneprojects.currencyconverter.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import fr.ozoneprojects.currencyconverter.Conf
import fr.ozoneprojects.currencyconverter.datasources.CurrenciesRatesApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
open class NetworkModule {
    @Provides
    @Singleton
    open fun provideGson(): Gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

    @Provides
    @Singleton
    open fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    open fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    open fun provideRetrofit(
        httpClient: OkHttpClient,
        gsonConverter: GsonConverterFactory
    ): Retrofit = Retrofit.Builder().apply {
        client(httpClient)
        baseUrl(Conf.BASE_URL)
        addConverterFactory(gsonConverter)
    }.build()

    @Provides
    @Singleton
    open fun provideCurrenciesRatesApi(retrofit: Retrofit): CurrenciesRatesApi =
        retrofit.create(CurrenciesRatesApi::class.java)
}