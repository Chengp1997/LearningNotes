package edu.vt.cs5254.dreamcatcher

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "dream")
data class Dream(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val title: String = "",
    val lastUpdated: Date = Date(),
) {
    @Ignore  // room can not directly store collections -- need another table
    var entries = listOf(DreamEntry(kind = DreamEntryKind.CONCEIVED, dreamId = id))
    val isFulfilled get() = entries.any { it.kind == DreamEntryKind.FULFILLED }
    val isDeferred get() = entries.any { it.kind == DreamEntryKind.DEFERRED }
    val photoFileName get() = "IMG_$id.JPG"
}

@Entity(tableName = "dream_entry")
data class DreamEntry(
    @PrimaryKey @ColumnInfo(name = "entryId") val id: UUID = UUID.randomUUID(),
    val text: String = "",
    val kind: DreamEntryKind,
    val dreamId: UUID
)

enum class DreamEntryKind {
    CONCEIVED,
    DEFERRED,
    FULFILLED,
    REFLECTION
}