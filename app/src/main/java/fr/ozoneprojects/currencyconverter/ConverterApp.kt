package fr.ozoneprojects.currencyconverter

import android.app.Application
import fr.ozoneprojects.currencyconverter.di.DaggerAppComponent

class ConverterApp : Application() {
    override fun onCreate() {
        super.onCreate()

        inject()
    }

    fun inject() {
        val appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
    }
}
