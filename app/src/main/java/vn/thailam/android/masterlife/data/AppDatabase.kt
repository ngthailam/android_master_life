package vn.thailam.android.masterlife.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vn.thailam.android.masterlife.data.dao.PassDao
import vn.thailam.android.masterlife.data.dao.UtilityDao
import vn.thailam.android.masterlife.data.entity.PassEntity
import vn.thailam.android.masterlife.data.entity.UtilityEntity

const val DB_VERSION = 3

@Database(
    exportSchema = false,
    entities = [UtilityEntity::class, PassEntity::class],
    version = DB_VERSION
)
abstract class AppDatabase : RoomDatabase() {
    abstract val utilityDao: UtilityDao
    abstract val passDao: PassDao
}

object AppDatabaseBuilder {

    private const val DB_NAME = "app-db"

    fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
}