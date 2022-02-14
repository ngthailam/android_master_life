package vn.thailam.android.masterlife.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

const val NOTE_TBL_NAME = "note"
const val NOTE_PRIMARY_KEY = "id"
const val NOTE_COL_TITLE = "title"
const val NOTE_COL_DESC = "desc"
const val NOTE_COL_CREATED_AT = "created_at"

@Parcelize
@Entity(tableName = NOTE_TBL_NAME)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = NOTE_PRIMARY_KEY)
    val id: Int? = null,
    @ColumnInfo(name = NOTE_COL_TITLE)
    val title: String? = null,
    @ColumnInfo(name = NOTE_COL_DESC)
    val desc: String? = null,
    @ColumnInfo(name = NOTE_COL_CREATED_AT)
    val createdAt: Date = Date(System.currentTimeMillis())
) : Parcelable

