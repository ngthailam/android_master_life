package vn.thailam.android.masterlife.app.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import vn.thailam.android.masterlife.data.AppDatabase
import vn.thailam.android.masterlife.data.AppDatabaseBuilder
import vn.thailam.android.masterlife.data.dao.AuthDao
import vn.thailam.android.masterlife.data.dao.AuthDaoImpl
import vn.thailam.android.masterlife.data.repo.AuthRepo
import vn.thailam.android.masterlife.data.repo.AuthRepoImpl
import vn.thailam.android.masterlife.data.repo.PassRepo
import vn.thailam.android.masterlife.data.repo.PassRepoImpl
import vn.thailam.android.masterlife.data.repo.UtilityRepo
import vn.thailam.android.masterlife.data.repo.UtilityRepoImpl
import vn.thailam.android.masterlife.data.utils.PassSecurityStore
import vn.thailam.android.masterlife.data.utils.PassSecurityStoreImpl

val dbModule = module {
    single {
        AppDatabaseBuilder.buildRoomDB(androidApplication())
    }
}

val dataUtils = module {
    factory<PassSecurityStore> { PassSecurityStoreImpl() }
}

val daoModule = module {
    fun provideUtilityDao(database: AppDatabase) = database.utilityDao
    fun providePassDao(database: AppDatabase) = database.passDao

    factory { provideUtilityDao(get()) }
    factory { providePassDao(get()) }
    factory<AuthDao> { AuthDaoImpl() }
}

val dataSource = module {
}

val repoModule = module {
    factory<UtilityRepo> { UtilityRepoImpl(get()) }
    factory<AuthRepo> { AuthRepoImpl(get()) }
    factory<PassRepo> { PassRepoImpl(get(), get()) }
}


val dataModules = listOf(
    dataUtils,
    dbModule,
    daoModule,
    dataSource,
    repoModule
)