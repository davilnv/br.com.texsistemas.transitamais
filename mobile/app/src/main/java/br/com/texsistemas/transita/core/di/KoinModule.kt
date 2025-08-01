package br.com.texsistemas.transita.core.di

import androidx.room.Room
import br.com.texsistemas.transita.data.local.AppDatabase
import br.com.texsistemas.transita.data.repository.PontoOnibusRepository
import br.com.texsistemas.transita.data.repository.PontoOnibusRepositoryImpl
import br.com.texsistemas.transita.data.repository.UsuarioLocalizacaoRepository
import br.com.texsistemas.transita.data.repository.UsuarioLocalizacaoRepositoryImpl
import br.com.texsistemas.transita.data.repository.UsuarioRepository
import br.com.texsistemas.transita.data.repository.UsuarioRepositoryImpl
import br.com.texsistemas.transita.data.repository.VeiculoRepository
import br.com.texsistemas.transita.data.repository.VeiculoRepositoryImpl
import br.com.texsistemas.transita.domain.service.UsuarioService
import br.com.texsistemas.transita.data.web.TransitaRemoteDataSource
import br.com.texsistemas.transita.data.web.TransitaRemoteDataSourceImpl
import br.com.texsistemas.transita.domain.service.PontoOnibusService
import br.com.texsistemas.transita.domain.service.UsuarioLocalizacaoService
import br.com.texsistemas.transita.domain.service.VeiculoService
import br.com.texsistemas.transita.domain.usecase.GetPontoOnibusUseCase
import br.com.texsistemas.transita.domain.usecase.GetUsuarioLocalizacaoUseCase
import br.com.texsistemas.transita.domain.usecase.GetVeiculoUseCase
import br.com.texsistemas.transita.domain.usecase.LoginUseCase
import br.com.texsistemas.transita.presentation.viewmodel.home.HomeViewModel
import br.com.texsistemas.transita.presentation.viewmodel.login.LoginViewModel
import br.com.texsistemas.transita.presentation.viewmodel.ponto_onibus.PontoOnibusViewModel
import br.com.texsistemas.transita.presentation.viewmodel.veiculo.VeiculoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin() {
        config?.invoke(this)
        modules(
            provideDataSourceModule,
            provideRepositoryModule,
            provideUseCaseModule,
            provideViewModelModule,
            provideDatabaseModule
        )
    }

val provideDataSourceModule = module {
    singleOf(::TransitaRemoteDataSourceImpl).bind(TransitaRemoteDataSource::class)
}

val provideRepositoryModule = module {
    singleOf(::UsuarioRepositoryImpl).bind(UsuarioRepository::class)
    singleOf(::UsuarioLocalizacaoRepositoryImpl).bind(UsuarioLocalizacaoRepository::class)
    single { PontoOnibusRepositoryImpl(get(), get()) }.bind(PontoOnibusRepository::class)
    single { VeiculoRepositoryImpl(get(), get()) }.bind(VeiculoRepository::class)
}

val provideUseCaseModule = module {
    singleOf(::UsuarioService).bind(LoginUseCase::class)
    singleOf(::UsuarioLocalizacaoService).bind(GetUsuarioLocalizacaoUseCase::class)
    singleOf(::PontoOnibusService).bind(GetPontoOnibusUseCase::class)
    singleOf(::VeiculoService).bind(GetVeiculoUseCase::class)
}

val provideViewModelModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::PontoOnibusViewModel)
    viewModelOf(::VeiculoViewModel)
}

val provideDatabaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "transita_database"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }
    single { get<AppDatabase>().pontoOnibusDao() }
    single { get<AppDatabase>().veiculoDao() }
}