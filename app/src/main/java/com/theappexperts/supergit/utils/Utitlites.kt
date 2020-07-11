package com.theappexperts.supergit.utils

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "Utitlites"

object Utitlites {
    fun runDelayForTesting(runDelay: Boolean, runWihDelayTesting: () -> Unit) {
        if (runDelay)
            GlobalScope.launch {
                delay(1000)
                runWihDelayTesting()
            }
        else
            runWihDelayTesting()
    }


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
                SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS", Locale.getDefault())
            timestamp = dateFormat.parse(this)?.time
        } catch (e: IOException) {
            Log.e(TAG, "convertToDate: ", e)
        } finally {
            return timestamp
        }

    }
}