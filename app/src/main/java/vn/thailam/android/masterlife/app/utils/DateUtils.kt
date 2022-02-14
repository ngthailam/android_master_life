package vn.thailam.android.masterlife.app.utils

import android.text.format.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateUtils {
    fun formatMyTime(date: Date): String {
        val cal = Calendar.getInstance()
        val currentDate = Date(System.currentTimeMillis())
        cal.time = currentDate
        val currentYear = cal.get(Calendar.YEAR)

        cal.time = date
        val year = cal.get(Calendar.YEAR)

        return if (currentYear != year) {
            DateFormat.format("MMM dd yyyy, hh:mm a", date).toString()
        } else {
            val diffInMillis = currentDate.time - date.time

            val format = when (TimeUnit.MILLISECONDS.toDays(diffInMillis).toInt()) {
                0 -> "hh:mm a"
                1 -> "Yesterday, hh:mm a"
                else -> "MMM dd, hh:mm a"
            }

            DateFormat.format(format, date).toString()
        }
    }
}