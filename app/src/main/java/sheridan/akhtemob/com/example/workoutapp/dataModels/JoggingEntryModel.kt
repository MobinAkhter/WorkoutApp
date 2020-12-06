package sheridan.akhtemob.com.example.workoutapp.dataModels

import androidx.room.Entity
import androidx.room.PrimaryKey
import sheridan.akhtemob.com.example.workoutapp.database.DatabaseConstants

@Entity(tableName = DatabaseConstants.tableJoggingEntries)
data class JoggingEntryModel(
    val date: Long,
    val time: Long,
    val distance: Double,
    val distanceUnit: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}