package sheridan.akhtemob.com.example.workoutapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import sheridan.akhtemob.com.example.workoutapp.dataModels.JoggingEntryModel
import sheridan.akhtemob.com.example.workoutapp.dataModels.FreeWeightsEntryModel

@Dao
interface LocalRepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addJoggingEntry(model: JoggingEntryModel)

    @Query("Select * from ${DatabaseConstants.tableJoggingEntries} order by id DESC")
    fun getAllJoggingEntries(): LiveData<List<JoggingEntryModel>>

    @Query("Select * from ${DatabaseConstants.tableJoggingEntries} where id = :id")
    fun getJoggingEntryById(id: Int): LiveData<JoggingEntryModel?>

    @Query("Delete from " + DatabaseConstants.tableJoggingEntries + " where id = :id")
    fun deleteJoggingEntry(id: Int)

    @Query("update " + DatabaseConstants.tableJoggingEntries + " set date = :date, time = :time, distance = :distance, distanceUnit = :distanceUnit where id = :id")
    fun updateJoggingEntry(date: Long, time: Long, distance: Double, distanceUnit: String, id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFreeWeightsEntry(model: FreeWeightsEntryModel)

    @Query("Select * from ${DatabaseConstants.tableFreeWeightsEntries} order by id DESC")
    fun getAllFreeWeightsEntries(): LiveData<List<FreeWeightsEntryModel>>

    @Query("Select * from ${DatabaseConstants.tableFreeWeightsEntries} where id = :id")
    fun getJFreeWeightsEntryById(id: Int): LiveData<FreeWeightsEntryModel?>

    @Query("Delete from " + DatabaseConstants.tableFreeWeightsEntries + " where id = :id")
    fun deleteFreeWeightsEntryModel(id: Int)

    @Query("update " + DatabaseConstants.tableFreeWeightsEntries + " set date = :date, time = :time, reps = :reps, sets = :sets, weight = :weight where id = :id")
    fun updateFreeWeightsEntryModel(
        date: Long,
        time: Long,
        reps: Int,
        sets: Int,
        weight: Double,
        id: Int
    )
}