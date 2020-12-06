package sheridan.akhtemob.com.example.workoutapp.ui.common

import android.util.Log

object Logger {

    // Declarations

    private const val generalTag = "App"
    private const val emptyText = "Given Text is Empty"

    fun info(text: String?, tag: String = generalTag) {
        Log.i(tag, text?: emptyText)
    }
}