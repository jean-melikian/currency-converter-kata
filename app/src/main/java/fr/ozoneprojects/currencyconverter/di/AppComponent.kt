package fr.ozoneprojects.currencyconverter.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import fr.ozoneprojects.currencyconverter.ConverterApp
import fr.ozoneprojects.currencyconverter.di.modules.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun inject(app: ConverterApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
