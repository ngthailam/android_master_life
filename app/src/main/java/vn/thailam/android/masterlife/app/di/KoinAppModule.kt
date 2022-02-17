package vn.thailam.android.masterlife.app.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import vn.thailam.android.masterlife.app.page.home.HomeViewModel
import vn.thailam.android.masterlife.app.page.inventory.InventoryViewModel
import vn.thailam.android.masterlife.app.page.inventory.create.InventoryCreateViewModel
import vn.thailam.android.masterlife.app.page.inventory.create.room.RoomChooserViewModel
import vn.thailam.android.masterlife.app.page.note.detail.NoteDetailViewModel
import vn.thailam.android.masterlife.app.page.note.list.NoteViewModel
import vn.thailam.android.masterlife.app.page.passsaver.list.PassSaverViewModel
import vn.thailam.android.masterlife.app.page.passsaver.create.PassSaverCreateViewModel
import vn.thailam.android.masterlife.app.page.splash.SplashViewModel

val utils = module {
}

val mapperModule = module {
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { PassSaverViewModel(get()) }
    viewModel { SplashViewModel(get(), get()) }
    viewModel { PassSaverCreateViewModel(get()) }
    viewModel { NoteViewModel(get()) }
    viewModel { NoteDetailViewModel(get()) }
    viewModel { InventoryViewModel(get()) }
    viewModel { InventoryCreateViewModel(get()) }
    viewModel { RoomChooserViewModel(get()) }
}

val appModules = listOf(
    utils,
    mapperModule,
    viewModelModule
)