package sheridan.akhtemob.com.example.workoutapp

import android.app.Application
import sheridan.akhtemob.com.example.workoutapp.database.LocalRepositoryImpl

class WorkoutApp: Application() {

    override fun onCreate() {
        super.onCreate()

        LocalRepositoryImpl.initialize(this)
    }
}