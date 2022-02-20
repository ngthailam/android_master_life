package vn.thailam.android.masterlife.data

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import vn.thailam.android.masterlife.data.dao.InventoryDao
import vn.thailam.android.masterlife.data.dao.NoteDao
import vn.thailam.android.masterlife.data.dao.PassDao
import vn.thailam.android.masterlife.data.dao.RoomTypeDao
import vn.thailam.android.masterlife.data.dao.ScheduleDao
import vn.thailam.android.masterlife.data.dao.UtilityDao
import vn.thailam.android.masterlife.data.entity.Converters
import vn.thailam.android.masterlife.data.entity.InventoryEntity
import vn.thailam.android.masterlife.data.entity.NoteEntity
import vn.thailam.android.masterlife.data.entity.PassEntity
import vn.thailam.android.masterlife.data.entity.RoomTypeEntity
import vn.thailam.android.masterlife.data.entity.ScheduleEntity
import vn.thailam.android.masterlife.data.entity.UtilityEntity

const val DB_VERSION = 2

@Database(
    exportSchema = true,
    entities = [UtilityEntity::class, PassEntity::class, NoteEntity::class, ScheduleEntity::class, InventoryEntity::class, RoomTypeEntity::class],
    version = DB_VERSION,
    autoMigrations = [
    ]
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val utilityDao: UtilityDao
    abstract val passDao: PassDao
    abstract val noteDao: NoteDao
    abstract val scheduleDao: ScheduleDao
    abstract val inventoryDao: InventoryDao
    abstract val roomTypeDao: RoomTypeDao
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