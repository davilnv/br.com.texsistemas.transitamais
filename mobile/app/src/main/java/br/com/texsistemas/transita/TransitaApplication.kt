package br.com.texsistemas.transita

import android.app.Application
import br.com.texsistemas.transita.core.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent

class TransitaApplication : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        initKoin() {
            androidLogger()
            androidContext(this@TransitaApplication)
        }
    }
}