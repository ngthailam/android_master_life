package vn.thailam.android.masterlife.app.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import vn.thailam.android.masterlife.app.page.home.HomeViewModel
import vn.thailam.android.masterlife.app.page.passsaver.PassSaverViewModel
import vn.thailam.android.masterlife.app.page.splash.SplashViewModel

val utils = module {
}

val mapperModule = module {
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { PassSaverViewModel(get()) }
    viewModel { SplashViewModel(get(), get()) }
}

val appModules = listOf(
    utils,
    mapperModule,
    viewModelModule
)