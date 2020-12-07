package sheridan.akhtemob.com.example.workoutapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import sheridan.akhtemob.com.example.workoutapp.dataModels.JoggingEntryModel
import sheridan.akhtemob.com.example.workoutapp.database.LocalRepositoryDao
import sheridan.akhtemob.com.example.workoutapp.database.LocalRepositoryImpl

class JoggingEntriesViewModel : ViewModel() {

    private val localRepository: LocalRepositoryDao = LocalRepositoryImpl.get()

    fun getAllJoggingEntries() = localRepository.getAllJoggingEntries()

    fun addJoggingEntry(model: JoggingEntryModel) {
        localRepository.addJoggingEntry(model)
    }

    fun getJoggingEntryById(id: Int): LiveData<JoggingEntryModel?> {
        return localRepository.getJoggingEntryById(id)
    }

    fun deleteJoggingEntry(id: Int) {
        localRepository.deleteJoggingEntry(id)
    }

    fun updateJoggingEntry(
        date: Long,
        time: Long,
        distance: Double,
        distanceUnit: String,
        id: Int
    ) {
        localRepository.updateJoggingEntry(date, time, distance, distanceUnit, id)
    }

}