package sheridan.akhtemob.com.example.workoutapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import sheridan.akhtemob.com.example.workoutapp.dataModels.FreeWeightsEntryModel
import sheridan.akhtemob.com.example.workoutapp.dataModels.JoggingEntryModel
import java.lang.IllegalStateException
import java.security.AccessControlContext
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LocalRepositoryImpl private constructor() : LocalRepositoryDao {

    companion object {
        private var INSTANCE: LocalRepositoryImpl? = null
        private lateinit var database: LocalDatabase
        private lateinit var executor: ExecutorService
        private lateinit var localRepositoryDao: LocalRepositoryDao

        fun initialize(context: Context) {
            if(INSTANCE == null) {
                INSTANCE = LocalRepositoryImpl()
                database = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    DatabaseConstants.databaseName
                ).fallbackToDestructiveMigration().build()
                localRepositoryDao = database.getDao()
                executor = Executors.newSingleThreadExecutor()
            }
        }

        fun get(): LocalRepositoryImpl {
            return INSTANCE ?: throw IllegalStateException("LocalRepositoryImpl must be initialized first.")
        }
    }

    override fun addJoggingEntry(model: JoggingEntryModel) {
        executor.execute {
            localRepositoryDao.addJoggingEntry(model)
        }
    }

    override fun getAllJoggingEntries(): LiveData<List<JoggingEntryModel>> {
        return localRepositoryDao.getAllJoggingEntries()
    }

    override fun getJoggingEntryById(id: Int): LiveData<JoggingEntryModel?> {
        return localRepositoryDao.getJoggingEntryById()
    }

    override fun deleteJoggingEntry(id: Int) {
        executor.execute{
            localRepositoryDao.deleteJoggingEntry(id)
        }
    }

    override fun updateJoggingEntry(
        date: Long,
        time: Long,
        distance: Double,
        distanceUnit: String,
        id: Int
    ) {
        executor.execute{
            localRepositoryDao.updateJoggingEntry(date, time, distance, distanceUnit, id)
        }
    }

    override fun addFreeWeightsEntry(model: FreeWeightsEntryModel) {
        executor.execute {
            localRepositoryDao.addFreeWeightsEntry(model)
        }
    }

    override fun getAllFreeWeightsEntries(): LiveData<List<FreeWeightsEntryModel>> {
        return localRepositoryDao.getAllFreeWeightsEntries()
    }

    override fun getJFreeWeightsEntryById(id: Int): LiveData<FreeWeightsEntryModel?> {
        return localRepositoryDao.getJFreeWeightsEntryById(id)
    }

    override fun deleteFreeWeightsEntryModel(id: Int) {
        executor.execute {
            localRepositoryDao.deleteFreeWeightsEntryModel(id)
        }
    }

    override fun updateFreeWeightsEntryModel(
        date: Long,
        time: Long,
        reps: Int,
        sets: Int,
        weight: Double,
        id: Int
    ) {
        executor.execute {
            localRepositoryDao.updateFreeWeightsEntryModel(date, time, reps, sets, weight, id)
        }
    }
}