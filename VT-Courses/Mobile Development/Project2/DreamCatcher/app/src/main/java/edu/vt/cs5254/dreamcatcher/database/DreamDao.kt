package edu.vt.cs5254.dreamcatcher.database

import androidx.room.*
import edu.vt.cs5254.dreamcatcher.Dream
import edu.vt.cs5254.dreamcatcher.DreamEntry
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface DreamDao {
    //using flow: step1 - make it emit a flow of dreams
    //flow will automatically emit the updated data to the collector ,keep them sync with db
    @Query("SELECT * FROM dream d JOIN dream_entry e ON e.dreamId = d.id ORDER BY d.lastUpdated DESC")
    fun getDreams(): Flow<Map<Dream, List<DreamEntry>>>

    //get all dreams without entries
    @Query("SELECT * FROM dream WHERE id=(:id)")
    suspend fun getDream(id: UUID): Dream

    //get all entries according to dream's id
    @Query("SELECT * FROM dream_entry where dreamId = (:id)")
    suspend fun getEntriesForDream(id: UUID): List<DreamEntry>

    //combine up two
    @Transaction
    suspend fun getDreamAndEntries(id: UUID): Dream {
        return getDream(id).apply { entries = getEntriesForDream(id) }
    }


    //update dream
    @Update
    suspend fun updateDream(dream: Dream)

    //if want to update, need to copy the old entries, therefore, should use insert
    @Insert
    suspend fun insertDreamEntry(dreamEntry: DreamEntry)

    @Query("DELETE FROM dream_entry where dreamId = (:id)")
    suspend fun deleteEntriesFromDream(id: UUID)

    //the fun that we gonna use, update dream and entries at the same time
    @Transaction
    suspend fun updateDreamAndEntries(dream: Dream) {
        deleteEntriesFromDream(dream.id)
        dream.entries.forEach { insertDreamEntry(it) }
        updateDream(dream)
    }

    //insert dream
    @Insert
    suspend fun insertDream(dream: Dream)

    @Transaction
    suspend fun insertDreamAndEntries(dream: Dream) {
        insertDream(dream)
        dream.entries.forEach { insertDreamEntry(it) }
    }

    //delete dream
    @Delete
    suspend fun deleteDream(dream: Dream)

    @Transaction
    suspend fun deleteDreamAndEntries(dream: Dream) {
        deleteEntriesFromDream(dream.id)
        deleteDream(dream)
    }

}