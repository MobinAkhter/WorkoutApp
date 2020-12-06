package sheridan.akhtemob.com.example.workoutapp.ui.common

import java.text.SimpleDateFormat
import java.util.*

object Store {

    fun getReadableDate(longDate: Long?): String {
        var toReturn = ""
        longDate?.let {
            val mFormat = SimpleDateFormat("EEEE MMM d, yyyy", Locale.ENGLISH)
            toReturn = mFormat.format(longDate)
        }
        return toReturn
    }

    fun getReadableTime(longDate: Long?): String {
        var toReturn = ""
        longDate?.let {
            val mFormat = SimpleDateFormat("h:mm a", Locale.ENGLISH)
            toReturn = mFormat.format(longDate)
        }
        return toReturn
    }

    fun convertStringToDoubleValue(str: String?): Double {
        var toReturn = 0.0
        try {
            str?.let {
                if (it.trim().isNotEmpty()) {
                    toReturn = it.trim().toDouble()
                }
            }
        } catch (e: Exception) {
            Logger.info("convertStringToDoubleValue() - $str - $e")
        }
        return toReturn
    }

    fun convertStringToIntValue(str: String?): Int {
        var toReturn = 0
        try {
            str?.let {
                if (it.trim().isNotEmpty()) {
                    toReturn = it.trim().toInt()
                }
            }
        } catch (e: Exception) {
            Logger.info("convertStringToIntValue() - $str - $e")
        }
        return toReturn
    }
}