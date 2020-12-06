package sheridan.akhtemob.com.example.workoutapp.viewModels

import androidx.lifecycle.LiveData
import sheridan.akhtemob.com.example.workoutapp.dataModels.FreeWeightsEntryModel
import sheridan.akhtemob.com.example.workoutapp.database.LocalRepositoryDao
import sheridan.akhtemob.com.example.workoutapp.database.LocalRepositoryImpl

class FreeWeightsEntriesViewModel {
    private val localRepository: LocalRepositoryDao = LocalRepositoryImpl.get()

    fun getAllFreeWeightsEntries() = localRepository.getAllFreeWeightsEntries()

    fun addFreeWeightsEntry(model: FreeWeightsEntryModel) {
        localRepository.addFreeWeightsEntry(model)
    }

    fun getJFreeWeightsEntryById(id: Int): LiveData<FreeWeightsEntryModel?> {
        return localRepository.getJFreeWeightsEntryById(id)
    }
    fun deleteFreeWeightsEntryModel(id: Int) {
        localRepository.deleteFreeWeightsEntryModel(id)
    }
    fun updateFreeWeightsEntryModel(
        date: Long,
        time: Long,
        reps: Int,
        sets: Int,
        weight: Double,
        id: Int
    ) {
        localRepository.updateFreeWeightsEntryModel(date, time, reps, sets, weight, id)
    }
}