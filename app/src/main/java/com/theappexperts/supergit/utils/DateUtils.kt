package com.theappexperts.supergit.utils

import android.util.Log
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "DateUtils"
object DateUtils {
    fun getDateAsHeaderId(milliseconds: Long): Long {
        val dateFormat = SimpleDateFormat("ddMMyyyy", Locale.getDefault())
        return dateFormat.format(Date(milliseconds)).toLong()
    }

    fun getDate(milliseconds: Long): String? {
        val dateFormat =
            SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return dateFormat.format(Date(milliseconds))
    }

    fun String.convertToDate(): Long? {

        var timestamp: Long? = null
        try {
            val dateFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            timestamp = dateFormat.parse(this)?.time
        } catch (e: IOException) {
            Log.e(TAG, "convertToDate: ", e)
        } finally {
            return timestamp
        }

    }

    fun getDateTime(milliseconds: Long): String? {
        val dateFormat =
            SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.getDefault())
        return dateFormat.format(Date(milliseconds))
    }
    fun parseStringToDate(stringDate: String?): Date? {
        val formatter = SimpleDateFormat("dd MMMM yyyy hh:mm")
        var date: Date? = null
        date = try {
            formatter.parse(stringDate)
        } catch (e: ParseException) {
            return null
        }
        return date
    }
    fun parseStringToMillis(stringDate: String?): Long {
        val date: Date = parseStringToDate(stringDate) ?: return 0
        return date.time
    }
}