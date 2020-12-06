package sheridan.akhtemob.com.example.workoutapp.dataModels

import sheridan.akhtemob.com.example.workoutapp.database.DatabaseConstants
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DatabaseConstants.tableFreeWeightsEntries)
data class FreeWeightsEntryModel(
    val date: Long,
    val time: Long,
    val reps: Int,
    val sets: Int,
    val weight: Double
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}